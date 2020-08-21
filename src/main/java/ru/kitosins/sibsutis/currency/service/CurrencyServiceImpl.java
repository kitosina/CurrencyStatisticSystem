package ru.kitosins.sibsutis.currency.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
 * @version 0.2
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
     * URL external API
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

        String dateAfterFromTime = dateAfter.concat(" 07:00:00.000000");
        String dateBeforeFromTime = dateBefore.concat(" 07:00:00.000000");
        return currencyRepository.findByDateGreaterThanEqualAndDateLessThanEqualAndBasicTitleCurrencyAndQuotedTitleCurrency(
                dateAfter, dateBefore, basicTitleCurrency, quotedTitleCurrency);
    }

    /**
     * This method sends a request to a third party API to get new data
     * @param paramRequestUpdateDateClient
     * @see Override
     * @return List Currency object
     */
    @Override
    public List<Currency> update(ParamRequestUpdateDateClient paramRequestUpdateDateClient) {
        String symbols = paramRequestUpdateDateClient.getSymbols();
        String base = paramRequestUpdateDateClient.getBase();

        //Loading Global Data in API Currency
        if(Objects.isNull(findMaxId(base, symbols))) {
            log.info("LOADING DB GLOBAL DATA!!!");
            String dateStartString = "2019-01-01";
            return getCurrencyList(paramRequestUpdateDateClient, symbols, base, dateStartString);
        }

        if (paramRequestUpdateDateClient.getDateEntryClient().after(findMaxDate(base, symbols))) {
            log.info("Preparation for Load new data");
            String dateStartString = dateFormat.format(findMaxDate(base, symbols));
            return getCurrencyList(paramRequestUpdateDateClient, symbols, base, dateStartString);
        }
        log.info("Actual data in DB, not need update");
        return null;
    }

    /**
     * This method stores the Currency list that comes from the API in the database
     * @param paramRequestUpdateDateClient
     * @param symbols
     * @param base
     * @param dateStartString
     * @return Save List<Currency>
     */
    private List<Currency> getCurrencyList(ParamRequestUpdateDateClient paramRequestUpdateDateClient, String symbols, String base, String dateStartString) {
        String urlRequest;
        String dateEndString = dateFormat.format(paramRequestUpdateDateClient.getDateEntryClient());
        urlRequest = API.concat("?start_at=").concat(dateStartString).concat("&end_at=").concat(dateEndString).concat("&symbols=").concat(symbols).concat("&base=").concat(base);
        log.info("Request API: " + urlRequest);
        return saveAll(urlRequest, symbols, base);
    }


    /**
     * This method saves the received data in DB
     * @param urlRequest
     * @param symbols
     * @param base
     * @return List Currency object
     */
    private List<Currency> saveAll(String urlRequest, String symbols, String base) {
        List<Currency> listCurrency = new LinkedList<>();

        ApiAnswer apiAnswer = restTemplate.getForEntity(urlRequest, ApiAnswer.class).getBody();
        log.info(apiAnswer.toString());
        Set<Date> dateSet = apiAnswer.getRates().keySet();
        for (Date date : dateSet) {
            log.warn("Date check in DB");
            Double value = apiAnswer.getRates().get(date).getValue();
            Currency currencyLoad = new Currency(null, base, symbols, date, value);
            log.warn(currencyLoad.toString());
            if (Objects.isNull(currencyRepository.findByDateAndBasicTitleCurrencyAndQuotedTitleCurrency(date, base, symbols))) {
                log.warn("Date not found DB save this data: " + currencyLoad);
                listCurrency.add(currencyLoad);
            }
        }
        return currencyRepository.saveAll(listCurrency);
    }

    /**
     * This is method clear DB
     * @see Override
     * @see Transactional
     */
    @Override
    @Transactional
    public void clear() {
        log.warn("Admin clear DB");
        for(Integer idDayLimit = 1; idDayLimit <= 178; idDayLimit++) {
            currencyRepository.deleteById(currencyRepository.findMinId());
        }
        log.warn("End clear DB");
    }

    /**
     * This method getting max id in DB
     * @return Long
     */
    public Long findMaxId(String basicTitleCurrency, String quotedTitleCurrency) {
        log.info("Query find max ID");
        return currencyRepository.findMaxId(basicTitleCurrency, quotedTitleCurrency);
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
        log.info("Converter: " + date + " - " + basicTitleCurrency +"/" + quotedTitleCurrency + " = " + value);
        return value;
    }

}
