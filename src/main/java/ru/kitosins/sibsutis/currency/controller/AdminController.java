package ru.kitosins.sibsutis.currency.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.bind.annotation.*;
import ru.kitosins.sibsutis.currency.service.CurrencyServiceImpl;
import ru.kitosins.sibsutis.currency.service.UsersServiceImpl;

/**
 * Admin controller class
 * Processing admin request
 * @author kitosina
 * @version 0.1
 * @see Slf4j
 * @see RestController
 * @see RequestMapping
 */
@Slf4j
@RestController
@RequestMapping("/administration")
public class AdminController {

    /**
     * usersService for repository interaction
     */
    private UsersServiceImpl usersService;

    /**
     * currencyService for repository interaction
     */
    private CurrencyServiceImpl currencyService;

    /**
     *
     * This method injects UserServiceImpl object
     * This method injects CurrencyServiceImpl object
     * @see Autowired
     * @param usersService
     * @param currencyService
     */
    @Autowired
    public AdminController(UsersServiceImpl usersService, CurrencyServiceImpl currencyService) {
        this.usersService = usersService;
        this.currencyService = currencyService;
    }

    /**
     * This method getting a list of users
     * @see GetMapping
     * @return List Users in ResponseEntity
     */
    @GetMapping
    public ResponseEntity findAll() {
        log.info("Output all user");
        return ResponseEntity.ok(usersService.findAll());
    }

    /**
     * This method delete user by username
     * @see PathVariable
     * @see DeleteMapping
     * @param username
     */
    @DeleteMapping("/delete/by/{username}")
    public void deleteByUsername(@PathVariable String username) {
        log.warn("Delete User" + username);
        usersService.deleteById(username);
    }

    /**
     * This method clear DB
     * @see DeleteMapping
     */
    @DeleteMapping("/clear")
    public void clear() {
        log.warn("Clear DB");
        currencyService.clear();
    }

}
