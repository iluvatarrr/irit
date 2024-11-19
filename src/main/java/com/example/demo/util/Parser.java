package com.example.demo.util;

import com.example.demo.entity.*;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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
            result.add(new Student(countId, name, " ",group, ulearnId, new ItemData(countId, studentExercise, studentHomeTask, studentQuestion),
                    new Course(courseId,courseName,
                            new ItemData(courseId, courseMaxExercise, courseMaxHomeTasks, courseMaxQuestion),
                            parseTheme(stringsOfCSV, themes, tasks, tasksMax))));
            countId++;
        }
        return result;
    }

    private static ArrayList<Theme> parseTheme(String[] dataOfUser, String[] themes, String[] tasks, String[] tasksMax) {
        var result = new ArrayList<Theme>();
        var themeName = themes[6];
        var lastTheme = "";
        var typeOfActive = "";
        var currentTasks = new ArrayList<Task>();
        ItemData fullScoreByPerson = new ItemData();
        ItemData fullScoreByItem = new ItemData();
        var counterTasks = 1;
        Theme theme = null;
        for (var i = 6; i < dataOfUser.length; i++) {
            if (!themes[i].isEmpty() && !Objects.equals(themeName, lastTheme)) {
                if (theme!=null) {
                    lastTheme = themeName;
                    var themeId = Integer.parseInt(themeName.split("\\.")[0]);
                    mapTheme(theme, themeId, themeName, fullScoreByPerson ,fullScoreByItem, currentTasks);
                    result.add(theme);
                    currentTasks = new ArrayList<>();
                    fullScoreByPerson = new ItemData();
                    fullScoreByItem = new ItemData();
                    counterTasks = 1;
                }
                theme = new Theme();
                themeName = themes[i];
            }
            if (List.of("КВ", "УПР", "ДЗ").contains(tasks[i]) && (themeName.equals(themes[i]) || themes[i].isEmpty())) {
                typeOfActive = tasks[i];
                switch (typeOfActive) {
                    case "КВ":
                        fullScoreByItem.setQuestionMax(Integer.parseInt(tasksMax[i]));
                        fullScoreByPerson.setQuestionMax(Integer.parseInt(dataOfUser[i]));
                        break;
                    case "ДЗ":
                        fullScoreByItem.setHomeTaskMax(Integer.parseInt(tasksMax[i]));
                        fullScoreByPerson.setHomeTaskMax(Integer.parseInt(dataOfUser[i]));
                        break;
                    case "УПР":
                        fullScoreByItem.setExerciseMax(Integer.parseInt(tasksMax[i]));
                        fullScoreByPerson.setExerciseMax(Integer.parseInt(dataOfUser[i]));
                        break;
                }
            }
            if (themes[i].isEmpty() && !List.of("КВ", "УПР", "ДЗ").contains(tasks[i])) {
                currentTasks.add(new Task(counterTasks, tasks[i], TaskType.valueOf(typeOfActive), Integer.parseInt(tasksMax[i]),Integer.parseInt(dataOfUser[i])));
                counterTasks++;
            }
        }
        assert theme != null;
        mapTheme(theme, result.size() + 1, themeName, fullScoreByPerson, fullScoreByItem, currentTasks);
        result.add(theme);
        return result;
    }

    private static void mapTheme(Theme theme, int themeId, String themeName,ItemData fullScoreByPerson, ItemData fullScoreByItem, ArrayList<Task> currentTasks) {
        theme.setId(themeId);
        theme.setTitle(themeName);
        fullScoreByItem.setId(themeId);
        fullScoreByPerson.setId(themeId);
        theme.setFullScoreByItem(fullScoreByItem);
        theme.setFullScoreByPerson(fullScoreByPerson);
        theme.setTasks(currentTasks);
    }
}