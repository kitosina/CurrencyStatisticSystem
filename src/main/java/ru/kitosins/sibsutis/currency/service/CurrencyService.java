package ru.kitosins.sibsutis.currency.service;

import ru.kitosins.sibsutis.currency.api.entity.ApiAnswer;
import ru.kitosins.sibsutis.currency.api.entity.ParamRequestUpdateDateClient;
import ru.kitosins.sibsutis.currency.entity.Currency;

import java.util.Date;
import java.util.List;
import java.util.TreeSet;

public interface CurrencyService {

    List<Currency> update(ParamRequestUpdateDateClient paramRequestUpdateDateClient);

    void clear();

}
