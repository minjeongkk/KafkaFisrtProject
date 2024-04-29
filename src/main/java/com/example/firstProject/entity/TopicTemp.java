package com.example.firstProject.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class TopicTemp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String json;

    @Column
    private Long userId;

    @Builder
    public TopicTemp(Long id, String json, Long userId) {
        this.id = id;
        this.json = json;
        this.userId = userId;
    }

    public TopicTemp(String json, Long userId) {
        this.json = json;
        this.userId = userId;
    }
}
