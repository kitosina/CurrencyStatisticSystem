package ru.kitosins.sibsutis.currency.repository;

import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;
import ru.kitosins.sibsutis.currency.entity.Fact;
import ru.kitosins.sibsutis.currency.entity.Users;

import java.util.UUID;

@Repository
public interface UsersRepository extends CassandraRepository<Users, Long> {

    @AllowFiltering
    Users findByUsername(String username);

    @AllowFiltering
    boolean existsByEmail(String email);

    @AllowFiltering
    boolean existsByUsername(String username);

    @AllowFiltering
    boolean existsByUsernameAndEmail(String username, String email);

    @Query("select MAX(id) FROM users ALLOW FILTERING")
    Long findMaxId();

}
