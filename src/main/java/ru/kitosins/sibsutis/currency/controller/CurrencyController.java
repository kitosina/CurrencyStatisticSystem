package ru.kitosins.sibsutis.currency.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kitosins.sibsutis.currency.entity.Currency;
import ru.kitosins.sibsutis.currency.service.CurrencyService;

import java.util.Date;
import java.util.List;
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

    ///currency/RUB/EUR пример запросса 1 EUR=84 RUB
    @PostMapping("update/{symbols}/{base}")
    public ResponseEntity update(@PathVariable String symbols, @PathVariable String base) {
        return Objects.isNull(currencyService.update(symbols, base))
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity findAll() {
        return ResponseEntity.ok(currencyService.findAll());
    }

    //localhost:8080/currency/range/2020-08-00/2020-08-03/RUB/USD
    //code style check
    @GetMapping("range/{dateAfter}/{dateBefore}/{quotedTitleCurrency}/{basicTitleCurrency}")
    public ResponseEntity findByDateAfterAndDateBeforeAndQuotedTitleCurrencyAndBasicTitleCurrency(
            @PathVariable String dateAfter, @PathVariable String dateBefore, @PathVariable String quotedTitleCurrency, @PathVariable String basicTitleCurrency) {

        return currencyService.findByDateGreaterThanEqualAndDateLessThanEqualAndQuotedTitleCurrencyAndBasicTitleCurrency(
                dateAfter, dateBefore, quotedTitleCurrency, basicTitleCurrency).isEmpty()
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(currencyService.findByDateGreaterThanEqualAndDateLessThanEqualAndQuotedTitleCurrencyAndBasicTitleCurrency(
                        dateAfter, dateBefore, quotedTitleCurrency, basicTitleCurrency));

    }




}
