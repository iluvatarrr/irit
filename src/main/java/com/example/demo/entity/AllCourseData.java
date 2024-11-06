package com.example.demo.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
public class AllCourseData {
    Integer id;
    Integer exerciseMax;
    Integer homeTaskMax;
    Integer questionMax;
}
