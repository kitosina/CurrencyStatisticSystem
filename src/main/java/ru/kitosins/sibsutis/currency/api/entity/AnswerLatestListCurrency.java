package ru.kitosins.sibsutis.currency.api.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnswerLatestListCurrency {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("RUB")
    private Double rub;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("USD")
    private Double usd;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("EUR")
    private Double eur;

}
