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

/**
 * Currency service class
 * @author kitosina
 * @version 0.1
 * @see Slf4j
 * @see Service
 * @see CurrencyService
 */
@Slf4j
@Service
public class CurrencyServiceImpl implements CurrencyService {

    /**
     * Dao currencyRepository
     */
    private CurrencyRepository currencyRepository;

    /**
     * API restTemplate
     */
    private RestTemplate restTemplate = new RestTemplate();

    /**
     * General date format
     */
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * URL API
     */
    private static final String API = "https://api.exchangeratesapi.io/history";

    /**
     * This method injects CurrencyRepository object
     * @see Autowired
     * @param currencyRepository
     */
    @Autowired
    public CurrencyServiceImpl(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    /**
     * This method needed for get Currency from DB
     * @return List Currency
     */
    public List<Currency> findAll() {
        return currencyRepository.findAll();
    }

    /**
     * This method needed for get Currency from DB by:
     * @param dateAfter
     * @param dateBefore
     * @param basicTitleCurrency
     * @param quotedTitleCurrency
     * @return Set Currency
     */
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

    /**
     * This method sends a request to a third party API to get new data
     * @param paramRequestUpdateDateClient
     * @see Override
     * @return List Currency object
     */
    @Override
    public List<Currency> update(ParamRequestUpdateDateClient paramRequestUpdateDateClient) {
        String urlRequest = API;
        String symbols = paramRequestUpdateDateClient.getSymbols();
        String base = paramRequestUpdateDateClient.getBase();
        if (paramRequestUpdateDateClient.getDateEntryClient().after(findMaxDate(base, symbols))) {
            log.warn("Load new data");
            String dateStartString = dateFormat.format(findMaxDate(base, symbols));
            String dateEndString = dateFormat.format(paramRequestUpdateDateClient.getDateEntryClient());
            urlRequest = API.concat("?start_at=").concat(dateStartString).concat("&end_at=").concat(dateEndString).concat("&symbols=").concat(symbols).concat("&base=").concat(base);
            log.info("Request API: " + urlRequest);
            return saveAll(urlRequest, symbols, base);
        }
        return null;
    }

    /**
     * This method saves the received data in DB
     * @param urlRequest
     * @param symbols
     * @param base
     * @return List Currency object
     */
    private List<Currency> saveAll(String urlRequest, String symbols, String base) {
        Long id = findMaxId() + 1;
        List<Currency> listCurrency = new LinkedList<>();

        ApiAnswer apiAnswer = restTemplate.getForEntity(urlRequest, ApiAnswer.class).getBody();
        log.info(apiAnswer.toString());
        Set<Date> dateSet = apiAnswer.getRates().keySet();
        for (Date date : dateSet) {
            log.warn("Date check in DB");
            Double value = apiAnswer.getRates().get(date).getValue();
            Currency currencyLoad = new Currency(id, base, symbols, date, value);
            log.warn(currencyLoad.toString());
            if (Objects.isNull(currencyRepository.findByDateAndBasicTitleCurrencyAndQuotedTitleCurrency(date, base, symbols))) {
                log.warn("Date not found DB save this data");
                listCurrency.add(currencyLoad);
                id++;
            }
        }
        return currencyRepository.saveAll(listCurrency);
    }

    /**
     * This is method clear DB
     * @see Override
     */
    @Override
    public void clear() {
        log.warn("Admin clear DB");
        log.info(String.valueOf(currencyRepository.findMaxId()));
        for(Integer idDayLimit = 1; idDayLimit <= 178; idDayLimit++) {
            currencyRepository.delete(currencyRepository.findMinId());
        }
    }

    /**
     * This method getting max id in DB
     * @return Long
     */
    public Long findMaxId() {
        log.info("Query find max ID");
        return currencyRepository.findMaxId();
    }

    /**
     * This method getting max date by:
     * @param basicTitleCurrency
     * @param quotedTitleCurrency
     * @return Date
     */
    public Date findMaxDate(String basicTitleCurrency, String quotedTitleCurrency) {
        log.info("Query find max DATE");
        return currencyRepository.findMaxDate(basicTitleCurrency, quotedTitleCurrency);
    }

    /**
     * This method getting actual value by:
     * @param basicTitleCurrency
     * @param quotedTitleCurrency
     * @return Double
     */
    public Double converterValue(String basicTitleCurrency, String quotedTitleCurrency) {
        Date date = findMaxDate(basicTitleCurrency, quotedTitleCurrency);
        Double value = currencyRepository.findByDateAndBasicTitleCurrencyAndQuotedTitleCurrency(date, basicTitleCurrency, quotedTitleCurrency).getValue();
        log.info("Converter: " + "Actual Date:" + date + " - " + basicTitleCurrency +"/" + quotedTitleCurrency + " = " + value);
        return value;
    }

}
