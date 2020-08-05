package ru.kitosins.sibsutis.currency.repository;

import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import ru.kitosins.sibsutis.currency.entity.Currency;
import ru.kitosins.sibsutis.currency.entity.Fact;

import java.util.List;
import java.util.Optional;

@Repository
public interface FactRepository extends CassandraRepository<Fact, Long> {

    @AllowFiltering
    Fact findByNameCurrency(String nameCurrency);


}
