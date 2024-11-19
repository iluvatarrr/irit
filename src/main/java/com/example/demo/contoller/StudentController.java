package com.example.demo.contoller;

import com.example.demo.entity.Student;
import com.example.demo.util.Parser;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
@RequestMapping("/students")
public class StudentController {

    @GetMapping
    @SneakyThrows
    public List<Student> getStudents() {
        var p = new Parser();
        return p.mainParse("src/main/resources/static/schema/itisbaase.csv", "Java Project", 1);
    }

    @SneakyThrows
    @GetMapping("/{id}")
    public Student getStudent(@PathVariable int id) {
        var p = new Parser();
        var students = p.mainParse("src/main/resources/static/schema/itisbaase.csv", "Java Project", 1);
        return students.get(id-1);
    }
}
