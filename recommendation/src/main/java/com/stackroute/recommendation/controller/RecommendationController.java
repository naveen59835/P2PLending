package com.stackroute.recommendation.controller;

import com.stackroute.recommendation.domain.RequestModel;
import com.stackroute.recommendation.repository.RecommendationRepository;
import com.stackroute.recommendation.service.recommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/recommendation")
public class RecommendationController {
    @Autowired
  recommendationService recommendationService;



    @PostMapping("/add")
    public ResponseEntity<?> savePerson(@RequestBody RequestModel requestModel){
        return new ResponseEntity<>(recommendationService.SavePerson(requestModel), HttpStatus.OK);
    }

    @GetMapping("/get/{score}")
    public ResponseEntity getAllBorrowers(@PathVariable String score)
    {
        return  new ResponseEntity<>(recommendationService.getAllBorrower(score),HttpStatus.OK);
    }

}
