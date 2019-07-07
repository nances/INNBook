package com.kaqi.niuniu.ireader.model.bean;

import java.util.List;

public class CategoryList extends Base {


    private List<MaleBean> male;
    private List<FemaleBean> female;
    private List<PictureBean> picture;
    private List<PressBean> press;

    public List<MaleBean> getMale() {
        return male;
    }

    public void setMale(List<MaleBean> male) {
        this.male = male;
    }

    public List<FemaleBean> getFemale() {
        return female;
    }

    public void setFemale(List<FemaleBean> female) {
        this.female = female;
    }

    public List<PictureBean> getPicture() {
        return picture;
    }

    public void setPicture(List<PictureBean> picture) {
        this.picture = picture;
    }

    public List<PressBean> getPress() {
        return press;
    }

    public void setPress(List<PressBean> press) {
        this.press = press;
    }

    public static class MaleBean {
        /**
         * name : 玄幻
         * bookCount : 578299
         * monthlyCount : 21965
         * icon : /icon/玄幻_.png
         * bookCover : ["/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F2107590%2F2107590_718bb20f6bba4ee2910cf30d51882112.jpg%2F","/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F1228859%2F1228859_b7114fbfc2a44969ac2879fcec1d6bea.jpg%2F","/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F41584%2F41584_873f1d8b2f7a447a8c59f3573094db1b.jpg%2F"]
         */

        private String name;
        private int bookCount;
        private int monthlyCount;
        private String icon;
        private List<String> bookCover;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getBookCount() {
            return bookCount;
        }

        public void setBookCount(int bookCount) {
            this.bookCount = bookCount;
        }

        public int getMonthlyCount() {
            return monthlyCount;
        }

        public void setMonthlyCount(int monthlyCount) {
            this.monthlyCount = monthlyCount;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public List<String> getBookCover() {
            return bookCover;
        }

        public void setBookCover(List<String> bookCover) {
            this.bookCover = bookCover;
        }
    }

    public static class FemaleBean {
        /**
         * name : 古代言情
         * bookCount : 598908
         * monthlyCount : 16124
         * icon : /icon/古代言情_.png
         * bookCover : ["/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F2045755%2F2045755_d19a460229b148d4b6a09fd38e70c90f.jpg%2F","/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F1158081%2F1158081_a3d108156a4a4c088d87225fb27e0131.jpg%2F","/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F885176%2F885176_47041b9643a14361898febb8d2c803e6.jpg%2F"]
         */

        private String name;
        private int bookCount;
        private int monthlyCount;
        private String icon;
        private List<String> bookCover;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getBookCount() {
            return bookCount;
        }

        public void setBookCount(int bookCount) {
            this.bookCount = bookCount;
        }

        public int getMonthlyCount() {
            return monthlyCount;
        }

        public void setMonthlyCount(int monthlyCount) {
            this.monthlyCount = monthlyCount;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public List<String> getBookCover() {
            return bookCover;
        }

        public void setBookCover(List<String> bookCover) {
            this.bookCover = bookCover;
        }
    }

    public static class PictureBean {
        /**
         * name : 热血
         * bookCount : 905
         * monthlyCount : 151
         * icon : /icon/热血_.png
         * bookCover : ["/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F168407%2F168407_749f2be713ea464193b3b4d8e314e00a.jpg%2F","/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F2225872%2F2225872_67c9061a735f4846bb4cc98b7dd56026.png%2F","/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F3343604%2F3343604_90d4d71b21ed48cc832c793c3685615b.png%2F"]
         */

        private String name;
        private int bookCount;
        private int monthlyCount;
        private String icon;
        private List<String> bookCover;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getBookCount() {
            return bookCount;
        }

        public void setBookCount(int bookCount) {
            this.bookCount = bookCount;
        }

        public int getMonthlyCount() {
            return monthlyCount;
        }

        public void setMonthlyCount(int monthlyCount) {
            this.monthlyCount = monthlyCount;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public List<String> getBookCover() {
            return bookCover;
        }

        public void setBookCover(List<String> bookCover) {
            this.bookCover = bookCover;
        }
    }

    public static class PressBean {
        /**
         * name : 传记名著
         * bookCount : 8306
         * monthlyCount : 494
         * icon : /icon/传记名著_.png
         * bookCover : ["/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F42798%2F42798_72627be42841400d89e32bf8db97ec34.jpg%2F","/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F2281875%2F2281875_db2c743f0d8d41eca45c46528d876b54.jpg%2F","/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F1209327%2F1209327_fd3412af2a534a3eb970432860e1d2bc.jpg%2F"]
         */

        private String name;
        private int bookCount;
        private int monthlyCount;
        private String icon;
        private List<String> bookCover;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getBookCount() {
            return bookCount;
        }

        public void setBookCount(int bookCount) {
            this.bookCount = bookCount;
        }

        public int getMonthlyCount() {
            return monthlyCount;
        }

        public void setMonthlyCount(int monthlyCount) {
            this.monthlyCount = monthlyCount;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public List<String> getBookCover() {
            return bookCover;
        }

        public void setBookCover(List<String> bookCover) {
            this.bookCover = bookCover;
        }
    }
}
