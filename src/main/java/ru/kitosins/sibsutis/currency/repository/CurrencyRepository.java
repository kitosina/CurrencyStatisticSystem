package ru.kitosins.sibsutis.currency.repository;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.Table;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.kitosins.sibsutis.currency.entity.Currency;

import java.util.Date;
import java.util.TreeSet;

/**
 * DAO repository for Currency objects
 * @author kitosina
 * @version 0.1
 * @see Repository
 * @see CassandraRepository
 */
@Repository
public interface CurrencyRepository extends CassandraRepository<Currency, Long> {

    /**
     * Searching Currency object in DB by:
     * @param date
     * @param basicTitleCurrency
     * @param quotedTitleCurrency
     * @see Query
     * @return Currency object
     */
    @AllowFiltering
    Currency findByDateAndBasicTitleCurrencyAndQuotedTitleCurrency(Date date, String basicTitleCurrency, String quotedTitleCurrency);

    /**
     * Searching max id in DB
     * @see Query
     * @return Long
     */
    @Query("select MAX(id) FROM currency ALLOW FILTERING")
    Long findMaxId();

    /**
     * Searching max date in DB by:
     * @param basicTitleCurrency
     * @param quotedTitleCurrency
     * @see Query
     * @return Date
     */
    @Query("select MAX(date) FROM currency WHERE basicTitleCurrency = ?0 AND quotedTitleCurrency = ?1 ALLOW FILTERING")
    Date findMaxDate(String basicTitleCurrency, String quotedTitleCurrency);

    /**
     * Searching Currency in DB by:
     * @param dateAfter
     * @param dateBefore
     * @param basicTitleCurrency
     * @param quotedTitleCurrency
     * @see AllowFiltering
     * @return Set Currency object
     */
    @AllowFiltering
    TreeSet<Currency> findByDateGreaterThanEqualAndDateLessThanEqualAndBasicTitleCurrencyAndQuotedTitleCurrency(
            String dateAfter, String dateBefore, String basicTitleCurrency, String quotedTitleCurrency);

    /**
     * Searching min id in DB
     * @see Query
     * @return Long
     */
    @Query("select MIN(id) FROM currency ALLOW FILTERING")
    Long findMinId();

    /**
     * Delete Currency in DB by:
     * @param id
     * @see Transactional
     * @see Query
     */
    @Transactional
    @Query("DELETE FROM currency WHERE id = ?0")
    void delete(Long id);

}
