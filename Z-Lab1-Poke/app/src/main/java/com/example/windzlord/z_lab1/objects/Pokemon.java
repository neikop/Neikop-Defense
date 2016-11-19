package com.example.windzlord.z_lab1.objects;

/**
 * Created by WindzLord on 11/17/2016.
 */

public class Pokemon {
    private int id;
    private String name;
    private String tag;
    private int gene;
    private String image;
    private String color;

    public Pokemon(int id, String name, String tag, int gene, String image, String color) {
        this.id = id;
        this.name = name;
        this.tag = tag;
        this.gene = gene;
        this.image = image;
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTag() {
        return tag;
    }

    public int getGene() {
        return gene;
    }

    public String getImage() {
        return image;
    }

    public String getColor() {
        return color;
    }
}
