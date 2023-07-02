package com.kelvin.spiderx.entity;

import lombok.Data;
import java.io.Serializable;

/***
 * @title Product
 * @desctption <TODO description class purpose>
 * @author LTF
 * @create 2023/6/21 22:55
 **/
@Data
public class Product implements Serializable,Cloneable{
    private Integer productId ;

    private String productName ;

    private Integer categoryId ;

    private String productTitle ;

    private String productIntro ;

    private String productPicture ;

    private Double productPrice ;

    private Double productSellingPrice ;

    private Integer productNum ;

    private Integer productSales ;

    private Integer status ;

}