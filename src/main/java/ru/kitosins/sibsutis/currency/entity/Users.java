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
 * Users representation class
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
@Table("users")
public class Users {

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
     * username field for DB
     * @see Column
     */
    @Column
    private String username;

    /**
     * password field for DB
     * @see Column
     */
    @Column
    private String password;

    /**
     * email field for DB
     * @see Column
     */
    @Column
    private String email;

    /**
     * roles field for DB
     * @see Column
     */
    @Column
    private List<String> roles;

}