package ru.kitosins.sibsutis.currency.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.Date;

/**
 * Currency representation class
 * @author kitosina
 * @version 0.1
 * @see Data
 * @see AllArgsConstructor
 * @see NoArgsConstructor
 * @see Table
 * @see Comparable
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("currency")
public class Currency implements Comparable<Currency>{

    /**
     * An id field for DB identification
     * @see Id
     * @see PrimaryKeyColumn
     */
    @Id
    @PrimaryKeyColumn(
            name = "id",
            ordinal = 2,
            type = PrimaryKeyType.PARTITIONED,
            ordering = Ordering.DESCENDING
    )
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



