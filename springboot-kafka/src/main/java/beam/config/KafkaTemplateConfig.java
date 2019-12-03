package beam.config;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.SeekToCurrentErrorHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: hujiansong
 */
@Configuration
public class  KafkaTemplateConfig {

    @Bean
    public KafkaTemplate<Object, Object> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public ProducerFactory<Object, Object> producerFactory() {
        DefaultKafkaProducerFactory<Object, Object> objectObjectDefaultKafkaProducerFactory = new DefaultKafkaProducerFactory<>(producerConfigs());
        //objectObjectDefaultKafkaProducerFactory.setTransactionIdPrefix("tid");
        return objectObjectDefaultKafkaProducerFactory;
    }

    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>(16);
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.RETRIES_CONFIG, "3");
        props.put(ProducerConfig.ACKS_CONFIG, "all");
        props.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "gzip");
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 200);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                "org.apache.kafka.common.serialization.StringSerializer");
        //props.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG,"kafka-tran");
        return props;
    }


    @Bean
    ConcurrentKafkaListenerContainerFactory<Object, Object>
    kafkaListenerContainerFactory(KafkaTemplate<Object, Object> template) {
        ConcurrentKafkaListenerContainerFactory<Object, Object> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setConcurrency(3);

        //手动提交
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
        //死信
        factory.setErrorHandler(new SeekToCurrentErrorHandler( new DeadLetterPublishingRecoverer(template), 3));
        return factory;
    }

    @Bean
    public ConsumerFactory<Object, Object> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
    }

    public Map<String, Object> consumerConfigs() {
        Map<String, Object> props = new HashMap<>(16);
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "test");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        //props.put(ConsumerConfig.CLIENT_ID_CONFIG, "local"+ "-h6-consumer");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return props;
    }
}
