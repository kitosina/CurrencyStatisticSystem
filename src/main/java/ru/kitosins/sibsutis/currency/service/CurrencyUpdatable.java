package ru.kitosins.sibsutis.currency.service;

import lombok.NonNull;
import org.apache.tinkerpop.shaded.kryo.NotNull;
import org.springframework.http.ResponseEntity;
import ru.kitosins.sibsutis.currency.entity.Currency;

public interface CurrencyUpdatable {

    ResponseEntity update(@NonNull String symbols, @NonNull String base);

}
