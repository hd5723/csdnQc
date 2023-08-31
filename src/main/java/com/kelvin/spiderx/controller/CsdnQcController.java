package com.kelvin.spiderx.controller;

import com.kelvin.spiderx.common.ResutlDto;
import com.kelvin.spiderx.common.ResutlTools;
import com.kelvin.spiderx.service.CsdnQcRestService;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

/***
 * @title CsdnQcController
 * @desctption CSDN博客质量查询
 * @author kelvin
 * @create 2023/7/1 14:33
 **/
@RestController
@CrossOrigin("*")
@RequestMapping("/csdn")
public class CsdnQcController {

    @Resource
    private CsdnQcRestService csdnQcService;

    @GetMapping("/qc/name")
    // 执行查询操作
    public ResutlDto qcbyname(@RequestParam String username ,
                              @RequestParam Integer currentPage) {
        if( currentPage == null ) {
            currentPage = 1;
        }
        return ResutlTools.buildSuccess(csdnQcService.allBlogQcDataByRest(username , currentPage));
    }

}
