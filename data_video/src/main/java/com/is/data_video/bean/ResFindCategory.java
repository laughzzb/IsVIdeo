package com.is.data_video.bean;

import android.os.Parcelable;

public class ResFindCategory implements Parcelable {
        /**
         * id : 25
         * name : 广告
         * image : https://ali-img.kaiyanapp.com/23d1a1dce9756535d314aed3cf9777a0.jpeg?image_process=image/auto-orient
         * icon : http://ali-img.kaiyanapp.com/c13345e2c2e812ef4e179a4eac2b81f1.png
         * description : 为广告人的精彩创意点赞
         * url : /cms/25.html
         * fullurl : https://titok.fzqq.fun/cms/25.html
         */

        private int id;
        private String name;
        private String image;
        private String icon;
        private String description;
        private String url;
        private String fullurl;
    private int people;//参与人数
    private int browse;//浏览人数

    public int getBrowse() {
        return browse;
    }

    public int getPeople() {
        return people;
    }

    public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getFullurl() {
            return fullurl;
        }

        public void setFullurl(String fullurl) {
            this.fullurl = fullurl;
        }


    public ResFindCategory() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.image);
        dest.writeString(this.icon);
        dest.writeString(this.description);
        dest.writeString(this.url);
        dest.writeString(this.fullurl);
    }

    protected ResFindCategory(android.os.Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.image = in.readString();
        this.icon = in.readString();
        this.description = in.readString();
        this.url = in.readString();
        this.fullurl = in.readString();
    }

    public static final Creator<ResFindCategory> CREATOR = new Creator<ResFindCategory>() {
        @Override
        public ResFindCategory createFromParcel(android.os.Parcel source) {
            return new ResFindCategory(source);
        }

        @Override
        public ResFindCategory[] newArray(int size) {
            return new ResFindCategory[size];
        }
    };
}

