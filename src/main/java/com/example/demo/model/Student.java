package com.example.demo.model;

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

    String name;
    Date birthday;
    String group;
    String ulearn;
    List<Theme> themeList;

    @Override
    public String toString() {
        return "Студент - %s. %s Состоящий в группе - %s. %s Под id: %s. %s".formatted(name, "\n", group, "\n", ulearn,"\n");
    }
}