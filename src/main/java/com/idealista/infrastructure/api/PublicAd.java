package com.idealista.infrastructure.api;

import java.util.List;
import java.util.Objects;

public class PublicAd {

  private Integer id;
  private String typology;
  private String description;
  private List<String> pictureUrls;
  private Integer houseSize;
  private Integer gardenSize;

  public PublicAd(Integer id, String typology, String description, List<String> pictureUrls, Integer houseSize, Integer gardenSize) {
    this.id = id;
    this.typology = typology;
    this.description = description;
    this.pictureUrls = pictureUrls;
    this.houseSize = houseSize;
    this.gardenSize = gardenSize;
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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    PublicAd publicAd = (PublicAd) o;
    return Objects.equals(id, publicAd.id) &&
      Objects.equals(typology, publicAd.typology) &&
      Objects.equals(description, publicAd.description) &&
      Objects.equals(pictureUrls, publicAd.pictureUrls) &&
      Objects.equals(houseSize, publicAd.houseSize) &&
      Objects.equals(gardenSize, publicAd.gardenSize);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, typology, description, pictureUrls, houseSize, gardenSize);
  }

  @Override
  public String toString() {
    return "PublicAd{" +
      "id=" + id +
      ", typology='" + typology + '\'' +
      ", description='" + description + '\'' +
      ", pictureUrls=" + pictureUrls +
      ", houseSize=" + houseSize +
      ", gardenSize=" + gardenSize +
      '}';
  }

  public static class PublicAdBuilder {

    private Integer id;
    private String typology;
    private String description;
    private List<String> pictureUrls;
    private Integer houseSize;
    private Integer gardenSize;

    public PublicAdBuilder setId(Integer id) {
      this.id = id;
      return this;
    }

    public PublicAdBuilder setTypology(String typology) {
      this.typology = typology;
      return this;
    }

    public PublicAdBuilder setDescription(String description) {
      this.description = description;
      return this;
    }

    public PublicAdBuilder setPictureUrls(List<String> pictureUrls) {
      this.pictureUrls = pictureUrls;
      return this;
    }

    public PublicAdBuilder setHouseSize(Integer houseSize) {
      this.houseSize = houseSize;
      return this;
    }

    public PublicAdBuilder setGardenSize(Integer gardenSize) {
      this.gardenSize = gardenSize;
      return this;
    }

    public PublicAd build() {
      return new PublicAd(id, typology, description, pictureUrls, houseSize, gardenSize);
    }
  }
}
