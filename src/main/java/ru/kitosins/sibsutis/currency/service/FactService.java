package ru.kitosins.sibsutis.currency.service;

import ru.kitosins.sibsutis.currency.entity.Fact;

import java.util.List;

public interface FactService {

    List<Fact> findAll();

    Fact save(Fact fact);

    Fact findByNameCurrency(String nameCurrency);

}
