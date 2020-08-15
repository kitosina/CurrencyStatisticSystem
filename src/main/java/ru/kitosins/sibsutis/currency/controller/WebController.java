package ru.kitosins.sibsutis.currency.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class WebController {

    @GetMapping("/")
    public String home() {
        return "/index";
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

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

}
