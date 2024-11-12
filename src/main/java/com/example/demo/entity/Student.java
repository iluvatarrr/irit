package com.example.demo.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
public class Student {
    Integer id;
    String name;
    Date birthday;
    String group;
    String ulearnId;
    ItemData allCourseScore;
    Course course;

    @Override
    public String toString() {
        return "Студент - %s. %s Состоящий в группе - %s. %s Под id: %s. %s За весь курс набрал: %s".formatted(name, "\n", group, "\n", ulearnId,"\n", allCourseScore);
    }
}