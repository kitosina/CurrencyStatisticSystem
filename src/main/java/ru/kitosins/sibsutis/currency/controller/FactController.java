package ru.kitosins.sibsutis.currency.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kitosins.sibsutis.currency.entity.Fact;
import ru.kitosins.sibsutis.currency.service.FactServiceImpl;

import java.io.IOException;
import java.util.Objects;

/**
 * Fact controller class
 * Processing fact request
 * @author kitosina
 * @version 0.2
 * @see Slf4j
 * @see RestController
 * @see RequestMapping
 */
@Slf4j
@RestController
public class FactController {
    /**
     * factService for repository interaction
     */
    private FactServiceImpl factService;

    /**
     * This method injects FactService object
     * @see Autowired
     * @param factService
     */
    @Autowired
    public FactController(FactServiceImpl factService) {
        this.factService = factService;
    }

    /**
     * This method save new fact
     * @see PostMapping
     * @see RequestBody
     * @param fact
     * @return Fact object in ResponseEntity
     */
    @PostMapping("/fact")
    public ResponseEntity save(@RequestBody Fact fact) {
        log.info("Save new fact" + fact.toString());
        return ResponseEntity.ok(factService.save(fact));
    }

    /**
     * This method getting list facts
     * @see GetMapping
     * @return List Fact in ResponseEntity
     */
    @GetMapping("/fact")
    public ResponseEntity findAll() {
        log.info("Output all facts");
        return ResponseEntity.ok(factService.findAll());
    }

    /**
     * This method getting facts by name currency
     * @see GetMapping
     * @see PathVariable
     * @param nameCurrency
     * @return Fact object in ResponseEntity
     */
    @GetMapping("/fact/{nameCurrency}")
    public ResponseEntity findByNameCurrency(@PathVariable String nameCurrency) {
        log.info("Fact currency " + nameCurrency);
        return Objects.isNull(factService.findByNameCurrency(nameCurrency))
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(factService.findByNameCurrency(nameCurrency));
    }

    @GetMapping("/fact/load")
    public void loadingDataBase() throws IOException {
        log.warn("Load DB");
        factService.loadingDataBase();
    }

}
