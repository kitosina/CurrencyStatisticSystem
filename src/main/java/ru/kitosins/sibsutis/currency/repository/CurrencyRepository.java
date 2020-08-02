package ru.kitosins.sibsutis.currency.repository;

import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import ru.kitosins.sibsutis.currency.entity.Currency;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface CurrencyRepository extends CassandraRepository<Currency, Long> {

    @AllowFiltering
    Currency findByDateAndBasicTitleCurrencyAndQuotedTitleCurrency(Date date, String basicTitleCurrency, String quotedTitleCurrency);

    @Query("select MAX(id) FROM currency ALLOW FILTERING")
    Long findMaxId();

    //добавить запрос на диапазон дат и валюты.

}
