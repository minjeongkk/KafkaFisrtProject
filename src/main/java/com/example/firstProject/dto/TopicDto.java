package com.example.firstProject.dto;


import com.example.firstProject.entity.Status;
import com.example.firstProject.entity.Topic;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class TopicDto {
    private Long id;
    private String topicName;
    private String monitoringName;
    private String ip;
    private Integer port;
    private Status status;

    public Topic toEntity() {
        Topic topic = Topic.builder()
                .id(id)
                .topicName(topicName)
                .monitoringName(monitoringName)
                .ip(ip)
                .port(port)
                .status(Status.STOPPED)
                .build();
        return topic;
    }

    @Builder
    public TopicDto(Long id, String topicName, String monitoringName, String ip, Integer port, Status status) {
        this.id = id;
        this.topicName = topicName;
        this.monitoringName = monitoringName;
        this.ip = ip;
        this.port = port;
        this.status = status;
    }
}
