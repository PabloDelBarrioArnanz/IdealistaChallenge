package com.idealista.application;

import com.idealista.infrastructure.api.QualityAd;

import java.util.function.Predicate;
import java.util.stream.Stream;

import static com.idealista.application.BusinessConstants.ZeroScore;
import static com.idealista.application.BusinessConstants.completeAdScore;
import static com.idealista.application.BusinessPredicates.*;

public enum CompleteConditions {

  GARAGE(isGarage.and(hasURLPictures)),
  FLAT(isFlat.and(hasDescription).and(hasHouseSize).and(hasURLPictures)),
  CHALET(isChalet.and(hasDescription).and(hasHouseSize).and(hasGardenSize).and(hasURLPictures));

  private final Predicate<QualityAd> condition;

  CompleteConditions(Predicate<QualityAd> condition) {
    this.condition = condition;
  }

  public static Integer getScoreForComplete(QualityAd ad) {
    return Stream.of(CompleteConditions.values())
      .filter(giveCase -> giveCase.condition.test(ad))
      .findAny()
      .map(score -> completeAdScore)
      .orElse(ZeroScore);
  }
}
