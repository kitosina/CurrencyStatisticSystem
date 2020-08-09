package ru.kitosins.sibsutis.currency.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kitosins.sibsutis.currency.api.entity.ApiAnswer;
import ru.kitosins.sibsutis.currency.api.entity.ParamRequestUpdateDateClient;
import ru.kitosins.sibsutis.currency.service.CurrencyService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/currency")
public class CurrencyController {

    private CurrencyService currencyService;

    @Autowired
    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    //currency/RUB/EUR пример запросса 1 EUR=84 RUB
//    @PostMapping("/updateLast/{symbols}/{base}")
//    public ResponseEntity update(@PathVariable String symbols, @PathVariable String base) {
//        return Objects.isNull(currencyService.update2(symbols, base))
//                ? ResponseEntity.noContent().build()
//                : ResponseEntity.ok().build();
//    }

    @PostMapping("/update")
    public ResponseEntity update(@RequestBody ParamRequestUpdateDateClient paramRequestUpdateDateClient) {
        return Objects.isNull(currencyService.update(paramRequestUpdateDateClient))
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity findAll() {
        return ResponseEntity.ok(currencyService.findAll());
    }

    //localhost:8080/currency/range/2020-08-00/2020-08-03/USD/EUR
    //code style check
    @GetMapping("/range/{dateAfter}/{dateBefore}/{basicTitleCurrency}/{quotedTitleCurrency}")
    public ResponseEntity findByDateAfterAndDateBeforeAndQuotedTitleCurrencyAndBasicTitleCurrency(
            @PathVariable String dateAfter, @PathVariable String dateBefore, @PathVariable String basicTitleCurrency, @PathVariable String quotedTitleCurrency) {

        return currencyService.findByDateGreaterThanEqualAndDateLessThanEqualAndBasicTitleCurrencyAndQuotedTitleCurrency(
                dateAfter, dateBefore, basicTitleCurrency, quotedTitleCurrency).isEmpty()
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(currencyService.findByDateGreaterThanEqualAndDateLessThanEqualAndBasicTitleCurrencyAndQuotedTitleCurrency(
                        dateAfter, dateBefore, basicTitleCurrency, quotedTitleCurrency));

    }

    @GetMapping("/converter/{basicTitleCurrency}/{quotedTitleCurrency}")
    public ResponseEntity converter(@PathVariable String basicTitleCurrency, @PathVariable String quotedTitleCurrency) {
        return ResponseEntity.ok(currencyService.converterValue(basicTitleCurrency, quotedTitleCurrency));
    }




}
