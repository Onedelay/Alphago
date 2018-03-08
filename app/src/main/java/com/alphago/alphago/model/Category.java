package com.alphago.alphago.model;

public class Category {
    private long id;
    private String label;
    private String filePath;
    private boolean select;

    public Category(long id, String category, String filePath) {
        this.id = id;
        this.label = category;
        this.filePath = filePath;
        this.select = false;
    }

    public long getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public String getFilePath() {
        return filePath;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }
}
