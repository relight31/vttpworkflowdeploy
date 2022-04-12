package com.example.mockassessment.calculator.services;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

@Service
public class JsonProcessingService {

    @Autowired
    private CalculatorService calculatorService;

    public Optional<Integer> getResult(String jsonString) {
        try (InputStream is = new ByteArrayInputStream(jsonString.getBytes())) {
            JsonReader reader = Json.createReader(is);
            JsonObject data = reader.readObject();
            int n1 = data.getInt("oper1");
            int n2 = data.getInt("oper2");
            String oper = data.getString("ops");
            return Optional.of(calculatorService.getResult(n1, n2, oper));
        } catch (IOException e) {
            return Optional.empty();
        }
    }
}
