package com.kelvin.spiderx.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import java.io.Serializable;

/***
 * @title Category
 * @desctption <TODO description class purpose>
 * @author LTF
 * @create 2023/6/21 22:55
 **/
@Data
@AllArgsConstructor
public class Category implements Serializable,Cloneable{
    private Integer categoryId ;

    private String categoryName ;

}