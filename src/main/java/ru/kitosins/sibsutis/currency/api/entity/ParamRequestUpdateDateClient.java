package ru.kitosins.sibsutis.currency.api.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParamRequestUpdateDateClient {

    private Date dateEntryClient;

    private String base;

    private String symbols;

}
