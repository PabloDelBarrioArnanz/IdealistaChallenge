package com.idealista.application;

import com.idealista.infrastructure.api.PublicAd;
import com.idealista.infrastructure.api.QualityAd;
import com.idealista.infrastructure.persistence.AdVO;
import org.springframework.stereotype.Service;

import static com.idealista.application.BusinessConstants.ZeroScore;

@Service
public class Mapper {

  public QualityAd toQualityAd(AdVO addVo) {
    return new QualityAd.QualityAdBuilder()
      .setId(addVo.getId())
      .setDescription(addVo.getDescription())
      .setGardenSize(addVo.getGardenSize())
      .setHouseSize(addVo.getHouseSize())
      .setTypology(addVo.getTypology())
      .setIrrelevantSince(addVo.getIrrelevantSince())
      .setScore(ZeroScore)
      .build();
  }

  public PublicAd toPublicAd(QualityAd ad) {
    return new PublicAd.PublicAdBuilder()
      .setId(ad.getId())
      .setDescription(ad.getDescription())
      .setGardenSize(ad.getGardenSize())
      .setHouseSize(ad.getHouseSize())
      .setTypology(ad.getTypology())
      .setPictureUrls(ad.getPictureUrls())
      .build();
  }

}
