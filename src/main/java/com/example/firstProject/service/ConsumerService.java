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
    private static String bootstrapServerIp = "";
    private static String bootstrapServerPort = "";
    private static String topicName = "";
    private static final String GROUP_ID = "null";

    private static KafkaConsumer<String, String> consumer;

    // 구독
    public void subscribe(String ip, String port, String topic){
        bootstrapServerIp = ip;
        bootstrapServerPort = port;
        topicName = topic;

        // consumer properties
        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServerIp+":"+bootstrapServerPort);
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID);
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());


        // consumer 객체 생성, 지정한 토픽에서 데이터 받아옴
        consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(Collections.singletonList(topicName));
    }

    // 조회
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

    // 중지
    public void stop(){
        consumer.close();
    }
}
