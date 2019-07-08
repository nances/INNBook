package com.kaqi.niuniu.ireader.model.bean;


import com.kaqi.niuniu.ireader.App;

import q.rorbin.badgeview.DisplayUtil;

public class AdConfigBean {
    private Property property;
    private int type;

    public static class Property {
        public int type;
        public int width = -1;
        public int height = DisplayUtil.dp2px(App.getContext(), 260);
        public int startOffset;
        public int startAdLine = -1;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Property getProperty(int type) {
        if (property == null) {
            switch (type) {

            }
            property = new Property();
            property.type = type;
        }
        return property;
    }
}
