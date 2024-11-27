package com.example.demo.contoller;

import com.example.demo.model.Student;
import com.example.demo.service.StudentEntityService;
import com.example.demo.util.Parser;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/students")
public class StudentController {

    private final StudentEntityService studentEntityService;

    @Autowired
    public StudentController(StudentEntityService studentEntityService) {
        this.studentEntityService = studentEntityService;
    }

    @GetMapping
    public List<Student> getAllStudents() {
        return studentEntityService.findAll();
    }

    @GetMapping("/{id}")
    public Student getStudent(@PathVariable int id) {
        return studentEntityService.findById(id);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> createTrain() {
        studentEntityService.saveStudents(studentEntityService.getParseData("src/main/resources/static/schema/itisbaase.csv"));
        return ResponseEntity.ok(HttpStatus.CREATED);
    }
}
