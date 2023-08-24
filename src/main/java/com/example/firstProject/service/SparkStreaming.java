package com.example.firstProject.service;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.VoidFunction2;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.StreamingContextState;
import org.apache.spark.streaming.Time;
import org.apache.spark.streaming.api.java.JavaInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka010.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.StreamSupport;

public class SparkStreaming implements Serializable {
    private SparkConf conf;
    private JavaSparkContext ctx;
    private JavaStreamingContext streamingContext;

    public SparkStreaming(){
        conf = new SparkConf().setAppName("kafka-spark").setMaster("local[2]");
        ctx = JavaSparkContext.fromSparkContext(SparkContext.getOrCreate(conf));
        streamingContext = new JavaStreamingContext(ctx, Durations.seconds(1));
        System.out.println("2222222222");
    }

    public boolean checkServer(String ip, String port){
        String bootstrapServerIp = ip;
        String bootstrapServerPort = port;

        // 카프카 정보
        Map<String, Object> kafkaParams = new HashMap<>();
        kafkaParams.put("bootstrap.servers", bootstrapServerIp + ":" + bootstrapServerPort);

        try {
            AdminClient client = AdminClient.create(kafkaParams);
            client.describeCluster().nodes().get(5, TimeUnit.SECONDS);
        } catch (TimeoutException | InterruptedException | ExecutionException e) {
//            System.out.printf(e.getMessage());
            return false;
        }

        return true;
    }

    // 구독
    public StreamingResponseBody subscribe(Long id, String ip, String port, String topic) throws InterruptedException {
        String bootstrapServerIp = ip;
        String bootstrapServerPort = port;
        String topicName = topic;
        String GROUP_ID = "consumer";

        // 응답값을 Byte로 변환해 Streaming 형태로 줄 때 사용하는 객체
        // OutputStream에 직접 쓸 수 있음
        StreamingResponseBody responseBody = new StreamingResponseBody() {
            @Override
            public void writeTo(OutputStream out) throws IOException {
//                SparkConf conf = new SparkConf().setAppName("kafka-spark-"+id).setMaster("local[2]");
//                JavaSparkContext ctx = JavaSparkContext.fromSparkContext(SparkContext.getOrCreate(conf));
//                JavaStreamingContext streamingContext = new JavaStreamingContext(ctx, Durations.seconds(1));

                // 카프카 정보
                Map<String, Object> kafkaParams = new HashMap<>();
                kafkaParams.put("bootstrap.servers", bootstrapServerIp+":"+bootstrapServerPort);
                kafkaParams.put("key.deserializer", StringDeserializer.class);
                kafkaParams.put("value.deserializer", StringDeserializer.class);
                kafkaParams.put("group.id", GROUP_ID);
                kafkaParams.put("auto.offset.reset", "latest");

                // 구독할 토픽
                Collection<String> topics = Arrays.asList(topicName);

                // 카프카 메시지의 DStream 생성
                JavaInputDStream<ConsumerRecord<String, String>> stream =
                        KafkaUtils.createDirectStream(
                                streamingContext,
                                LocationStrategies.PreferConsistent(),
                                ConsumerStrategies.<String, String>Subscribe(topics, kafkaParams)
                        );

                // 수신할 데이터 스트림
                stream.foreachRDD(new VoidFunction2<JavaRDD<ConsumerRecord<String, String>>, Time>() {
                    @Override
                    public void call(JavaRDD<ConsumerRecord<String, String>> v1, Time v2) throws Exception {
                        JavaRDD<String> wRow = v1.mapPartitions(new MapPartitions());
                        // block
                        List<String> list = wRow.collect();
                        for (String ss : list) {
                            String msg = "row : " + ss;
                            out.write(msg.getBytes());
                            out.flush();
                        }
                    }
                });

                streamingContext.start();

                try {
                    streamingContext.awaitTermination();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };

        return responseBody;
    }

    // 중지
    public void stop(Long id) {
        streamingContext.close();
    }

}
