package ru.kitosins.sibsutis.currency.api.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * This class needed to collecting request params
 * @author kitosina
 * @version 0.1
 * @see NoArgsConstructor
 * @see AllArgsConstructor
 * @see Data
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParamRequestUpdateDateClient {

    /**
     *Date of visit client
     */
    private Date dateEntryClient;

    /**
     *Basic currency
     */
    private String base;

    /**
     *Quoted currency
     */
    private String symbols;

}
