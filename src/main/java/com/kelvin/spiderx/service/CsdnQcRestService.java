package com.kelvin.spiderx.service;

import com.google.gson.Gson;
import com.kelvin.spiderx.common.Common;
import com.kelvin.spiderx.entity.BlogInfoBody;
import com.kelvin.spiderx.entity.BlogInfoDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import java.io.IOException;
import java.util.*;

/***
 * @title CsdnQcService
 * @desctption CSDN查询质量分
 * @author LTF
 * @create 2023/6/21 23:02
 **/
@Slf4j
@Service
public class CsdnQcRestService {


    /**
     *  获取质量数据
     * @throws IOException
     */
    BlogInfoDetail csdnQcByRest(RestTemplate restTemplate , String blogUrl) {
        log.info("csdnQcByRest start！");
        //循环调用csdn接口查询所有的博客质量分
        String qcUrl = "https://bizapi.csdn.net/trends/api/v1/get-article-score";

        //请求头
        HttpHeaders headers = new HttpHeaders();
        headers.set("accept","application/json, text/plain, */*");
        headers.set("x-ca-key","203930474");
        headers.set("x-ca-nonce","eebad1d0-3c4b-49fb-84c2-dcb094cea816");
        headers.set("x-ca-signature","i9amP6avvJfoppkZ+PG0Qmnhy8ZHJerAMAak2R4RsA0=");
        headers.set("x-ca-signature-headers","x-ca-key,x-ca-nonce");
        headers.set("x-ca-signed-content-type","multipart/form-data");

        MultiValueMap<String,String> requestBody = new LinkedMultiValueMap<>();
        requestBody.put("url", Collections.singletonList(blogUrl));
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(qcUrl, requestEntity, String.class);

        if(responseEntity.hasBody()) {
            BlogInfoBody blogInfoBody = new Gson().fromJson(responseEntity.getBody() , BlogInfoBody.class);
            if( blogInfoBody.getCode() == 200 ) {
                log.info("csdnQcByRest end！");
                BlogInfoDetail detail = blogInfoBody.getData();
                detail.setBlog_url(blogUrl);
                return detail;
            }
        }
        log.info("csdnQcByRest fail！");
        return null;
    }


    /**
     *  查询指定博主的文章质量分
     */
    public List<BlogInfoDetail> allBlogQcDataByRest( String userName ) {
        List<BlogInfoDetail> list =  Common.csdnblogdataCache.get(userName);
        if(!CollectionUtils.isEmpty(list)) {
            return Common.csdnblogdataCache.get(userName);
        }

        RestTemplate restTemplate = new RestTemplate();
        int page = 1;
        int total = 0;

        //首先查询所有的文章
        String url = "https://blog.csdn.net/community/home-api/v1/get-business-list?page="+ page +"&size="+ total +"&businessType=blog&orderby=&noMore=false&year=&month=&username="+userName;

        List<BlogInfoDetail> csdnBlogInfoList = null;
        HashMap jsonObject =  restTemplate.getForObject(url, HashMap.class);
        if( jsonObject.get("code").equals(200) ) {
            LinkedHashMap blogInfoMap = (LinkedHashMap) jsonObject.get("data");
            total = (int) blogInfoMap.get("total");
            List<LinkedHashMap> blogInfoDetails = (List<LinkedHashMap>) blogInfoMap.get("list");
            System.out.println( blogInfoDetails.size() );
            if( CollectionUtils.isEmpty(blogInfoDetails) ) {
                log.info("此博主没有发表文章！");
            } else {
                log.info("此博主有文章，开始解析文章质量分！");
                csdnBlogInfoList = new ArrayList<>();
                int num = 0;
                for (LinkedHashMap blogInfoDetail : blogInfoDetails) {
                    String blogUrl = (String) blogInfoDetail.get("url");
                    try{
                        BlogInfoDetail csdnBlogInfo = this.csdnQcByRest(restTemplate , blogUrl);
                        if( null != csdnBlogInfo ) {
                            csdnBlogInfoList.add(csdnBlogInfo);
                        }
                        num ++;
                    } catch (Exception e) {
                        log.info("解析文章质量分失败,文章：{}" , blogUrl);
                    }
                }
            }

        }
        log.info("csdnBlogInfoList size: {}" , csdnBlogInfoList.size());

        log.info("读取数据完毕！the end!");
        if( !CollectionUtils.isEmpty(csdnBlogInfoList) ) {
            Common.csdnblogdataCache.put(userName , csdnBlogInfoList);
        }
        return csdnBlogInfoList;
    }

    public static void main(String[] args) {
        String username = "s445320";
        CsdnQcRestService csdnQcService = new CsdnQcRestService();
        csdnQcService.allBlogQcDataByRest(username);
    }


}
