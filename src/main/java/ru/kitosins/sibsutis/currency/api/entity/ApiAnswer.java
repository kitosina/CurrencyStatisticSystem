package ru.kitosins.sibsutis.currency.api.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiAnswer {

    public Map<Date, AnswerLatestListCurrency> rates;

}