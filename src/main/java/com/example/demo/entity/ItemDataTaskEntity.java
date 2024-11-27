package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
@Entity
@Table(name = "full_score_task")
@ToString(exclude = "themeEntity")
public class ItemDataTaskEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "exercise_max")
    Integer exerciseMax;

    @Column(name = "home_task_max")
    Integer homeTaskMax;

    @Column(name = "question_max")
    Integer questionMax;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "theme_id", referencedColumnName = "id")
    ThemeEntity themeEntity;
}