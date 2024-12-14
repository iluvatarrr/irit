package com.example.demo.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
public class ItemData {
    int  exerciseMax;
    int homeTaskMax;
    int questionMax;

    @Override
    public String toString() {
        return "Данные: Баллы за УПР - %s. Баллы за УПР - %s. Баллы за УПР - %s.".formatted(exerciseMax, homeTaskMax, questionMax);
    }
}