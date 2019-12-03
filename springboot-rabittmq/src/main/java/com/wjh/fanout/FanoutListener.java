package com.wjh.fanout;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class FanoutListener {

    @RabbitListener(queues = "fanout.A")
    public void fanoutA(String msg) {
        System.out.println("A  : " + msg);
    }

    @RabbitListener(queues = "fanout.B")
    public void fanoutB(String msg) {
        System.out.println("B  : " + msg);
    }

    @RabbitListener(queues = "fanout.C")
    public void fanoutC(String msg) {
        System.out.println("C  : " + msg);
    }
 
}