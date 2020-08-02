package ru.kitosins.sibsutis.currency.repository;

import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import ru.kitosins.sibsutis.currency.entity.Currency;
import ru.kitosins.sibsutis.currency.entity.Fact;

import java.util.List;

@Repository
public interface FactRepository extends CassandraRepository<Fact, Long> {

    @AllowFiltering
    List<String> findByNameCurrency(String nameCurrency);


}
