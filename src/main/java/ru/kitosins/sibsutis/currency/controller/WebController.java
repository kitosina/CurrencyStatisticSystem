package ru.kitosins.sibsutis.currency.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping("/home")
    public String home() {
        return "index";
    }

    @GetMapping("/date_statistics")
    public String dateStatistics() {
        return "date_statistics";
    }

    @GetMapping("/actual_statistics")
    public String actualStatistics() {
        return "actual_statistics";
    }

    @GetMapping("/converter")
    public String converter() {
        return "converter";
    }

    @GetMapping("/facts")
    public String facts() {
        return "fact";
    }

}
