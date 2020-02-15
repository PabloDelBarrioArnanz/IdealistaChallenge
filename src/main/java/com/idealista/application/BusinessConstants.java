package com.idealista.application;

import java.util.Arrays;
import java.util.List;

public class BusinessConstants {

  public static String HD = "HD";
  public static String FLAT = "FLAT";
  public static String GARAGE = "GARAGE";
  public static String CHALET = "CHALET";
  public static List<String> keyWords = Arrays.asList("Luminoso", "Nuevo", "Céntrico", "Reformado", "Ático");
  public static Integer completeAdScore = 40;
  public static Integer descriptionAdScore = 5;
  public static Integer medDescriptionAdScore = 10;
  public static Integer maxDescriptionAdScoreFlat = 30;
  public static Integer maxDescriptionAdScoreChalet = 20;
  public static Integer pictureHDScore = 20;
  public static Integer pictureSDScore = 10;
  public static Integer noPicturesScore = -10;
  public static Integer keyWordsScore = 5;
  public static Integer ZeroScore = 0;

}
