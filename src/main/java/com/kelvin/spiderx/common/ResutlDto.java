package com.kelvin.spiderx.common;

import lombok.AllArgsConstructor;
import lombok.Data;

/***
 * @title ResutlDto
 * @desctption 返回对象
 * @author LTF
 * @create 2023/7/1 15:17
 **/
@Data
@AllArgsConstructor
public class ResutlDto {

    private int code;

    private String message;

    private Object data;

}
