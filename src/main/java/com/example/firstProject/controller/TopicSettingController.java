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
    private TopicService topicService;

    @Autowired
    public TopicSettingController(TopicService topicService) {
        this.topicService = topicService;
    }

    // 토픽 저장
    @RequestMapping(method = RequestMethod.POST, value = "/saveTopic")
    public ResponseEntity<String> saveTopic(TopicDto topicDto) {
        System.out.println(topicDto.toString());
        if (topicService.checkTopicName(topicDto.getTopicName())) {
            return new ResponseEntity<>("토픽명 중복", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Long topicId = topicService.saveTopic(topicDto);
        return new ResponseEntity<>(topicId.toString() + ":토픽 저장 성공", HttpStatus.OK);
    }

    // 토픽 전체 조회
    @RequestMapping(method = RequestMethod.GET, value = "getAllTopic")
    public ResponseEntity<List<TopicDto>> getAllTopic() {
        List<TopicDto> topicDtoList = topicService.getAllTopic();
        System.out.println("topicDtoList::" + topicDtoList.toString());
        return new ResponseEntity<>(topicDtoList, HttpStatus.OK);
    }

    // 토픽 세부 조회
    @RequestMapping(method = RequestMethod.GET, value = "getTopic/{id}")
    public ResponseEntity<TopicDto> getTopic(@PathVariable Long id) {
        TopicDto topicDto = topicService.findById(id);
        System.out.println("가져옴: " + topicDto.toString());
        return new ResponseEntity<>(topicDto, HttpStatus.OK);
    }

    // 토픽 삭제
    @RequestMapping(method = RequestMethod.DELETE, value = "delete/{id}")
    public ResponseEntity<String> deleteTopic(@PathVariable Long id) {
        Long topicId = topicService.deleteTopic(id);
        return new ResponseEntity<>(topicId.toString() + ":토픽 삭제 성공", HttpStatus.OK);
    }

    // 토픽 수정
    @RequestMapping(method = RequestMethod.POST, value = "edit/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, TopicDto topicDto) {
        System.out.println("수정 : " + topicDto.toString());
        if (topicService.checkTopicName(topicDto.getTopicName())) {
            return new ResponseEntity<>("토픽명 중복", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Long topicId = topicService.updateTopic(id, topicDto);
        return new ResponseEntity<>(topicId.toString() + ":토픽 수정 성공", HttpStatus.OK);
    }

}
