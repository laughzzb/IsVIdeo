package com.is.feature_piaza.bean;

import java.util.List;

public class ResPlaza {

    private  String type;
    private List<PlazaDetail> lists;

    public List<PlazaDetail> getLists() {
        return lists;
    }

    public static class PlazaDetail{
        /**
         * id : 28
         * name : 剧情
         * image : http://ali-img.kaiyanapp.com/945fa937f0955b31224314a4eeef59b8.jpeg?image_process=image/auto-orient
         * icon : http://ali-img.kaiyanapp.com/a7da9dca491c75f750a96c329a179789.png
         * description : 用一个好故事，描绘生活的不可思议
         * url : /cms/28.html
         * fullurl : https://titok.fzqq.fun/cms/28.html
         */


        private int id;
        private String name;
        private String image;
        private String icon;
        private String description;
        private String url;
        private String fullurl;
        /**
         * title : 一览众山小！
         * images : ["http://ali-img.kaiyanapp.com/305033974/0-e483e9c473aa4908521bbe53af214456.jpeg?image_process=image/auto-orient,1/resize,w_480/format,webp/interlace,1/quality,q_80","http://ali-img.kaiyanapp.com/302209431/9c40f4d0392212e3cecb34b9e84aa595.png?image_process=image/auto-orient,1/resize,w_480/format,webp/interlace,1/quality,q_80","https://i0.hdslb.com/bfs/archive/47c9efd5ccb88d4f1651824a06a04c67738d6d54.jpg"]
         * author : 李白2
         * avatar : https://titok.fzqq.fun/uploads/20240829/e4ed3f7b987c92f77f1ffb45cf1e41ad.png
         * cover : http://ali-img.kaiyanapp.com/305033974/0-e483e9c473aa4908521bbe53af214456.jpeg?image_process=image/auto-orient,1/resize,w_480/format,webp/interlace,1/quality,q_80
         */

        private String title;
        private String author;
        private String avatar;
        private String cover;
        private List<String> images;

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

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }
    }

}
