package com.alphago.alphago.dto;

public class ResponseRequestResult {
    private String category;
    private String ko;
    private String en;
    private String ja;
    private String zh_CN;
    private int LABEL_ID;
    private int CAT_ID;

    public String getCategory() {
        return category;
    }

    public String getKo() {
        return ko;
    }

    public String getEn() {
        return en;
    }

    public String getJa() {
        return ja;
    }

    public String getZh_CN() {
        return zh_CN;
    }

    public int getLABEL_ID() {
        return LABEL_ID;
    }

    public int getCAT_ID() {
        return CAT_ID;
    }
}
