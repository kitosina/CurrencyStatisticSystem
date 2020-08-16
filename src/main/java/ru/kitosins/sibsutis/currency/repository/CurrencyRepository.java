package ru.kitosins.sibsutis.currency.repository;

import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.kitosins.sibsutis.currency.entity.Currency;

import java.util.Date;
import java.util.TreeSet;

@Repository
public interface CurrencyRepository extends CassandraRepository<Currency, Long> {

    @AllowFiltering
    Currency findByDateAndBasicTitleCurrencyAndQuotedTitleCurrency(Date date, String basicTitleCurrency, String quotedTitleCurrency);

    @Query("select MAX(id) FROM currency ALLOW FILTERING")
    Long findMaxId();

    @Query("select MAX(date) FROM currency WHERE basicTitleCurrency = ?0 AND quotedTitleCurrency = ?1 ALLOW FILTERING")
    Date findMaxDate(String basicTitleCurrency, String quotedTitleCurrency);

    @AllowFiltering
    TreeSet<Currency> findByDateGreaterThanEqualAndDateLessThanEqualAndBasicTitleCurrencyAndQuotedTitleCurrency(
            String dateAfter, String dateBefore, String basicTitleCurrency, String quotedTitleCurrency);

    @Query("select MIN(id) FROM currency ALLOW FILTERING")
    Long findMinId();

    @Transactional
    @Query("DELETE FROM currency WHERE id = ?0")
    void delete(Long id);

}
