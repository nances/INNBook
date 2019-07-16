package com.kaqi.niuniu.ireader.model.bean;

/**
 * Created by niqiao on 2019年07月16日16:28:00.
 */

public class MessageBean {
    public String messageInfo;
    public String messageTime;
    public String messageTitle;


    public MessageBean() {
    }

    public MessageBean(String messageInfo, String messageTime, String messageTitle) {
        this.messageInfo = messageInfo;
        this.messageTime = messageTime;
        this.messageTitle = messageTitle;
    }

    public String getMessageInfo() {
        return messageInfo;
    }

    public void setMessageInfo(String messageInfo) {
        this.messageInfo = messageInfo;
    }

    public String getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(String messageTime) {
        this.messageTime = messageTime;
    }

    public String getMessageTitle() {
        return messageTitle;
    }

    public void setMessageTitle(String messageTitle) {
        this.messageTitle = messageTitle;
    }
}

