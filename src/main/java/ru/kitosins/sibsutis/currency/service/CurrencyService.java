package ru.kitosins.sibsutis.currency.service;

import ru.kitosins.sibsutis.currency.api.entity.ParamRequestUpdateDateClient;
import ru.kitosins.sibsutis.currency.entity.Currency;

import java.util.List;

/**
 * CurrencyService special method for:
 * @see CurrencyServiceImpl
 * @author kitosina
 * @version 0.1
 */
public interface CurrencyService {

    /**
     * This method update data in DB by:
     * @param paramRequestUpdateDateClient
     * @return List Currency Object
     */
    List<Currency> update(ParamRequestUpdateDateClient paramRequestUpdateDateClient);

    /**
     * This method clear DB
     */
    void clear();

}
