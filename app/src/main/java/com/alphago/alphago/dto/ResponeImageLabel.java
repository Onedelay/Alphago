package com.alphago.alphago.dto;

/**
 * Created by su_me on 2018-02-04.
 */

public class ResponeImageLabel {
    private String category;
    private String max_label;
    private int ID;
    private int cate_ID;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getResponseLabel() {
        return max_label;
    }

    public void setResponseLabel(String max_label) {
        this.max_label = max_label;
    }

    public int getID() {
        return ID;
    }

    public int getCate_ID() {
        return cate_ID;
    }
}
