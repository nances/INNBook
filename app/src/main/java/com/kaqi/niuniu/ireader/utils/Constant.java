package com.kaqi.niuniu.ireader.utils;

import android.graphics.Color;
import android.support.annotation.StringDef;

import java.io.File;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by newbiechen on 17-4-16.
 */

public class Constant {
    /*SharedPreference*/
    public static final String FITST_APP = "first_app";
    public static final String SHARED_SEX = "sex";
    public static final String SHARED_SAVE_BOOK_SORT = "book_sort";
    public static final String SHARED_SAVE_BILLBOARD = "billboard";
    public static final String SHARED_CONVERT_TYPE = "convert_type";
    public static final String SEX_BOY = "boy";
    public static final String SEX_GIRL = "girl";
    public static final String UID = "uid";
    //是否开启赚钱模式
    public static final String MONEY_VP = "money_vp";

    /*URL_BASE*/
//    public static final String API_BASE_URL = "http://apim.ssmnx.com";
    public static final String API_BASE_URL = "http://api.zhuishushenqi.com";
    public static final String API_BASE_NIUNIU_URL = "http://apim.ssmnx.com";
    public static final String IMG_BASE_URL = "http://statics.zhuishushenqi.com";
    //book type
    public static final String BOOK_TYPE_COMMENT = "normal";
    public static final String BOOK_TYPE_VOTE = "vote";
    //book state
    public static final String BOOK_STATE_NORMAL = "normal";
    public static final String BOOK_STATE_DISTILLATE = "distillate";
    //Book Date Convert Format
    public static final String FORMAT_BOOK_DATE = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String FORMAT_TIME = "HH:mm";
    public static final String FORMAT_FILE_DATE = "yyyy-MM-dd";

    public static String PATH_DATA = FileUtils.createRootPath(AppUtils.getAppContext()) + "/cache";

    public static String PATH_COLLECT = FileUtils.createRootPath(AppUtils.getAppContext()) + "/collect";

    public static String PATH_HISTORY = FileUtils.createRootPath(AppUtils.getAppContext()) + "/history";

    public static final String TYPE_ACTIVITY_CENTER = "recommend";
    public static String EXTRA_AV = "extra_av";
    public static String EXTRA_IMG_URL = "extra_img_url";
    public static final String EXTRA_INFO = "extra_info";
    public static final String VIDEO_TYPE_MP4 = "mp4";
    public static final String SWITCH_MODE_KEY = "mode_key";
    public static final String EXTRA_RID = "extra_rid";
    public static final String EXTRA_POSITION = "extra_pos";
    public static final int ADVERTISING_RID = 165;

    public static final String SUFFIX_TXT = ".txt";
    public static final String SUFFIX_PDF = ".pdf";
    public static final String SUFFIX_EPUB = ".epub";
    public static final String SUFFIX_ZIP = ".zip";
    public static final String SUFFIX_CHM = ".chm";

    public static String PATH_TXT = PATH_DATA + "/book/";

    public static String PATH_EPUB = PATH_DATA + "/epub";

    public static String PATH_CHM = PATH_DATA + "/chm";
    //RxBus
    public static final int MSG_SELECTOR = 1;
    //BookCachePath (因为getCachePath引用了Context，所以必须是静态变量，不能够是静态常量)
    public static String BOOK_CACHE_PATH = FileUtils.getCachePath()+File.separator
            + "book_cache"+ File.separator ;
    //文件阅读记录保存的路径
    public static String BOOK_RECORD_PATH = FileUtils.getCachePath() + File.separator
            + "book_record" + File.separator;


    //BookType
    @StringDef({
            BookType.ALL,
            BookType.XHQH,
            BookType.WXXX,
            BookType.DSYN,
            BookType.LSJS,
            BookType.YXJJ,
            BookType.KHLY,
            BookType.CYJK,
            BookType.HMZC,
            BookType.XDYQ,
            BookType.GDYQ,
            BookType.HXYQ,
            BookType.DMTR
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface BookType {
        String ALL = "all";

        String XHQH = "xhqh";

        String WXXX = "wxxx";

        String DSYN = "dsyn";

        String LSJS = "lsjs";

        String YXJJ = "yxjj";
        String KHLY = "khly";
        String CYJK = "cyjk";
        String HMZC = "hmzc";
        String XDYQ = "xdyq";
        String GDYQ = "gdyq";
        String HXYQ = "hxyq";
        String DMTR = "dmtr";
    }

    public static Map<String, String> bookType = new HashMap<String, String>() {{
        put("qt", "其他");
        put(BookType.XHQH, "玄幻奇幻");
        put(BookType.WXXX, "武侠仙侠");
        put(BookType.DSYN, "都市异能");
        put(BookType.LSJS, "历史军事");
        put(BookType.YXJJ, "游戏竞技");
        put(BookType.KHLY, "科幻灵异");
        put(BookType.CYJK, "穿越架空");
        put(BookType.HMZC, "豪门总裁");
        put(BookType.XDYQ, "现代言情");
        put(BookType.GDYQ, "古代言情");
        put(BookType.HXYQ, "幻想言情");
        put(BookType.DMTR, "耽美同人");
    }};

    public static final int[] tagColors = new int[]{
            Color.parseColor("#90C5F0"),
            Color.parseColor("#91CED5"),
            Color.parseColor("#F88F55"),
            Color.parseColor("#C0AFD0"),
            Color.parseColor("#E78F8F"),
            Color.parseColor("#67CCB7"),
            Color.parseColor("#F6BC7E"),
            Color.parseColor("#FFD11B")
    };
}
