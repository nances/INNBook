package com.kaqi.niuniu.ireader.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    /**
     * 是否是正确的手机号
     *
     * @param area
     * @return 不是正确的手机号  false
     */
    public static boolean isPhoneNoRight(Context context, String input_phone, String area) {
        if (TextUtils.isEmpty(input_phone)) {
            Utils.showToast(context, "手机号码不能为空");
            return false;
        }
        if ("大陆".equals(area) || "86".equals(area) || "+86".equals(area)) {
            if (!Utils.isMobileNO(input_phone)) {
                Utils.showToast(context, "请填写正确的大陆手机号");
                return false;
            }
        } else if ("香港".equals(area) || "852".equals(area)) {

            if (!Utils.isHkMobileNO(input_phone)) {
                Utils.showToast(context, "请填写正确的香港手机号");
                return false;
            }
        } else if ("澳门".equals(area) || "853".equals(area)) {
            if (!Utils.isAomenMobileNO(input_phone)) {
                Utils.showToast(context, "请填写正确的澳门手机号");
                return false;
            }
        } else if ("台湾".equals(area) || "886".equals(area)) {
            if (!Utils.isTaiwanMobileNO(input_phone)) {
                Utils.showToast(context, "请填写正确的台湾手机号");
                return false;
            }
        }
        return true;
    }

    public static boolean isPhone(Context context, String input_phone, String area) {
        if (TextUtils.isEmpty(input_phone)) {
            Utils.showToast(context, "手机号码不能为空");
            return false;
        }
        return true;
    }
    /**
     * 判断是否是纯数字的密码
     */
    public static boolean isJustNum(String password) {
        Pattern p = Pattern.compile("[0-9]$");
        Matcher m = p.matcher(password);
        return m.matches();
    }

    /**
     * @param context
     * @param content
     */
    public static void showToast(Context context, String content) {
        Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
    }

    public static boolean isMobileNO(String mobiles) {

        Pattern p = Pattern
                .compile("^((13[0-9])|(14[0-9])|(15[^4,\\D])|(18[0-9])|(17[0-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    public static boolean isHkMobileNO(String mobiles) {

        Pattern p = Pattern
                .compile("^(6|9)[0-9]{7}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    public static boolean isAomenMobileNO(String mobiles) {

        Pattern p = Pattern
                .compile("^6[0-9]{7}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    public static boolean isTaiwanMobileNO(String mobiles) {

        Pattern p = Pattern
                .compile("^9[0-9]{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    /**
     * 生成一个startNum 到 endNum之间的随机数(不包含endNum的随机数)
     * @param startNum
     * @param endNum
     * @return
     */
    public static int getNum(int startNum,int endNum){
        if(endNum > startNum){
            Random random = new Random();
            return random.nextInt(endNum - startNum) + startNum;
        }
        return 0;
    }

}
