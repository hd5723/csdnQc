package com.kelvin.spiderx.util;

import lombok.extern.slf4j.Slf4j;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/***
 * @title SeleniumUtil
 * @desctption Selenium辅助类
 * @author Kelvin
 * @create 2023/6/21 22:47
 **/
@Slf4j
public class SeleniumUtil {

    public final static String CHROMEDRIVERPATH = "/Users/apple/Downloads/chromedriver_mac64/chromedriver";

    public final static String LOCATION_IMG_BASE_PATH = "~/java/code/spiderX/img/";

    public static void sleep(int m) {
        try {
            Thread.sleep(m);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 文件下载到指定路径
     *
     * @param urlString 链接
     * @throws Exception
     */
    public static boolean download(String urlString, String parentFile , String key)  {
        String savePath = SeleniumUtil.LOCATION_IMG_BASE_PATH + parentFile + "/" + key + "/";
        String filename = new Date().getTime() + ".png";
        try{
            // 构造URL
            URL url = new URL(urlString);
            // 打开连接
            URLConnection con = url.openConnection();
            //设置请求超时为20s
            con.setConnectTimeout(20 * 1000);
            //文件路径不存在 则创建
            File sf = new File(savePath);
            if (!sf.exists()) {
                sf.mkdirs();
            }
            //jdk 1.7 新特性自动关闭
            try (InputStream in = con.getInputStream();
                 OutputStream out = new FileOutputStream(sf.getPath() + "//" + filename)) {
                //创建缓冲区
                byte[] buff = new byte[1024];
                int n;
                // 开始读取
                while ((n = in.read(buff)) >= 0) {
                    out.write(buff, 0, n);
                }
            } catch (Exception e) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        log.info("【下载图片成功，本地地址：{}】" , savePath + filename);
        return true;
    }

    /**
     * 是否包含中文
     * @param str
     * @return
     */
    public static boolean isContainChinese(String str) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }
}

