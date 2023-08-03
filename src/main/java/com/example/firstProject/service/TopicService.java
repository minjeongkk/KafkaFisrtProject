package com.example.firstProject.service;

import com.example.firstProject.dto.TopicDto;
import com.example.firstProject.entity.Status;
import com.example.firstProject.entity.Topic;
import com.example.firstProject.repository.TopicMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TopicService {

    @Autowired
    private TopicMapper topicMapper;

    // 토픽 저장
    @Transactional
    public Long saveTopic(TopicDto topicDto) {
        topicDto.setStatus(Status.Stopped);
        return topicMapper.saveTopic(topicDto.toEntity());
    }

    // 토픽 전체 조회
    @Transactional
    public List<TopicDto> getAllTopic() {
        List<Topic> topicList = topicMapper.getAllTopic();
        List<TopicDto> topicDtoList = new ArrayList<>();

        for (Topic topic : topicList) {
            TopicDto topicDto = TopicDto.builder()
                    .id(topic.getId())
                    .topicName(topic.getTopicName())
                    .monitoringName(topic.getMonitoringName())
                    .ip(topic.getIp())
                    .port(topic.getPort())
                    .status(topic.getStatus())
                    .build();
            topicDtoList.add(topicDto);
        }
        return topicDtoList;
    }

    // 토픽 삭제
    @Transactional
    public Long deleteTopic(Long id){
        topicMapper.deleteTopic(id);
        return id;
    }

    // 토픽 수정
    @Transactional
    public Long updateTopic(Long id, TopicDto topicDto){
        topicDto.setId(id);
        topicMapper.updateTopic(topicDto.toEntity());
        return topicDto.getId();
    }

    // 토픽 id로 찾기
    public TopicDto findById(Long id){
        Topic topic = topicMapper.getTopicById(id);
        TopicDto topicDto = TopicDto.builder()
                .id(topic.getId())
                .topicName(topic.getTopicName())
                .monitoringName(topic.getMonitoringName())
                .ip(topic.getIp())
                .port(topic.getPort())
                .status(topic.getStatus())
                .build();
        return topicDto;
    }

    // 토픽명 중복 확인
    public boolean checkTopicName(String name){
        Topic topic = topicMapper.getTopicByName(name);
        if (topic == null){
            return false;
        }
        else{
            return true;
        }
    }

    // 토픽 상태 변경
    public Long updateStatus(Long id, TopicDto topicDto, Status status) {
        topicDto.setId(id);
        topicDto.setStatus(status);
        topicMapper.updateStatus(topicDto.toEntity());
        return topicDto.getId();
    }
}
