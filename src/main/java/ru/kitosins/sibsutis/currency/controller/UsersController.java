package ru.kitosins.sibsutis.currency.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kitosins.sibsutis.currency.entity.Users;
import ru.kitosins.sibsutis.currency.service.UsersService;
import ru.kitosins.sibsutis.currency.service.UsersServiceImpl;

@RestController
@RequestMapping("/user")
public class UsersController {

    private UsersServiceImpl usersService;

    @Autowired
    public UsersController(UsersServiceImpl usersService) {
        this.usersService = usersService;
    }

    @PostMapping("/registration")
    public ResponseEntity registration(@RequestBody Users users) {
        return ResponseEntity.ok(usersService.save(users));
    }
}
