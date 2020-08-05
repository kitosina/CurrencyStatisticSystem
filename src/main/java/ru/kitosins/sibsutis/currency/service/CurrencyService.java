package ru.kitosins.sibsutis.currency.service;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.kitosins.sibsutis.currency.api.entity.AnswerLatestData;
import ru.kitosins.sibsutis.currency.entity.Currency;
import ru.kitosins.sibsutis.currency.repository.CurrencyRepository;

import java.util.*;

@Slf4j
@Service
public class CurrencyService implements CurrencyUpdatable {

    private CurrencyRepository currencyRepository;
    private RestTemplate restTemplate = new RestTemplate();
    private static final String API = "https://api.exchangeratesapi.io/latest";

    @Autowired
    public CurrencyService(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    public List<Currency> findAll() {
        return currencyRepository.findAll();
    }

    @Override
    public Currency update(@NonNull String symbols, @NonNull String base) {
        Long id = currencyRepository.findMaxId()+1;
        Currency currencyLoadDate = new Currency();
        String apiRequest = API.concat("?symbols=").concat(symbols).concat("&base=").concat(base);
        AnswerLatestData answerLatestData = restTemplate.getForEntity(apiRequest, AnswerLatestData.class).getBody();
        Date date = answerLatestData.getDate();
        Double value = Objects.isNull(answerLatestData.getRates().getRub())
                ? Objects.isNull(answerLatestData.getRates().getEur())
                ? answerLatestData.getRates().getUsd()
                : answerLatestData.getRates().getEur()
                : answerLatestData.getRates().getRub();
        currencyLoadDate.setId(id);
        currencyLoadDate.setQuotedTitleCurrency(symbols);
        currencyLoadDate.setBasicTitleCurrency(base);
        currencyLoadDate.setDate(date);
        currencyLoadDate.setValue(value);
        return Objects.isNull(currencyRepository.findByDateAndBasicTitleCurrencyAndQuotedTitleCurrency(date, base, symbols))
                ? currencyRepository.save(currencyLoadDate)
                : null;
    }

    //code style check
    public List<Currency> findByDateGreaterThanEqualAndDateLessThanEqualAndQuotedTitleCurrencyAndBasicTitleCurrency(
            String dateAfter, String dateBefore, String quotedTitleCurrency, String basicTitleCurrency) {

        String dateAfterFromTime = dateAfter.concat(" 00:00:00+0000");
        String dateBeforeFromTime = dateBefore.concat(" 00:00:00+0000");
        return currencyRepository.findByDateGreaterThanEqualAndDateLessThanEqualAndQuotedTitleCurrencyAndBasicTitleCurrency(
                dateAfterFromTime, dateBeforeFromTime, quotedTitleCurrency, basicTitleCurrency);
    }

}
