package ru.kitosins.sibsutis.currency.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kitosins.sibsutis.currency.service.CurrencyServiceImpl;
import ru.kitosins.sibsutis.currency.service.UsersServiceImpl;

import java.util.Objects;

@RestController
@RequestMapping("/administration")
public class AdminController {

    private UsersServiceImpl usersService;
    private CurrencyServiceImpl currencyService;

    @Autowired
    public AdminController(UsersServiceImpl usersService, CurrencyServiceImpl currencyService) {
        this.usersService = usersService;
        this.currencyService = currencyService;
    }

    @GetMapping
    public ResponseEntity findAll() {
        return ResponseEntity.ok(usersService.findAll());
    }

    @DeleteMapping("/delete/by/{username}")
    public void deleteByUsername(@PathVariable String username) {
        usersService.deleteById(username);
    }

    @DeleteMapping("/clear")
    public void clear() {
        currencyService.clear();
    }

}
