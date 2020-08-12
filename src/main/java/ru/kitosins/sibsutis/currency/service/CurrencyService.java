package ru.kitosins.sibsutis.currency.service;

import ru.kitosins.sibsutis.currency.api.entity.ApiAnswer;
import ru.kitosins.sibsutis.currency.api.entity.ParamRequestUpdateDateClient;
import ru.kitosins.sibsutis.currency.entity.Currency;

import java.util.Date;
import java.util.List;
import java.util.TreeSet;

public interface CurrencyService {

    List<Currency> findAll();

    TreeSet<Currency> findByDateGreaterThanEqualAndDateLessThanEqualAndBasicTitleCurrencyAndQuotedTitleCurrency(
            String dateAfter, String dateBefore, String basicTitleCurrency, String quotedTitleCurrency);

    List<Currency> update(ParamRequestUpdateDateClient paramRequestUpdateDateClient);

    Date findMaxDate(String basicTitleCurrency, String quotedTitleCurrency);

    Double converterValue(String basicTitleCurrency, String quotedTitleCurrency);

}
