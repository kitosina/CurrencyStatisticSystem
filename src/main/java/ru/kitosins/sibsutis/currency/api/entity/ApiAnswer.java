package ru.kitosins.sibsutis.currency.api.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Map;

/**
 * This class is required to create an ApiAnswer object
 * For parsing json from API
 * @author kitosina
 * @version 0.1
 * @see NoArgsConstructor
 * @see AllArgsConstructor
 * @see Data
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiAnswer {

    /**
     * Map currency values ​​by date
     * Dictionary is a {@link Map} collection that contains {@link Date} as
     * key and {@link AnswerLatestListCurrency} as value.
     */
    public Map<Date, AnswerLatestListCurrency> rates;

}