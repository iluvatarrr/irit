package com.example.demo.service;

import com.example.demo.model.Student;
import com.example.demo.repository.VkRepository;
import com.vk.api.sdk.objects.users.UserFull;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class VkService {

    private final VkRepository vkRepository;

    @Autowired
    public VkService(VkRepository vkRepository) {
        this.vkRepository = vkRepository;
    }

    @SneakyThrows
    public List<Student> enrichStudents(List<Student> students) {
        var c = 0;
        List<String> groups = List.of("Третий курс ИОТ, УрФУ", "Союз студентов УрФУ #posnews");
        List<Integer> groupsId = enrichGroupList(groups);
        var studentDct = new HashMap<String, UserFull>();
        for (var s : students) {
            var name = s.getName();
            for (var i = 0; i < groupsId.size() && studentDct.get(name) == null; i++) {
                var userFull = vkRepository.getUserByNameAndGroup(name, groupsId.get(i));
                if (userFull != null && !userFull.isEmpty()) {
                    var user = userFull.getFirst();
                    var bDate =  user.getBdate();
                    c = updateBirthdayModel(s, bDate, user, c);
                    studentDct.put(s.getName(), user);
                }
            }
            if (studentDct.get(name) == null) {
                System.out.printf("Студент(ка) %s не найден(а)%n", s.getName());
            }
            Thread.sleep(1000);
        }
        return students;
    }
    @SneakyThrows
    private static Date createBirthdayDate(String[] bDateArr) {
        var day = "";
        var month = "";
        var year = "";

        if (bDateArr.length == 2) {
            day = checkItemOfData(bDateArr[0]);
            month = checkItemOfData(bDateArr[1]);
            year = "9999";
        }

        if (bDateArr.length == 3) {
            day = checkItemOfData(bDateArr[0]);
            month = checkItemOfData(bDateArr[1]);
            year = bDateArr[2];
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        return formatter.parse(String.join("-", year,month,day));
    }

    private static String checkItemOfData(String bDateArr) {
        return bDateArr.length() < 2 ? "0" + bDateArr : bDateArr;
    }

    private static int updateBirthdayModel(Student s, String bDate, UserFull user, int c) {
        if (bDate != null) {
            var bDateArr = bDate.split("\\.");
            s.setBirthday(createBirthdayDate(bDateArr));
            System.out.println(++c);
            System.out.printf("Студент(ка) %s %s найден(а). День рождения: %s \n", user.getFirstName(), user.getLastName(), s.getBirthday());
        } else {
            System.out.printf("Студент(ка) %s %s найден(а). День рождения не указан. \n", user.getFirstName(), user.getLastName());
        }
        return c;
    }

    private List<Integer> enrichGroupList(List<String> groups) throws InterruptedException {
        var groupsId = new ArrayList<Integer>();
        for (var g : groups) {
            var groupz = vkRepository.getGroupByName(g);
            if (groupz != null && groupz.size() > 0) {
                groupsId.add(groupz.getFirst().getId());
            }
            Thread.sleep(1000);
        }
        return groupsId;
    }
}
