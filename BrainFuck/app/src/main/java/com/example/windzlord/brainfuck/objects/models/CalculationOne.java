package com.example.windzlord.brainfuck.objects.models;

/**
 * Created by Ha San~ on 12/31/2016.
 */

public class CalculationOne {
    private int id;
    private String calculation;
    private int results;
    private int levels;

    public CalculationOne(int id, String calculation, int results, int levels) {
        this.id = id;
        this.calculation = calculation;
        this.results = results;
        this.levels = levels;
    }

    public CalculationOne() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCalculation() {
        return calculation;
    }

    public void setCalculation(String calculation) {
        this.calculation = calculation;
    }

    public int getResults() {
        return results;
    }

    public void setResults(int results) {
        this.results = results;
    }

    public int getLevels() {
        return levels;
    }

    public void setLevels(int levels) {
        this.levels = levels;
    }
}
