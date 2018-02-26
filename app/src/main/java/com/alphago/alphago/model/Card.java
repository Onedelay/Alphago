package com.alphago.alphago.model;

/**
 * Created by sclab on 2018-02-26.
 */

public class Card {
    private long id;
    private long imgId;
    private String filePath;

    public Card(long id, long imgId, String filePath) {
        this.id = id;
        this.imgId = imgId;
        this.filePath = filePath;
    }

    public long getId() {
        return id;
    }

    public long getImgId() {
        return imgId;
    }

    public String getFilePath() {
        return filePath;
    }
}
