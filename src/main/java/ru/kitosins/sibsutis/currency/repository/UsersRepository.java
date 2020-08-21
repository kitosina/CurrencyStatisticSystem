package ru.kitosins.sibsutis.currency.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.kitosins.sibsutis.currency.entity.Users;

/**
 * DAO repository for Users objects
 * @author kitosina
 * @version 0.2
 * @see Repository
 * @see JpaRepository
 */
@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

    /**
     * Searching Users object in DB by:
     * @param username
     * @return Users object
     */
    Users findByUsername(String username);

    /**
     * Checking exists of Users by:
     * @param email
     * @return boolean
     */
    boolean existsByEmail(String email);

    /**
     * Checking exists of Users by:
     * @param username
     * @return boolean
     */
    boolean existsByUsername(String username);

    /**
     * Checking exists of Users by:
     * @param username
     * @param email
     * @return boolean
     */
    boolean existsByUsernameAndEmail(String username, String email);

    /**
     * Delete Users in DB by:
     * @param username
     * @see Transactional
     */
    @Transactional
    void deleteByUsername(String username);

}
