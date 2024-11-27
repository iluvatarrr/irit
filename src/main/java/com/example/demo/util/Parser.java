package com.example.demo.util;

import com.example.demo.model.*;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import lombok.SneakyThrows;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Parser {

    public List<Student> mainParse(String path) throws IOException, CsvValidationException {
        CSVParser csvParser = new CSVParserBuilder().withSeparator('\t').build();
        try (CSVReader csvReader = new CSVReaderBuilder(new FileReader(path)).withCSVParser(csvParser).build()) {
            var themes = csvReader.readNext();
            var tasks = csvReader.readNext();
            var tasksMax = csvReader.readNext();
            return fillResult(csvReader, themes, tasks, tasksMax);
        }
    }

    @SneakyThrows
    private static List<Student> fillResult(CSVReader csvReader, String[] themes, String[] tasks, String[] tasksMax) {
        var result = new ArrayList<Student>();
        String[] stringsOfCSV;
        while ((stringsOfCSV = csvReader.readNext()) != null) {
            var name = stringsOfCSV[0];
            var ulearnId = stringsOfCSV[1];
            var group = stringsOfCSV[2];
            result.add(new Student(name, null, group, ulearnId,
                            parseTheme(stringsOfCSV, themes, tasks, tasksMax)));
        }
        return result;
    }

    private static ArrayList<Theme> parseTheme(String[] dataOfUser, String[] themes, String[] tasks, String[] tasksMax) {
        var result = new ArrayList<Theme>();
        var themeName = themes[3];
        var lastTheme = "";
        var typeOfActive = "";
        var currentTasks = new ArrayList<Task>();
        ItemData fullScoreByPerson = new ItemData();
        ItemData fullScoreByItem = new ItemData();
        Theme theme = null;
        for (var i = 3; i < dataOfUser.length; i++) {
            if (!themes[i].isEmpty() && !Objects.equals(themeName, lastTheme)) {
                if (theme!=null) {
                    lastTheme = themeName;
                    mapTheme(theme, themeName, fullScoreByPerson ,fullScoreByItem, currentTasks);
                    result.add(theme);
                    currentTasks = new ArrayList<>();
                    fullScoreByPerson = new ItemData();
                    fullScoreByItem = new ItemData();
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
                currentTasks.add(new Task(tasks[i], TaskType.valueOf(typeOfActive), Integer.parseInt(tasksMax[i]),Integer.parseInt(dataOfUser[i])));
            }
        }
        assert theme != null;
        mapTheme(theme, themeName, fullScoreByPerson, fullScoreByItem, currentTasks);
        result.add(theme);
        return result;
    }

    private static void mapTheme(Theme theme, String themeName, ItemData fullScoreByPerson, ItemData fullScoreByItem, ArrayList<Task> currentTasks) {
        theme.setTitle(themeName);
        theme.setFullScoreByItem(fullScoreByItem);
        theme.setFullScoreByPerson(fullScoreByPerson);
        theme.setTasks(currentTasks);
    }
}