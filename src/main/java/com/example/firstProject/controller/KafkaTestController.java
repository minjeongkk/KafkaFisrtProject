package com.example.firstProject.controller;

import com.example.firstProject.dto.TopicDto;
import com.example.firstProject.entity.Status;
import com.example.firstProject.service.ConsumerTest;
import com.example.firstProject.service.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequiredArgsConstructor
public class KafkaTestController {
    private ConsumerTest consumerTest;
    @Autowired
    private TopicService topicService;

    // 구독
    @GetMapping("/subscribe/{id}")
    public String subscribe(@PathVariable Long id) {
        // 구독 (ip, port, topic이름 전달)
        if (consumerTest == null) {
            consumerTest = new ConsumerTest();
        }
        TopicDto topicDto = topicService.findById(id);
        consumerTest.subscribe(id, topicDto.getIp(), topicDto.getPort().toString(), topicDto.getTopicName());

        // 토픽 상태 Running으로 변경
        TopicDto topicDto1 = topicService.findById(id);
        topicService.updateStatus(id, topicDto1, Status.Running);

        return id.toString() + ":subscribe";
    }

    // 조회
    @GetMapping(value = "/getData/{id}")
    public ArrayList getMessage(@PathVariable Long id) {
        return consumerTest.test(id);
    }

    // 중지
    @GetMapping("/stop/{id}")
    public String stop(@PathVariable Long id) {
        // 구독 중지
        consumerTest.stop(id);

        // 토픽 상태 Stopped로 변경
        TopicDto topicDto1 = topicService.findById(id);
        topicService.updateStatus(id, topicDto1, Status.Stopped);

        return id.toString() + ":stop";
    }
}
