package com.example.demo.entity;

import com.example.demo.repository.VkRepository;
import com.example.demo.util.Parser;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.enums.GroupsSort;
import com.vk.api.sdk.objects.groups.Group;
import com.vk.api.sdk.objects.users.Fields;
import com.vk.api.sdk.objects.users.UserFull;
import lombok.SneakyThrows;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public class main {
    @SneakyThrows
    public static void main(String[] args) {
        //вывод человека с полными данными перенес в контроллер для нормального просмотра в json
        var p = new Parser();
        List<Student> students = p.mainParse("src/main/resources/static/schema/itisbaase.csv", "Java Project", 1);
        System.out.println(students.getFirst());
        students = enrichStudents(students, new VkRepository());
//        System.out.println(students.getFirst());
    }

    @SneakyThrows
    public static List<Student> enrichStudents(List<Student> students, VkRepository vk) {
        for (var s : students) {
            List<UserFull> list = vk.getUserWithName(s.getName(), Fields.UNIVERSITIES, Fields.BDATE);
            Group group = vk.getUserByMainGroup("Третий курс ИОТ, УрФУ");
            Optional<UserFull> userFullOptional = null;
            Optional<UserFull> userFullOptionalLight = vk.getUserByUniversityName(list, "УрФУ им. первого Президента России Б. Н. Ельцина");
            //если легкая, но непопулярная операция совершена успешно, то более производительнуюю не вызываем
            userFullOptional = (userFullOptionalLight.isPresent()) ? userFullOptionalLight : vk.getUserByGroupName(list, group);
            if (userFullOptional.isPresent()) {
                var user = userFullOptional.get();
                s.setBirthday(user.getBdate());
                System.out.printf("Студент(ка) %s %s найден(а). Дата рождения: %s \n",user.getFirstName(), user.getLastName(), s.getBirthday());
            } else {
                System.out.printf("Студент(ка) %s не найден(а)%n", s.getName());
            }
        }
        return students;
    }
}