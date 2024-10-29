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
    List<Theme> themeList;
    List<Student> studentList;
}
