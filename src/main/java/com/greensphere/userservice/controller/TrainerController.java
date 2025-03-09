package com.greensphere.userservice.controller;

import com.greensphere.userservice.service.TrainerService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/trainer")
public class TrainerController {

    private TrainerService trainerService;

    public TrainerController(TrainerService trainerService) {

    }

}

