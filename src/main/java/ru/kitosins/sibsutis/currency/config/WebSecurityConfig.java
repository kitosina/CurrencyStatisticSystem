package ru.kitosins.sibsutis.currency.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.Md4PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.kitosins.sibsutis.currency.service.UsersServiceImpl;


/**
 * WebSecurityConfig class adding and limit rights for users that asks
 * access to methods and web-pages
 * @author kitosina
 * @version 0.1
 * @see WebSecurityConfigurerAdapter
 * @see Configuration
 * @see EnableWebSecurity
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * usersService used by daoAuthenticationProvider
     */
    private UsersServiceImpl usersService;

    /**
     * This method injects UserServiceImpl object
     * @see Autowired
     * @param usersService
     */
    @Autowired
    public void setUsersService(UsersServiceImpl usersService) {
        this.usersService = usersService;
    }

    /**
     * This method restricts user rights depending on the role
     * @see Override
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .antMatchers("/","/actual_statistics", "/converter", "/facts", "/registration", "/js/**", "/css/**", "/contents/**").permitAll()
                .antMatchers("/date_statistics").hasAuthority("USER")
                .antMatchers("/admin", "/administration", "/administration/**").hasAuthority("ADMIN")
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .permitAll();
    }

    /**
     * This method encode password
     * @see Bean
     * @return encoder password object
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new Md4PasswordEncoder();
    }

    /**
     * This method setting up DaoAuthenticationProvider for security
     * @see Bean
     * @return encoder password object
     */
//    @Bean
//    public DaoAuthenticationProvider daoAuthenticationProvider() {
//        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
//        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
//        daoAuthenticationProvider.setUserDetailsService(usersService);
//        return daoAuthenticationProvider;
//    }

}

