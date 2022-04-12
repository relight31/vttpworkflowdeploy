package com.example.mockassessment.calculator.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.example.mockassessment.calculator.services.JsonProcessingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonObject;

@RestController
@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class CalculatorController {
    Logger logger = Logger.getLogger(CalculatorController.class.getName());
    @Autowired
    private JsonProcessingService jService;

    @PostMapping(path = "/calculate")
    public ResponseEntity<String> getResult(
            @RequestBody String payload,
            @RequestHeader(name = "User-Agent") String userAgent) {
        try {
            Optional<Integer> optional = jService.getResult(payload);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
            Date date = new Date(System.currentTimeMillis());
            JsonObject result = Json.createObjectBuilder()
                    .add("result", optional.get())
                    .add("timestamp", formatter.format(date))
                    .add("userAgent", userAgent)
                    .build();
            logger.log(Level.INFO, result.toString());
            return ResponseEntity.ok(result.toString());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }

    }
}
