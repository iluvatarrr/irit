package com.example.demo.contoller;

import com.example.demo.service.ChartService;
import com.example.demo.service.GroupEntityService;
import com.example.demo.service.StudentEntityService;
import jakarta.servlet.http.HttpServletResponse;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.io.OutputStream;

@Controller
@RequestMapping("/main")
public class MainController {
    private final GroupEntityService groupEntityService;
    private final ChartService chartService;
    @Autowired
    public MainController(GroupEntityService groupEntityService, ChartService chartService) {
        this.groupEntityService = groupEntityService;
        this.chartService = chartService;
    }

    @GetMapping("/main")
    public String getMainPage(Model model) {
        var groups = groupEntityService.findAll();
        model.addAttribute("groups", groups);
        return "main/main";
    }

    @GetMapping("/piechart")
    public void getPieChart(HttpServletResponse response) throws IOException {
        response.setContentType("image/png");
        try (OutputStream out = response.getOutputStream()) {
            ChartUtils.writeChartAsPNG(out, chartService.setJFreeChartForCirlce().getChart(), 600, 400);
        }
    }
}
