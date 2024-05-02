package com.example.firstProject.repository;

import com.example.firstProject.entity.Topic;
import com.example.firstProject.entity.TopicTemp;
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

    // 토픽 중복 확인
    public Topic getTopicByName(String name);

    // 토픽 삭제
    public Long deleteTopic(Long id);

    // 토픽 수정
    public Long updateTopic(@Param("topic") Topic topic);

    // 토픽 상태 수정
    public Long updateStatus(@Param("topic") Topic topic);
    // 토픽 임시저장
    public void saveTemp(TopicTemp topicTemp);
    // 토픽 임시저장 불러오기
    public  String getTemp(Long userId);
    // 토픽 임시저장 작성자로 검색하여 불러오기
    public List<TopicTemp> getTopicTempByUserId(Long userId);
    // 토픽 임시저장 삭제
    public void deleteTopicTemp(Long id);
}
