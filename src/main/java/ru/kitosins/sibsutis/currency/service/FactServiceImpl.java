package ru.kitosins.sibsutis.currency.service;

import com.mysql.cj.jdbc.MysqlDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kitosins.sibsutis.currency.entity.Fact;
import ru.kitosins.sibsutis.currency.repository.FactRepository;

import java.io.*;
import java.util.List;

/**
 * Fact service class
 * @author kitosina
 * @version 0.2
 * @see Slf4j
 * @see Service
 * @see CurrencyService
 */
@Slf4j
@Service
public class FactServiceImpl implements FactService {

    /**
     * Dao factRepository
     */
    private FactRepository factRepository;

    /**
     * Username in the database
     * @see Value
     */
    @Value("${spring.datasource.username}")
    private String username;

    /**
     * Password in the database
     * @see Value
     */
    @Value("${spring.datasource.password}")
    private String password;

    /**
     * URL database
     * @see Value
     */
    @Value("${spring.datasource.url}")
    private String urlDb;

    /**
     * This method injects FactRepository object
     * @see Autowired
     * @param factRepository
     */
    @Autowired
    public FactServiceImpl(FactRepository factRepository) {
        this.factRepository = factRepository;
    }

    /**
     * This method needed for get Fact from DB
     * @return List Fact
     */
    public List<Fact> findAll() {
        return factRepository.findAll();
    }

    /**
     * This method save new Fact object in DB
     * @param fact
     * @return Fact object
     */
    public Fact save(Fact fact) {
        return factRepository.save(fact);
    }

    /**
     * This method needed for get Fact from DB by:
     * @param nameCurrency
     * @return Fact object
     */
    public List<Fact> findByNameCurrency(String nameCurrency) {
        return factRepository.findByNameCurrency(nameCurrency);
    }

    /**
     * This method executes a SQL script to load data into the database
     * @see Override
     * @throws IOException
     */
    @Override
    @Transactional
    public void loadingDataBase() throws IOException {
        log.warn("Script start ...");
        MysqlDataSource ds = new MysqlDataSource();
        ds.setURL(urlDb);
        ds.setUser(username);
        ds.setPassword(password);

        JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
        BufferedReader resource = new BufferedReader(new FileReader(getClass().getClassLoader().getResource("script_db/fact.sql").getFile()));
        String query;
        while ((query = resource.readLine()) != null) {
            log.warn(query);
            jdbcTemplate.update(query);
        }
        log.warn("Script end!");
    }
}
