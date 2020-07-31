package ru.kitosins.sibsutis.currency.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;
import ru.kitosins.sibsutis.currency.entity.Currency;

@Repository
public interface CurrencyRepository extends CassandraRepository<Currency, Long> {
}
