package com.wjh.direct;

import com.rabbitmq.client.Channel;
import com.wjh.entity.Mail;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


@Component
public class DirectListener1 {
	@RabbitListener(queues = "directqueue1")
	public void displayMail(Mail mail, Channel channel, Message messageer) throws Exception {
		System.out.println("directqueue1队列监听器1号收到消息"+mail.toString());
		channel.basicAck(messageer.getMessageProperties().getDeliveryTag(),false);
	}

	@RabbitListener(queues = "directqueue2")
	public void displayMail2(Mail mail, Channel channel, Message messageer) throws Exception {
		Thread.sleep(10000);
		System.out.println("directqueue2队列监听器2号收到消息"+mail.toString());
		channel.basicAck(messageer.getMessageProperties().getDeliveryTag(),false);

	}
}
