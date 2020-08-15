package ru.kitosins.sibsutis.currency.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kitosins.sibsutis.currency.api.entity.ParamRequestUpdateDateClient;
import ru.kitosins.sibsutis.currency.service.CurrencyServiceImpl;

import java.util.Date;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/currency")
public class CurrencyController {

    private CurrencyServiceImpl currencyServiceImpl;

    @Autowired
    public CurrencyController(CurrencyServiceImpl currencyServiceImpl) {
        this.currencyServiceImpl = currencyServiceImpl;
    }

    @PostMapping("/update")
    public ResponseEntity update(@RequestBody ParamRequestUpdateDateClient paramRequestUpdateDateClient) {
        return Objects.isNull(currencyServiceImpl.update(paramRequestUpdateDateClient))
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity findAll() {
        return ResponseEntity.ok(currencyServiceImpl.findAll());
    }

    //localhost:8080/currency/range/2020-08-00/2020-08-03/USD/RUB
    @GetMapping("/range/{dateAfter}/{dateBefore}/{basicTitleCurrency}/{quotedTitleCurrency}")
    public ResponseEntity findByDateAfterAndDateBeforeAndQuotedTitleCurrencyAndBasicTitleCurrency(
            @PathVariable String dateAfter, @PathVariable String dateBefore, @PathVariable String basicTitleCurrency, @PathVariable String quotedTitleCurrency) {

        return currencyServiceImpl.findByDateGreaterThanEqualAndDateLessThanEqualAndBasicTitleCurrencyAndQuotedTitleCurrency(
                dateAfter, dateBefore, basicTitleCurrency, quotedTitleCurrency).isEmpty()
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(currencyServiceImpl.findByDateGreaterThanEqualAndDateLessThanEqualAndBasicTitleCurrencyAndQuotedTitleCurrency(
                        dateAfter, dateBefore, basicTitleCurrency, quotedTitleCurrency));

    }

    @GetMapping("/actual/{basicTitleCurrency}/{quotedTitleCurrency}")
    public ResponseEntity converter(@PathVariable String basicTitleCurrency, @PathVariable String quotedTitleCurrency) {
        return ResponseEntity.ok(currencyServiceImpl.converterValue(basicTitleCurrency, quotedTitleCurrency));
    }

    @GetMapping("/last_data/{basicTitleCurrency}/{quotedTitleCurrency}")
    public Date findMaxDate(@PathVariable String basicTitleCurrency, @PathVariable String quotedTitleCurrency) {
        return currencyServiceImpl.findMaxDate(basicTitleCurrency, quotedTitleCurrency);
    }

}
