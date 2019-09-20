package com.atguigu.gmall1205.logger.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

//import org.slf4j.LoggerFactory;
//import org.slf4j.LoggerFactory;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController // @RestController相当于 @Controller + @ResponseBody
public class LoggerController {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(LoggerController.class);

    @PostMapping("/log")
    public String doLog(@RequestParam("log") String logJson){

        //补时间戳
        JSONObject jsonObject = JSON.parseObject(logJson);
        jsonObject.put("ts",System.currentTimeMillis());

        //将jsonObject日志写到log配置的文件夹中
        logger.info(jsonObject.toJSONString());

        //发送到kafka

        return "success";
    }
}
