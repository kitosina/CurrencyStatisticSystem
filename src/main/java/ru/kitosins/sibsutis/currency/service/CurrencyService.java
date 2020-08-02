package ru.kitosins.sibsutis.currency.service;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.kitosins.sibsutis.currency.api.entity.AnswerLatestData;
import ru.kitosins.sibsutis.currency.entity.Currency;
import ru.kitosins.sibsutis.currency.repository.CurrencyRepository;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class CurrencyService implements CurrencyUpdatable {

    private CurrencyRepository currencyRepository;

    private RestTemplate restTemplate = new RestTemplate();
//    private static final String API_1 = "https://api.exchangeratesapi.io/2010-01-01?symbols=RUB&base=USD";
//    private String testApi = "https://api.exchangeratesapi.io/history?start_at=2015-01-01&end_at=2020-08-02&symbols=USD&base=EUR";
//    https://api.exchangeratesapi.io/history?start_at=2015-01-01&end_at=2020-08-02&symbols=RUB&base=USD
//    https://api.exchangeratesapi.io/history?start_at=2015-01-01&end_at=2020-08-02&symbols=RUB&base=EUR
//    https://api.exchangeratesapi.io/history?start_at=2015-01-01&end_at=2020-08-02&symbols=USD&base=EUR

    //URL которые нужны:
    /*
    1)https://api.exchangeratesapi.io/latest?symbols=RUB&base=USD
    2)https://api.exchangeratesapi.io/latest?symbols=RUB,USD&base=EUR
     */

    private static final String API = "https://api.exchangeratesapi.io/latest";
//    private static final String API_2 = "https://api.exchangeratesapi.io/latest?symbols=RUB&base=USD";

    @Autowired
    public CurrencyService(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    public Currency save(Currency currency) {
        return currencyRepository.save(currency);
    }

    public List<Currency> findAll() {
        return currencyRepository.findAll();
    }

    @Override
    public ResponseEntity update(@NonNull String symbols, @NonNull String base) {
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
        if (Objects.isNull(currencyRepository.findByDateAndBasicTitleCurrencyAndQuotedTitleCurrency(date, base, symbols))) {
            return ResponseEntity.ok(currencyRepository.save(currencyLoadDate));
        } else return ResponseEntity.noContent().build();
    }

    public void delete(Long id) {
        currencyRepository.deleteById(id);
    }

//    public AnswerLatestData get() {
//        return restTemplate.getForEntity(test, AnswerLatestData.class).getBody();
//    }

//    public AnswerApi get() {
//        Long Itearto = 2854L;
//        AnswerApi api = restTemplate.getForEntity(testApi, AnswerApi.class).getBody();
//        Set<Date> data = api.getRates().keySet();
//        for (Date key : data) {
//            Itearto++;
//            Currency currency = new Currency();
//            currency.setId(Itearto);
//            currency.setDate(key);
//            currency.setValue(api.getRates().get(key).get("USD"));
//            currency.setBasicTitleCurrency("EUR");
//            currency.setQuotedTitleCurrency("USD");
//            currencyRepository.save(currency);
//        }
//        return api;
//    }
}
