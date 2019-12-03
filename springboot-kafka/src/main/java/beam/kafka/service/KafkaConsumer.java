package beam.kafka.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;


@Component
public class KafkaConsumer {

    /**
     * 监听testTopic主题,有消息就读取
     * @param message
     */
    //@KafkaListener(topics = {"testTopic"})
    public void receiveMessage(String message){
        //收到通道的消息之后执行操作
        System.out.println("消息1："+message);
    

    }

    /**
     * 监听test2主题,有消息就读取
     * @param message
     */
    @KafkaListener(topics = {"test"})
    public void receiveMap(ConsumerRecord<?,?> message){

        //收到通道的消息之后执行操作
        System.out.println("消息1："+message.value());
        /*if (message.equals("1")){
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            throw  new RuntimeException("??");
        }*/
        // throw  new RuntimeException("??");
        //acknowledgment.acknowledge();
    }

    @KafkaListener(topics = {"test.DLT"})
    public void receiveMapDlt(ConsumerRecord<String,?> message/*, Acknowledgment acknowledgment*/){
        //收到通道的消息之后执行操作
        System.out.println("死信消息2："+message);
        //acknowledgment.acknowledge();
    }

}