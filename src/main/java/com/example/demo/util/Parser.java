package com.example.demo.util;

import com.example.demo.entity.*;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class Parser {

    public List<Student> mainParse(String path, String courseName, Integer courseId) throws IOException, CsvValidationException {
        CSVParser csvParser = new CSVParserBuilder().withSeparator('\t').build();
        try (CSVReader csvReader = new CSVReaderBuilder(new FileReader(path)).withCSVParser(csvParser).build()) {
            var themes = csvReader.readNext();
            var tasks = csvReader.readNext();
            var tasksMax = csvReader.readNext();
            return fillResult(csvReader, courseName, courseId, themes, tasks, tasksMax);
        }
    }

    private static List<Student> fillResult(CSVReader csvReader, String courseName, Integer courseId, String[] themes, String[] tasks, String[] tasksMax) throws IOException, CsvValidationException {
        var countId = 1;
        var result = new ArrayList<Student>();
        var courseMaxExercise = Integer.parseInt(tasksMax[3]);
        var courseMaxHomeTasks = Integer.parseInt(tasksMax[4]);
        var courseMaxQuestion = Integer.parseInt(tasksMax[5]);
        String[] stringsOfCSV;
        while ((stringsOfCSV = csvReader.readNext()) != null) {
            var name = stringsOfCSV[0];
            var ulearnId = stringsOfCSV[1];
            var group = stringsOfCSV[2];
            var studentExercise = Integer.parseInt(stringsOfCSV[3]);
            var studentHomeTask = Integer.parseInt(stringsOfCSV[4]);
            var studentQuestion = Integer.parseInt(stringsOfCSV[5]);
            result.add(new Student(countId, name, new Date(),group, ulearnId, new Course(courseId,courseName,
                    new AllCourseData(courseId, courseMaxExercise, courseMaxHomeTasks, courseMaxQuestion), parseTheme(stringsOfCSV ,themes, tasks, tasksMax), null),
                    new AllCourseData(countId, studentExercise, studentHomeTask, studentQuestion)));
            countId++;
        }
        return result;
    }

    private static ArrayList<Theme> parseTheme(String[] dataOfUser, String[] themes, String[] tasks, String[] tasksMax) {
        var result = new ArrayList<Theme>();
        var themeName = "";
        var typeOfActive = "";
        var currentTasks = new ArrayList<Task>();
        var fullScoreQuestion = 0;
        var fullScoreHomeTask = 0;
        var fullScoreExercise = 0;
        var counterTasks = 1;
        for (var i = 6; i < dataOfUser.length; i++) {
            if (!themes[i].isEmpty() && themeName.isEmpty()) {
                themeName = themes[i];
            }
            if (List.of("КВ", "УПР", "ДЗ").contains(tasks[i])) {
                typeOfActive = tasks[i];
                switch (typeOfActive) {
                    case "КВ":
                        fullScoreQuestion = Integer.parseInt(dataOfUser[i]);
                        break;
                    case "ДЗ":
                        fullScoreHomeTask = Integer.parseInt(dataOfUser[i]);
                        break;
                    case "УПР":
                        fullScoreExercise = Integer.parseInt(dataOfUser[i]);
                        break;
                }
            }
            if (themes[i].isEmpty()) {
                currentTasks.add(new Task(counterTasks, tasks[i], TaskType.valueOf(typeOfActive), Integer.parseInt(tasksMax[i]),Integer.parseInt(dataOfUser[i])));
                counterTasks++;
            }

            if (!themes[i].isEmpty() && !themeName.isEmpty()) {
                var themeId = Integer.parseInt(themeName.split("\\.")[0]);
                result.add(new Theme(themeId,themeName, fullScoreQuestion, fullScoreHomeTask, fullScoreExercise, currentTasks));
                themeName = themes[i];
                currentTasks = new ArrayList<Task>();
                counterTasks = 1;
            }
        }
        return result;
    }
}

