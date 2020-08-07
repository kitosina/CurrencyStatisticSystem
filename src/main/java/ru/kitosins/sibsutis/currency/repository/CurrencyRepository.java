package ru.kitosins.sibsutis.currency.repository;

import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import ru.kitosins.sibsutis.currency.entity.Currency;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.TreeSet;

@Repository
public interface CurrencyRepository extends CassandraRepository<Currency, Long> {

    @AllowFiltering
    Currency findByDateAndBasicTitleCurrencyAndQuotedTitleCurrency(Date date, String basicTitleCurrency, String quotedTitleCurrency);

    @Query("select MAX(id) FROM currency ALLOW FILTERING")
    Long findMaxId();

    @Query("select MAX(date) FROM currency WHERE basicTitleCurrency = ?0 AND quotedTitleCurrency =?1 ALLOW FILTERING")
    Date findMaxDate(String basicTitleCurrency, String quotedTitleCurrency);

    @AllowFiltering
    //code style check
    TreeSet<Currency> findByDateGreaterThanEqualAndDateLessThanEqualAndQuotedTitleCurrencyAndBasicTitleCurrency(
            String dateAfter, String dateBefore, String quotedTitleCurrency, String basicTitleCurrency);

}
