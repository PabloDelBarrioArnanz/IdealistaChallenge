package com.idealista.infrastructure.api;

import java.util.Date;
import java.util.List;

public class QualityAd {

    private Integer id;
    private String typology;
    private String description;
    private List<String> pictureUrls;
    private Integer houseSize;
    private Integer gardenSize;
    private Integer score;
    private Date irrelevantSince;

  public QualityAd(Integer id, String typology, String description, List<String> pictureUrls, Integer houseSize, Integer gardenSize, Integer score, Date irrelevantSince) {
    this.id = id;
    this.typology = typology;
    this.description = description;
    this.pictureUrls = pictureUrls;
    this.houseSize = houseSize;
    this.gardenSize = gardenSize;
    this.score = score;
    this.irrelevantSince = irrelevantSince;
  }

  public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTypology() {
        return typology;
    }

    public void setTypology(String typology) {
        this.typology = typology;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getPictureUrls() {
        return pictureUrls;
    }

    public void setPictureUrls(List<String> pictureUrls) {
        this.pictureUrls = pictureUrls;
    }

    public Integer getHouseSize() {
        return houseSize;
    }

    public void setHouseSize(Integer houseSize) {
        this.houseSize = houseSize;
    }

    public Integer getGardenSize() {
        return gardenSize;
    }

    public void setGardenSize(Integer gardenSize) {
        this.gardenSize = gardenSize;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Date getIrrelevantSince() {
        return irrelevantSince;
    }

    public void setIrrelevantSince(Date irrelevantSince) {
        this.irrelevantSince = irrelevantSince;
    }

    public boolean isRelevant() {
      return score > 40;
    }

    public boolean isIIrrelevant() {
      return !isRelevant();
    }

    public static class QualityAdBuilder {

      private Integer id;
      private String typology;
      private String description;
      private List<String> pictureUrls;
      private Integer houseSize;
      private Integer gardenSize;
      private Integer score;
      private Date irrelevantSince;

      public QualityAdBuilder setId(Integer id) {
        this.id = id;
        return this;
      }

      public QualityAdBuilder setTypology(String typology) {
        this.typology = typology;
        return this;
      }

      public QualityAdBuilder setDescription(String description) {
        this.description = description;
        return this;
      }

      public QualityAdBuilder setPictureUrls(List<String> pictureUrls) {
        this.pictureUrls = pictureUrls;
        return this;
      }

      public QualityAdBuilder setHouseSize(Integer houseSize) {
        this.houseSize = houseSize;
        return this;
      }

      public QualityAdBuilder setGardenSize(Integer gardenSize) {
        this.gardenSize = gardenSize;
        return this;
      }

      public QualityAdBuilder setScore(Integer score) {
        this.score = score;
        return this;
      }

      public QualityAdBuilder setIrrelevantSince(Date irrelevantSince) {
        this.irrelevantSince = irrelevantSince;
        return this;
      }

      public QualityAd build() {
        return new QualityAd(id, typology, description, pictureUrls, houseSize, gardenSize, score, irrelevantSince);
      }
    }
}
