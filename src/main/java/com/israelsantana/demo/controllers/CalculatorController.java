package com.israelsantana.demo.controllers;

import java.io.IOException;
import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.israelsantana.demo.models.Action;
import com.israelsantana.demo.models.Portfolio;
import com.israelsantana.demo.models.User;
import com.israelsantana.demo.models.Action.BarpiData;
import com.israelsantana.demo.models.Action.SymbolValuePair;
import com.israelsantana.demo.models.Action.SymbolValueReturn;
import com.israelsantana.demo.models.dto.PortfolioCreateDTO;
import com.israelsantana.demo.models.enums.ProfileEnum;
import com.israelsantana.demo.security.UserSpringSecurity;
import com.israelsantana.demo.services.ActionService;
import com.israelsantana.demo.services.CalculatorService;
import com.israelsantana.demo.services.PortfolioService;
import com.israelsantana.demo.services.UserService;
import com.israelsantana.demo.services.exceptions.AuthorizationException;


@RestController
@RequestMapping("/calculator")
@Validated
public class CalculatorController {
    
    @Autowired
    private CalculatorService calculatorService;

    @Autowired
    private UserService userService;

    @Autowired
    private ActionService actionService;

    @Autowired
    private PortfolioService portfolioService;


    @GetMapping("/calculator")
    public ResponseEntity<?> calculator() {

        List<Action> actions = actionService.findAll();
        LocalDateTime limitDate = LocalDateTime.of(2024, 5, 1, 0, 0);
        List<Action> actionsVolatility = new ArrayList<>();

        for (Action action : actions) {

            String regularMarketTimeStr = action.getRegularMarketTime();

            LocalDateTime marketTime = null;
            try {
                DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                marketTime = LocalDateTime.parse(regularMarketTimeStr, formatter1);
            } catch (DateTimeParseException e1) {
                try {
                    DateTimeFormatter formatter2 = DateTimeFormatter.ISO_DATE_TIME;
                    marketTime = LocalDateTime.parse(regularMarketTimeStr, formatter2);
                } catch (DateTimeParseException e2) {
                    System.out.println("Erro ao analisar a data: " + regularMarketTimeStr);
                    continue;
                }
            }

            if (marketTime.isBefore(limitDate)) {
                actionsVolatility.add(action);
            }
        }
        

        List<String> tierList = volatility(actionsVolatility);


        List<String> actionChosen = new ArrayList<>();
        actionChosen.add("--------- Action High Volatility:");
        for (int i = 0; i < 3 ; i++) {
            actionChosen.add(tierList.get(i));

            Action action = actionService.findBySymbolStock(tierList.get(i));
            User user = userService.findById(1L);


            LocalDate today = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            String todayString = today.format(formatter);

            Double valuePurchased = 100.0;
            Double numberStockPurchased = valuePurchased / action.getRegularMarketPrice();

            
            Portfolio portfolio = new Portfolio(null, user, action, tierList.get(i), action.getRegularMarketPrice(), valuePurchased, numberStockPurchased, todayString);
            portfolioService.create(portfolio);
        }


        actionChosen.add("--------- Action Medium Volatility:");
        for (int i = 3; i < 6 ; i++) {
            actionChosen.add(tierList.get(i));

            Action action = actionService.findBySymbolStock(tierList.get(i));
            User user = userService.findById(1L);


            LocalDate today = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            String todayString = today.format(formatter);

            Double valuePurchased = 100.0;
            Double numberStockPurchased = valuePurchased / action.getRegularMarketPrice();

            Portfolio portfolio = new Portfolio(null, user, action, tierList.get(i), action.getRegularMarketPrice(), valuePurchased, numberStockPurchased, todayString);
            portfolioService.create(portfolio);
        }


        actionChosen.add("--------- Action Low Volatility:");
        for (int i = 6; i < 10 ; i++) {
            actionChosen.add(tierList.get(i));

            Action action = actionService.findBySymbolStock(tierList.get(i));
            User user = userService.findById(1L);


            LocalDate today = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            String todayString = today.format(formatter);

            Double valuePurchased = 100.0;
            Double numberStockPurchased = valuePurchased / action.getRegularMarketPrice();

            Portfolio portfolio = new Portfolio(null, user, action, tierList.get(i), action.getRegularMarketPrice(),valuePurchased, numberStockPurchased, todayString);
            portfolioService.create(portfolio);
        }
        actionChosen.add("-------------------------------------------- Total: 0");
        
    

        return ResponseEntity.ok().body(actionChosen);
    }










