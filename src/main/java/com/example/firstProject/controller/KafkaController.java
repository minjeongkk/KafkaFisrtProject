package com.example.firstProject.controller;

import com.example.firstProject.dto.TopicDto;
import com.example.firstProject.entity.Status;
import com.example.firstProject.service.ConsumerService;
import com.example.firstProject.service.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequiredArgsConstructor
public class KafkaController {
    private ConsumerService[] consumerServices;
    @Autowired
    private TopicService topicService;

    // 구독
    @GetMapping("/subscribe/{id}")
    public String subscribe(@PathVariable Long id){
        // 객체 배열 생성
        if(consumerServices==null){
            int length = Math.toIntExact(topicService.getAllTopic().get(0).getId());
            this.consumerServices = new ConsumerService[length+1];
        }

        // 구독 (ip, port, topic이름 전달)
        int topicId = Integer.parseInt(id.toString());
        consumerServices[topicId] = new ConsumerService();
        TopicDto topicDto = topicService.findById(id);
        consumerServices[topicId].subscribe(topicDto.getIp(), topicDto.getPort().toString(), topicDto.getTopicName());

        // 토픽 상태 Running으로 변경
        TopicDto topicDto1 = topicService.findById(id);
        topicService.updateStatus(id, topicDto1, Status.Running);

        return "subscribe";
    }

    // 조회
    @GetMapping(value="/getData/{id}")
    public ArrayList getMessage(@PathVariable Long id){
        int topicId = Integer.parseInt(id.toString());
        return consumerServices[topicId].test();
    }

    // 중지
    @GetMapping("/stop/{id}")
    public String stop(@PathVariable Long id){
        // 구독 중지
        int topicId = Integer.parseInt(id.toString());
        consumerServices[topicId].stop();

        // 토픽 상태 Stopped로 변경
        TopicDto topicDto1 = topicService.findById(id);
        topicService.updateStatus(id, topicDto1, Status.Stopped);

        return "stop";
    }
}
