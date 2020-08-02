package ru.kitosins.sibsutis.currency.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kitosins.sibsutis.currency.entity.Fact;
import ru.kitosins.sibsutis.currency.repository.FactRepository;

import java.util.List;

@Service
public class FactService {

    private FactRepository factRepository;

    @Autowired
    public FactService(FactRepository factRepository) {
        this.factRepository = factRepository;
    }

    public List<Fact> findAll() {
        return factRepository.findAll();
    }

    public List<String> findByNameCurrency(String nameCurrency) {
        return factRepository.findByNameCurrency(nameCurrency);
    }
}
