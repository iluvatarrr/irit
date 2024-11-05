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
    Course courses;
}