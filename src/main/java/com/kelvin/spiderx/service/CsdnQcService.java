package com.kelvin.spiderx.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.kelvin.spiderx.util.SeleniumUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/***
 * @title CsdnQcService
 * @desctption CSDN查询质量分
 * @author LTF
 * @create 2023/6/21 23:02
 **/
@Slf4j
public class CsdnQcService {

    @Data
    class CsdnBlogInfo {
        private String title;

        private String posttime;

        private String score;

        private String remark;

    }

    /**
     *  获取质量数据
     * @throws IOException
     */
    CsdnBlogInfo csdnQcBySelenium(String blogUrl , WebDriver driver , boolean isFirst) {
        log.info("csdnQcBySelenium start！");
        CsdnBlogInfo csdnBlogInfo = new CsdnBlogInfo();

        //第一次时加载查询质量分页面
        if( isFirst ) {
            driver.get("https://www.csdn.net/qc");
            SeleniumUtil.sleep(500);
        }

        //定位到输入框
        WebElement inputSelectE = driver.findElement(By.cssSelector("input.el-input__inner"));
        //输入文字地址
        inputSelectE.sendKeys(blogUrl);

        SeleniumUtil.sleep(100);

        //定位查询按钮
        WebElement qcSelectE = driver.findElement(By.cssSelector("div.trends-input-box-btn"));
        //点击查询按钮
        qcSelectE.click();

        SeleniumUtil.sleep(1000);

        //获取右边区域 -- 文章质量分结果区域
        WebElement mainSelectE = driver.findElement(By.cssSelector("div.csdn-body-right"));

        //转化为Jsoup文档处理
        Document doc = Jsoup.parse( mainSelectE.getAttribute("outerHTML") );

        //获取文章标题
        String title = doc.select("span.title").text();
        if(!StringUtils.isEmpty(title)) {
            csdnBlogInfo.setTitle(title);
        }

        //获取作者和发布时间
        String posttime = doc.select("span.name").text();
        if(!StringUtils.isEmpty(posttime)) {
            csdnBlogInfo.setPosttime(posttime);
        }

        //获取质量分
        String score = doc.select("p.img").text();
        if(!StringUtils.isEmpty(score)) {
            csdnBlogInfo.setScore(score);
        }

        //获取博文质量分建议
        String remark = doc.select("p.desc").text();
        if(!StringUtils.isEmpty(remark)) {
            csdnBlogInfo.setRemark(remark);
        }

        //打印结果
        log.info("文章标题:{} , 作者和发布时间:{} , 质量分:{} , 博文建议:{}" , title , posttime , score , remark );

//        driver.quit();
        log.info("csdnQcBySelenium end！");
        return csdnBlogInfo;
    }



    /**
     *  获取质量数据
     * @throws IOException
     */
    CsdnBlogInfo csdnQcByRest(RestTemplate restTemplate , String userName) {
        log.info("csdnQcByRest start！");
        CsdnBlogInfo csdnBlogInfo = new CsdnBlogInfo();

        int page = 1;

        //首先查询所有的文章
        String url = "https://blog.csdn.net/community/home-api/v1/get-business-list?page="+page+"&size=100&businessType=blog&orderby=&noMore=false&year=&month=&username="+userName;

        JsonObject jsonObject =  restTemplate.getForObject(url, JsonObject.class);

        //获取文章标题


        //获取作者和发布时间


        //获取质量分


        //获取博文质量分建议


        //打印结果
//        log.info("文章标题:{} , 作者和发布时间:{} , 质量分:{} , 博文建议:{}" , title , posttime , score , remark );

//        driver.quit();
        log.info("csdnQcByRest end！");
        return csdnBlogInfo;
    }


