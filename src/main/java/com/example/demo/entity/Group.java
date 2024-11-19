package com.example.demo.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
public class Group {
   Integer id;
   List<Student> students;

   @Override
   public String toString() {
      return "Группа - %s".formatted(id);
   }
}
