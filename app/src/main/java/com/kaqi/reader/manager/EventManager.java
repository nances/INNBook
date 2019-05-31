package com.kaqi.reader.manager;

import com.kaqi.reader.bean.support.RefreshCollectionIconEvent;
import com.kaqi.reader.bean.support.RefreshCollectionListEvent;
import com.kaqi.reader.bean.support.ShareEvent;
import com.kaqi.reader.bean.support.SubEvent;
import com.kaqi.reader.bean.support.UserSexChooseFinishedEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * @author yuyh.
 * @date 17/1/30.
 */

public class EventManager {

    public static void refreshCollectionList() {
        EventBus.getDefault().post(new RefreshCollectionListEvent());
    }

    public static void refreshCollectionIcon() {
        EventBus.getDefault().post(new RefreshCollectionIconEvent());
    }

    public static void refreshSubCategory(String minor, String type) {
        EventBus.getDefault().post(new SubEvent(minor, type));
    }

    public static void rerefreshMainRecomend() {
        EventBus.getDefault().post(new RefreshCollectionIconEvent());
    }

    public static void getShareUtisl(int share_type) {
        EventBus.getDefault().post(new ShareEvent(share_type));
    }

    public static void setRecommendListook(){
        EventBus.getDefault().post(new UserSexChooseFinishedEvent());
    }

}
