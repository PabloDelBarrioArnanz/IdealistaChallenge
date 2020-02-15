package com.idealista.infrastructure.api;

import com.idealista.application.QualityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AdsController {

    public QualityService qualityService;

    @Autowired
    public AdsController(QualityService qualityService) {
        this.qualityService = qualityService;
    }

    @GetMapping("quality/list")
    public ResponseEntity<List<QualityAd>> qualityListing() {
        return new ResponseEntity<>(qualityService.getQualityListing(), HttpStatus.OK);
    }

    @GetMapping("public/list")
    public ResponseEntity<List<PublicAd>> publicListing() {
        return new ResponseEntity<>(qualityService.getPublicListing(), HttpStatus.OK);
    }

    @GetMapping("calculate")
    public ResponseEntity<Void> calculateScore() {
        qualityService.calculateScore();
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
