package com.example.windzlord.brainfuck.objects.models;

/**
 * Created by Ha San~ on 12/31/2016.
 */

public class Calculation {
    private int id;
    private String calculation;
    private int results;

    public Calculation(int id, String calculation, int results, int levels) {
        this.id = id;
        this.calculation = calculation;
        this.results = results;
    }

    public Calculation() {

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
}
