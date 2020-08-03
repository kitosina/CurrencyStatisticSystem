package ru.kitosins.sibsutis.currency.service;

import lombok.NonNull;
import org.apache.tinkerpop.shaded.kryo.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ru.kitosins.sibsutis.currency.entity.Currency;

@Component
public interface CurrencyUpdatable {

    Currency update(@NonNull String symbols, @NonNull String base);

}
