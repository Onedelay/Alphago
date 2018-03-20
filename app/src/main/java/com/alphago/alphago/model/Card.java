package com.alphago.alphago.model;

/**
 * Created by sclab on 2018-02-26.
 */

public class Card {
    private long id;
    private long imgId;
    private String filePath;
    private String label;

    public Card(long id, long imgId, String filePath, String label) {
        this.id = id;
        this.imgId = imgId;
        this.filePath = filePath;
        this.label = label;
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

    public String getLabel() {
        return label;
    }
}
