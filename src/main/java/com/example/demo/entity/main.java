package com.example.demo.entity;

import com.example.demo.util.Parser;
import lombok.SneakyThrows;

import java.util.Date;

public class main {
    @SneakyThrows
    public static void main(String[] args) {
        var p = new Parser();
        var a = p.mainParse("src/main/resources/static/schema/itisbaase.csv", "Java, ебана", 1);
        for (var b : a) {
            System.out.println(b);
        }
    }
}
