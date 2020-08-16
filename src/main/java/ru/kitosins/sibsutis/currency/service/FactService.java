package ru.kitosins.sibsutis.currency.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kitosins.sibsutis.currency.entity.Fact;
import ru.kitosins.sibsutis.currency.repository.FactRepository;

import java.util.List;

/**
 * Fact service class
 * @author kitosina
 * @version 0.1
 * @see Slf4j
 * @see Service
 * @see CurrencyService
 */
@Slf4j
@Service
public class FactService {

    /**
     * Dao factRepository
     */
    private FactRepository factRepository;

    /**
     * This method injects FactRepository object
     * @see Autowired
     * @param factRepository
     */
    @Autowired
    public FactService(FactRepository factRepository) {
        this.factRepository = factRepository;
    }

    /**
     * This method needed for get Fact from DB
     * @return List Fact
     */
    public List<Fact> findAll() {
        return factRepository.findAll();
    }

    /**
     * This method save new Fact object in DB
     * @param fact
     * @return Fact object
     */
    public Fact save(Fact fact) {
        return factRepository.save(fact);
    }

    /**
     * This method needed for get Fact from DB by:
     * @param nameCurrency
     * @return Fact object
     */
    public Fact findByNameCurrency(String nameCurrency) {
        return factRepository.findByNameCurrency(nameCurrency);
    }
}
