package ru.kitosins.sibsutis.currency.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.kitosins.sibsutis.currency.api.entity.ApiAnswer;
import ru.kitosins.sibsutis.currency.api.entity.ParamRequestUpdateDateClient;
import ru.kitosins.sibsutis.currency.entity.Currency;
import ru.kitosins.sibsutis.currency.repository.CurrencyRepository;

import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Service
public class CurrencyService implements CurrencyUpdatable {

    private CurrencyRepository currencyRepository;
    private RestTemplate restTemplate = new RestTemplate();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    //    private static final String API = "https://api.exchangeratesapi.io/latest";
    private static final String API = "https://api.exchangeratesapi.io/history";


    @Autowired
    public CurrencyService(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    public List<Currency> findAll() {
        return currencyRepository.findAll();
    }

//    public Currency update2(@NonNull String symbols, @NonNull String base) {
//        Long id = currencyRepository.findMaxId();
//        Currency currencyLoadDate = new Currency();
//        String apiRequest = API.concat("?symbols=").concat(symbols).concat("&base=").concat(base);
//        AnswerLatestData answerLatestData = restTemplate.getForEntity(apiRequest, AnswerLatestData.class).getBody();
//        Date date = answerLatestData.getDate();
//        Double value = Objects.isNull(answerLatestData.getRates().getRub())
//                ? Objects.isNull(answerLatestData.getRates().getEur())
//                ? answerLatestData.getRates().getUsd()
//                : answerLatestData.getRates().getEur()
//                : answerLatestData.getRates().getRub();
//        currencyLoadDate.setId(id);
//        currencyLoadDate.setQuotedTitleCurrency(symbols);
//        currencyLoadDate.setBasicTitleCurrency(base);
//        currencyLoadDate.setDate(date);
//        currencyLoadDate.setValue(value);
//        return Objects.isNull(currencyRepository.findByDateAndBasicTitleCurrencyAndQuotedTitleCurrency(date, base, symbols))
//                ? currencyRepository.save(currencyLoadDate)
//                : null;
//    }

    //code style check
    public TreeSet<Currency> findByDateGreaterThanEqualAndDateLessThanEqualAndBasicTitleCurrencyAndQuotedTitleCurrency(
            String dateAfter, String dateBefore, String basicTitleCurrency, String quotedTitleCurrency) {

        String dateAfterFromTime = dateAfter.concat(" 00:00:00+0000");
        String dateBeforeFromTime = dateBefore.concat(" 00:00:00+0000");
        TreeSet<Currency> set = new TreeSet<>();
        return currencyRepository.findByDateGreaterThanEqualAndDateLessThanEqualAndBasicTitleCurrencyAndQuotedTitleCurrency(
                dateAfterFromTime, dateBeforeFromTime, basicTitleCurrency, quotedTitleCurrency);
    }


    // Дата клиента больше последней даты - условие
    //date primer {
    //	"dateEntryClient":"2020-08-07",
    //	"base": "EUR",
    //	"symbols": "USD"
    //}
    //https://api.exchangeratesapi.io/history?start_at=2020-08-06&end_at=2020-08-07&symbol=RUB,EUR&base=USD
    @Override
    public List<Currency> update(ParamRequestUpdateDateClient paramRequestUpdateDateClient) {
        String urlRequest = API;
        String symbols = paramRequestUpdateDateClient.getSymbols();
        String base = paramRequestUpdateDateClient.getBase();
//        Long id = currencyRepository.findMaxId()+1;
//        List<Currency> listCurrency = new LinkedList<>();

        if (paramRequestUpdateDateClient.getDateEntryClient().after(currencyRepository.findMaxDate(base, symbols))) {
            String dateStartString = dateFormat.format(currencyRepository.findMaxDate(base, symbols));
            String dateEndString = dateFormat.format(paramRequestUpdateDateClient.getDateEntryClient());

            urlRequest = API.concat("?start_at=").concat(dateStartString).concat("&end_at=").concat(dateEndString).concat("&symbols=").concat(symbols).concat("&base=").concat(base);
//            ApiAnswer apiAnswer = restTemplate.getForEntity(urlRequest, ApiAnswer.class).getBody();
//            Set<Date> dateSet = apiAnswer.getRates().keySet();
//            for(Date listDate : dateSet) {
//                Currency currencyLoad = new Currency();
//                currencyLoad.setId(id);
//                currencyLoad.setDate(listDate);
//                Double value = apiAnswer.getRates().get(listDate).getValue();
//                currencyLoad.setValue(value);
//                currencyLoad.setBasicTitleCurrency(base);
//                currencyLoad.setQuotedTitleCurrency(symbols);
//
//                if(Objects.isNull(currencyRepository.findByDateAndBasicTitleCurrencyAndQuotedTitleCurrency(listDate, base, symbols))) {
//                    listCurrency.add(currencyLoad);
//                    id++;
//                }
//
//            }
//            return currencyRepository.saveAll(listCurrency);
            return this.saveAll(urlRequest, symbols, base);
        }
        return null;
    }

    private List<Currency> saveAll(String urlRequest, String symbols, String base) {
        Long id = currencyRepository.findMaxId() + 1;
        List<Currency> listCurrency = new LinkedList<>();

        ApiAnswer apiAnswer = restTemplate.getForEntity(urlRequest, ApiAnswer.class).getBody();
        log.warn(urlRequest);
//        log.warn(apiAnswer.toString());
        Set<Date> dateSet = apiAnswer.getRates().keySet();
        for (Date date : dateSet) {
            log.warn("Good");
            Double value = apiAnswer.getRates().get(date).getValue();
            Currency currencyLoad = new Currency(id, base, symbols, date, value);
            log.warn(currencyLoad.toString());
            if (Objects.isNull(currencyRepository.findByDateAndBasicTitleCurrencyAndQuotedTitleCurrency(date, base, symbols))) {
                log.warn("GoodX2");
                listCurrency.add(currencyLoad);
                id++;
            }
        }
        log.warn(currencyRepository.saveAll(listCurrency).toString());
        return currencyRepository.saveAll(listCurrency);
    }

//    public Currency findByDateAndBasicTitleCurrencyAndQuotedTitleCurrency(Date date, String basicTitleCurrency, String quotedTitleCurrency) {
//        return currencyRepository.findByDateAndBasicTitleCurrencyAndQuotedTitleCurrency(date, basicTitleCurrency, quotedTitleCurrency);
//    }
//
//    public Date findMaxDate(String basicTitleCurrency, String quotedTitleCurrency) {
//        return currencyRepository.findMaxDate(basicTitleCurrency, quotedTitleCurrency);
//    }

    public Double converterValue(String basicTitleCurrency, String quotedTitleCurrency) {
        Date date = currencyRepository.findMaxDate(basicTitleCurrency, quotedTitleCurrency);
        Double value = currencyRepository.findByDateAndBasicTitleCurrencyAndQuotedTitleCurrency(date, basicTitleCurrency, quotedTitleCurrency).getValue();
        return value;
    }

}
