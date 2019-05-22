package com.kaqi.reader.view.chmview;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import org.ccil.cowan.tagsoup.jaxp.SAXParserImpl;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    public static CHMFile chm = null;

    public static ArrayList<String> domparse(String filePath, String extractPath, String md5) throws IOException {
        final ArrayList<String> listSite = new ArrayList<>();
        listSite.add(md5);
        try {

            final FileOutputStream fosHTMLMap = new FileOutputStream(extractPath + "/" + md5);
            final FileOutputStream fosListSite = new FileOutputStream(extractPath + "/site_map_" + md5);
            try {
                fosListSite.write((md5 + ";").getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (chm.getResourceAsStream("") != null) {
                SAXParserImpl.newInstance(null).parse(
                        chm.getResourceAsStream(""),
                        new DefaultHandler() {
                            class MyUrl {
                                public int status = 0;
                                public String name;
                                public String local;

                                public String toString() {
                                    if (status == 1)
                                        return "<a href=\"#\">" + name + "</a>";
                                    else
                                        return "<a href=\"" + local + "\">" + name + "</a>";
                                }
                            }

                            MyUrl url = new MyUrl();
                            HashMap<String, String> myMap = new HashMap<String, String>();
                            int count = 0;

                            public void startElement(String uri, String localName, String qName,
                                                     Attributes attributes) throws SAXException {

                                if (qName.equals("param")) {
                                    count++;
                                    for (int i = 0; i < attributes.getLength(); i++) {
                                        myMap.put(attributes.getQName(i).toLowerCase(), attributes.getValue(i).toLowerCase());
                                    }
                                    if (myMap.get("name").equals("name") && myMap.get("value") != null) {
                                        url.name = myMap.get("value");
                                        url.status = 1;
                                    } else if (myMap.get("name").equals("local") && myMap.get("value") != null) {
                                        url.local = myMap.get("value");
                                        url.status = 2;
                                        listSite.add(url.local.replaceAll("%20", " "));
                                        try {
                                            fosListSite.write((url.local.replaceAll("%20", " ") + ";").getBytes());
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    if (url.status == 2) {
                                        url.status = 0;
                                        try {
                                            fosHTMLMap.write(url.toString().getBytes());
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                } else {
                                    if (url.status == 1) {
                                        try {
                                            fosHTMLMap.write(url.toString().getBytes());
                                            url.status = 0;
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }

                                if (!qName.equals("object") && !qName.equals("param"))
                                    try {
                                        fosHTMLMap.write(("<" + qName + ">").getBytes());
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }


                            }

                            public void endElement(String uri, String localName,
                                                   String qName) throws SAXException {
                                if (!qName.equals("object") && !qName.equals("param"))
                                    try {
                                        fosHTMLMap.write(("</" + qName + ">").getBytes());
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                            }
                        }
                );
            } else {
                fosHTMLMap.write("<HTML> <BODY> <UL>".getBytes());
                for (String fileName : chm.list()) {
                    fileName = fileName.substring(1);
                    if (fileName.endsWith(".htm") || fileName.endsWith(".html")) {
                        fosListSite.write((fileName + ";").getBytes());
                        fosHTMLMap.write(("<li><a href=\"" + fileName + "\">" + fileName + "</a></li>").getBytes());
                        listSite.add(fileName);
                    }
                }
                fosHTMLMap.write("</UL> </BODY> </HTML>".getBytes());
            }
            fosHTMLMap.close();
            fosListSite.close();
        } catch (SAXException | IOException e) {
            e.printStackTrace();
        }
        ///////////////////////////////////////////////////


        return listSite;
    }

    public static ArrayList<String> getListSite(String extractPath, String md5) {
        ArrayList<String> listSite = new ArrayList<>();

        StringBuilder reval = new StringBuilder();
        try {
            InputStream in = new FileInputStream(extractPath + "/site_map_" + md5);
            byte[] buf = new byte[1024];
            int c = 0;
            while ((c = in.read(buf)) >= 0) {
                reval.append(new String(buf, 0, c));
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        String[] arrSite = reval.toString().split(";");
        Collections.addAll(listSite, arrSite);
        return listSite;
    }

    public static ArrayList<String> getBookmark(String extractPath, String md5) {
        ArrayList<String> listBookMark = new ArrayList<>();
        StringBuilder reval = new StringBuilder();
        try {
            InputStream in = new FileInputStream(extractPath + "/bookmark_" + md5);
            byte[] buf = new byte[1024];
            int c = 0;
            while ((c = in.read(buf)) >= 0) {
                reval.append(new String(buf, 0, c));
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] arrSite = reval.toString().split(";");
        for (String str : arrSite) {
            if (str.length() > 0) {
                listBookMark.add(str);
            }
        }
        return listBookMark;
    }

    public static int getHistory(String extractPath, String md5) {
        StringBuilder reval = new StringBuilder();
        try {
            InputStream in = new FileInputStream(extractPath + "/history_" + md5);
            byte[] buf = new byte[1024];
            int c = 0;
            while ((c = in.read(buf)) >= 0) {
                reval.append(new String(buf, 0, c));
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
            return 1;
        }
        try {
            return Integer.parseInt(reval.toString());
        } catch (Exception e) {
            return 0;
        }

    }


    public static void saveBookmark(String extractPath, String md5, ArrayList<String> listBookmark) {
        try {
            FileOutputStream fos = new FileOutputStream(extractPath + "/bookmark_" + md5, false);
            for (String str : listBookmark) {
                fos.write((str + ";").getBytes());
            }
            fos.close();
        } catch (IOException ignored) {
        }
    }

    public static void saveHistory(String extractPath, String md5, int index) {
        try {
            FileOutputStream fos = new FileOutputStream(extractPath + "/history_" + md5, false);
            fos.write(("" + index).getBytes());
            fos.close();
        } catch (IOException ignored) {
        }
    }


    private static String getSiteMap(String filePath) {
        StringBuilder reval = new StringBuilder();
        try {
            if (chm == null) {
                chm = new CHMFile(filePath);
            }
            byte[] buf = new byte[1024];
            InputStream in = chm.getResourceAsStream("");
            int c = 0;
            while ((c = in.read(buf)) >= 0) {
                reval.append(new String(buf, 0, c));
            }
//            chm.close();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
        return reval.toString();
    }

    public static boolean extract(String filePath, String pathExtract) {
        try {
            if (chm == null) {
                chm = new CHMFile(filePath);
            }
            File filePathTemp = new File(pathExtract);
            if (!filePathTemp.exists()) {
                if (!filePathTemp.mkdirs()) throw new IOException();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static String checkSum(String path) {
        String checksum = null;
        try {
            FileInputStream fis = new FileInputStream(path);
            MessageDigest md = MessageDigest.getInstance("MD5");

            //Using MessageDigest update() method to provide input
            byte[] buffer = new byte[8192];
            int numOfBytesRead;
            while ((numOfBytesRead = fis.read(buffer)) > 0) {
                md.update(buffer, 0, numOfBytesRead);
            }
            byte[] hash = md.digest();
            checksum = new BigInteger(1, hash).toString(16); //don't use this, truncates leading zero
        } catch (IOException | NoSuchAlgorithmException ignored) {
        }
        assert checksum != null;
        return checksum.trim();
    }

    public static boolean extractSpecificFile(String filePath, String pathExtractFile, String insideFileName) {
        try {
            if (chm == null) {
                chm = new CHMFile(filePath);
            }
            if (new File(pathExtractFile).exists()) return true;
            String path = pathExtractFile.substring(0, pathExtractFile.lastIndexOf("/"));
            File filePathTemp = new File(path);
            if (!filePathTemp.exists()) {
                if (!filePathTemp.mkdirs()) throw new IOException();
            }
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(pathExtractFile);
                byte[] buf = new byte[1024];
                InputStream in = chm.getResourceAsStream(insideFileName);
                int c;
                while ((c = in.read(buf)) >= 0) {
                    fos.write(buf, 0, c);
                }
            } catch (IOException e) {
                Log.d("Error extract file: ", insideFileName);
                e.printStackTrace();
            } finally {
                if (fos != null) fos.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

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
