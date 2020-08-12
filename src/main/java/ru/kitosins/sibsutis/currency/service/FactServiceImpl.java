package ru.kitosins.sibsutis.currency.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kitosins.sibsutis.currency.entity.Fact;
import ru.kitosins.sibsutis.currency.repository.FactRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class FactServiceImpl implements FactService {

    private FactRepository factRepository;

    @Autowired
    public FactServiceImpl(FactRepository factRepository) {
        this.factRepository = factRepository;
    }

    @Override
    public List<Fact> findAll() {
        return factRepository.findAll();
    }

    @Override
    public Fact save(Fact fact) {
        return factRepository.save(fact);
    }

    @Override
    public Fact findByNameCurrency(String nameCurrency) {
        return factRepository.findByNameCurrency(nameCurrency);
    }
}
