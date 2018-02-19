package com.alphago.alphago.dto;

/**
 * Created by su_me on 2018-02-04.
 */

public class ResponeImageLabel {
    private String category;
    private String max_label;

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
}
