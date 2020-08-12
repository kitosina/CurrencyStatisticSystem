package ru.kitosins.sibsutis.currency.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("users")
public class Users {
    @Id
    @PrimaryKeyColumn(
            name = "id",
            ordinal = 2,
            type = PrimaryKeyType.PARTITIONED,

            ordering = Ordering.DESCENDING
    )
    private Long id;

    private String username;

    private String password;

    private String email;

    private List<String> roles;

}