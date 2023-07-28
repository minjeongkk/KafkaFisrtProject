package com.example.firstProject.repository;

import com.example.firstProject.dto.TopicDto;
import com.example.firstProject.entity.Topic;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface TopicMapper {
    // 토픽 생성
    public Long saveTopic(@Param("topic") Topic topic);

    // 토픽 전체 조회
    public List<Topic> getAllTopic();
}
