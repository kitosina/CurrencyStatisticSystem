package ru.kitosins.sibsutis.currency.service;

import java.io.IOException;

/**
 * UsersService special method for:
 * @see FactServiceImpl
 * @author kitosina
 * @version 0.2
 */
public interface FactService {

    /**
     * Loading data in DB method
     * @throws IOException
     */
    void loadingDataBase() throws IOException;

}
