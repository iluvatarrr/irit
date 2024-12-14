package com.example.demo.entity;

import com.example.demo.model.TaskType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name="task")
@ToString(exclude = "themeEntity")
public class TaskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column
    String title;

    @Column
    @Enumerated(EnumType.STRING)
    TaskType type;

    @Column(name = "max_score")
    Integer maxScore;

    @Column
    Integer score;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "theme_id")
    ThemeEntity themeEntity;
}