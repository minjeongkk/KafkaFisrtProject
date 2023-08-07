package com.example.firstProject.service;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.*;

public class Consumer {
    private static String bootstrapServerIp = "";
    private static String bootstrapServerPort = "";
    private static String topicName = "";
    private static final String GROUP_ID = "consumer";

    private static KafkaConsumer<String, String> consumer;
    private HashMap<Long, KafkaConsumer<String, String>> consumers =  new HashMap<Long, KafkaConsumer<String, String>>( );


    // 구독
    public void subscribe(Long id, String ip, String port, String topic){
        bootstrapServerIp = ip;
        bootstrapServerPort = port;
        topicName = topic;

        // consumer properties
        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServerIp+":"+bootstrapServerPort);
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID+id.toString());
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");

        consumer = new KafkaConsumer<>(properties);
        consumers.put(id, consumer);
        consumer.subscribe(Arrays.asList(topicName));
        ConsumerRecords<String, String> records = consumers.get(id).poll(Duration.ofSeconds(1));
    }

    // 조회
    public ArrayList test(Long id){
        // 데이터를 받아서 출력
        try{
            ConsumerRecords<String, String> records = consumers.get(id).poll(Duration.ofSeconds(1));
            ArrayList list = new ArrayList<>();
            for (ConsumerRecord<String, String> record : records){
                System.out.println(record.value());
                list.add(record.value());
            }
            return list;
        } finally {
        }
    }

    // 중지
    public void stop(Long id){
        consumers.get(id).close();
        consumers.remove(id);
    }
}
