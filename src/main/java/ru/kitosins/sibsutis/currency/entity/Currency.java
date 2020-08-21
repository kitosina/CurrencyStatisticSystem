package ru.kitosins.sibsutis.currency.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * Currency representation class
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
@Table(name = "currency")
public class Currency implements Comparable<Currency>{

    /**
     * An id field for DB identification
     * @see Id
     * @see GeneratedValue
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * basicTitleCurrency field for DB
     * @see Column
     */
    @Column
    private String basicTitleCurrency;

    /**
     * quotedTitleCurrency field for DB
     * @see Column
     */
    @Column
    private String quotedTitleCurrency;

    /**
     * date field for DB
     * @see Column
     */
    @Column
    private Date date;

    /**
     * value field for DB
     * @see Column
     */
    @Column
    private Double value;

    /**
     * This method is for ordering objects
     * @see Override
     * @param currency
     * @return int
     */
    @Override
    public int compareTo(Currency currency) {
        return date.compareTo(currency.getDate());
    }

}