package com.kaqi.niuniu.ireader.view.banner;

/**
 * Created by hcc on 16/8/24 21:37
 * 100332338@qq.com
 * <p>
 * Banner模型类
 */
public class BannerEntity {
    public String title;
    public String img;
    public int type;
    public String link;

    public BannerEntity(String link, String title, String img, int type) {
        this.link = link;
        this.title = title;
        this.img = img;
        this.type = type;
    }

}
