package ru.kitosins.sibsutis.currency.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.kitosins.sibsutis.currency.entity.Currency;

import java.time.LocalDate;
import java.util.Date;
import java.util.TreeSet;

/**
 * DAO repository for Currency objects
 * @author kitosina
 * @version 0.2
 * @see Repository
 * @see JpaRepository
 */
@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Long> {

    /**
     * Searching Currency object in DB by:
     * @param date
     * @param basicTitleCurrency
     * @param quotedTitleCurrency
     * @return Currency object
     */
    Currency findByDateAndBasicTitleCurrencyAndQuotedTitleCurrency(Date date, String basicTitleCurrency, String quotedTitleCurrency);

    /**
     * Searching max id in DB by:
     * @param basicTitleCurrency
     * @param quotedTitleCurrency
     * @see Query
     * @return max id
     */
    @Query(value = "select MAX(id) FROM currency WHERE basic_Title_Currency = ?1 AND quoted_Title_Currency = ?2", nativeQuery = true)
    Long findMaxId(String basicTitleCurrency, String quotedTitleCurrency);

    /**
     * Searching max date in DB by:
     * @param basicTitleCurrency
     * @param quotedTitleCurrency
     * @see Query
     * @return max date
     */
    @Query(value = "select MAX(date) FROM currency WHERE basic_Title_Currency = ?1 AND quoted_Title_Currency = ?2", nativeQuery = true)
    Date findMaxDate(String basicTitleCurrency, String quotedTitleCurrency);

    /**
     * Searching Currency in DB by:
     * @param dateAfter
     * @param dateBefore
     * @param basicTitleCurrency
     * @param quotedTitleCurrency
     * @see Query
     * @return Set Currency object
     */
    @Query(value = "SELECT * FROM currency  " +
            "WHERE date >= ?1 and date <= ?2 and basic_Title_Currency = ?3 and quoted_Title_Currency = ?4",
            nativeQuery = true)
    TreeSet<Currency> findByDateGreaterThanEqualAndDateLessThanEqualAndBasicTitleCurrencyAndQuotedTitleCurrency(
            String dateAfter, String dateBefore, String basicTitleCurrency, String quotedTitleCurrency);

    /**
     * Searching min id in DB
     * @see Query
     * @return min id
     */
    @Query(value = "select MIN(id) FROM currency", nativeQuery = true)
    Long findMinId();

    /**
     * Delete Currency in DB by:
     * @param id
     * @see Transactional
     */
    @Transactional
    void deleteById(Long id);

}
