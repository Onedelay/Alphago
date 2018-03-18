package com.alphago.alphago.model;

public class CollectCategory {
    private long id;
    private String label;
    private String filePath;
    private boolean select;
    private float achievementRate;

    public CollectCategory(long id, String category, String filePath, float achievementRate) {
        this.id = id;
        this.label = category;
        this.filePath = filePath;
        this.select = false;
        this.achievementRate = achievementRate;
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

    public float getAchievementRate() {
        return achievementRate;
    }
}
