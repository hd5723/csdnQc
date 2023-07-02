package com.kelvin.spiderx.entity;

import lombok.Data;

/***
 * @title BlogInfoBody
 * @desctption <TODO description class purpose>
 * @author LTF
 * @create 2023/7/1 15:24
 **/
@Data
public class BlogInfoBody {

    private int code;
    private String message;
    private BlogInfoDetail data;

}