    @GetMapping("/simulador")
    public ResponseEntity<?> simulador() {
        



        List<Portfolio> portfolios = portfolioService.findAll();
        List<String> tierList = volatility();
        Double total = 0.0;


        tierList.remove(9);
        tierList.add("MGLU3");
        Double ultimoValordeMercado = 10.0;
        

        for (int i=0; i <= 9; i++) {

            Action action = actionService.findBySymbolStock(portfolios.get(i).getSymbolAction());
            LocalDate today = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            String todayString = today.format(formatter);

            if (portfolios.get(i).getSymbolAction().equals(tierList.get(i))) {          

                
            }
            else {
                Action actionNew = actionService.findBySymbolStock(tierList.get(i));

                // Double valueSelling = action.getRegularMarketPrice() * portfolios.get(i).getNumberStockPurchased();
                Double valueSelling = ultimoValordeMercado * portfolios.get(i).getNumberStockPurchased();

                Double performance =  valueSelling - portfolios.get(i).getValuePurchased();

                total += performance;

                Double newValuePruchased = performance + portfolios.get(i).getValuePurchased();

                Double newNumberStockPurchased = newValuePruchased / actionNew.getRegularMarketPrice();

                portfolios.get(i).setValuePurchased(newValuePruchased);
                portfolios.get(i).setValueStock(actionNew.getRegularMarketPrice());
                portfolios.get(i).setNumberStockPurchased(newNumberStockPurchased);
                portfolios.get(i).setSymbolAction(actionNew.getSymbol());
                portfolios.get(i).setAction(actionNew);
                portfolios.get(i).setUpdateLastStock(todayString);


                portfolioService.update(portfolios.get(i));

            }

        }



        return ResponseEntity.ok().body("-------------------------------------- Total: " + total);
    }











    public static double mediaValoresList(List<Double> lista) {
        double soma = 0.0;
        for (double valor : lista) {
            soma += valor;
        }
        return soma / lista.size();
    }

    public static List<SymbolValueReturn> criarTierList(List<SymbolValuePair> symbolValuePairs, List<SymbolValueReturn> symbolValueReturns) {
        List<SymbolValueReturn> tierList = new ArrayList<>();
        Set<String> symbolsAdded = new HashSet<>(); 
        
        for (SymbolValuePair pair : symbolValuePairs) {
            for (SymbolValueReturn ret : symbolValueReturns) {
                if (pair.symbol.equals(ret.symbol) && !symbolsAdded.contains(pair.symbol)) {
                    SymbolValueReturn returnPair = new SymbolValueReturn();
                    returnPair.symbol = pair.symbol;
                    returnPair.value = ret.value;
                    tierList.add(returnPair);
                    symbolsAdded.add(pair.symbol); 
                }
            }
        }


        Collections.sort(tierList, new Comparator<SymbolValueReturn>() {
            @Override
            public int compare(SymbolValueReturn o1, SymbolValueReturn o2) {
               
                return Double.compare(o2.value, o1.value);
            }
        });

        return tierList;
    }





    public static double calculateVolatility(List<Double> prices) {
        if (prices == null || prices.size() < 2) {
            throw new IllegalArgumentException("É necessário fornecer pelo menos dois preços para calcular a volatilidade.");
        }
    
        List<Double> returns = new ArrayList<>();
        for (int i = 1; i < prices.size(); i++) {
            try {
                Double ret = (prices.get(i) - prices.get(i - 1)) / prices.get(i - 1);
                returns.add(ret);
            } catch (NullPointerException e) {
                System.err.println("Um valor nulo encontrado. Ignorando e continuando...");
            }
        }
    
        Double meanReturn = returns.stream().mapToDouble(Double::doubleValue).average().orElse(0);
    
        Double sumSquaredDifferences = 0.0;
        for (double ret : returns) {
            sumSquaredDifferences += Math.pow(ret - meanReturn, 2);
        }
    
        Double volatility = Math.sqrt(sumSquaredDifferences / (returns.size() - 1));
        return volatility;
    }    



