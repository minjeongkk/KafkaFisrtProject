package com.example.firstProject.entity;

import jakarta.persistence.*;
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
    private Status status;

}
