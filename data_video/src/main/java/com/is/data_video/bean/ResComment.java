package com.is.data_video.bean;

import java.util.Objects;

public class ResComment {

    /**
     * id : 550
     * user_id : 86
     * pid : 0
     * content : lllllll
     * comments : 0
     * createtime : 1770736131
     * user : {"id":86,"nickname":"年薪2w","avatar":"https://titok.fzqq.fun/uploads/20260220/bf817925df5fbcb6c51cb8e66c865d68.jpg","bio":"道阻且长，行且将至","email":"","url":"/u/86"}
     * create_date : 1周前
     */

    private int id;
    private int user_id;
    private int pid;
    private String content;
    private int comments;
    private int createtime;
    private UserBean user;
    private String create_date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public int getCreatetime() {
        return createtime;
    }

    public void setCreatetime(int createtime) {
        this.createtime = createtime;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public static class UserBean {
        /**
         * id : 86
         * nickname : 年薪2w
         * avatar : https://titok.fzqq.fun/uploads/20260220/bf817925df5fbcb6c51cb8e66c865d68.jpg
         * bio : 道阻且长，行且将至
         * email :
         * url : /u/86
         */

        private int id;
        private String nickname;
        private String avatar;
        private String bio;
        private String email;
        private String url;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getBio() {
            return bio;
        }

        public void setBio(String bio) {
            this.bio = bio;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ResComment that = (ResComment) o;
        return id == that.id && user_id == that.user_id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user_id);
    }
}
