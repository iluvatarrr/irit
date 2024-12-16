package com.example.demo.dto;


import com.example.demo.model.Theme;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StudentsDTO {
    Integer id;
    String name;
    Date birthday;
    String group;
    String ulearn;
}
