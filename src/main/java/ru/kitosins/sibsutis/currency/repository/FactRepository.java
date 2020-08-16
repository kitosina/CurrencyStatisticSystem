package ru.kitosins.sibsutis.currency.repository;

import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;
import ru.kitosins.sibsutis.currency.entity.Fact;

/**
 * DAO repository for Fact objects
 * @author kitosina
 * @version 0.1
 * @see Repository
 * @see CassandraRepository
 */
@Repository
public interface FactRepository extends CassandraRepository<Fact, Long> {

    /**
     * Searching Fact object in DB by:
     * @param nameCurrency
     * @see AllowFiltering
     * @return Fact object
     */
    @AllowFiltering
    Fact findByNameCurrency(String nameCurrency);

}
