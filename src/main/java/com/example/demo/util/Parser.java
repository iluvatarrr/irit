package com.example.demo.util;

import com.example.demo.entity.Course;
import com.example.demo.entity.Task;
import com.example.demo.entity.Theme;
import com.example.demo.entity.Student;
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
        var coutId = 1;
        var result = new ArrayList<Student>();
        String[] stringsOfCSV;
        while ((stringsOfCSV = csvReader.readNext()) != null) {
            var name = stringsOfCSV[0];
            var ulearnId = stringsOfCSV[1];
            var group = stringsOfCSV[2];
            result.add(new Student(coutId, name, new Date(), ulearnId, group, new Course()));
            coutId++;
        }
        return result;
    }
}

