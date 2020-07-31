package ru.kitosins.sibsutis.currency.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;
import ru.kitosins.sibsutis.currency.entity.Fact;

@Repository
public interface FactRepository extends CassandraRepository<Long, Fact> {
}
