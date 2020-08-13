package ru.kitosins.sibsutis.currency.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.Md4PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kitosins.sibsutis.currency.entity.Users;
import ru.kitosins.sibsutis.currency.repository.UsersRepository;

import java.util.*;

@Slf4j
@Service
public class UsersServiceImpl implements UsersService, UserDetailsService {

    private UsersRepository usersRepository;

    private Md4PasswordEncoder md4PasswordEncoder = new Md4PasswordEncoder();

    @Autowired
    public UsersServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public Users findByUsername(String username) {
        return usersRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public Byte save(Users users) {
        Long id = usersRepository.findMaxId() + 1;
        if(usersRepository.existsByUsername(users.getUsername())) {
            return 1;
        }
        if(usersRepository.existsByEmail(users.getEmail())) {
            return 2;
        }
        if(usersRepository.existsByUsernameAndEmail(users.getUsername(), users.getEmail())) {
            return 3;
        }
        users.setId(id);
        users.setPassword(md4PasswordEncoder.encode(users.getPassword()));
        users.setRoles(Collections.singletonList("USER"));
        usersRepository.save(users);
        return 0;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users = usersRepository.findByUsername(username);
        if (users == null) {
            throw new UsernameNotFoundException(String.format("User '%s' not found", username));
        }
        return new User(users.getUsername(), users.getPassword(), listAuthority(users.getRoles()));
    }

    private Collection<? extends GrantedAuthority> listAuthority(List<String> roles) {
        List<GrantedAuthority> grantedAuthorityList = new LinkedList<>();
        for(String listRoles : roles) {
            grantedAuthorityList.add(new SimpleGrantedAuthority(listRoles));
        }
        log.warn(grantedAuthorityList.toString());
        return grantedAuthorityList;
    }

}
