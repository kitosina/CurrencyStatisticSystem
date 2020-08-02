package ru.kitosins.sibsutis.currency.api.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnswerLatestData {

    @JsonProperty("rates")
    private AnswerLatestListCurrency rates;

    @JsonProperty("base")
    private String base;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonProperty("date")
    private Date date;

}
