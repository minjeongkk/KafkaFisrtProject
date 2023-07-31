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

    // 토픽 id로 조회
    public Topic getTopicById(Long id);

    // 토픽 삭제
    public Long deleteTopic(Long id);

    // 토픽 수정
    public Long updateTopic(@Param("topic") Topic topic);

    // 토픽 상태 수정
    public Long updateStatus(@Param("topic") Topic topic);
}
