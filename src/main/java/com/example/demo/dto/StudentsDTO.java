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
public class StudentsDTO {
    public Integer id;
    public String name;
    public Date birthday;
    public String group;
    public String ulearn;
}
