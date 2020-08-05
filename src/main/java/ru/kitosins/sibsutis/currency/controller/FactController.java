package ru.kitosins.sibsutis.currency.controller;

import lombok.extern.slf4j.Slf4j;
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

    @PostMapping
    public ResponseEntity save(@RequestBody Fact fact) {
        return ResponseEntity.ok(factService.save(fact));
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
