package beam.kafka.controller;

import beam.kafka.service.KafkaSender;
import beam.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: kafka演示
 * @author: hs
 * @create: 2019-04-03 10:13:12
 **/
@RequestMapping(value = "/kafka")
@RestController
public class KafkaController {


    @Autowired
    private KafkaTemplate<Object,Object> kafkaTemplate;

    @Autowired
    private KafkaSender kafkaSender;

    @GetMapping(value = "/test")
    @Transactional
    public R test(String msg){

        //kafkaSender.sendChannelMess("testTopic",msg);
        kafkaTemplate.send("test",msg,msg);
        //kafkaSender.sendChannelTransaction("test",msg);
        return R.ok();

    }


}
