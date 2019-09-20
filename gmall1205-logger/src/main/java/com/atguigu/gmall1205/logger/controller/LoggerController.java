package com.atguigu.gmall1205.logger.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

//import org.slf4j.LoggerFactory;
//import org.slf4j.LoggerFactory;
import com.atguigu.gmall1205.common.constant.GmallConstant;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController // @RestController相当于 @Controller + @ResponseBody
public class LoggerController {

    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(LoggerController.class);

    @PostMapping("/log")
    public String doLog(@RequestParam("log") String logJson){

        //补时间戳
        JSONObject jsonObject = JSON.parseObject(logJson);
        jsonObject.put("ts",System.currentTimeMillis());

        //将jsonObject日志写到log配置的文件夹中
        logger.info(jsonObject.toJSONString());

        //发送到kafka
        if("startup".equals(jsonObject.getString("type"))){
            kafkaTemplate.send(GmallConstant.KAFKA_TOPIC_STARTUP,jsonObject.toString());
        }else{
            kafkaTemplate.send(GmallConstant.KAFKA_TOPIC_EVENT,jsonObject.toString());
        }
        return "success";
    }
}
