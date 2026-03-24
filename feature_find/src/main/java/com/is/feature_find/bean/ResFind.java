package com.is.feature_find.bean;

import com.is.data_video.bean.ResFindCategory;

import java.util.List;

public class ResFind {
        private List<ResFindCategory> category;
        private List<ResFindAnchor> anchor;
        private List<ResFindTopic> topic;

        public List<ResFindCategory> getCategory() {
            return category;
        }

        public void setCategory(List<ResFindCategory> category) {
            this.category = category;
        }

        public List<ResFindAnchor> getAnchor() {
            return anchor;
        }

        public void setAnchor(List<ResFindAnchor> anchor) {
            this.anchor = anchor;
        }

        public List<ResFindTopic> getTopic() {
            return topic;
        }


        public void setTopic(List<ResFindTopic> topic) {
            this.topic = topic;
        }


}
