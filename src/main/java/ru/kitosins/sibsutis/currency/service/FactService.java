package ru.kitosins.sibsutis.currency.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kitosins.sibsutis.currency.entity.Fact;
import ru.kitosins.sibsutis.currency.repository.FactRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

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

    public Fact save(Fact fact) {
        return factRepository.save(fact);
    }

    public Fact findByNameCurrency(String nameCurrency) {
        return factRepository.findByNameCurrency(nameCurrency);
    }
}
