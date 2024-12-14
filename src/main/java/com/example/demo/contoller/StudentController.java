package com.example.demo.contoller;

import com.example.demo.dto.StudentsDTO;
import com.example.demo.entity.StudentEntity;
import com.example.demo.model.Student;
import com.example.demo.service.GroupEntityService;
import com.example.demo.service.StudentEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller()
@RequestMapping("/students")
public class StudentController {

    private final StudentEntityService studentEntityService;
    private final GroupEntityService groupEntityService;
    @Autowired
    public StudentController(StudentEntityService studentEntityService, GroupEntityService groupEntityService) {
        this.studentEntityService = studentEntityService;
        this.groupEntityService = groupEntityService;
    }

    @GetMapping("/all")
    public String getAllStudents(Model model, @PageableDefault(size = 10) Pageable pageable) {
        Page<StudentsDTO> page = studentEntityService.findAll(pageable);
        model.addAttribute("items", page.getContent());
        model.addAttribute("currentPage", page.getNumber());
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("students", page.getContent());
        return "students/all";
    }

    @GetMapping("/{id}")
    public String  getStudent(@PathVariable int id, Model model) {
        model.addAttribute("student", studentEntityService.findById(id));
        return "students/show";
    }

    @PostMapping("/update")
    public String updateData() {
        if (!groupEntityService.findAll().isEmpty()) return "main/main";
        studentEntityService.saveStudents(studentEntityService.getParseData("src/main/resources/static/schema/itisbaase.csv"));
        return "main/main";
    }
}