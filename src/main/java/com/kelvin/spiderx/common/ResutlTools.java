package com.kelvin.spiderx.common;

import lombok.AllArgsConstructor;
import lombok.Data;

/***
 * @title ResutlDto
 * @desctption 返回类辅助类
 * @author LTF
 * @create 2023/7/1 15:17
 **/
@Data
@AllArgsConstructor
public class ResutlTools {

    public static ResutlDto buildSuccess(Object data) {
        return new ResutlDto(200 , "success" , data);
    }

    public static ResutlDto buildSuccess(String message , Object data) {
        return new ResutlDto(200 , message , data);
    }

    public static ResutlDto buildFail(int code , String message , Object data) {
        return new ResutlDto(code , message , data);
    }

    public static ResutlDto buildFail(int code) {
        return new ResutlDto(code , "系统异常" , null);
    }

}
