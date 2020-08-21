package ru.kitosins.sibsutis.currency.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

/**
 * Fact representation class
 * @author kitosina
 * @version 0.2
 * @see Data
 * @see AllArgsConstructor
 * @see NoArgsConstructor
 * @see Table
 * @see Entity
 * @see Comparable
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "fact")
public class Fact {

    /**
     * An id field for DB identification
     * @see Id
     * @see GeneratedValue
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * nameCurrency field for DB
     * @see Column
     */
    @Column
    private String nameCurrency;

    /**
     * listFact field for DB
     * @see Column
     */
    @Column
    private String fact;

}
