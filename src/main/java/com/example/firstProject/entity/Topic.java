package com.example.firstProject.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String topicName;

    @Column
    private String monitoringName;

    @Column
    private String ip;

    @Column
    private Integer port;

    @Column
    @Enumerated(EnumType.STRING)
    private Status status;

    @Builder
    public Topic(Long id, String topicName, String monitoringName, String ip, Integer port, Status status) {
        this.id = id;
        this.topicName = topicName;
        this.monitoringName = monitoringName;
        this.ip = ip;
        this.port = port;
        this.status = status;
    }
}
