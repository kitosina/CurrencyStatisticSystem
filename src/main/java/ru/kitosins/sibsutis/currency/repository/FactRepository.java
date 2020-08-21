package ru.kitosins.sibsutis.currency.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kitosins.sibsutis.currency.entity.Fact;

import java.util.List;

/**
 * DAO repository for Fact objects
 * @author kitosina
 * @version 0.2
 * @see Repository
 * @see JpaRepository
 */
@Repository
public interface FactRepository extends JpaRepository <Fact, Long> {

    /**
     * Searching Fact object in DB by:
     * @param nameCurrency
     * @return Fact object
     */
    List<Fact> findByNameCurrency(String nameCurrency);

}
