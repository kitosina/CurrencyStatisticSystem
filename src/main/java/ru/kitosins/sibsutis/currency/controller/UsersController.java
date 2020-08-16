package ru.kitosins.sibsutis.currency.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kitosins.sibsutis.currency.entity.Users;
import ru.kitosins.sibsutis.currency.service.UsersServiceImpl;

/**
 * Users controller class
 * Processing user request
 * @author kitosina
 * @version 0.1
 * @see Slf4j
 * @see RestController
 * @see RequestMapping
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UsersController {

    /**
     * usersService for repository interaction
     */
    private UsersServiceImpl usersService;

    /**
     * This method injects UsersServiceImpl object
     * @see Autowired
     * @param usersService
     */
    @Autowired
    public UsersController(UsersServiceImpl usersService) {
        this.usersService = usersService;
    }

    /**
     * This method save new user
     * @see PostMapping
     * @see RequestBody
     * @param users
     * @return Registration status
     */
    @PostMapping("/registration")
    public ResponseEntity registration(@RequestBody Users users) {
        log.warn("Registration new user: " + users.getUsername());
        return ResponseEntity.ok(usersService.save(users));
    }
}
