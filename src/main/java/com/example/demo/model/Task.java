package com.example.demo.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Task {
    String title;
    TaskType type;
    Integer maxScore;
    Integer score;

    @Override
    public String toString() {
        return "Задание - %s. Тип - %s. Максимальный балл - %s".formatted(title, type, maxScore);
    }
}