package com.israelsantana.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.israelsantana.demo.models.Action;
import com.israelsantana.demo.models.Action.BarpiData;
import com.israelsantana.demo.repositories.ActionRepository;
import com.israelsantana.demo.services.exceptions.ObjectNotFoundException;

import lombok.ToString;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
public class ActionService {

    @Autowired
    private ActionRepository actionRepository;


    public List<Action> findAll() {
        List<Action> actions = this.actionRepository.findAll();
        return actions;
    }



    @Transactional
    public List<Action> createList( List<Action> objs) {

        for(Action obj : objs) {
            obj.setId(null);
            this.actionRepository.save(obj);
        }
        return objs;
    }


    // public Action fromDTO(@Valid ActionCreateDTO obj) {
    //     Action action = new Action();
    //     action.setAdjClose(obj.getAdjClose());
    //     action.setClose(obj.getClose());
    //     action.setDate(obj.getDate());
    //     action.setHigh(obj.getHigh());
    //     action.setLow(obj.getLow());
    //     action.setName(obj.getName());
    //     action.setOpen(obj.getOpen());
    //     action.setVolume(obj.getVolume());
    //     return action;
    // }


    public Action findById(Long id) {
        Optional<Action> action = this.actionRepository.findById(id);
        return action.orElseThrow(() -> new ObjectNotFoundException(
                "Action not found! Id: " + id + ", Type: " + Action.class.getName()));
    }



    public Action CreateStock(List<String> stockValues) {

        Action action = new Action();

        action.setCurrency(stockValues.get(0));
        action.setTwoHundredDayAverage(stockValues.get(1) != "0.0" ? Double.parseDouble(stockValues.get(1)) : null);
        // action.setTwoHundredDayAverage(Double.parseDouble(stockValues.get(1)));
        action.setTwoHundredDayAverageChange(stockValues.get(2) != "0.0" ? Double.parseDouble(stockValues.get(2)) : null);
        // action.setTwoHundredDayAverageChange(Double.parseDouble(stockValues.get(2)));
        action.setTwoHundredDayAverageChangePercent(stockValues.get(3) != "0.0" ? Double.parseDouble(stockValues.get(3)) : null);
        // action.setTwoHundredDayAverageChangePercent(Double.parseDouble(stockValues.get(3)));
        action.setMarketCap(stockValues.get(4) != "0.0" ? Long.parseLong(stockValues.get(4)) : null);
        // action.setMarketCap(Long.parseLong(stockValues.get(4)));
        action.setShortName(stockValues.get(5) != "0.0" ? stockValues.get(5) : null);
        // action.setShortName(stockValues.get(5));
        action.setLongName(stockValues.get(6) != "0.0" ? stockValues.get(6): null);
        // action.setLongName(stockValues.get(6));
        action.setRegularMarketChange(stockValues.get(7) != "0.0" ? Double.parseDouble(stockValues.get(7)) : null);
        // action.setRegularMarketChange(Double.parseDouble(stockValues.get(7)));
        action.setRegularMarketChangePercent(stockValues.get(8) != "0.0" ? Double.parseDouble(stockValues.get(8)) : null);
        // action.setRegularMarketChangePercent(Double.parseDouble(stockValues.get(8)));
        action.setRegularMarketTime(stockValues.get(9) != "0.0" ? stockValues.get(9) : null);
        // action.setRegularMarketTime(stockValues.get(9));
        action.setRegularMarketPrice(stockValues.get(10) != "0.0" ? Double.parseDouble(stockValues.get(10)) : null);
        // action.setRegularMarketPrice(Double.parseDouble(stockValues.get(10)));
        action.setRegularMarketDayHigh(stockValues.get(11) != "0.0" ? Double.parseDouble(stockValues.get(11)) : null);
        // action.setRegularMarketDayHigh(Double.parseDouble(stockValues.get(11)));
        action.setRegularMarketDayRange(stockValues.get(12) != "0.0" ? stockValues.get(12) : null);
        // action.setRegularMarketDayRange(stockValues.get(12));
        action.setRegularMarketDayLow(stockValues.get(13) != "0.0" ? Double.parseDouble(stockValues.get(13)) : null);
        // action.setRegularMarketDayLow(Double.parseDouble(stockValues.get(13)));
        action.setRegularMarketVolume(stockValues.get(14) != "0.0" ? Double.parseDouble(stockValues.get(14)) : null);
        // action.setRegularMarketVolume(Double.parseDouble(stockValues.get(14)));
        action.setRegularMarketPreviousClose(stockValues.get(15) != "0.0" ? Double.parseDouble(stockValues.get(15)) : null);
        // action.setRegularMarketPreviousClose(Double.parseDouble(stockValues.get(15)));
        action.setRegularMarketOpen(stockValues.get(16) != "0.0" ? Double.parseDouble(stockValues.get(16)) : null);
        // action.setRegularMarketOpen(Double.parseDouble(stockValues.get(16)));
        action.setAverageDailyVolume3Month(stockValues.get(17) != "0.0" ? Double.parseDouble(stockValues.get(17)) : null);
        // action.setAverageDailyVolume3Month(Double.parseDouble(stockValues.get(17)));
        action.setAverageDailyVolume10Day(stockValues.get(18) != "0.0" ? Double.parseDouble(stockValues.get(18)) : null);
        // action.setAverageDailyVolume10Day(Double.parseDouble(stockValues.get(18)));
        action.setFiftyTwoWeekLowChange(stockValues.get(19) != "0.0" ? Double.parseDouble(stockValues.get(19)) : null);
        // action.setFiftyTwoWeekLowChange(Double.parseDouble(stockValues.get(19)));
        action.setFiftyTwoWeekRange(stockValues.get(20) != "0.0" ? stockValues.get(20) : null);
        // action.setFiftyTwoWeekRange(stockValues.get(20));
        action.setFiftyTwoWeekHighChange(stockValues.get(21) != "0.0" ? Double.parseDouble(stockValues.get(21)) : null);
        // action.setFiftyTwoWeekHighChange(Double.parseDouble(stockValues.get(21)));
        action.setFiftyTwoWeekHighChangePercent(stockValues.get(22) != "0.0" ? Double.parseDouble(stockValues.get(22)) : null);
        // action.setFiftyTwoWeekHighChangePercent(Double.parseDouble(sockValues.get(22)));
        action.setFiftyTwoWeekLow(stockValues.get(23) != "0.0" ? Double.parseDouble(stockValues.get(23)) : null);
        // action.setFiftyTwoWeekLow(Double.parseDouble(stockValues.get(23)));
        action.setFiftyTwoWeekHigh(stockValues.get(24) != "0.0" ? Double.parseDouble(stockValues.get(24)) : null);
        // action.setFiftyTwoWeekHigh(Double.parseDouble(stockValues.get(24)));
        action.setSymbol(stockValues.get(25) != "0.0" ? stockValues.get(25) : null);
        // action.setSymbol(stockValues.get(25));

        action = this.actionRepository.save(action);
        return action;
    }


