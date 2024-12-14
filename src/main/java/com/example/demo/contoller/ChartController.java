package com.example.demo.contoller;

import com.example.demo.model.Student;
import com.example.demo.service.ChartService;
import com.example.demo.service.StudentEntityService;
import jakarta.servlet.http.HttpServletResponse;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.OutputStream;

@Controller()
@RequestMapping("/charts")
public class ChartController {
    private final ChartService chartService;

    public ChartController(ChartService chartService) {
        this.chartService = chartService;
    }

    @GetMapping("/mouth-chart")
    public String getMouthChart(HttpServletResponse response){
        return "charts/mouth-chart";
    }

    @GetMapping("/student/{id}")
    public void getStudentChart(HttpServletResponse response, @PathVariable int id) throws IOException{
        response.setContentType("image/png");
        try (OutputStream out = response.getOutputStream()) {
            ChartUtils.writeChartAsPNG(out, chartService.setJFreeChartById(id), 1000, 600);
        }
    }

    @GetMapping("/mouth-chart/image-all")
    public void getMouthChartImageAll(HttpServletResponse response) throws IOException {
        response.setContentType("image/png");
        try (OutputStream out = response.getOutputStream()) {
            ChartUtils.writeChartAsPNG(out, chartService.setJFreeChartForMouthAll(), 1000, 600);
        }
    }

    @GetMapping("/mouth-chart/image-home")
    public void getMouthChartImageHome(HttpServletResponse response) throws IOException {
        response.setContentType("image/png");
        try (OutputStream out = response.getOutputStream()) {
            ChartUtils.writeChartAsPNG(out, chartService.setJFreeChartForMouthHome(), 1000, 600);
        }
    }

    @GetMapping("/mouth-chart/image-question")
    public void getMouthChartImageQuestion(HttpServletResponse response) throws IOException {
        response.setContentType("image/png");
        try (OutputStream out = response.getOutputStream()) {
            ChartUtils.writeChartAsPNG(out, chartService.setJFreeChartForMouthQuestion(), 1000, 600);
        }
    }

    @GetMapping("/mouth-chart/image-exercises")
    public void getMouthChartImageExercises(HttpServletResponse response) throws IOException {
        response.setContentType("image/png");
        try (OutputStream out = response.getOutputStream()) {
            ChartUtils.writeChartAsPNG(out, chartService.setJFreeChartForMouthExercises(), 1000, 600);
        }
    }
}