package ru.kitosins.sibsutis.currency.api.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * This class needed to created AnswerLatestListCurrency object
 * @author kitosina
 * @version 0.1
 * @see NoArgsConstructor
 * @see AllArgsConstructor
 * @see Data
 * @see ApiAnswer
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnswerLatestListCurrency {

    /**
     * RUB currency value
     * @see JsonInclude
     * @see JsonProperty
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("RUB")
    private Double rub;

    /**
     * USD currency value
     * @see JsonInclude
     * @see JsonProperty
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("USD")
    private Double usd;

    /**
     * EUR currency value
     * @see JsonInclude
     * @see JsonProperty
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("EUR")
    private Double eur;

    /**
     * The method returns the currency value
     * @return currency value
     */
    public Double getValue() {
        return Objects.isNull(this.rub)
                ? (Objects.isNull(this.eur) ? this.usd : this.eur)
                : this.rub;
    }

}
