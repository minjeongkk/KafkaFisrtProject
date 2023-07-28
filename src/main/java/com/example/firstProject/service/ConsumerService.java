package com.example.firstProject.service;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Properties;

public class ConsumerService {
    private static final String bootstrapServer = "192.168.0.142:9092";
    private static final String topicName = "number";
    private static final String GROUP_ID = "Consumer1";

    private static KafkaConsumer<String, String> consumer;

    public void subscribe(){
        // consumer properties
        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID);
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());


        // consumer 객체 생성, 지정한 토픽에서 데이터 받아옴
        consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(Collections.singletonList(topicName));
    }

    public ArrayList test(){
        // 데이터를 받아서 출력
        try{
            while(true){
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(1));
                ArrayList list = new ArrayList<>();
                for (ConsumerRecord<String, String> record : records){
                    System.out.println(record.value());
                    list.add(record.value());
                }
                return list;
            }
        } finally {
        }
    }

    public void stop(){
        consumer.close();
    }
}