    public static List<Double> calculateDailyReturns(List<Double> prices) {
        List<Double> dailyReturns = new ArrayList<>();
    
        if (prices == null || prices.size() < 2) {
            throw new IllegalArgumentException("É necessário fornecer pelo menos dois preços para calcular os retornos diários.");
        }
    
        for (int i = 1; i < prices.size(); i++) {
            try {
                double previousPrice = prices.get(i - 1);
                double currentPrice = prices.get(i);
                
                // Verifica se os preços não são zero antes de calcular o retorno diário
                if (previousPrice == 0.0) {
                    throw new IllegalArgumentException("O preço anterior não pode ser zero.");
                }
    
                double dailyReturn = (currentPrice - previousPrice) / previousPrice;
                dailyReturns.add(dailyReturn);
            } catch (NullPointerException e) {
                // Se ocorrer uma NullPointerException, apenas ignore e continue o loop
                System.err.println("Um valor nulo encontrado. Ignorando e continuando...");
            }
        }
    
        return dailyReturns;
    }
    




    public List<String> volatility(List<Action> listAction) {

        List<String> listSymbols = new ArrayList<>();
        String symbolPassing = listAction.get(0).getSymbol();
        listSymbols.add(symbolPassing);
        List<Double> pricesClose = new ArrayList<>();
        List<Double> listVolatility = new ArrayList<>();

        int cont = 0;
        for (Action act : listAction) {
            cont += 1;
            if (act.getSymbol().equals(symbolPassing)) {
                pricesClose.add(act.getRegularMarketPrice());
                if (cont == listAction.size()) {
                    listVolatility.add(calculateVolatility(pricesClose));
                    pricesClose.clear();
                    cont = 0;
                }
            } 
            else {
                listSymbols.add(act.getSymbol());
                listVolatility.add(calculateVolatility(pricesClose));
                pricesClose.clear();
                symbolPassing = act.getSymbol();
                pricesClose.add(act.getRegularMarketPrice());
            }
        }

        List<SymbolValuePair> highVolatility = new ArrayList<>();
        List<SymbolValuePair> mediumVolatility = new ArrayList<>();
        List<SymbolValuePair> lowVolatility = new ArrayList<>();

        List<SymbolValuePair> pairs = new ArrayList<>();
        for (int i = 0; i < listSymbols.size(); i++) {
            pairs.add(new SymbolValuePair(listSymbols.get(i), listVolatility.get(i) * 100));
            if (pairs.get(i).getValue() >= 2) {
                highVolatility.add(pairs.get(i));
            }
            if (pairs.get(i).getValue() < 2 && pairs.get(i).getValue() >= 1 ) {
                mediumVolatility.add(pairs.get(i));
            }
            if (pairs.get(i).getValue() < 1) {
                lowVolatility.add(pairs.get(i));
            }
        }

        
        


        symbolPassing = listAction.get(0).getSymbol();
        List<SymbolValueReturn> pairsReturn = new ArrayList<>();
        for (Action act : listAction) {
            cont += 1;
            if (act.getSymbol().equals(symbolPassing)) {
                
                pricesClose.add(act.getRegularMarketPrice());
                if (cont == listAction.size()) {
                    pairsReturn.add(new SymbolValueReturn(symbolPassing,mediaValoresList(calculateDailyReturns(pricesClose))));
                    pricesClose.clear();
                    cont = 0;
                    symbolPassing = listAction.get(0).getSymbol();
                }
            }
            else {
                pairsReturn.add(new SymbolValueReturn(symbolPassing,mediaValoresList(calculateDailyReturns(pricesClose))));
                pricesClose.clear();
                symbolPassing = act.getSymbol();
                pricesClose.add(act.getRegularMarketPrice());
            }
            
        }
        List<SymbolValueReturn> tierListHighVolatility = criarTierList(highVolatility, pairsReturn);
        List<SymbolValueReturn> tierListMediumVolatility = criarTierList(mediumVolatility, pairsReturn);
        List<SymbolValueReturn> tierListLowVolatility = criarTierList(lowVolatility, pairsReturn);


       
        List<String> actionChosen = new ArrayList<>();
        for (int i = 0; i < 3 ; i++) {
            actionChosen.add(tierListHighVolatility.get(i).symbol);
        }
        for (int i = 0; i < 3 ; i++) {
            actionChosen.add(tierListMediumVolatility.get(i).symbol);
        }
        for (int i = 0; i < 4 ; i++) {
            actionChosen.add(tierListLowVolatility.get(i).symbol);
        }
    

        return actionChosen;
    }


    @GetMapping("/{id}")
    public ResponseEntity<Action> findById(@PathVariable Long id) {
        Action action = this.actionService.findById(id);
        return ResponseEntity.ok().body(action);
    }



    @GetMapping()
    public ResponseEntity<List<Action>> findAll() {
        List<Action> actions = this.actionService.findAll();
        return ResponseEntity.ok().body(actions);
    }
    
}
