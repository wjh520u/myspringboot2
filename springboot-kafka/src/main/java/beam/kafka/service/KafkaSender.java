package beam.kafka.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Component
public class KafkaSender {

    @Autowired
    private KafkaTemplate<Object,Object> kafkaTemplate;

    /**
     * 发送消息到kafka
     */
    @Transactional
    public void sendChannelMess(String channel, String message){
        kafkaTemplate.send(channel,message);
    }

    public void sendChannelTransaction(String channel, String message){
        kafkaTemplate.send(channel,message);
    }


    //发送消息方法
    public void sendJson(String topic, String json) {
        JSONObject jsonObj = JSON.parseObject(json);

        jsonObj.put("topic", topic);
        jsonObj.put("ts", System.currentTimeMillis() + "");

        System.out.println("json+++++++++++++++++++++  message = {}"+ jsonObj.toJSONString());

        ListenableFuture<SendResult<Object, Object>> future = kafkaTemplate.send(topic, jsonObj.toJSONString());
        future.addCallback(new ListenableFutureCallback<SendResult<Object, Object>>() {
            @Override
            public void onSuccess(SendResult<Object, Object> result) {
                System.out.println("msg OK." + result.toString());
            }

            @Override
            public void onFailure(Throwable ex) {
                System.out.println("msg send failed: ");
            }
        });
    }

}