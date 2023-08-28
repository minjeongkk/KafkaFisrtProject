package com.example.firstProject.controller;

import com.example.firstProject.dto.TopicDto;
import com.example.firstProject.entity.Status;
import com.example.firstProject.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;


@RestController
@RequiredArgsConstructor
public class RealtimeTopicMonitoringController {
    private SparkStreaming sparkStreaming;
    private TopicService topicService;

    @Autowired
    public RealtimeTopicMonitoringController(SparkStreaming sparkStreaming, TopicService topicService) {
        this.sparkStreaming = sparkStreaming;
        this.topicService = topicService;
    }

    // 서버 확인
    @GetMapping("/checkServer1/{id}")
    public ResponseEntity<String> checkServer(@PathVariable Long id) {
        TopicDto topicDto = topicService.findById(id);

        // 서버 작동
        boolean isChecked = sparkStreaming.checkServer(topicDto.getIp(), topicDto.getPort().toString());
        if (isChecked) {
            return new ResponseEntity<>(id.toString() + ":ok", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(id.toString() + ":not found", HttpStatus.NOT_FOUND);
        }
    }

    // 구독
    @GetMapping("/subscribe1/{id}")
    public ResponseEntity<StreamingResponseBody> subscribe(@PathVariable Long id) throws InterruptedException {
        TopicDto topicDto = topicService.findById(id);
        StreamingResponseBody responseBody = sparkStreaming.subscribe(topicDto.getId(), topicDto.getIp(), topicDto.getPort().toString(), topicDto.getTopicName());
        return new ResponseEntity(responseBody, HttpStatus.OK);
    }

    // 중지
    @GetMapping("/stop1/{id}")
    public ResponseEntity<String> stop(@PathVariable Long id) {
        // 구독 중지
        sparkStreaming.stop(id);

        // 토픽 상태 Stopped로 변경
        TopicDto topicDto = topicService.findById(id);
        topicService.updateStatus(id, topicDto, Status.Stopped);

        return new ResponseEntity<>(id.toString() + ":stop", HttpStatus.OK);
    }

}
