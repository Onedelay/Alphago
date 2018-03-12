package com.alphago.alphago.model;

/**
 * Created by su_me on 2018-03-11.
 */

public class Collection {
    private String name;
    private long labelId;
    private String filePath;
    private int collected;

    public Collection(String name, long labelId, String filePath, int collected) {
        this.name = name;
        this.labelId = labelId;
        this.filePath = filePath;
        this.collected = collected;
    }

    public String getName() {
        return name;
    }

    public long getLabelId() {
        return labelId;
    }

    public String getFilePath() {
        return filePath;
    }

    public int getCollected() {
        return collected;
    }
}
