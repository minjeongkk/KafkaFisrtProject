package com.example.firstProject.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
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
