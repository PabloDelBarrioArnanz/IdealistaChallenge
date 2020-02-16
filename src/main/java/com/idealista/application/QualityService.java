package com.idealista.application;

import com.idealista.infrastructure.api.PublicAd;
import com.idealista.infrastructure.api.QualityAd;
import com.idealista.infrastructure.persistence.AdVO;
import com.idealista.infrastructure.persistence.InMemoryPersistence;
import com.idealista.infrastructure.persistence.PictureVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.idealista.application.BusinessConstants.*;
import static com.idealista.application.BusinessPredicates.*;
import static java.util.concurrent.CompletableFuture.supplyAsync;

@Service
public class QualityService {

  private InMemoryPersistence persistence;
  private Mapper mapper;
  private List<QualityAd> qualityAds;
  private Executor executor;

  @Autowired
  public QualityService(InMemoryPersistence persistence, Mapper mapper) {
    this.persistence = persistence;
    this.mapper = mapper;
    this.qualityAds = Collections.emptyList();
    this.executor = Executors.newFixedThreadPool(14);
  }

  public List<QualityAd> getQualityListing() {
    return Optional.of(qualityAds)
      .filter(List::isEmpty)
      .map(list -> calculateScore())
      .orElse(qualityAds);
  }

  public List<PublicAd> getPublicListing() {
    return Optional.of(qualityAds)
      .filter(List::isEmpty)
      .map(list -> calculateScore())
      .orElse(qualityAds)
      .stream()
      .filter(isRelevant)
      .map(mapper::toPublicAd)
      .collect(Collectors.toList());
  }

  public List<QualityAd> calculateScore() {
    qualityAds = persistence.getAds()
      .parallelStream()
      .map(ad -> calculateScore(ad, persistence.getPictures()))
      .sorted(Comparator.comparingInt(QualityAd::getScore)
        .reversed())
      .collect(Collectors.toList());
    return qualityAds;
  }

  private QualityAd calculateScore(AdVO ad, List<PictureVO> pictures) {
    QualityAd qualityAd = mapper.toQualityAd(ad);
    qualityAd.setPictureUrls(getURLOfPictures(ad.getPictures(), pictures));

    //PARALLEL
    CompletableFuture<Integer> picturesScore = supplyAsync(() -> calculatePicturesScore(ad, pictures), executor);
    CompletableFuture<Integer> descriptionScore = supplyAsync(() -> calculateDescriptionScore(qualityAd), executor);
    CompletableFuture<Integer> completeScore = supplyAsync(() -> calculateComplete(qualityAd), executor);
    CompletableFuture<Integer> keyWordsScore = supplyAsync(() -> containsKeyWords(qualityAd), executor);

    Stream.of(picturesScore, descriptionScore, completeScore, keyWordsScore)
      .map(CompletableFuture::join)
      .mapToInt(Integer::intValue)
      .reduce(Integer::sum)
      .ifPresent(finalScore -> {
        qualityAd.setScore(finalScore);
        Optional.of(qualityAd)
          .filter(isIrrelevant)
          .ifPresent(givenAd -> givenAd.setIrrelevantSince(new Date()));
      });

    //SEQUENTIAL
    /*Optional.of(ad)
      .map(giveAd -> calculatePicturesScore(giveAd, pictures))
      .map(score -> sumScore(qualityAd, score))
      .map(this::calculateDescriptionScore)
      .map(score -> sumScore(qualityAd, score))
      .map(this::calculateComplete)
      .map(score -> sumScore(qualityAd, score))
      .map(this::containsKeyWords)
      .map(score -> sumScore(qualityAd, score))
      .filter(isIrrelevant)
      .ifPresent(givenAd -> givenAd.setIrrelevantSince(new Date()));*/

    return qualityAd;
  }

  private Integer calculatePicturesScore(AdVO ad, List<PictureVO> pictures) {
    int picturesScore = getQualityOfPictures(ad.getPictures(), pictures)
      .stream()
      .map(quality -> Optional.of(quality)
        .filter(isHD)
        .map(score -> pictureHDScore)
        .orElse(pictureSDScore))
      .mapToInt(Integer::intValue)
      .sum();

    return Optional.of(picturesScore)
      .filter(score -> score != 0)
      .orElse(noPicturesScore);

  }

  private Integer calculateDescriptionScore(QualityAd ad) {
    return DescriptionConditions.getScoreForDescription(ad);
  }

  private Integer containsKeyWords(QualityAd ad) {
    return Optional.of(ad)
      .filter(hasDescription.and(hasKeyWords))
      .map(score -> keyWordsScore)
      .orElse(ZeroScore);
  }

  private Integer calculateComplete(QualityAd ad) {
    return CompleteConditions.getScoreForComplete(ad);
  }

  private List<PictureVO> getPictures(List<Integer> pictures, List<PictureVO> picturesVo) {
    return pictures.stream()
      .map(this::getById)
      .collect(Collectors.toList());
  }

  private List<String> getQualityOfPictures(List<Integer> pictures, List<PictureVO> picturesVo) {
    return getPictures(pictures, picturesVo)
      .stream()
      .map(PictureVO::getQuality)
      .collect(Collectors.toList());
  }

  private List<String> getURLOfPictures(List<Integer> pictures, List<PictureVO> picturesVo) {
    return getPictures(pictures, picturesVo)
      .stream()
      .map(PictureVO::getUrl)
      .collect(Collectors.toList());
  }

  public PictureVO getById(Integer id) {
    return persistence.getPictures()
      .stream()
      .filter(pictureVO -> pictureVO.getId().equals(id))
      .findFirst()
      .orElseThrow(() -> new RuntimeException("Integrity error"));
  }

  private QualityAd sumScore(QualityAd ad, Integer score) {
    ad.setScore(ad.getScore() + score);
    return ad;
  }
}
