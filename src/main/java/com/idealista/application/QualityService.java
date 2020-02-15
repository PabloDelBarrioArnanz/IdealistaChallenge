package com.idealista.application;

import com.idealista.infrastructure.api.PublicAd;
import com.idealista.infrastructure.api.QualityAd;
import com.idealista.infrastructure.persistence.AdVO;
import com.idealista.infrastructure.persistence.InMemoryPersistence;
import com.idealista.infrastructure.persistence.PictureVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.idealista.application.BusinessConstants.*;
import static com.idealista.application.BusinessPredicates.hasKeyWords;
import static com.idealista.application.BusinessPredicates.isHD;

@Service
public class QualityService {

  private InMemoryPersistence persistence;
  private Mapper mapper;
  private List<QualityAd> qualityAds;

  @Autowired
  public QualityService(InMemoryPersistence persistence, Mapper mapper) {
    this.persistence = persistence;
    this.mapper = mapper;
    this.qualityAds = Collections.emptyList();
  }


  public List<QualityAd> getQualityListing() {
    return qualityAds
      .parallelStream()
      .collect(Collectors.toList());
  }

  public List<PublicAd> getPublicListing() {
    return qualityAds
      .stream()
      .filter(QualityAd::isRelevant)
      .map(mapper::toPublicAd)
      .collect(Collectors.toList());
  }

  public void calculateScore() {
    qualityAds = persistence.getAds()
      .stream()
      .map(ad -> calculateScore(ad, persistence.getPictures()))
      .sorted(Comparator.comparingInt(QualityAd::getScore).reversed())
      .collect(Collectors.toList());
  }

  private QualityAd calculateScore(AdVO ad, List<PictureVO> pictures) {
    QualityAd qualityAd = mapper.toQualityAd(ad);
    qualityAd.setPictureUrls(getURLOfPictures(ad.getPictures(), pictures));
    Optional.of(ad)
      .map(giveAd -> calculatePicturesScore(giveAd, pictures))
      .map(score -> sumScore(qualityAd, score))
      .map(this::calculateDescriptionScore)
      .map(score -> sumScore(qualityAd, score))
      .map(this::calculateComplete)
      .map(score -> sumScore(qualityAd, score))
      .map(this::containsKeyWords)
      .map(score -> sumScore(qualityAd, score))
      .filter(QualityAd::isIIrrelevant)
      .ifPresent(givenAd -> givenAd.setIrrelevantSince(new Date()));

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
    return ConditionsDescription.getScoreForDescription(ad);
  }

  private Integer containsKeyWords(QualityAd ad) {
    return Optional.of(ad)
      .map(QualityAd::getDescription)
      .filter(hasKeyWords)
      .map(score -> keyWordsScore)
      .orElse(ZeroScore);
  }

  private Integer calculateComplete(QualityAd ad) {
    return ConditionsComplete.getScoreForComplete(ad);
  }

  private List<PictureVO> getPictures(List<Integer> pictures, List<PictureVO> picturesVo) {
    return pictures.stream()
      .map(persistence::getById)
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

  private QualityAd sumScore(QualityAd ad, Integer score) {
    ad.setScore(ad.getScore() + score);
    return ad;
  }

}
