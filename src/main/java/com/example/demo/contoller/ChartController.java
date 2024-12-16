package com.example.demo.contoller;

import com.example.demo.service.ChartService;
import com.example.demo.service.GroupEntityService;
import jakarta.servlet.http.HttpServletResponse;
import org.jfree.chart.ChartUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.io.OutputStream;

@Controller()
@RequestMapping("/charts")
public class ChartController {
    private final ChartService chartService;
    private final GroupEntityService groupEntityService;

    public ChartController(ChartService chartService, GroupEntityService groupEntityService) {
        this.chartService = chartService;
        this.groupEntityService = groupEntityService;
    }

    @GetMapping("/main")
    public String getMainPage(Model model) {
        var groups = groupEntityService.findAll();
        model.addAttribute("groups", groups);
        return "charts/main";
    }

    @GetMapping("/piechart")
    public void getPieChart(HttpServletResponse response) throws IOException {
        response.setContentType("image/png");
        try (OutputStream out = response.getOutputStream()) {
            ChartUtils.writeChartAsPNG(out, chartService.setJFreeChartForCirlce().getChart(), 600, 400);
        }
    }

    @GetMapping("/mouth-chart")
    public String getMouthChart(){
        return "charts/mouth-chart";
    }

    @GetMapping("/mouth-chart-dot")
    public String getMouthChartDot(){
        return "charts/mouth-chart-dot";
    }

    @GetMapping("/student/{id}")
    public void getStudentChart(HttpServletResponse response, @PathVariable int id) throws IOException{
        response.setContentType("image/png");
        try (OutputStream out = response.getOutputStream()) {
            ChartUtils.writeChartAsPNG(out, chartService.setJFreeChartById(id), 1000, 600);
        }
    }

    @GetMapping("/mouth-chart-dot/image-all")
    public void generateScatterPlot(HttpServletResponse response) throws IOException {
        response.setContentType("image/png");
        ChartUtils.writeChartAsPNG(response.getOutputStream(), chartService.setScatterPlot(), 1000, 600);
    }

    @GetMapping("/mouth-chart-dot/image-home")
    public void getMouthChartDotImageHome(HttpServletResponse response) throws IOException {
        response.setContentType("image/png");
        try (OutputStream out = response.getOutputStream()) {
            ChartUtils.writeChartAsPNG(out, chartService.setJFreeChartDotForMouthHome(), 1000, 600);
        }
    }

    @GetMapping("/mouth-chart-dot/image-question")
    public void getMouthChartDotImageQuestion(HttpServletResponse response) throws IOException {
        response.setContentType("image/png");
        try (OutputStream out = response.getOutputStream()) {
            ChartUtils.writeChartAsPNG(out, chartService.setJFreeChartDotForMouthQuestion(), 1000, 600);
        }
    }

    @GetMapping("/mouth-chart-dot/image-exercises")
    public void getMouthChartDotImageExercises(HttpServletResponse response) throws IOException {
        response.setContentType("image/png");
        try (OutputStream out = response.getOutputStream()) {
            ChartUtils.writeChartAsPNG(out, chartService.setJFreeChartDotForMouthExercises(), 1000, 600);
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