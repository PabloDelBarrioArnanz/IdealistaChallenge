package com.idealista.application;

import com.idealista.infrastructure.api.QualityAd;

import java.util.function.Predicate;
import java.util.stream.Stream;

import static com.idealista.application.BusinessConstants.*;
import static com.idealista.application.BusinessPredicates.*;

public enum ConditionsDescription {

  FLAT_MAX_DESCRIPTION(isFlat.and(hasMaxDescription), maxDescriptionAdScoreFlat),
  FLAT_MED_DESCRIPTION(isFlat.and(hasMedDescription), medDescriptionAdScore),
  FLAT_DESCRIPTION(isFlat.and(hasDescription), descriptionAdScore),

  CHALET_MAX_DESCRIPTION(isChalet.and(hasMaxDescription), maxDescriptionAdScoreChalet),
  CHALET_MED_DESCRIPTION(isChalet.and(hasMedDescription), medDescriptionAdScore),
  CHALET_DESCRIPTION(isChalet.and(hasDescription), descriptionAdScore);

  private final Predicate<QualityAd> condition;
  private final Integer score;

  ConditionsDescription(Predicate<QualityAd> condition, Integer score) {
    this.condition = condition;
    this.score = score;
  }

  public static Integer getScoreForDescription(QualityAd ad) {
    return Stream.of(ConditionsDescription.values())
      .filter(giveCase -> giveCase.condition.test(ad))
      .findAny()
      .map(successCase -> successCase.score)
      .orElse(ZeroScore);
  }
}
