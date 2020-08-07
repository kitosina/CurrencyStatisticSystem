package ru.kitosins.sibsutis.currency.service;

import ru.kitosins.sibsutis.currency.api.entity.ApiAnswer;
import ru.kitosins.sibsutis.currency.api.entity.ParamRequestUpdateDateClient;
import ru.kitosins.sibsutis.currency.entity.Currency;

import java.util.List;

public interface CurrencyUpdatable {

    List<Currency> update(ParamRequestUpdateDateClient paramRequestUpdateDateClient);

}