    public Action CreateHistorical(List<String> historicalDataList) {

        Action action = new Action();

        action.setCurrency(historicalDataList.get(0));

        action.setTwoHundredDayAverage(null);
        action.setTwoHundredDayAverageChange(null);
        action.setTwoHundredDayAverageChangePercent(null);
        action.setMarketCap(null);
        action.setShortName(historicalDataList.get(8));
        action.setLongName(historicalDataList.get(9));
        action.setRegularMarketChange(null);
        action.setRegularMarketChangePercent(null);

        long segundos = Long.parseLong(historicalDataList.get(2));
        Date data = new Date(segundos * 1000);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        action.setRegularMarketTime(sdf.format(data));
        // action.setRegularMarketTime(historicalDataList.get(2));

        action.setRegularMarketPrice(Double.parseDouble(historicalDataList.get(6)));
        action.setRegularMarketDayHigh(Double.parseDouble(historicalDataList.get(4)));

        action.setRegularMarketDayRange(null);

        action.setRegularMarketDayLow(Double.parseDouble(historicalDataList.get(5)));
        action.setRegularMarketVolume(Double.parseDouble(historicalDataList.get(7)));

        action.setRegularMarketPreviousClose(null);


        action.setRegularMarketOpen(Double.parseDouble(historicalDataList.get(3)));


        action.setAverageDailyVolume3Month(null);
        action.setAverageDailyVolume10Day(null);
        action.setFiftyTwoWeekLowChange(null);
        action.setFiftyTwoWeekRange(null);
        action.setFiftyTwoWeekHighChange(null);
        action.setFiftyTwoWeekHighChangePercent(null);
        action.setFiftyTwoWeekLow(null);
        action.setFiftyTwoWeekHigh(null);

        action.setSymbol(historicalDataList.get(1));

        action = this.actionRepository.save(action);
        return action;
    }
}

