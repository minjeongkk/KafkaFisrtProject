package com.example.firstProject.controller;

import com.example.firstProject.service.ConsumerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequiredArgsConstructor
public class KafkaController {
    private ConsumerService consumerTest;

    @GetMapping("/subscribe")
    public String subscribe(){
        consumerTest = new ConsumerService();
        consumerTest.subscribe();
        return "subscribe";
    }

    @GetMapping(value="/getData")
    public ArrayList getMessage(){
        return consumerTest.test();
    }

    @GetMapping("/stop")
    public String stop(){
        consumerTest.stop();
        return "stop";
    }
}
