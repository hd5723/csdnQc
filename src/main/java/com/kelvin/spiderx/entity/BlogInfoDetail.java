package com.kelvin.spiderx.entity;

import lombok.Data;

/***
 * @title BlogInfoDetail
 * @desctption <TODO description class purpose>
 * @author LTF
 * @create 2023/7/1 15:23
 **/
@Data
public
class BlogInfoDetail {
    private String article_id;
    private int score;
    private String nickname;
    private String message;
    private String title;
    private String post_time;
    private String username;
    private String blog_url;
}
