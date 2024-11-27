package com.example.demo.service;

import com.example.demo.entity.*;
import com.example.demo.model.Student;
import com.example.demo.model.Theme;
import com.example.demo.repository.StudentEntityRepository;
import com.example.demo.util.Parser;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class StudentEntityService {

    private final StudentEntityRepository studentEntityRepository;
    private final GroupEntityService groupEntityService;
    private final Parser parser;
    private final ModelMapper modelMapper;
    private final VkService vkService;

    @Autowired
    public StudentEntityService(StudentEntityRepository studentEntityRepository, GroupEntityService groupEntityService, Parser parser, ModelMapper modelMapper, VkService vkService) {
        this.studentEntityRepository = studentEntityRepository;
        this.groupEntityService = groupEntityService;
        this.parser = parser;
        this.modelMapper = modelMapper;
        this.vkService = vkService;
    }

    public List<Student> findAll() {
        return studentEntityRepository.findAll().stream().map(this::createToDTO).toList();
    }
    public Student findById(Integer id) {
        return createToDTO(studentEntityRepository.findById(id).orElseGet(StudentEntity::new));
    }

    @Transactional
    public void save(StudentEntity studentEntity) {
        studentEntityRepository.save(studentEntity);
    }

    @Transactional
    public void saveStudents(List<Student> students) {
        students = updateByVk(students);
        for (var student : students) {
            var studentEntity = createFromDTO(student);
            save(studentEntity);
        }
    }

    private List<Student> updateByVk(List<Student> students) {
        return vkService.enrichStudents(students);
    }

    @SneakyThrows
    public List<Student> getParseData(String path) {
        return parser.mainParse(path);
    }

    private StudentEntity createFromDTO(Student student) {
        var s = modelMapper.map(student, StudentEntity.class);
        GroupEntity group = groupEntityService.findOrCreateGroup(student.getGroup());
        group.getStudentEntities().add(s);
        s.setGroupEntity(group);
        var themeList = student.getThemeList().stream().map(t -> {
            var theme = modelMapper.map(t, ThemeEntity.class);

            var tasks = t.getTasks().stream().map(z -> modelMapper.map(z, TaskEntity.class)).toList();
            theme.setTasks(tasks);
            tasks.forEach(tt -> tt.setThemeEntity(theme));

            var personFullScoreEntity = modelMapper.map(t.getFullScoreByPerson(), ItemDataEntity.class);
            theme.setFullScoreByPerson(personFullScoreEntity);
            personFullScoreEntity.setThemeEntity(theme);

            var taskFullScoreEntity = modelMapper.map(t.getFullScoreByItem(), ItemDataTaskEntity.class);
            theme.setFullScoreByItem(taskFullScoreEntity);
            taskFullScoreEntity.setThemeEntity(theme);

            return theme;
        }).toList();

        themeList.forEach(t -> t.setStudentEntity(s));
        s.setThemeList(themeList);
        return s;
    }

    private Student createToDTO(StudentEntity studentEntity) {
        var mappedEntity = modelMapper.map(studentEntity, Student.class);
        mappedEntity.setGroup(studentEntity.getGroupEntity().getTitle());
        return mappedEntity;
    }
}