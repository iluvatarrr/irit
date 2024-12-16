package com.example.demo.service;

import com.example.demo.entity.ItemDataEntity;
import com.example.demo.entity.StudentEntity;
import com.example.demo.entity.ThemeEntity;
import com.example.demo.model.Month;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.time.ZoneId;
import java.util.List;

@Service
public class ChartService {

    private final StudentEntityService studentEntityService;
    private List<StudentEntity> students;
    private List<StudentEntity> studentsWithBirthDay;

    @Autowired
    public ChartService(StudentEntityService studentEntityService) {
        this.studentEntityService = studentEntityService;
    }

    private void updateData() {
        students = studentEntityService.findAll();
        studentsWithBirthDay = students.stream().filter(s -> s.getBirthday() != null).toList();
    }

    private DefaultPieDataset setDataForCircle() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        if (students == null) updateData();
        dataset.setValue("Найдены", studentsWithBirthDay.size());
        dataset.setValue("Не найдены", students.stream().filter(s -> s.getBirthday() == null).count());
        return dataset;
    }

    public JFreeChart setJFreeChartById(int id) {
        if (students != null) updateData();
        var s = studentEntityService.findByIdLocal(id);
        XYSeriesCollection dataset = createDataset(s);
        JFreeChart areaChart = ChartFactory.createXYAreaChart(
                "Отношение полученных баллов к максимальным баллам",
                "Номер темы",
                "Балл за тему",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false);
        return areaChart;
    }

    private XYSeriesCollection createDataset(StudentEntity studentEntity) {
        if (students == null) updateData();
        var series1 = new XYSeries("Результаты Ученика");
        var studentScore = studentEntity.getThemeList().stream().map(ThemeEntity::getFullScoreByPerson).map(s -> s.getQuestionMax() + s.getExerciseMax() + s.getHomeTaskMax()).skip(1).toList();
        var taskScore = studentEntity.getThemeList().stream().map(ThemeEntity::getFullScoreByItem).map(s -> s.getQuestionMax() + s.getExerciseMax() + s.getHomeTaskMax()).skip(1).toList();
        for (var i =0; i < studentScore.size()-1; i++) {
            series1.add(i + 1, studentScore.get(i));
        }
        var series2 = new XYSeries("Максимальный балл за тему");
        for (var i =0; i < taskScore.size()-1; i++) {
            series2.add(i + 1, taskScore.get(i));
        }
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series1);
        dataset.addSeries(series2);
        return dataset;
    }

    public ChartPanel setJFreeChartForCirlce() {
        var chart = ChartFactory.createPieChart(
                "Отношение найденнык к ненайденным",
                setDataForCircle(),
                true,
                true,
                false
        );
        var plot = (PiePlot) chart.getPlot();
        plot.setSectionPaint("Найдены", Color.RED);
        plot.setSectionPaint("Не найдены", Color.ORANGE);
        return new ChartPanel(chart);
    }

    public JFreeChart setJFreeChartForMouthAll() {
        return setJFreeChartForMouth("Весь курс", setDataForMonthAll());
    }
    public JFreeChart setJFreeChartForMouthHome() {
        return setJFreeChartForMouth("Практики", setDataForMonthHome());
    }

    public JFreeChart setJFreeChartForMouthQuestion() {
        return setJFreeChartForMouth("Вопросы", setDataForMonthQuestion());
    }

    public JFreeChart setJFreeChartForMouthExercises() {
        return setJFreeChartForMouth("Упражнения", setDataForMonthExercises());
    }

    public JFreeChart setJFreeChartForMouth(String s, CategoryDataset categoryDataset) {
        return ChartFactory.createBarChart(
                "Среднее значение успеваемости по месяцам за %s".formatted(s),
                "Месяцы",
                "Баллы",
                categoryDataset
        );
    }

    private CategoryDataset setDataForMonthAll() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (var m : Month.values()) {
            var itemsData = getItemDataForMonth(m).stream().map(i -> i.getExerciseMax() + i.getHomeTaskMax() + i.getQuestionMax()).toList();
            dataset.addValue((double) itemsData.stream().reduce(Integer::sum).get() / itemsData.size(), "Максимальные баллы за курс", m.getName());
        }
        return dataset;
    }

    private CategoryDataset setDataForMonthHome() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (var m : Month.values()) {
            var itemsData = getItemDataForMonth(m).stream().map(ItemDataEntity::getHomeTaskMax).toList();
            dataset.addValue((double) itemsData.stream().reduce(Integer::sum).get() / itemsData.size(), "Максимальные баллы за курс", m.getName());
        }
        return dataset;
    }

    private CategoryDataset setDataForMonthQuestion() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (var m : Month.values()) {
            var itemsData = getItemDataForMonth(m).stream().map(ItemDataEntity::getQuestionMax).toList();
            dataset.addValue((double) itemsData.stream().reduce(Integer::sum).get() / itemsData.size(), "Максимальные баллы за курс", m.getName());
        }
        return dataset;
    }

    private CategoryDataset setDataForMonthExercises() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (var m : Month.values()) {
            var itemsData = getItemDataForMonth(m).stream().map(i -> i.getExerciseMax()).toList();
            dataset.addValue((double) itemsData.stream().reduce(Integer::sum).get() / itemsData.size(), "Максимальные баллы за курс", m.getName());
        }
        return dataset;
    }

    private List<ItemDataEntity> getItemDataForMonth(Month m) {
        if (students == null) updateData();
        var studentdWithMouth = studentsWithBirthDay.stream()
                .filter(s -> s.getBirthday()
                        .toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate()
                        .getMonth().getValue() == m.getNumber()).toList();

        return studentdWithMouth.stream()
                .flatMap(s -> s.getThemeList()
                        .stream()
                        .filter(t -> t.getTitle().equals("За весь курс"))
                        .map(ThemeEntity::getFullScoreByPerson).limit(1)).toList();
    }

    public JFreeChart setScatterPlot() {
        XYSeriesCollection dataset = getXySeriesCollectionAll();
        return setJFreeChartDotForMouth("Весь курс",dataset);
    }

    private XYSeriesCollection getXySeriesCollectionAll() {
        XYSeries series = new XYSeries("Максимальный балл за курс");
        for (var m : Month.values()) {
            var itemsData = getItemDataForMonth(m).stream().map(i -> i.getExerciseMax() + i.getHomeTaskMax() + i.getQuestionMax()).toList();
            for (var s : itemsData) {
                series.add(m.getNumber(), s);
            }
        }
        return new XYSeriesCollection(series);
    }

    private XYSeriesCollection getXySeriesCollectionHome() {
        XYSeries series = new XYSeries("Максимальный балл за курс");
        for (var m : Month.values()) {
            var itemsData = getItemDataForMonth(m).stream().map(ItemDataEntity::getHomeTaskMax).toList();
            for (var s : itemsData) {
                series.add(m.getNumber(), s);
            }
        }
        return new XYSeriesCollection(series);
    }
    private XYSeriesCollection getXySeriesCollectionQuestion() {
        XYSeries series = new XYSeries("Максимальный балл за курс");
        for (var m : Month.values()) {
            var itemsData = getItemDataForMonth(m).stream().map(ItemDataEntity::getQuestionMax).toList();
            for (var s : itemsData) {
                series.add(m.getNumber(), s);
            }
        }
        return new XYSeriesCollection(series);
    }

    private XYSeriesCollection getXySeriesCollectionExercises() {
        XYSeries series = new XYSeries("Максимальный балл за курс");
        for (var m : Month.values()) {
            var itemsData = getItemDataForMonth(m).stream().map(ItemDataEntity::getExerciseMax).toList();
            for (var s : itemsData) {
                series.add(m.getNumber(), s);
            }
        }
        return new XYSeriesCollection(series);
    }

    public JFreeChart setJFreeChartDotForMouthHome() {
        return setJFreeChartDotForMouth("Практики", getXySeriesCollectionHome());
    }

    public JFreeChart setJFreeChartDotForMouthQuestion() {
        return setJFreeChartDotForMouth("Вопросы", getXySeriesCollectionQuestion());
    }

    public JFreeChart setJFreeChartDotForMouthExercises() {
        return setJFreeChartDotForMouth("Упражнения", getXySeriesCollectionExercises());
    }

    public JFreeChart setJFreeChartDotForMouth(String s, XYSeriesCollection categoryDataset) {
        return ChartFactory.createScatterPlot(
                "Значения успеваемости по месяцам за %s".formatted(s),
                "Номера месяцев",
                "Баллы",
                categoryDataset
        );
    }
}