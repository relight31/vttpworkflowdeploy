package com.example.mockassessment.calculator.services;

import org.springframework.stereotype.Service;

@Service
public class CalculatorService {

    private int plus(int n1, int n2) {
        return n1 + n2;
    }

    private int minus(int n1, int n2) {
        return n1 - n2;
    }

    private int times(int n1, int n2) {
        return n1 * n2;
    }

    private int divide(int n1, int n2) {
        return n1 / n2;
    }

    public int getResult(int n1, int n2, String oper) {
        if (oper.equals("plus")) {
            return this.plus(n1, n2);
        } else if (oper.equals("minus")) {
            return this.minus(n1, n2);
        } else if (oper.equals("times")) {
            return this.times(n1, n2);
        } else if (oper.equals("divide")){
            return this.divide(n1, n2);
        } else{
            throw new IllegalArgumentException("invalid operator");
        }
    }
}
