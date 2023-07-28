package com.example.firstProject.controller;

import com.example.firstProject.dto.TopicDto;
import com.example.firstProject.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
    public String saveTopic(TopicDto topicDto) {
        System.out.println(topicDto.toString());
        topicService.saveTopic(topicDto);
        return "redirect:/";
    }

    // 토픽 전체 조회
    @RequestMapping(method = RequestMethod.GET, value = "getAllTopic")
    @ResponseBody
    public List<TopicDto> getAllTopic() {
        List<TopicDto> topicDtoList = topicService.getAllTopic();
        System.out.println("topicDtoList::" + topicDtoList.toString());
        return topicDtoList;
    }
}
