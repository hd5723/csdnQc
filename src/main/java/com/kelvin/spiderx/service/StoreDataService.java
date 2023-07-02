package com.kelvin.spiderx.service;

import com.kelvin.spiderx.util.SeleniumUtil;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.util.StringUtils;

import java.io.IOException;

/***
 * @title StoreDataService
 * @desctption <TODO description class purpose>
 * @author LTF
 * @create 2023/6/21 23:02
 **/
@Slf4j
public class StoreDataService {

    private static final String model = "xxStore";

    /**
     *  获取商品数据
     * @throws IOException
     */
    void storeInfoSearchBySelenium() {
        log.info("storeInfoSearchBySelenium start！");

        String baseUrl = "https://search.jd.com/Search?enc=utf-8&keyword=";

        System.setProperty("webdriver.chrome.driver", SeleniumUtil.CHROMEDRIVERPATH );// chromedriver localPath
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--remote-allow-origins=*");
        chromeOptions.addArguments("–no-sandbox");  //--start-maximized

        WebDriver driver = new ChromeDriver(chromeOptions);

        String[] searchs = { "电脑" , "奶粉" , "玫瑰花" , "PPT" };
        String[] sortSearchName = { "dn" , "nf" , "mgh" , "ppt" };
        for (int i = 0; i < searchs.length; i++) {
            getStoreInfoSearchData(driver , baseUrl , searchs[i] , sortSearchName[i] );
        }

        driver.quit();
        log.info("storeInfoSearchBySelenium end！");
    }

    private void getStoreInfoSearchData(WebDriver driver, String baseUrl , String searchName , String sortName) {
        String url = baseUrl + searchName;
        driver.get(url);
        SeleniumUtil.sleep(3000);

        JavascriptExecutor jsDriver = (JavascriptExecutor) driver;//将java中的driver强制转型为JS类型
        String js ="window.scrollTo(0, document.body.scrollHeight)";
        jsDriver.executeScript(js);

        SeleniumUtil.sleep(2000);

        //Selenium获取网页内容
        //获取商品区域数据
        WebElement mainSelectE = driver.findElement(By.cssSelector("div.J-goods-list"));

        //转化为Jsoup文档处理
        Document doc = Jsoup.parse( mainSelectE.getAttribute("outerHTML") );
//        driver.quit();

        //找到商品数据集合
        Elements elements = doc.select("ul.gl-warp>li");
        log.info("商品 元素的长度:{}" , elements.size() );

        if(elements!=null&&elements.size()>0) {
            for (Element ele : elements) {
                //商品图片信息
                String imgPath = ele.select("div.p-img>a>img").attr("data-lazy-img");
                if(StringUtils.isEmpty(imgPath) == false) {
                    imgPath = "https:" + imgPath;
                    log.info("图片地址：{} , 目录：{}" , imgPath , sortName);
                    SeleniumUtil.download(imgPath , this.model , sortName);
                }

                String title = ele.select("div.p-name>a>em").text();
                if(StringUtils.isEmpty(title) == false) {
                    log.info("标题：{} " , title);
                }

                String price = ele.select("div.p-price>strong>i").text();
                if(StringUtils.isEmpty(price) == false) {
                    log.info("价格：{} " , price);
                }

            }
        }

        SeleniumUtil.sleep(500);

    }

    public static void main(String[] args) {
        StoreDataService storeDataService = new StoreDataService();
        storeDataService.storeInfoSearchBySelenium();
    }


}
