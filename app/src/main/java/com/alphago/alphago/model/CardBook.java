package com.alphago.alphago.model;

public class CardBook {
    private long id;
    private String name;
    private long categoryId;
    private String category;
    private String filePath;

    public CardBook(long id, String name, long categoryId, String category, String filePath) {
        this.id = id;
        this.name = name;
        this.categoryId = categoryId;
        this.category = category;
        this.filePath = filePath;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public String getCategory() {
        return category;
    }

    public String getFilePath() {
        return filePath;
    }
}
