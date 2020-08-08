package ru.kitosins.sibsutis.currency.api.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiAnswer {

//    @JsonFormat(shape = JsonFormat.Shape.ARRAY, pattern = "yyyy-MM-dd")
    public Map<Date, AnswerLatestListCurrency> rates;

}