package com.alphago.alphago.model;

public class CardBook {
    private String name;
    private int imageResourceId;

    public CardBook(String name, int imageResourceId) {
        this.name = name;
        this.imageResourceId = imageResourceId;
    }

    public String getName() {
        return name;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }
}
