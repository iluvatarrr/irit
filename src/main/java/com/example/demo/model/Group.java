package com.example.demo.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
public class Group {

    String title;

    @Override
    public String toString() {
        return "Группа - %s".formatted(title);
    }
}