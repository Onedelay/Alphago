package com.alphago.alphago.dto;

/**
 * Created by su_me on 2018-02-04.
 */

public class ResponeImageLabel {
    private String category;
    private String max_label;
    private String ko;
    private String ja;
    private String zh_CN;
    private int LABEL_ID;
    private int CAT_ID;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getResponseLabel() {
        return max_label;
    }

    public String getKo() {
        return ko;
    }

    public void setResponseLabel(String max_label) {
        this.max_label = max_label;
    }

    public int getLABEL_ID() {
        return LABEL_ID;
    }

    public int getCAT_ID() {
        return CAT_ID;
    }

    public String getJa() {
        return ja;
    }

    public String getZh_CN() {
        return zh_CN;
    }
}
