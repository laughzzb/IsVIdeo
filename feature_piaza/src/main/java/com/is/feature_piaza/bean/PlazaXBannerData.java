package com.is.feature_piaza.bean;

import com.stx.xhb.androidx.entity.BaseBannerInfo;

public class PlazaXBannerData implements BaseBannerInfo {

    private String imgUrl;
    private String title;
    private String description;
    @Override
    public String getXBannerUrl() {
        return imgUrl;
    }

    @Override
    public String getXBannerTitle() {
        return title;
    }

    public PlazaXBannerData(String imgUrl, String title, String description) {
        this.imgUrl = imgUrl;
        this.title = title;
        this.description = description;
    }

    public String getDescription(){
        return description;
    }
}
