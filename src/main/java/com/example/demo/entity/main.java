package com.example.demo.entity;

import com.example.demo.util.Parser;
import lombok.SneakyThrows;

import java.util.Date;
import java.util.List;

public class main {
    @SneakyThrows
    public static void main(String[] args) {
        //вывод человека с полными данными перенес в контроллер для нормального просмотра в json
        var p = new Parser();
        List<Student> students = p.mainParse("src/main/resources/static/schema/itisbaase.csv", "Java Project", 1);
        students.forEach(a -> System.out.println(a + "\n"));
        System.out.println(students.getFirst().getCourse());
        students.getFirst().getCourse().getThemeList().forEach(a -> System.out.println(a + "\n"));
    }
}
