package ru.kitosins.sibsutis.currency.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kitosins.sibsutis.currency.service.CurrencyService;

@RestController
@Slf4j
@RequestMapping("/currency")
public class CurrencyController {

    private CurrencyService currencyService;

    @Autowired
    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping("/{symbols}/{base}")
    public ResponseEntity update(@PathVariable String symbols, @PathVariable String base) {
        log.warn(currencyService.update(symbols, base).toString());
        return currencyService.update(symbols, base);
    }

}