    /**
     *  查询指定博主的文章质量分
     */
    void allBlogQcDataBySelenium() {
        String baseUrl = "https://blog.csdn.net/s445320?type=blog";

        System.setProperty("webdriver.chrome.driver", SeleniumUtil.CHROMEDRIVERPATH );// chromedriver localPath
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--remote-allow-origins=*");
        chromeOptions.addArguments("–no-sandbox");  //--start-maximized

        // 增加禁止加载图片的设置
        HashMap<String, Object> prefs = new HashMap<>();
        prefs.put("profile.default_content_settings", 2);
        chromeOptions.setExperimentalOption("prefs", prefs);
        chromeOptions.addArguments("blink-settings=imagesEnabled=false");//禁用图片

        WebDriver driver = new ChromeDriver(chromeOptions);

        driver.get(baseUrl);

        SeleniumUtil.sleep(200);

        //定位到文章列表
        WebElement mainSelectE = driver.findElement(By.cssSelector("div.mainContent"));

        boolean isEnd = false;
        // 获取Top40的数量
        int topNum = 100;
        // 上一次读取的文章数
        int prePoint = 0;
        List<String> blogUrlList = new ArrayList<>();
        List<WebElement>  webElements = null;
        while ( isEnd == false  ) {
            JavascriptExecutor jsDriver = (JavascriptExecutor) driver;//将java中的driver强制转型为JS类型
            jsDriver.executeScript("window.scrollTo(0, 50)");
            jsDriver.executeScript("window.scrollTo(0, document.body.scrollHeight-20)");
            SeleniumUtil.sleep(500);
            jsDriver.executeScript("window.scrollTo(0, document.body.scrollHeight +1)");
            SeleniumUtil.sleep(2000);

            webElements = mainSelectE.findElements(By.cssSelector("article.blog-list-box>a"));

            // 如果上一次的文章数
            //    等于 当前页面的文章数：文章已全部读取完
            //    否则，继续加载下一页
            if( webElements.size() == prePoint){
                for(WebElement element : webElements ){
                    System.out.println(  element.getAttribute("href")  );
                    blogUrlList.add(element.getAttribute("href"));
                }
                log.info("文章已全部读取完");
                break;
            } else {
                prePoint = webElements.size();
            }

            //如果加载的数据超过或等于 要求的最大长度，返回现在已加载的数据
            if( webElements.size() >= topNum ) {
                for(WebElement element : webElements ){
                    System.out.println(  element.getAttribute("href")  );
                    blogUrlList.add(element.getAttribute("href"));
                }
                log.info("文章已读取 {} 条，最大限制 {} 条！" , webElements.size() , topNum);
                break;
            }
        }
        log.info("blogUrlList size:{}" , blogUrlList.size());
        log.info("blogUrlList:{}" , new Gson().toJson(blogUrlList) );

        List<CsdnBlogInfo> csdnBlogInfoList = null;
        if(CollectionUtils.isEmpty(blogUrlList)) {
            log.info("此博主没有发表文章！");
        } else {
            log.info("此博主有文章，开始解析文章质量分！");
            csdnBlogInfoList = new ArrayList<>();
            int num = 0;
            for (String blogUrl : blogUrlList) {
                try{
                    CsdnBlogInfo csdnBlogInfo = this.csdnQcBySelenium(blogUrl , driver , num <= 0 );
                    if( null != csdnBlogInfo ) {
                        csdnBlogInfoList.add(csdnBlogInfo);
                    }
                    num ++;
                } catch (Exception e) {
                    log.info("解析文章质量分失败,文章：{}" , blogUrl);
                }
            }

            if(CollectionUtils.isEmpty(csdnBlogInfoList)) {
                log.info("解析文章质量分失败！");
            } else {
                log.info("此博主质量分如下：");
                log.info(new Gson().toJson(csdnBlogInfoList));
            }

        }
        driver.quit();
        log.info("读取数据完毕！the end!");
    }

    public static void main(String[] args) {
        CsdnQcService csdnQcService = new CsdnQcService();
        csdnQcService.allBlogQcDataBySelenium();
    }


}
