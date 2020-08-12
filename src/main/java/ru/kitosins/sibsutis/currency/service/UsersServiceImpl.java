package ru.kitosins.sibsutis.currency.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kitosins.sibsutis.currency.entity.Users;
import ru.kitosins.sibsutis.currency.repository.UsersRepository;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

@Slf4j
@Service
public class UsersServiceImpl implements UsersService, UserDetailsService {

    private UsersRepository usersRepository;

    @Autowired
    public void setUsersRepository(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public Users findByUsername(String username) {
        return usersRepository.findByUsername(username);
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
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(listRoles);
            grantedAuthorityList.add(grantedAuthority);
        }
        log.warn(grantedAuthorityList.toString());
        return grantedAuthorityList;
    }

}
