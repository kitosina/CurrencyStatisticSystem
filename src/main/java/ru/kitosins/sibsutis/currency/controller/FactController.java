package ru.kitosins.sibsutis.currency.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kitosins.sibsutis.currency.entity.Fact;
import ru.kitosins.sibsutis.currency.service.FactService;

import java.util.Objects;

@RestController
@RequestMapping("/fact")
public class FactController {

    private FactService factService;

    @Autowired
    public FactController(FactService factService) {
        this.factService = factService;
    }

    @GetMapping
    public ResponseEntity findAll() {
        return ResponseEntity.ok(factService.findAll());
    }

    @GetMapping("/{nameCurrency}")
    public ResponseEntity findByNameCurrency(@PathVariable String nameCurrency) {
        return Objects.isNull(factService.findByNameCurrency(nameCurrency))
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(factService.findByNameCurrency(nameCurrency));
    }

}
