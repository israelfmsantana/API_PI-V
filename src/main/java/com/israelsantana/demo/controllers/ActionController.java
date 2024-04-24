package com.israelsantana.demo.controllers;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
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
import com.israelsantana.demo.models.Action.BarpiData;
import com.israelsantana.demo.models.Action.SymbolValuePair;
import com.israelsantana.demo.models.Action.SymbolValueReturn;
import com.israelsantana.demo.models.dto.PortfolioCreateDTO;
import com.israelsantana.demo.services.ActionService;


@RestController
@RequestMapping("/actions")
@Validated
public class ActionController {
    
    @Autowired
    private ActionService actionService;

    @Autowired
    private PortfolioController portfolioController;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public ActionController(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/barpi")
    public ResponseEntity<?> create() {
        List<String> sectorList = Arrays.asList("Retail Trade","finance");

        try {
            
            for (String sector : sectorList) {

                ResponseEntity<String> response = restTemplate.getForEntity("https://brapi.dev/api/quote/list?sector=" + sector + "&token=6gVPmTbucyXJD7ki6adiZS", String.class);
            
                ObjectMapper objectMapper = new ObjectMapper();
                Action action = new Action();
                
                try {
                    
                    JsonNode jsonNode = objectMapper.readTree(response.getBody());
                    
                  
                    JsonNode stocksNode = jsonNode.get("stocks");
    
                 
                    List<String> stockCodes = new ArrayList<>();
    
                    Iterator<JsonNode> iterator = stocksNode.iterator();
                    JsonNode t = objectMapper.createObjectNode().put("chave1", "stock");
            
                    while (iterator.hasNext()) {
                        JsonNode stockNode = iterator.next();
                        if (stockNode.get("type").asText().equals(t.get("chave1").asText())) 
                        {
                            String stockCode = stockNode.get("stock").asText();
                            stockCodes.add(stockCode);
                        }
                    }
    
                    
                    int cont = 1;
                
                    
                    for (String code : stockCodes) {
                        if (cont <= 5) {
                            ResponseEntity<String> response2 = restTemplate.getForEntity("https://brapi.dev/api/quote/" + code + "?range=3mo&interval=1d&token=6gVPmTbucyXJD7ki6adiZS", String.class);
    
                            JsonNode jsonNode2 = objectMapper.readTree(response2.getBody());
                            JsonNode stocksNode2 = jsonNode2.get("results");
    
                            
                            
                            List<String> stockValues = new ArrayList<>();
    
                        
    
                            if (stocksNode2 != null && stocksNode2.isArray()) {
                                for (JsonNode stockNode : stocksNode2) {

                                    JsonNode currencyNode = stockNode.get("currency");
                                    if (currencyNode != null) {
                                        String currency = currencyNode.asText();
                                        stockValues.add(currency);
                                    } else {
                                        stockValues.add("0.0");
                                    }
                                    

                                    JsonNode twoHundredDayAverageNode = stockNode.get("twoHundredDayAverage");
                                    if (twoHundredDayAverageNode != null) {
                                        String twoHundredDayAverage = twoHundredDayAverageNode.asText();
                                        stockValues.add(twoHundredDayAverage);
                                    } else {
                                        stockValues.add("0.0");
                                    }
                                    


                                    JsonNode twoHundredDayAverageChangeNode = stockNode.get("twoHundredDayAverageChange");
                                    if (twoHundredDayAverageChangeNode != null) {
                                        String twoHundredDayAverageChange = twoHundredDayAverageChangeNode.asText();
                                        stockValues.add(twoHundredDayAverageChange);
                                    } else {
                                        stockValues.add("0.0");
                                    }
                                   


                                    JsonNode twoHundredDayAverageChangePercentNode = stockNode.get("twoHundredDayAverageChangePercent");
                                    if (twoHundredDayAverageChangePercentNode != null) {
                                        String twoHundredDayAverageChangePercent = twoHundredDayAverageChangePercentNode.asText();
                                        stockValues.add(twoHundredDayAverageChangePercent);
                                    } else {
                                        stockValues.add("0.0");
                                    }
                                  

                                    JsonNode marketCapNode = stockNode.get("marketCap");
                                    if (marketCapNode != null) {
                                        String marketCap = marketCapNode.asText();
                                        stockValues.add(marketCap);
                                    } else {
                                        stockValues.add("0.0");
                                    }
                                    


                                    JsonNode shortNameNode = stockNode.get("shortName");
                                    if (shortNameNode != null) {
                                        String shortName = shortNameNode.asText();
                                        stockValues.add(shortName);
                                    } else {
                                        stockValues.add("0.0");
                                    }
                                   


                                    JsonNode longNameNode = stockNode.get("longName");
                                    if (longNameNode != null) {
                                        String longName = longNameNode.asText();
                                        stockValues.add(longName);
                                    } else {
                                        stockValues.add("0.0");
                                    }
                                    


                                    JsonNode regularMarketChangeNode = stockNode.get("regularMarketChange");
                                    if (regularMarketChangeNode != null) {
                                        String regularMarketChange = regularMarketChangeNode.asText();
                                        stockValues.add(regularMarketChange);
                                    } else {
                                        stockValues.add("0.0");
                                    }
                                   


                                    JsonNode regularMarketChangePercentNode = stockNode.get("regularMarketChangePercent");
                                    if (regularMarketChangePercentNode != null) {
                                        String regularMarketChangePercent = regularMarketChangePercentNode.asText();
                                        stockValues.add(regularMarketChangePercent);
                                    } else {
                                        stockValues.add("0.0");
                                    }
                                   

                                    JsonNode regularMarketTimeNode = stockNode.get("regularMarketTime");
                                    if (regularMarketTimeNode != null) {
                                        String regularMarketTime = regularMarketTimeNode.asText();
                                        stockValues.add(regularMarketTime);
                                    } else {
                                        stockValues.add("0.0");
                                    }
                                    


                                    JsonNode regularMarketPriceNode = stockNode.get("regularMarketPrice");
                                    if (regularMarketPriceNode != null) {
                                        String regularMarketPrice = regularMarketPriceNode.asText();
                                        stockValues.add(regularMarketPrice);
                                    } else {
                                        stockValues.add("0.0");
                                    }
                                   


                                    JsonNode regularMarketDayHighNode = stockNode.get("regularMarketDayHigh");
                                    if (regularMarketDayHighNode != null) {
                                        String regularMarketDayHigh = regularMarketDayHighNode.asText();
                                        stockValues.add(regularMarketDayHigh);
                                    } else {
                                        stockValues.add("0.0");
                                    }
                                   
                                    
                                    JsonNode regularMarketDayRangeNode = stockNode.get("regularMarketDayRange");
                                    if (regularMarketDayRangeNode != null) {
                                        String regularMarketDayRange = regularMarketDayRangeNode.asText();
                                        stockValues.add(regularMarketDayRange);
                                    } else {
                                        stockValues.add("0.0");
                                    }
                                   


                                    JsonNode regularMarketDayLowNode = stockNode.get("regularMarketDayLow");
                                    if (regularMarketDayLowNode != null) {
                                        String regularMarketDayLow = regularMarketDayLowNode.asText();
                                        stockValues.add(regularMarketDayLow);
                                    } else {
                                        stockValues.add("0.0");
                                    }
                                   

                                    JsonNode regularMarketVolumeNode = stockNode.get("regularMarketVolume");
                                    if (regularMarketVolumeNode != null) {
                                        String regularMarketVolume = regularMarketVolumeNode.asText();
                                        stockValues.add(regularMarketVolume);
                                    } else {
                                        stockValues.add("0.0");
                                    }
                                   


                                    JsonNode regularMarketPreviousCloseNode = stockNode.get("regularMarketPreviousClose");
                                    if (regularMarketPreviousCloseNode != null) {
                                        String regularMarketPreviousClose = regularMarketPreviousCloseNode.asText();
                                        stockValues.add(regularMarketPreviousClose);
                                    } else {
                                        stockValues.add("0.0");
                                    }
                                  


                                    JsonNode regularMarketOpenNode = stockNode.get("regularMarketOpen");
                                    if (regularMarketOpenNode != null) {
                                        String regularMarketOpen = regularMarketOpenNode.asText();
                                        stockValues.add(regularMarketOpen);
                                    } else {
                                        stockValues.add("0.0");
                                    }
                                   


                                    JsonNode averageDailyVolume3MonthNode = stockNode.get("averageDailyVolume3Month");
                                    if (averageDailyVolume3MonthNode != null) {
                                        String averageDailyVolume3Month = averageDailyVolume3MonthNode.asText();
                                        stockValues.add(averageDailyVolume3Month);
                                    } else {
                                        stockValues.add("0.0");
                                    }
                                  


                                    JsonNode averageDailyVolume10DayNode = stockNode.get("averageDailyVolume10Day");
                                    if (averageDailyVolume10DayNode != null) {
                                        String averageDailyVolume10Day = averageDailyVolume10DayNode.asText();
                                        stockValues.add(averageDailyVolume10Day);
                                    } else {
                                        stockValues.add("0.0");
                                    }
                                  

                                    JsonNode fiftyTwoWeekLowChangeNode = stockNode.get("fiftyTwoWeekLowChange");
                                    if (fiftyTwoWeekLowChangeNode != null) {
                                        String fiftyTwoWeekLowChange = fiftyTwoWeekLowChangeNode.asText();
                                        stockValues.add(fiftyTwoWeekLowChange);
                                    } else {
                                        stockValues.add("0.0");
                                    }
                                   


                                    JsonNode fiftyTwoWeekRangeNode = stockNode.get("fiftyTwoWeekRange");
                                    if (fiftyTwoWeekRangeNode != null) {
                                        String fiftyTwoWeekRange = fiftyTwoWeekRangeNode.asText();
                                        stockValues.add(fiftyTwoWeekRange);
                                    } else {
                                        stockValues.add("0.0");
                                    }
                                  

                                    JsonNode fiftyTwoWeekHighChangeNode = stockNode.get("fiftyTwoWeekHighChange");
                                    if (fiftyTwoWeekHighChangeNode != null) {
                                        String fiftyTwoWeekHighChange = fiftyTwoWeekHighChangeNode.asText();
                                        stockValues.add(fiftyTwoWeekHighChange);
                                    } else {
                                        stockValues.add("0.0");
                                    }
                                  

                                    JsonNode fiftyTwoWeekHighChangePercentNode = stockNode.get("fiftyTwoWeekHighChangePercent");
                                    if (fiftyTwoWeekHighChangePercentNode != null) {
                                        String fiftyTwoWeekHighChangePercent = fiftyTwoWeekHighChangePercentNode.asText();
                                        stockValues.add(fiftyTwoWeekHighChangePercent);
                                    } else {
                                        stockValues.add("0.0");
                                    }
                                  


                                    JsonNode fiftyTwoWeekLowNode = stockNode.get("fiftyTwoWeekLow");
                                    if (fiftyTwoWeekLowNode != null) {
                                        String fiftyTwoWeekLow = fiftyTwoWeekLowNode.asText();
                                        stockValues.add(fiftyTwoWeekLow);
                                    } else {
                                        stockValues.add("0.0");
                                    }
                                   


                                    JsonNode fiftyTwoWeekHighNode = stockNode.get("fiftyTwoWeekHigh");
                                    if (fiftyTwoWeekHighNode != null) {
                                        String fiftyTwoWeekHigh = fiftyTwoWeekHighNode.asText();
                                        stockValues.add(fiftyTwoWeekHigh);
                                    } else {
                                        stockValues.add("0.0");
                                    }
                                   


                                    JsonNode symbolNode = stockNode.get("symbol");
                                    if (symbolNode != null) {
                                        String symbol = symbolNode.asText();
                                        stockValues.add(symbol);
                                    } else {
                                        stockValues.add("0.0");
                                    }
                                 
    
                                    action = this.actionService.CreateStock(stockValues);
    
    
                                    JsonNode historicalData = stockNode.get("historicalDataPrice");
    
                                  
                                    if (historicalData != null && historicalData.isArray()) {
                                        for (JsonNode data : historicalData) {
    
                                            List<String> historicalDataList = new ArrayList<>();
                                            
                                            historicalDataList.add(stockNode.get("currency").asText());
                                            historicalDataList.add(stockNode.get("symbol").asText());
                                            historicalDataList.add(data.get("date").asText());
                                            historicalDataList.add(data.get("open").asText());
                                            historicalDataList.add(data.get("high").asText());
                                            historicalDataList.add(data.get("low").asText());
                                            historicalDataList.add(data.get("close").asText());
                                            historicalDataList.add(data.get("volume").asText());
                                            historicalDataList.add(stockNode.get("shortName").asText());
                                            historicalDataList.add(stockNode.get("longName").asText());
    
                                            action = this.actionService.CreateHistorical(historicalDataList);
                                        }
                                    }
                                }
    
                            }
    
                        }
                        cont += 1;
                    }
    
                    
                } catch (Exception e) {
                    e.printStackTrace();
                    return ResponseEntity.status(500).body("Erro na logica da API Barpi");
                }
            }

            return ResponseEntity.ok().body("foi");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erro na API");
        }
        
    }


    @GetMapping("/calculator")
    public ResponseEntity<?> calculator() {


        

        List<Action> listAction = actionService.findAll();
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
        actionChosen.add("Action High Volatility:");
        for (int i = 0; i < 3 ; i++) {
            actionChosen.add(tierListHighVolatility.get(i).symbol);
        }
        actionChosen.add(" ");
        actionChosen.add("Action Midium Volatility:");
        for (int i = 0; i < 3 ; i++) {
            actionChosen.add(tierListMediumVolatility.get(i).symbol);
        }

        actionChosen.add(" ");
        actionChosen.add("\nAction low Volatility:");
        for (int i = 0; i < 4 ; i++) {
            actionChosen.add(tierListLowVolatility.get(i).symbol);
        }
    

        return ResponseEntity.ok().body(actionChosen);
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
            double ret = (prices.get(i) - prices.get(i - 1)) / prices.get(i - 1);
            returns.add(ret);
        }

       
        double meanReturn = returns.stream().mapToDouble(Double::doubleValue).average().orElse(0);

       
        double sumSquaredDifferences = 0;
        for (double ret : returns) {
            sumSquaredDifferences += Math.pow(ret - meanReturn, 2);
        }

       
        double volatility = Math.sqrt(sumSquaredDifferences / (returns.size() - 1));
        return volatility;
    }



    public static List<Double> calculateDailyReturns(List<Double> prices) {
        List<Double> dailyReturns = new ArrayList<>();

       
        if (prices == null || prices.size() < 2) {
            throw new IllegalArgumentException("É necessário fornecer pelo menos dois preços para calcular os retornos diários.");
        }

       
        for (int i = 1; i < prices.size(); i++) {
            double previousPrice = prices.get(i - 1);
            double currentPrice = prices.get(i);
            double dailyReturn = (currentPrice - previousPrice) / previousPrice;
            dailyReturns.add(dailyReturn);
        }

        return dailyReturns;
    }
    
}
