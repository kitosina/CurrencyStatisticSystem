package ru.kitosins.sibsutis.currency.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kitosins.sibsutis.currency.api.entity.ParamRequestUpdateDateClient;
import ru.kitosins.sibsutis.currency.service.CurrencyServiceImpl;

import java.util.Date;
import java.util.Objects;


/**
 * Currency controller class
 * Processing currency request
 * @author kitosina
 * @version 0.1
 * @see Slf4j
 * @see RestController
 * @see RequestMapping
 */
@Slf4j
@RestController
@RequestMapping("/currency")
public class CurrencyController {

    /**
     * currencyServiceImpl for repository interaction
     */
    private CurrencyServiceImpl currencyServiceImpl;

    /**
     * This method injects CurrencyServiceImpl object
     * @see Autowired
     * @param currencyServiceImpl
     */
    @Autowired
    public CurrencyController(CurrencyServiceImpl currencyServiceImpl) {
        this.currencyServiceImpl = currencyServiceImpl;
    }

    /**
     * This method getting actual data by request parameters
     * @see RequestBody
     * @see PostMapping
     * @see ParamRequestUpdateDateClient
     * @param paramRequestUpdateDateClient
     * @return Update response
     */
    @PostMapping("/update")
    public ResponseEntity update(@RequestBody ParamRequestUpdateDateClient paramRequestUpdateDateClient) {
        log.info("Update DB " + paramRequestUpdateDateClient.getBase() + "/" + paramRequestUpdateDateClient.getSymbols() + " - " + paramRequestUpdateDateClient.getDateEntryClient());
        return Objects.isNull(currencyServiceImpl.update(paramRequestUpdateDateClient))
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok().build();
    }

    /**
     * This method getting a list of currency
     * @see GetMapping
     * @return List Currency in ResponseEntity
     */
    @GetMapping
    public ResponseEntity findAll() {
        return ResponseEntity.ok(currencyServiceImpl.findAll());
    }

    //localhost:8080/currency/range/2020-08-00/2020-08-03/USD/RUB

    /**
     * This method getting value currency by range and title
     * @see GetMapping
     * @see PathVariable
     * @param dateAfter
     * @param dateBefore
     * @param basicTitleCurrency
     * @param quotedTitleCurrency
     * @return Set Currency in ResponseEntity
     */
    @GetMapping("/range/{dateAfter}/{dateBefore}/{basicTitleCurrency}/{quotedTitleCurrency}")
    public ResponseEntity findByDateAfterAndDateBeforeAndQuotedTitleCurrencyAndBasicTitleCurrency(
            @PathVariable String dateAfter, @PathVariable String dateBefore, @PathVariable String basicTitleCurrency, @PathVariable String quotedTitleCurrency) {

        log.info("Date range " + "dateAfter/dateBefore: " + dateAfter + "/" + dateBefore + " - basicCurrency/quotedCurrency: " + basicTitleCurrency + " - " + quotedTitleCurrency);
        return currencyServiceImpl.findByDateGreaterThanEqualAndDateLessThanEqualAndBasicTitleCurrencyAndQuotedTitleCurrency(
                dateAfter, dateBefore, basicTitleCurrency, quotedTitleCurrency).isEmpty()
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(currencyServiceImpl.findByDateGreaterThanEqualAndDateLessThanEqualAndBasicTitleCurrencyAndQuotedTitleCurrency(
                        dateAfter, dateBefore, basicTitleCurrency, quotedTitleCurrency));

    }

    /**
     * This method getting last currency value
     * @see GetMapping
     * @see PathVariable
     * @param basicTitleCurrency
     * @param quotedTitleCurrency
     * @return Actual value in ResponseEntity
     */
    @GetMapping("/actual/{basicTitleCurrency}/{quotedTitleCurrency}")
    public ResponseEntity converter(@PathVariable String basicTitleCurrency, @PathVariable String quotedTitleCurrency) {
        log.info("Actual value " + basicTitleCurrency + "/" + quotedTitleCurrency);
        return ResponseEntity.ok(currencyServiceImpl.converterValue(basicTitleCurrency, quotedTitleCurrency));
    }

    /**
     * This method getting last date
     * @see GetMapping
     * @see PathVariable
     * @param basicTitleCurrency
     * @param quotedTitleCurrency
     * @return Last date in ResponseEntity
     */
    @GetMapping("/last_data/{basicTitleCurrency}/{quotedTitleCurrency}")
    public ResponseEntity findMaxDate(@PathVariable String basicTitleCurrency, @PathVariable String quotedTitleCurrency) {
        log.info("Actual value " + basicTitleCurrency + "/" + quotedTitleCurrency);
        return ResponseEntity.ok(currencyServiceImpl.findMaxDate(basicTitleCurrency, quotedTitleCurrency));
    }

}
