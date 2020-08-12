package ru.kitosins.sibsutis.currency.service;

import ru.kitosins.sibsutis.currency.entity.Users;

public interface UsersService {

    Users findByUsername(String username);

}
