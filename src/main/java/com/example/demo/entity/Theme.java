package com.example.demo.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
public class Theme {
    Integer id;
    String title;
    Integer fullScore;
    List<Practice> practices;
    List<Exercise> exercises;
    List<Activity> activities;
}