package com.example.firstProject.controller;

import com.example.firstProject.dto.TopicDto;
import com.example.firstProject.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TopicSettingController {
    @Autowired
    private TopicService topicService;

    // 토픽 저장
    @RequestMapping(method = RequestMethod.POST, value = "/saveTopic")
    public ResponseEntity<Long> saveTopic(TopicDto topicDto) {
        System.out.println(topicDto.toString());
        if(topicService.checkTopicName(topicDto.getTopicName())){
            return new ResponseEntity<>(0l, HttpStatusCode.valueOf(500));
        }
        Long topicId = topicService.saveTopic(topicDto);
        return new ResponseEntity<>(topicId, HttpStatus.OK);
    }

    // 토픽 전체 조회
    @RequestMapping(method = RequestMethod.GET, value = "getAllTopic")
    public List<TopicDto> getAllTopic() {
        List<TopicDto> topicDtoList = topicService.getAllTopic();
        System.out.println("topicDtoList::" + topicDtoList.toString());
        return topicDtoList;
    }

    // 토픽 세부 조회
    @RequestMapping(method = RequestMethod.GET, value = "getTopic/{id}")
    public TopicDto getTopic(@PathVariable Long id) {
        TopicDto topicDto = topicService.findById(id);
        System.out.println("가져옴: "+topicDto.toString());
        return topicDto;
    }

    // 토픽 삭제
    @RequestMapping(method = RequestMethod.DELETE, value = "delete/{id}")
    public String deleteTopic(@PathVariable Long id){
        topicService.deleteTopic(id);
        return "redirect:/";
    }

    // 토픽 수정
    @RequestMapping(method = RequestMethod.POST, value = "edit/{id}")
    public ResponseEntity<Long> update(@PathVariable Long id, TopicDto topicDto) {
        System.out.println("수정 : "+topicDto.toString());
        if(topicService.checkTopicName(topicDto.getTopicName())){
            return new ResponseEntity<>(0l, HttpStatusCode.valueOf(500));
        }
        Long topicId = topicService.updateTopic(id, topicDto);
        return new ResponseEntity<>(topicId, HttpStatus.OK);
    }

}
