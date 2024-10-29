package com.example.demo.entity;


import lombok.*;
import lombok.experimental.FieldDefaults;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public abstract class Person {
    Integer id;
    String name;
    Date birthday;
}
