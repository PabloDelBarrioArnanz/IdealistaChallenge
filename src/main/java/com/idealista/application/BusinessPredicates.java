package com.idealista.application;

import com.idealista.infrastructure.api.QualityAd;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

import static com.idealista.application.BusinessConstants.*;

public class BusinessPredicates {

  public static Predicate<QualityAd> hasDescription = given -> !Objects.isNull(given.getDescription()) && !given.getDescription().isEmpty();
  public static Predicate<QualityAd> hasMaxDescription = hasDescription.and(given -> given.getDescription().length() > 50);
  public static Predicate<QualityAd> hasMedDescription = hasDescription.and(given -> given.getDescription().length() < 49 && given.getDescription().length() > 20);


  public static Predicate<QualityAd> isFlat = given -> !Objects.isNull(given.getTypology()) && given.getTypology().equals(FLAT);
  public static Predicate<QualityAd> isGarage = given -> !Objects.isNull(given.getTypology()) && given.getTypology().equals(GARAGE);
  public static Predicate<QualityAd> isChalet = given -> !Objects.isNull(given.getTypology()) && given.getTypology().equals(CHALET);


  public static Predicate<QualityAd> hasURLPictures = given -> !Objects.isNull(given.getPictureUrls()) && !given.getPictureUrls().isEmpty();
  public static Predicate<String> isHD = given -> given.equals(HD);

  public static Predicate<QualityAd> isRelevant = given -> given.getScore() > 40;
  public static Predicate<QualityAd> isIrrelevant = isRelevant.negate();

  public static Predicate<QualityAd> hasHouseSize = given ->
    Optional.ofNullable(given.getHouseSize())
      .filter(size -> size > 0)
      .map(result -> Boolean.TRUE)
      .orElse(Boolean.FALSE);

  public static Predicate<QualityAd> hasGardenSize = given ->
    Optional.ofNullable(given.getGardenSize())
      .filter(size -> size > 0)
      .map(result -> Boolean.TRUE)
      .orElse(Boolean.FALSE);

  public static Predicate<QualityAd> hasKeyWords = given ->
    Optional.of(given)
      .map(QualityAd::getDescription)
      .map(description -> Arrays.stream(description.split(" "))
        .distinct()
        .map(String::toUpperCase)
        .filter(keyWords::contains)
        .findFirst()
        .map(result -> Boolean.TRUE)
        .orElse(Boolean.FALSE))
      .orElse(Boolean.FALSE);
}