package com.example.mockassessment.calculator;

import com.example.mockassessment.calculator.controllers.CalculatorController;
import com.example.mockassessment.calculator.services.CalculatorService;
import com.example.mockassessment.calculator.services.JsonProcessingService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BeanCreationTest {
    @Autowired
    CalculatorController controller;

    @Autowired
    JsonProcessingService jService;

    @Autowired
    CalculatorService calculatorService;

    @Test
    void BeanCreationTests() {
        Assertions.assertNotNull(controller);
        Assertions.assertNotNull(jService);
        Assertions.assertNotNull(calculatorService);
    }

}
