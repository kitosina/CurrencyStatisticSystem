package ru.kitosins.sibsutis.currency.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.Md4PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kitosins.sibsutis.currency.entity.Role;
import ru.kitosins.sibsutis.currency.entity.Users;
import ru.kitosins.sibsutis.currency.repository.UsersRepository;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Users service class
 * @author kitosina
 * @version 0.2
 * @see Slf4j
 * @see Service
 * @see CurrencyService
 */
@Slf4j
@Service
public class UsersServiceImpl implements UsersService, UserDetailsService {

    /**
     * Dao usersRepository
     */
    private UsersRepository usersRepository;

    /**
     * Object for password encoding md4PasswordEncoder
     */
    private Md4PasswordEncoder md4PasswordEncoder = new Md4PasswordEncoder();

    /**
     * This method injects UsersRepository object
     * @see Autowired
     * @param usersRepository
     */
    @Autowired
    public UsersServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    /**
     * This method getting Users by:
     * @param username
     * @see Transactional
     * @return Users object
     */
    @Transactional
    public Users findByUsername(String username) {
        return usersRepository.findByUsername(username);
    }

    /**
     * This method needed for get Users from DB
     * @see Transactional
     * @return List Users
     */
    @Transactional
    public List<Users> findAll() {
        return usersRepository.findAll();
    }

    /**
     * Delete Users in DB by:
     * @param username
     * @see Transactional
     */
    @Transactional
    public void deleteById(String username) {
        log.warn("Delete user: " + username);
        usersRepository.deleteByUsername(username);
    }

    /**
     * This method save new Users object in DB
     * @param users
     * @see Override
     * @see Transactional
     * @return Code save
     */
    @Override
    @Transactional
    public Byte save(Users users) {

        if(usersRepository.existsByUsernameAndEmail(users.getUsername(), users.getEmail())) {
            log.warn("New user not created - exists username and email");
            return 3;
        }
        if(usersRepository.existsByUsername(users.getUsername())) {
            log.warn("New user not created - exists username");
            return 1;
        }
        if(usersRepository.existsByEmail(users.getEmail())) {
            log.warn("New user not created - exists email");
            return 2;
        }

        users.setPassword(md4PasswordEncoder.encode(users.getPassword()));
        users.setRoles(Collections.singleton(Role.USER));
        usersRepository.save(users);
        log.info("Success save new user");
        return 0;
    }

    /**
     * This method getting UserDetails by:
     * @param username
     * @see UserDetails
     * @see Override
     * @see Transactional
     * @return Users object
     * @throws UsernameNotFoundException
     */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users = findByUsername(username);
        if (users == null) {
            throw new UsernameNotFoundException(String.format("User '%s' not found", username));
        }
        return new User(users.getUsername(), users.getPassword(), listAuthority(users.getRoles()));
    }

    /**
     * This method create list authority
     * @param roles
     * @see GrantedAuthority
     * @see Collection
     * @return Users roles
     */
    private Collection<? extends GrantedAuthority> listAuthority(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getAuthority())).collect(Collectors.toList());
    }

}
