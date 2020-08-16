package ru.kitosins.sibsutis.currency.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


/**
 * View all templates
 * @author kitosina
 * @version 0.1
 * @see Controller
 */
@Controller
public class WebController {

    /**
     * Mapping for index page method
     * @see GetMapping
     * @return index page
     */
    @GetMapping("/")
    public String index() {
        return "/index";
    }

    /**
     * Mapping for date_statistics page method
     * @see GetMapping
     * @return date_statistics page
     */
    @GetMapping("/date_statistics")
    public String dateStatistics() {
        return "date_statistics";
    }

    /**
     * Mapping for actual_statistics page method
     * @see GetMapping
     * @return actual_statistics page
     */
    @GetMapping("/actual_statistics")
    public String actualStatistics() {
        return "actual_statistics";
    }

    /**
     * Mapping for converter page method
     * @see GetMapping
     * @return converter page
     */
    @GetMapping("/converter")
    public String converter() {
        return "converter";
    }

    /**
     * Mapping for fact page method
     * @see GetMapping
     * @return fact page
     */
    @GetMapping("/facts")
    public String facts() {
        return "fact";
    }

    /**
     * Mapping for login page method
     * @see GetMapping
     * @return login page
     */
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    /**
     * Mapping for registration page method
     * @see GetMapping
     * @return registration page
     */
    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    /**
     * Mapping for admin page method
     * @see GetMapping
     * @return admin page
     */
    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

}
