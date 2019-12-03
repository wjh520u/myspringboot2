package com.wjh.delay_demo;

import com.wjh.entity.Mail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("mq")
public class DeleyTestController {

    @Autowired
    SendServiceImpl sendService;

    @RequestMapping(value="/delay",produces = {"application/json;charset=UTF-8"})
    public void produce(String rkey) throws Exception{
        Mail m=new Mail("1","qqq",1f);
        sendService.sendNormal(rkey);
    }

}
