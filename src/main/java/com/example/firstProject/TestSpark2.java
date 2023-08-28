package com.example.firstProject;

import com.google.gson.internal.bind.JsonTreeReader;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.spark.SparkConf;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.*;
import org.apache.spark.streaming.kafka010.ConsumerStrategies;
import org.apache.spark.streaming.kafka010.KafkaUtils;
import org.apache.spark.streaming.kafka010.LocationStrategies;
import scala.Tuple2;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class TestSpark2 {
    public static void main(String[] args) throws Exception {
        // 스파크 초기화
        // 로컬 Streaming Context 생성, 배치 간격 1초
        SparkConf conf = new SparkConf().setAppName("kafka-spark").setMaster("local[2]");
        JavaStreamingContext streamingContext = new JavaStreamingContext(conf, Durations.seconds(1));

        // 카프카 정보
        Map<String, Object> kafkaParams = new HashMap<>();
        kafkaParams.put("bootstrap.servers", "192.168.0.142:9092");
        kafkaParams.put("key.deserializer", StringDeserializer.class);
        kafkaParams.put("value.deserializer", StringDeserializer.class);
        kafkaParams.put("group.id", "consumer");
        kafkaParams.put("auto.offset.reset", "latest");

        // 구독할 토픽
        Collection<String> topics = Arrays.asList("number");

        // 수신할 데이터 스트림
        JavaInputDStream<ConsumerRecord<String, String>> stream =
                KafkaUtils.createDirectStream(
                        streamingContext,
                        LocationStrategies.PreferConsistent(),
                        ConsumerStrategies.<String, String>Subscribe(topics, kafkaParams)
                );

        // 데이터 연산 및 출력
//        stream.map(record -> record.value()).print();
        stream.foreachRDD(rdd -> {
            rdd.foreach(record ->
                    System.out.println("number : " + record.value()));
        });

        streamingContext.start();
        streamingContext.awaitTermination();
    }

}
