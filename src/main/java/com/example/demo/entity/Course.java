package com.example.demo.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
public class Course {
    Integer id;
    String title;
    ItemData allCourseScoreMax;
    List<Theme> themeList;
//    List<Student> studentList;

    @Override
    public String toString() {
        return "Курс: Название курса - %s.  Полный балл курса - %s".formatted(title, allCourseScoreMax);
    }
}
