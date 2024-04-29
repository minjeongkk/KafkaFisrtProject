package com.example.firstProject.dto;


import com.example.firstProject.entity.TopicTemp;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class TopicTempDto {
    private Long id;
    private String json;
    private Long userId;

    public TopicTemp toEntity() {
        TopicTemp topicTemp = TopicTemp.builder()
                .id(id)
                .json(json)
                .userId(userId)
                .build();
        return topicTemp;
    }

    @Builder
    public TopicTempDto(Long id, String json, Long userId) {
        this.id = id;
        this.json = json;
        this.userId = userId;
    }

    public String convertToJson() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(this);
    }
}
