package com.example.windzlord.brainfuck.objects.models;

/**
 * Created by Ha San~ on 12/31/2016.
 */

public class Calculator {

    private int id;
    private String calculator;
    private int result;

    public Calculator(int id, String calculator, int result, int level) {
        this.id = id;
        this.calculator = calculator;
        this.result = result;
    }

    public Calculator() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCalculator() {
        return calculator;
    }

    public void setCalculator(String calculator) {
        this.calculator = calculator;
    }

    public int getResult() {
        return result;
    }
}
