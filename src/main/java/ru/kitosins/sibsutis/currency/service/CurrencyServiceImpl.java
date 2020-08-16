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
public class CurrencyServiceImpl implements CurrencyService {

    private CurrencyRepository currencyRepository;
    private RestTemplate restTemplate = new RestTemplate();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static final String API = "https://api.exchangeratesapi.io/history";

    @Autowired
    public CurrencyServiceImpl(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    public List<Currency> findAll() {
        return currencyRepository.findAll();
    }

    public TreeSet<Currency> findByDateGreaterThanEqualAndDateLessThanEqualAndBasicTitleCurrencyAndQuotedTitleCurrency(
            String dateAfter, String dateBefore, String basicTitleCurrency, String quotedTitleCurrency) {

        String dateAfterFromTime = dateAfter.concat(" 00:00:00+0000");
        String dateBeforeFromTime = dateBefore.concat(" 00:00:00+0000");
        return currencyRepository.findByDateGreaterThanEqualAndDateLessThanEqualAndBasicTitleCurrencyAndQuotedTitleCurrency(
                dateAfterFromTime, dateBeforeFromTime, basicTitleCurrency, quotedTitleCurrency);
    }


    //date example {
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
        if (paramRequestUpdateDateClient.getDateEntryClient().after(findMaxDate(base, symbols))) {
            String dateStartString = dateFormat.format(findMaxDate(base, symbols));
            String dateEndString = dateFormat.format(paramRequestUpdateDateClient.getDateEntryClient());
            urlRequest = API.concat("?start_at=").concat(dateStartString).concat("&end_at=").concat(dateEndString).concat("&symbols=").concat(symbols).concat("&base=").concat(base);
            return saveAll(urlRequest, symbols, base);
        }
        return null;
    }

    private List<Currency> saveAll(String urlRequest, String symbols, String base) {
        Long id = findMaxId() + 1;
        List<Currency> listCurrency = new LinkedList<>();

        ApiAnswer apiAnswer = restTemplate.getForEntity(urlRequest, ApiAnswer.class).getBody();
        log.warn(urlRequest);
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
        return currencyRepository.saveAll(listCurrency);
    }

    @Override
    public void clear() {
        log.info("Admin clear DB");
        Long minId = currencyRepository.findMinId();
        for(Integer idDayLimit = 1; idDayLimit <= 178; idDayLimit++) {
            currencyRepository.delete(currencyRepository.findMinId());
        }
    }

    public Long findMaxId() {
        return currencyRepository.findMaxId();
    }

    public Date findMaxDate(String basicTitleCurrency, String quotedTitleCurrency) {
        return currencyRepository.findMaxDate(basicTitleCurrency, quotedTitleCurrency);
    }

    public Double converterValue(String basicTitleCurrency, String quotedTitleCurrency) {
        Date date = findMaxDate(basicTitleCurrency, quotedTitleCurrency);
        Double value = currencyRepository.findByDateAndBasicTitleCurrencyAndQuotedTitleCurrency(date, basicTitleCurrency, quotedTitleCurrency).getValue();
        return value;
    }

}
