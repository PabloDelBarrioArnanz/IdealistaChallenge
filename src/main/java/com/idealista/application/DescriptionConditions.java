package com.idealista.application;

import com.idealista.infrastructure.api.QualityAd;

import java.util.function.Predicate;
import java.util.stream.Stream;

import static com.idealista.application.BusinessConstants.*;
import static com.idealista.application.BusinessPredicates.*;

public enum DescriptionConditions {

  GARAGE_ANY_DESCRIPTION(isGarage.and(hasDescription), descriptionAdScore),

  FLAT_MAX_DESCRIPTION(isFlat.and(hasMaxDescription), maxDescriptionAdScoreFlat + descriptionAdScore),
  FLAT_MED_DESCRIPTION(isFlat.and(hasMedDescription), medDescriptionAdScore + descriptionAdScore),
  FLAT_DESCRIPTION(isFlat.and(hasDescription), descriptionAdScore),

  CHALET_MAX_DESCRIPTION(isChalet.and(hasMaxDescription), maxDescriptionAdScoreChalet + descriptionAdScore),
  CHALET_MED_DESCRIPTION(isChalet.and(hasMedDescription), medDescriptionAdScore + descriptionAdScore),
  CHALET_DESCRIPTION(isChalet.and(hasDescription), descriptionAdScore);

  private final Predicate<QualityAd> condition;
  private final Integer score;

  DescriptionConditions(Predicate<QualityAd> condition, Integer score) {
    this.condition = condition;
    this.score = score;
  }

  public static Integer getScoreForDescription(QualityAd ad) {
    return Stream.of(DescriptionConditions.values())
      .filter(giveCase -> giveCase.condition.test(ad))
      .findAny()
      .map(successCase -> successCase.score)
      .orElse(ZeroScore);
  }
}
