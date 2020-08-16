package ru.kitosins.sibsutis.currency.repository;

import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;
import ru.kitosins.sibsutis.currency.entity.Users;

/**
 * DAO repository for Users objects
 * @author kitosina
 * @version 0.1
 * @see Repository
 * @see CassandraRepository
 */
@Repository
public interface UsersRepository extends CassandraRepository<Users, Long> {

    /**
     * Searching Users object in DB by:
     * @param username
     * @see AllowFiltering
     * @return Users object
     */
    @AllowFiltering
    Users findByUsername(String username);

    /**
     * Checking exists of Users by:
     * @param email
     * @see AllowFiltering
     * @return boolean
     */
    @AllowFiltering
    boolean existsByEmail(String email);

    /**
     * Checking exists of Users by:
     * @param username
     * @see AllowFiltering
     * @return boolean
     */
    @AllowFiltering
    boolean existsByUsername(String username);

    /**
     * Checking exists of Users by:
     * @param username
     * @param email
     * @see AllowFiltering
     * @return boolean
     */
    @AllowFiltering
    boolean existsByUsernameAndEmail(String username, String email);

    /**
     * Searching max id in DB
     * @see Query
     * @return Long
     */
    @Query("select MAX(id) FROM users ALLOW FILTERING")
    Long findMaxId();

    /**
     * Searching id in DB by:
     * @param username
     * @see Query
     * @return Long
     */
    @Query("select id from users where username = ?0 ALLOW FILTERING")
    Long findId(String username);

    /**
     * Delete Users in DB by:
     * @param id
     */
    void deleteById(Long id);

}
