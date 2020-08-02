package ru.kitosins.sibsutis.currency.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("currency")
public class Currency {

    @Id
    @PrimaryKeyColumn(
            name = "id",
            ordinal = 2,
            type = PrimaryKeyType.PARTITIONED,
            ordering = Ordering.DESCENDING
    )
    private Long id;

    // ------ USD/RUB  (USD-basic, RUB-quoted)
    @Column
    private String basicTitleCurrency;

    @Column
    private String quotedTitleCurrency;

    @Column
    private Date date;

    @Column
    private Double value;

}



