package com.example.firstProject.service;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Random;

import java.util.Properties;

public class Producer {

    public static void main(String[] args) throws InterruptedException {
        String bootstrapServer = "192.168.0.142:9092";

        // producer properties
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        // producer 객체 생성
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(properties);

        // topic으로 message 전달
        while (true) {
            String message = String.valueOf(((int) (Math.random() * 10000))); // 1~10000 중 랜덤숫자
            kafkaProducer.send(new ProducerRecord<>("number", message));

            String message2 = String.valueOf((char) ((int) (Math.random() * 26) + 65)); // 랜덤한 대문자
            kafkaProducer.send(new ProducerRecord<>("uppercase", message2));

            String message3 = String.valueOf((char) ((int) (Math.random() * 26) + 97)); // 랜덤한 소문자
            kafkaProducer.send(new ProducerRecord<>("lowercase", message3));

            String message4 = String.valueOf((char) ((int) (Math.random() * 14) + 33)); // 랜덤한 기호
            kafkaProducer.send(new ProducerRecord<>("specialSymbol", message4));

            Thread.sleep(1000); // 1초
        }

    }
}

