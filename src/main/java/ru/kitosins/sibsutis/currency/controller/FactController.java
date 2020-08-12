package ru.kitosins.sibsutis.currency.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kitosins.sibsutis.currency.entity.Fact;
import ru.kitosins.sibsutis.currency.service.FactServiceImpl;

import java.util.Objects;

@RestController
@RequestMapping("/fact")
public class FactController {

    private FactServiceImpl factServiceImpl;

    @Autowired
    public FactController(FactServiceImpl factServiceImpl) {
        this.factServiceImpl = factServiceImpl;
    }

    @PostMapping
    public ResponseEntity save(@RequestBody Fact fact) {
        return ResponseEntity.ok(factServiceImpl.save(fact));
    }

    @GetMapping
    public ResponseEntity findAll() {
        return ResponseEntity.ok(factServiceImpl.findAll());
    }

    @GetMapping("/{nameCurrency}")
    public ResponseEntity findByNameCurrency(@PathVariable String nameCurrency) {
        return Objects.isNull(factServiceImpl.findByNameCurrency(nameCurrency))
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(factServiceImpl.findByNameCurrency(nameCurrency));
    }

}
