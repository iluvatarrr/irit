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
public class Student extends Person {
    Integer groupId;
    List<Course> courses;
    public Student(Integer id, String name, Date birthday, Integer groupId, List<Course> courses) {
        super(id, name, birthday);
        this.groupId = groupId;
        this.courses = courses;
    }
}
