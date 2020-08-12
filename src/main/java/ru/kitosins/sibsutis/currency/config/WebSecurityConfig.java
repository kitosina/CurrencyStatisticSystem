package ru.kitosins.sibsutis.currency.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.WebMvcSecurityConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.Md4PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.kitosins.sibsutis.currency.service.UsersServiceImpl;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private UsersServiceImpl usersService;

    @Autowired
    public void setUsersService(UsersServiceImpl usersService) {
        this.usersService = usersService;
    }
// "/","/date_statistics", "/actual_statistics", "/converter", "/facts"
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/","/date_statistics", "/actual_statistics", "/converter", "/facts").hasAuthority("USER")
                .and()
//                .antMatchers("/","/date_statistics", "/actual_statistics", "/converter", "/facts").hasRole("ADMIN")
//                .and()
                .formLogin()
                .and()
                .logout().logoutSuccessUrl("/");

    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new Md4PasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(usersService);
        return daoAuthenticationProvider;
    }

    //  @GetMapping("/home")
    //    public String home() {
    //        return "index";
    //    }
    //
    //    @GetMapping("/date_statistics")
    //    public String dateStatistics() {
    //        return "date_statistics";
    //    }
    //
    //    @GetMapping("/actual_statistics")
    //    public String actualStatistics() {
    //        return "actual_statistics";
    //    }
    //
    //    @GetMapping("/converter")
    //    public String converter() {
    //        return "converter";
    //    }
    //
    //    @GetMapping("/facts")
    //    public String facts() {
    //        return "fact";
}

