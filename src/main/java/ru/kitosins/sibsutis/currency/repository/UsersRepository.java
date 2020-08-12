package ru.kitosins.sibsutis.currency.repository;

import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;
import ru.kitosins.sibsutis.currency.entity.Fact;
import ru.kitosins.sibsutis.currency.entity.Users;

@Repository
public interface UsersRepository extends CassandraRepository<Users, Long> {

    @AllowFiltering
    Users findByUsername(String username);

}
