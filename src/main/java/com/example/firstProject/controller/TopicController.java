package com.example.firstProject.controller;

import com.example.firstProject.dto.TopicDto;
import com.example.firstProject.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class TopicController {
    @Autowired
    private TopicService topicService;

    // 메인 화면 호출
    @RequestMapping(method = RequestMethod.GET, value = "/")
    public String viewMain() {
        return "Main";
    }

    // topic 관리 화면 호출
    @RequestMapping(method = RequestMethod.GET, value = "/TopicSetting")
    public String viewSetting() {
        return "TopicSetting";
    }

    // topic 모니터링 화면 호출
    @RequestMapping(method = RequestMethod.GET, value = "/Monitoring")
    public String viewMonitoring() {
        return "Monitoring";
    }

    // 토픽 저장
    @RequestMapping(method = RequestMethod.POST, value = "/saveTopic")
    public ResponseEntity<Long> saveTopic(TopicDto topicDto) {
        System.out.println(topicDto.toString());
        if(topicService.checkTopicName(topicDto.getTopicName())){
            return new ResponseEntity<>(0l, HttpStatus.BAD_REQUEST);
        }
        Long topicId = topicService.saveTopic(topicDto);
        return new ResponseEntity<>(topicId, HttpStatus.OK);
    }

    // 토픽 전체 조회
    @RequestMapping(method = RequestMethod.GET, value = "getAllTopic")
    @ResponseBody
    public List<TopicDto> getAllTopic() {
        List<TopicDto> topicDtoList = topicService.getAllTopic();
        System.out.println("topicDtoList::" + topicDtoList.toString());
        return topicDtoList;
    }

    // 토픽 세부 조회
    @RequestMapping(method = RequestMethod.GET, value = "edit/{id}")
    @ResponseBody
    public TopicDto getTopic(@PathVariable Long id) {
        TopicDto topicDto = topicService.findById(id);
        System.out.println("가져옴: "+topicDto.toString());
        return topicDto;
    }

    // 토픽 삭제
    @RequestMapping(method = RequestMethod.GET, value = "delete/{id}")
    public String deleteTopic(@PathVariable Long id){
        topicService.deleteTopic(id);
        return "redirect:/";
    }

    // 토픽 수정
    @RequestMapping(method = RequestMethod.POST, value = "edit/{id}")
    public ResponseEntity<Long> update(@PathVariable Long id, TopicDto topicDto) {
        System.out.println("수정 : "+topicDto.toString());
        Long topicId = topicService.updateTopic(id, topicDto);
        return new ResponseEntity<>(topicId, HttpStatus.OK);
    }

}
