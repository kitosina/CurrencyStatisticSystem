package ru.kitosins.sibsutis.currency.service;

import ru.kitosins.sibsutis.currency.entity.Users;

/**
 * UsersService special method for:
 * @see UsersServiceImpl
 * @author kitosina
 * @version 0.1
 */
public interface UsersService {

    /**
     * This method save user in DB by:
     * @param users
     * @return Byte
     */
    Byte save(Users users);

}
