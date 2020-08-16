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

import java.util.List;

/**
 * Fact representation class
 * @author kitosina
 * @version 0.1
 * @see Data
 * @see AllArgsConstructor
 * @see NoArgsConstructor
 * @see Table
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("fact")
public class Fact {

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
    private List<String> listFact;

}
