package ru.kitosins.sibsutis.currency.service;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.kitosins.sibsutis.currency.api.entity.AnswerLatestData;
import ru.kitosins.sibsutis.currency.api.entity.ApiAnswer;
import ru.kitosins.sibsutis.currency.api.entity.ParamRequestUpdateDateClient;
import ru.kitosins.sibsutis.currency.entity.Currency;
import ru.kitosins.sibsutis.currency.repository.CurrencyRepository;

import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Service
public class CurrencyService implements CurrencyUpdatable {

    private CurrencyRepository currencyRepository;
    private RestTemplate restTemplate = new RestTemplate();
//    private static final String API = "https://api.exchangeratesapi.io/latest";
    private static final String API = "https://api.exchangeratesapi.io/history";


    @Autowired
    public CurrencyService(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    public List<Currency> findAll() {
        return currencyRepository.findAll();
    }

    public Currency update2(@NonNull String symbols, @NonNull String base) {
        Long id = currencyRepository.findMaxId();
        Currency currencyLoadDate = new Currency();
        String apiRequest = API.concat("?symbols=").concat(symbols).concat("&base=").concat(base);
        AnswerLatestData answerLatestData = restTemplate.getForEntity(apiRequest, AnswerLatestData.class).getBody();
        Date date = answerLatestData.getDate();
        Double value = Objects.isNull(answerLatestData.getRates().getRub())
                ? Objects.isNull(answerLatestData.getRates().getEur())
                ? answerLatestData.getRates().getUsd()
                : answerLatestData.getRates().getEur()
                : answerLatestData.getRates().getRub();
        currencyLoadDate.setId(id);
        currencyLoadDate.setQuotedTitleCurrency(symbols);
        currencyLoadDate.setBasicTitleCurrency(base);
        currencyLoadDate.setDate(date);
        currencyLoadDate.setValue(value);
        return Objects.isNull(currencyRepository.findByDateAndBasicTitleCurrencyAndQuotedTitleCurrency(date, base, symbols))
                ? currencyRepository.save(currencyLoadDate)
                : null;
    }

    //code style check
    public TreeSet<Currency> findByDateGreaterThanEqualAndDateLessThanEqualAndQuotedTitleCurrencyAndBasicTitleCurrency(
            String dateAfter, String dateBefore, String quotedTitleCurrency, String basicTitleCurrency) {

        String dateAfterFromTime = dateAfter.concat(" 00:00:00+0000");
        String dateBeforeFromTime = dateBefore.concat(" 00:00:00+0000");
        TreeSet<Currency> set = new TreeSet<>();
        return currencyRepository.findByDateGreaterThanEqualAndDateLessThanEqualAndQuotedTitleCurrencyAndBasicTitleCurrency(
                dateAfterFromTime, dateBeforeFromTime, quotedTitleCurrency, basicTitleCurrency);
    }

    //https://api.exchangeratesapi.io/history?start_at=2020-08-06&end_at=2020-08-07&symbol=RUB,EUR&base=USD
    @Override
    public List<Currency> update(ParamRequestUpdateDateClient paramRequestUpdateDateClient) {
        String urlRequest = API;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String symbols = paramRequestUpdateDateClient.getSymbols();
        String base = paramRequestUpdateDateClient.getBase();
        Long id = currencyRepository.findMaxId();
        List<Currency> listCurrency = new LinkedList<>();

        // Дата клиента больше последней даты - условие
        log.warn("after if");
        if(paramRequestUpdateDateClient.getDateEntryClient().after(currencyRepository.findMaxDate(base, symbols))){
            log.warn("before if");
            String dateRequestString = sdf.format(paramRequestUpdateDateClient.getDateEntryClient());
            String dateMaxString = sdf.format(currencyRepository.findMaxDate(base, symbols));

            urlRequest = API.concat("?start_at=").concat(dateMaxString).concat("&end_at=").concat(dateRequestString).concat("&symbols=").concat(symbols).concat("&base=").concat(base);
            log.warn(urlRequest);
            ApiAnswer apiAnswer = restTemplate.getForEntity(urlRequest, ApiAnswer.class).getBody();
            log.warn(apiAnswer.toString());
            Set<Date> allDate = apiAnswer.getRates().keySet();
            log.warn(allDate.toString());
            for(Date listDate : allDate) {
                /*
                log
                2020-08-08 04:31:40.986  WARN 15044 --- [        s0-io-3] c.d.o.d.i.core.cql.CqlRequestHandler     : Query '[0 values] select MAX(id) FROM currency ALLOW FILTERING' generated server side warning(s): Aggregation query used without partition key
2020-08-08 04:31:40.989  WARN 15044 --- [nio-8764-exec-1] r.k.s.currency.service.CurrencyService   : after if
2020-08-08 04:31:41.089  WARN 15044 --- [        s0-io-3] c.d.o.d.i.core.cql.CqlRequestHandler     : Query '[0 values] select max(date) FROM currency ALLOW FILTERING' generated server side warning(s): Aggregation query used without partition key
2020-08-08 04:31:41.092  WARN 15044 --- [nio-8764-exec-1] r.k.s.currency.service.CurrencyService   : before if
2020-08-08 04:31:41.218  WARN 15044 --- [        s0-io-3] c.d.o.d.i.core.cql.CqlRequestHandler     : Query '[0 values] select max(date) FROM currency ALLOW FILTERING' generated server side warning(s): Aggregation query used without partition key
2020-08-08 04:31:41.221  WARN 15044 --- [nio-8764-exec-1] r.k.s.currency.service.CurrencyService   : https://api.exchangeratesapi.io/history?start_at=2020-08-06&end_at=2020-08-07&symbols=USD&base=EUR
2020-08-08 04:31:43.042  WARN 15044 --- [nio-8764-exec-1] r.k.s.currency.service.CurrencyService   : ApiAnswer(rates={Fri Aug 07 07:00:00 NOVT 2020=AnswerLatestListCurrency(rub=null, usd=1.1817, eur=null), Thu Aug 06 07:00:00 NOVT 2020=AnswerLatestListCurrency(rub=null, usd=1.1843, eur=null)}, base=EUR)
2020-08-08 04:31:43.045  WARN 15044 --- [nio-8764-exec-1] r.k.s.currency.service.CurrencyService   : [Fri Aug 07 07:00:00 NOVT 2020, Thu Aug 06 07:00:00 NOVT 2020]
2020-08-08 04:31:43.046  WARN 15044 --- [nio-8764-exec-1] r.k.s.currency.service.CurrencyService   : Currency(id=4294, basicTitleCurrency=EUR, quotedTitleCurrency=USD, date=Fri Aug 07 07:00:00 NOVT 2020, value=1.1817)
2020-08-08 04:31:43.202  WARN 15044 --- [nio-8764-exec-1] r.k.s.currency.service.CurrencyService   : before if two
2020-08-08 04:31:43.202  WARN 15044 --- [nio-8764-exec-1] r.k.s.currency.service.CurrencyService   : Currency(id=4295, basicTitleCurrency=EUR, quotedTitleCurrency=USD, date=Thu Aug 06 07:00:00 NOVT 2020, value=1.1843)

                 */
                id++;
                Currency currencyLoad = new Currency();
                currencyLoad.setId(id);
                currencyLoad.setDate(listDate);
                Double value = Objects.isNull(apiAnswer.getRates().get(listDate).getRub())
                ? Objects.isNull(apiAnswer.getRates().get(listDate).getEur())
                ? apiAnswer.getRates().get(listDate).getUsd()
                : apiAnswer.getRates().get(listDate).getEur()
                : apiAnswer.getRates().get(listDate).getRub();
                currencyLoad.setValue(value);
                currencyLoad.setBasicTitleCurrency(base);
                currencyLoad.setQuotedTitleCurrency(symbols);
                log.warn(currencyLoad.toString());
                if(Objects.isNull(currencyRepository.findByDateAndBasicTitleCurrencyAndQuotedTitleCurrency(listDate, base, symbols))) {
                    log.warn("before if two");
                    listCurrency.add(currencyLoad);
                }
            }
            return currencyRepository.saveAll(listCurrency);
        }
        return null;
    }

}
