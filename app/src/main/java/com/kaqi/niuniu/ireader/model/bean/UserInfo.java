package com.kaqi.niuniu.ireader.model.bean;


public class UserInfo {

    private String nickName;

    private String userImg;

    private String userPhone;

    private String token;

    private int gender;

    private String open_id;

    private String money_total;

    private String money_today;

    public UserInfo() {
        super();
    }

    public UserInfo(String nickName, String userImg, String userPhone, String token, int gender, String open_id, String money_total, String money_today) {
        this.nickName = nickName;
        this.userImg = userImg;
        this.userPhone = userPhone;
        this.token = token;
        this.gender = gender;
        this.open_id = open_id;
        this.money_total = money_total;
        this.money_today = money_today;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getOpen_id() {
        return open_id;
    }

    public void setOpen_id(String open_id) {
        this.open_id = open_id;
    }

    public String getMoney_total() {
        return money_total;
    }

    public void setMoney_total(String money_total) {
        this.money_total = money_total;
    }

    public String getMoney_today() {
        return money_today;
    }

    public void setMoney_today(String money_today) {
        this.money_today = money_today;
    }
}
