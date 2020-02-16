package com.idealista.infrastructure.api;

import com.idealista.application.QualityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.logging.Logger;

@RestController
public class AdsController {

  private final static Logger logger = Logger.getGlobal();
  public QualityService qualityService;

  @Autowired
  public AdsController(QualityService qualityService) {
    this.qualityService = qualityService;
  }

  @GetMapping("quality/ads")
  public ResponseEntity<List<QualityAd>> qualityListing() {
    Instant origin = Instant.now();
    List<QualityAd> qualityListing = qualityService.getQualityListing();
    logger.info("Time => " + Duration.between(origin, Instant.now()).toMillis() + " ms");
    return new ResponseEntity<>(qualityListing, HttpStatus.OK);
  }

  @GetMapping("/ads")
  public ResponseEntity<List<PublicAd>> publicListing() {
    Instant origin = Instant.now();
    List<PublicAd> publicAds = qualityService.getPublicListing();
    logger.info("Time => " + Duration.between(origin, Instant.now()).toMillis() + " ms");
    return new ResponseEntity<>(publicAds, HttpStatus.OK);
  }

  //Not necessary
  @GetMapping("calculate")
  public ResponseEntity<Void> calculateScore() {
    qualityService.calculateScore();
    return new ResponseEntity<>(HttpStatus.ACCEPTED);
  }
}
