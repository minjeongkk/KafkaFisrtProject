package com.example.firstProject.controller;

import com.example.firstProject.dto.TopicDto;
import com.example.firstProject.entity.Status;
import com.example.firstProject.service.Consumer;
import com.example.firstProject.service.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequiredArgsConstructor
public class TopicMonitoringController {
    private Consumer consumer;
    private TopicService topicService;

    @Autowired
    public TopicMonitoringController(Consumer consumer, TopicService topicService) {
        this.consumer = consumer;
        this.topicService = topicService;
    }

    // 구독
    @GetMapping("/subscribe/{id}")
    public ResponseEntity<String> subscribe(@PathVariable Long id) {
        TopicDto topicDto = topicService.findById(id);

        // 구독 (ip, port, topic이름 전달)
        boolean isSubscribed = consumer.subscribe(id, topicDto.getIp(), topicDto.getPort().toString(), topicDto.getTopicName());
        if (isSubscribed) {
            // 토픽 상태 Running으로 변경
            topicService.updateStatus(id, topicDto, Status.Running);
            return new ResponseEntity<>(id.toString() + ":subscribe", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(id.toString() + ":can't subscribe", HttpStatus.NOT_FOUND);
        }
    }

    // 조회
    @GetMapping(value = "/getData/{id}")
    public List getMessage(@PathVariable Long id) {
        return consumer.getData(id);
    }

    // 중지
    @GetMapping("/stop/{id}")
    public ResponseEntity<String> stop(@PathVariable Long id) {
        // 구독 중지
        consumer.stop(id);

        // 토픽 상태 Stopped로 변경
        TopicDto topicDto = topicService.findById(id);
        topicService.updateStatus(id, topicDto, Status.Stopped);

        return new ResponseEntity<>(id.toString() + ":stop", HttpStatus.OK);
    }
}
