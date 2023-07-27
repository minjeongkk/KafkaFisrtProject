package com.example.firstProject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class TopicController {
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
}
