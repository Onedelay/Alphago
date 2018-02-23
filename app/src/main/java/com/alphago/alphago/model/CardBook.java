package com.alphago.alphago.model;

public class CardBook {
    private String name;
    private String category;
    private int imageResourceId;

    public CardBook(String name, String category, int imageResourceId) {
        this.name = name;
        this.category = category;
        this.imageResourceId = imageResourceId;
    }

    public String getName() {
        return name;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public String getCategory() {
        return category;
    }
}
