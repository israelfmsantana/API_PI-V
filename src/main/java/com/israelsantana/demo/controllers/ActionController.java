package com.israelsantana.demo.controllers;

import java.io.IOException;
import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import com.israelsantana.demo.services.PortfolioService;
import com.israelsantana.demo.services.UserService;
import com.israelsantana.demo.services.exceptions.AuthorizationException;


@RestController
@RequestMapping("/actions")
@Validated
public class ActionController {
    
    @Autowired
    private ActionService actionService;

    @Autowired
    private UserService userService;

    @Autowired
    private PortfolioService portfolioService;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public ActionController(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/buscaAcoesBarpi")
    public ResponseEntity<?> create() {

        int cont = 1;
        try {
            
            ResponseEntity<String> response = restTemplate.getForEntity("https://brapi.dev/api/quote/list?&token=6gVPmTbucyXJD7ki6adiZS", String.class);
        
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
                    String stockCode = stockNode.get("stock").asText();
                    stockCodes.add(stockCode);
                }

                
                
            
                String codInvalid = "MRSA6B";
                for (String code : stockCodes) {
                    if (cont<=300 && !code.equals(codInvalid)) {
                        ResponseEntity<String> response2 = restTemplate.getForEntity("https://brapi.dev/api/quote/" + code + "?range=3mo&interval=1d&token=6gVPmTbucyXJD7ki6adiZS", String.class);

                        JsonNode jsonNode2 = objectMapper.readTree(response2.getBody());
                        JsonNode stocksNode2 = jsonNode2.get("results");

                        
                        
                        List<String> stockValues = new ArrayList<>();

                    

                        if (stocksNode2 != null && stocksNode2.isArray()) {
                            for (JsonNode stockNode : stocksNode2) {

                                JsonNode currencyNode = stockNode.get("currency");
                                if (currencyNode != null && !currencyNode.isNull()) {
                                    String currency = currencyNode.asText();
                                    stockValues.add(currency);
                                } else {
                                    stockValues.add("0.0");
                                }
                                

                                JsonNode twoHundredDayAverageNode = stockNode.get("twoHundredDayAverage");
                                if (twoHundredDayAverageNode != null && !twoHundredDayAverageNode.isNull()) {
                                    String twoHundredDayAverage = twoHundredDayAverageNode.asText();
                                    stockValues.add(twoHundredDayAverage);
                                } else {
                                    stockValues.add("0.0");
                                }
                                


                                JsonNode twoHundredDayAverageChangeNode = stockNode.get("twoHundredDayAverageChange");
                                if (twoHundredDayAverageChangeNode != null && !twoHundredDayAverageChangeNode.isNull()) {
                                    String twoHundredDayAverageChange = twoHundredDayAverageChangeNode.asText();
                                    stockValues.add(twoHundredDayAverageChange);
                                } else {
                                    stockValues.add("0.0");
                                }
                                


                                JsonNode twoHundredDayAverageChangePercentNode = stockNode.get("twoHundredDayAverageChangePercent");
                                if (twoHundredDayAverageChangePercentNode != null && !twoHundredDayAverageChangePercentNode.isNull()) {
                                    String twoHundredDayAverageChangePercent = twoHundredDayAverageChangePercentNode.asText();
                                    stockValues.add(twoHundredDayAverageChangePercent);
                                } else {
                                    stockValues.add("0.0");
                                }
                                

                                JsonNode marketCapNode = stockNode.get("marketCap");
                                if (marketCapNode != null && !marketCapNode.isNull()) {
                                    String marketCap = marketCapNode.asText();
                                    stockValues.add(marketCap);
                                } else {
                                    stockValues.add("0.0");
                                }
                                


                                JsonNode shortNameNode = stockNode.get("shortName");
                                if (shortNameNode != null && !shortNameNode.isNull()) {
                                    String shortName = shortNameNode.asText();
                                    stockValues.add(shortName);
                                } else {
                                    stockValues.add("0.0");
                                }
                                


                                JsonNode longNameNode = stockNode.get("longName");
                                if (longNameNode != null && !longNameNode.isNull()) {
                                    String longName = longNameNode.asText();
                                    stockValues.add(longName);
                                } else {
                                    stockValues.add("0.0");
                                }
                                


                                JsonNode regularMarketChangeNode = stockNode.get("regularMarketChange");
                                if (regularMarketChangeNode != null && !regularMarketChangeNode.isNull()) {
                                    String regularMarketChange = regularMarketChangeNode.asText();
                                    stockValues.add(regularMarketChange);
                                } else {
                                    stockValues.add("0.0");
                                }
                                


                                JsonNode regularMarketChangePercentNode = stockNode.get("regularMarketChangePercent");
                                if (regularMarketChangePercentNode != null && !regularMarketChangePercentNode.isNull()) {
                                    String regularMarketChangePercent = regularMarketChangePercentNode.asText();
                                    stockValues.add(regularMarketChangePercent);
                                } else {
                                    stockValues.add("0.0");
                                }
                                

                                JsonNode regularMarketTimeNode = stockNode.get("regularMarketTime");
                                if (regularMarketTimeNode != null && !regularMarketTimeNode.isNull()) {
                                    String regularMarketTime = regularMarketTimeNode.asText();
                                    stockValues.add(regularMarketTime);
                                } else {
                                    stockValues.add("0.0");
                                }
                                


                                JsonNode regularMarketPriceNode = stockNode.get("regularMarketPrice");
                                if (regularMarketPriceNode != null && !regularMarketPriceNode.isNull()) {
                                    String regularMarketPrice = regularMarketPriceNode.asText();
                                    stockValues.add(regularMarketPrice);
                                } else {
                                    stockValues.add("0.0");
                                }
                                


                                JsonNode regularMarketDayHighNode = stockNode.get("regularMarketDayHigh");
                                if (regularMarketDayHighNode != null && !regularMarketDayHighNode.isNull()) {
                                    String regularMarketDayHigh = regularMarketDayHighNode.asText();
                                    stockValues.add(regularMarketDayHigh);
                                } else {
                                    stockValues.add("0.0");
                                }
                                
                                
                                JsonNode regularMarketDayRangeNode = stockNode.get("regularMarketDayRange");
                                if (regularMarketDayRangeNode != null && !regularMarketDayRangeNode.isNull()) {
                                    String regularMarketDayRange = regularMarketDayRangeNode.asText();
                                    stockValues.add(regularMarketDayRange);
                                } else {
                                    stockValues.add("0.0");
                                }
                                


                                JsonNode regularMarketDayLowNode = stockNode.get("regularMarketDayLow");
                                if (regularMarketDayLowNode != null && !regularMarketDayLowNode.isNull()) {
                                    String regularMarketDayLow = regularMarketDayLowNode.asText();
                                    stockValues.add(regularMarketDayLow);
                                } else {
                                    stockValues.add("0.0");
                                }
                                

                                JsonNode regularMarketVolumeNode = stockNode.get("regularMarketVolume");
                                if (regularMarketVolumeNode != null && !regularMarketVolumeNode.isNull()) {
                                    String regularMarketVolume = regularMarketVolumeNode.asText();
                                    stockValues.add(regularMarketVolume);
                                } else {
                                    stockValues.add("0.0");
                                }
                                


                                JsonNode regularMarketPreviousCloseNode = stockNode.get("regularMarketPreviousClose");
                                if (regularMarketPreviousCloseNode != null && !regularMarketPreviousCloseNode.isNull()) {
                                    String regularMarketPreviousClose = regularMarketPreviousCloseNode.asText();
                                    stockValues.add(regularMarketPreviousClose);
                                } else {
                                    stockValues.add("0.0");
                                }
                                


                                JsonNode regularMarketOpenNode = stockNode.get("regularMarketOpen");
                                if (regularMarketOpenNode != null && !regularMarketOpenNode.isNull()) {
                                    String regularMarketOpen = regularMarketOpenNode.asText();
                                    stockValues.add(regularMarketOpen);
                                } else {
                                    stockValues.add("0.0");
                                }
                                


                                JsonNode averageDailyVolume3MonthNode = stockNode.get("averageDailyVolume3Month");
                                if (averageDailyVolume3MonthNode != null && !averageDailyVolume3MonthNode.isNull()) {
                                    String averageDailyVolume3Month = averageDailyVolume3MonthNode.asText();
                                    stockValues.add(averageDailyVolume3Month);
                                } else {
                                    stockValues.add("0.0");
                                }
                                


                                JsonNode averageDailyVolume10DayNode = stockNode.get("averageDailyVolume10Day");
                                if (averageDailyVolume10DayNode != null && !averageDailyVolume10DayNode.isNull()) {
                                    String averageDailyVolume10Day = averageDailyVolume10DayNode.asText();
                                    stockValues.add(averageDailyVolume10Day);
                                } else {
                                    stockValues.add("0.0");
                                }
                                

                                JsonNode fiftyTwoWeekLowChangeNode = stockNode.get("fiftyTwoWeekLowChange");
                                if (fiftyTwoWeekLowChangeNode != null && !fiftyTwoWeekLowChangeNode.isNull()) {
                                    String fiftyTwoWeekLowChange = fiftyTwoWeekLowChangeNode.asText();
                                    stockValues.add(fiftyTwoWeekLowChange);
                                } else {
                                    stockValues.add("0.0");
                                }
                                


                                JsonNode fiftyTwoWeekRangeNode = stockNode.get("fiftyTwoWeekRange");
                                if (fiftyTwoWeekRangeNode != null && !fiftyTwoWeekRangeNode.isNull()) {
                                    String fiftyTwoWeekRange = fiftyTwoWeekRangeNode.asText();
                                    stockValues.add(fiftyTwoWeekRange);
                                } else {
                                    stockValues.add("0.0");
                                }
                                

                                JsonNode fiftyTwoWeekHighChangeNode = stockNode.get("fiftyTwoWeekHighChange");
                                if (fiftyTwoWeekHighChangeNode != null && !fiftyTwoWeekHighChangeNode.isNull()) {
                                    String fiftyTwoWeekHighChange = fiftyTwoWeekHighChangeNode.asText();
                                    stockValues.add(fiftyTwoWeekHighChange);
                                } else {
                                    stockValues.add("0.0");
                                }
                                

                                JsonNode fiftyTwoWeekHighChangePercentNode = stockNode.get("fiftyTwoWeekHighChangePercent");
                                if (fiftyTwoWeekHighChangePercentNode != null && !fiftyTwoWeekHighChangePercentNode.isNull()) {
                                    String fiftyTwoWeekHighChangePercent = fiftyTwoWeekHighChangePercentNode.asText();
                                    stockValues.add(fiftyTwoWeekHighChangePercent);
                                } else {
                                    stockValues.add("0.0");
                                }
                                


                                JsonNode fiftyTwoWeekLowNode = stockNode.get("fiftyTwoWeekLow");
                                if (fiftyTwoWeekLowNode != null && !fiftyTwoWeekLowNode.isNull()) {
                                    String fiftyTwoWeekLow = fiftyTwoWeekLowNode.asText();
                                    stockValues.add(fiftyTwoWeekLow);
                                } else {
                                    stockValues.add("0.0");
                                }
                                


                                JsonNode fiftyTwoWeekHighNode = stockNode.get("fiftyTwoWeekHigh");
                                if (fiftyTwoWeekHighNode != null && !fiftyTwoWeekHighNode.isNull()) {
                                    String fiftyTwoWeekHigh = fiftyTwoWeekHighNode.asText();
                                    stockValues.add(fiftyTwoWeekHigh);
                                } else {
                                    stockValues.add("0.0");
                                }
                                


                                JsonNode symbolNode = stockNode.get("symbol");
                                if (symbolNode != null && !symbolNode.isNull()) {
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

                                        JsonNode currencyHistoricalNode = stockNode.get("currency");
                                        if (currencyHistoricalNode != null && !currencyHistoricalNode.isNull()) {
                                            String currency = currencyHistoricalNode.asText();
                                            historicalDataList.add(currency);
                                        } else {
                                            historicalDataList.add("0.0");
                                        }


                                        JsonNode symbolHistoricalNode = stockNode.get("symbol");
                                        if (symbolHistoricalNode != null && !symbolHistoricalNode.isNull()) {
                                            String symbol = symbolHistoricalNode.asText();
                                            historicalDataList.add(symbol);
                                        } else {
                                            historicalDataList.add("0.0");
                                        }


                                        JsonNode dateHistoricalNode = data.get("date");
                                        if (dateHistoricalNode != null && !dateHistoricalNode.isNull()) {
                                            String date = dateHistoricalNode.asText();
                                            historicalDataList.add(date);
                                        } else {
                                            historicalDataList.add("0.0");
                                        }


                                        JsonNode openHistoricalNode = data.get("open");
                                        if (openHistoricalNode != null && !openHistoricalNode.isNull()) {
                                            String open = openHistoricalNode.asText();
                                            historicalDataList.add(open);
                                        } else {
                                            historicalDataList.add("0.0");
                                        }
                                        

                                        JsonNode highHistoricalNode = data.get("high");
                                        if (highHistoricalNode != null && !highHistoricalNode.isNull()) {
                                            String high = highHistoricalNode.asText();
                                            historicalDataList.add(high);
                                        } else {
                                            historicalDataList.add("0.0");
                                        }


                                        JsonNode lowHistoricalNode = data.get("low");
                                        if (lowHistoricalNode != null && !lowHistoricalNode.isNull()) {
                                            String low = lowHistoricalNode.asText();
                                            historicalDataList.add(low);
                                        } else {
                                            historicalDataList.add("0.0");
                                        }


                                        JsonNode closeHistoricalNode = data.get("close");
                                        if (closeHistoricalNode != null && !closeHistoricalNode.isNull()) {
                                            String close = closeHistoricalNode.asText();
                                            historicalDataList.add(close);
                                        } else {
                                            historicalDataList.add("0.0");
                                        }


                                        JsonNode volumeHistoricalNode = data.get("volume");
                                        if (volumeHistoricalNode != null && !volumeHistoricalNode.isNull()) {
                                            String volume = volumeHistoricalNode.asText();
                                            historicalDataList.add(volume);
                                        } else {
                                            historicalDataList.add("0.0");
                                        }


                                        JsonNode shortNameHistoricalNode = stockNode.get("shortName");
                                        if (shortNameHistoricalNode != null && !shortNameHistoricalNode.isNull()) {
                                            String shortName = shortNameHistoricalNode.asText();
                                            historicalDataList.add(shortName);
                                        } else {
                                            historicalDataList.add("0.0");
                                        }


                                        JsonNode longNameHistoricalNode = stockNode.get("longName");
                                        if (longNameHistoricalNode != null && !longNameHistoricalNode.isNull()) {
                                            String longName = longNameHistoricalNode.asText();
                                            historicalDataList.add(longName);
                                        } else {
                                            historicalDataList.add("0.0");
                                        }

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
                return ResponseEntity.status(500).body("Erro na logica da API Barpi - " + cont);
            }

            return ResponseEntity.ok().body("foi");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erro na API");
        }
        
    }


    // Authentication required
    @GetMapping("/calculator")
    public ResponseEntity<?> calculator() {

        UserSpringSecurity userSpringSecurity = UserService.authenticated();
        if (Objects.isNull(userSpringSecurity))
            throw new AuthorizationException("Access denied!");


        List<String> tierList = volatility();


        List<String> actionChosen = new ArrayList<>();
        actionChosen.add("--------- Action High Volatility:");
        for (int i = 0; i < 3 ; i++) {
            actionChosen.add(tierList.get(i));

            Action action = actionService.findBySymbolStock(tierList.get(i));
            User user = userService.findById(userSpringSecurity.getId());


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
            User user = userService.findById(userSpringSecurity.getId());


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
            User user = userService.findById(userSpringSecurity.getId());


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


        int cont = 1;
        // try {
            
        //     ResponseEntity<String> response = restTemplate.getForEntity("https://brapi.dev/api/quote/list?&token=6gVPmTbucyXJD7ki6adiZS", String.class);
        
        //     ObjectMapper objectMapper = new ObjectMapper();
        //     Action action = new Action();
            
        //     try {
                
        //         JsonNode jsonNode = objectMapper.readTree(response.getBody());
                
                
        //         JsonNode stocksNode = jsonNode.get("stocks");

                
        //         List<String> stockCodes = new ArrayList<>();

        //         Iterator<JsonNode> iterator = stocksNode.iterator();
        //         JsonNode t = objectMapper.createObjectNode().put("chave1", "stock");
        
        //         while (iterator.hasNext()) {
        //             JsonNode stockNode = iterator.next();
        //             String stockCode = stockNode.get("stock").asText();
        //             stockCodes.add(stockCode);
        //         }

                
                
            
        //         String codInvalid = "MRSA6B";
        //         for (String code : stockCodes) {
        //             if (cont<=300 && !code.equals(codInvalid)) {
        //                 ResponseEntity<String> response2 = restTemplate.getForEntity("https://brapi.dev/api/quote/" + code + "?token=6gVPmTbucyXJD7ki6adiZS", String.class);

        //                 JsonNode jsonNode2 = objectMapper.readTree(response2.getBody());
        //                 JsonNode stocksNode2 = jsonNode2.get("results");

                        
        //                 List<String> stockValues = new ArrayList<>();

                    

        //                 if (stocksNode2 != null && stocksNode2.isArray()) {
        //                     for (JsonNode stockNode : stocksNode2) {

        //                         JsonNode currencyNode = stockNode.get("currency");
        //                         if (currencyNode != null && !currencyNode.isNull()) {
        //                             String currency = currencyNode.asText();
        //                             stockValues.add(currency);
        //                         } else {
        //                             stockValues.add("0.0");
        //                         }
                                

        //                         JsonNode twoHundredDayAverageNode = stockNode.get("twoHundredDayAverage");
        //                         if (twoHundredDayAverageNode != null && !twoHundredDayAverageNode.isNull()) {
        //                             String twoHundredDayAverage = twoHundredDayAverageNode.asText();
        //                             stockValues.add(twoHundredDayAverage);
        //                         } else {
        //                             stockValues.add("0.0");
        //                         }
                                


        //                         JsonNode twoHundredDayAverageChangeNode = stockNode.get("twoHundredDayAverageChange");
        //                         if (twoHundredDayAverageChangeNode != null && !twoHundredDayAverageChangeNode.isNull()) {
        //                             String twoHundredDayAverageChange = twoHundredDayAverageChangeNode.asText();
        //                             stockValues.add(twoHundredDayAverageChange);
        //                         } else {
        //                             stockValues.add("0.0");
        //                         }
                                


        //                         JsonNode twoHundredDayAverageChangePercentNode = stockNode.get("twoHundredDayAverageChangePercent");
        //                         if (twoHundredDayAverageChangePercentNode != null && !twoHundredDayAverageChangePercentNode.isNull()) {
        //                             String twoHundredDayAverageChangePercent = twoHundredDayAverageChangePercentNode.asText();
        //                             stockValues.add(twoHundredDayAverageChangePercent);
        //                         } else {
        //                             stockValues.add("0.0");
        //                         }
                                

        //                         JsonNode marketCapNode = stockNode.get("marketCap");
        //                         if (marketCapNode != null && !marketCapNode.isNull()) {
        //                             String marketCap = marketCapNode.asText();
        //                             stockValues.add(marketCap);
        //                         } else {
        //                             stockValues.add("0.0");
        //                         }
                                


        //                         JsonNode shortNameNode = stockNode.get("shortName");
        //                         if (shortNameNode != null && !shortNameNode.isNull()) {
        //                             String shortName = shortNameNode.asText();
        //                             stockValues.add(shortName);
        //                         } else {
        //                             stockValues.add("0.0");
        //                         }
                                


        //                         JsonNode longNameNode = stockNode.get("longName");
        //                         if (longNameNode != null && !longNameNode.isNull()) {
        //                             String longName = longNameNode.asText();
        //                             stockValues.add(longName);
        //                         } else {
        //                             stockValues.add("0.0");
        //                         }
                                


        //                         JsonNode regularMarketChangeNode = stockNode.get("regularMarketChange");
        //                         if (regularMarketChangeNode != null && !regularMarketChangeNode.isNull()) {
        //                             String regularMarketChange = regularMarketChangeNode.asText();
        //                             stockValues.add(regularMarketChange);
        //                         } else {
        //                             stockValues.add("0.0");
        //                         }
                                


        //                         JsonNode regularMarketChangePercentNode = stockNode.get("regularMarketChangePercent");
        //                         if (regularMarketChangePercentNode != null && !regularMarketChangePercentNode.isNull()) {
        //                             String regularMarketChangePercent = regularMarketChangePercentNode.asText();
        //                             stockValues.add(regularMarketChangePercent);
        //                         } else {
        //                             stockValues.add("0.0");
        //                         }
                                

        //                         JsonNode regularMarketTimeNode = stockNode.get("regularMarketTime");
        //                         if (regularMarketTimeNode != null && !regularMarketTimeNode.isNull()) {
        //                             String regularMarketTime = regularMarketTimeNode.asText();
        //                             stockValues.add(regularMarketTime);
        //                         } else {
        //                             stockValues.add("0.0");
        //                         }
                                


        //                         JsonNode regularMarketPriceNode = stockNode.get("regularMarketPrice");
        //                         if (regularMarketPriceNode != null && !regularMarketPriceNode.isNull()) {
        //                             String regularMarketPrice = regularMarketPriceNode.asText();
        //                             stockValues.add(regularMarketPrice);
        //                         } else {
        //                             stockValues.add("0.0");
        //                         }
                                


        //                         JsonNode regularMarketDayHighNode = stockNode.get("regularMarketDayHigh");
        //                         if (regularMarketDayHighNode != null && !regularMarketDayHighNode.isNull()) {
        //                             String regularMarketDayHigh = regularMarketDayHighNode.asText();
        //                             stockValues.add(regularMarketDayHigh);
        //                         } else {
        //                             stockValues.add("0.0");
        //                         }
                                
                                
        //                         JsonNode regularMarketDayRangeNode = stockNode.get("regularMarketDayRange");
        //                         if (regularMarketDayRangeNode != null && !regularMarketDayRangeNode.isNull()) {
        //                             String regularMarketDayRange = regularMarketDayRangeNode.asText();
        //                             stockValues.add(regularMarketDayRange);
        //                         } else {
        //                             stockValues.add("0.0");
        //                         }
                                


        //                         JsonNode regularMarketDayLowNode = stockNode.get("regularMarketDayLow");
        //                         if (regularMarketDayLowNode != null && !regularMarketDayLowNode.isNull()) {
        //                             String regularMarketDayLow = regularMarketDayLowNode.asText();
        //                             stockValues.add(regularMarketDayLow);
        //                         } else {
        //                             stockValues.add("0.0");
        //                         }
                                

        //                         JsonNode regularMarketVolumeNode = stockNode.get("regularMarketVolume");
        //                         if (regularMarketVolumeNode != null && !regularMarketVolumeNode.isNull()) {
        //                             String regularMarketVolume = regularMarketVolumeNode.asText();
        //                             stockValues.add(regularMarketVolume);
        //                         } else {
        //                             stockValues.add("0.0");
        //                         }
                                


        //                         JsonNode regularMarketPreviousCloseNode = stockNode.get("regularMarketPreviousClose");
        //                         if (regularMarketPreviousCloseNode != null && !regularMarketPreviousCloseNode.isNull()) {
        //                             String regularMarketPreviousClose = regularMarketPreviousCloseNode.asText();
        //                             stockValues.add(regularMarketPreviousClose);
        //                         } else {
        //                             stockValues.add("0.0");
        //                         }
                                


        //                         JsonNode regularMarketOpenNode = stockNode.get("regularMarketOpen");
        //                         if (regularMarketOpenNode != null && !regularMarketOpenNode.isNull()) {
        //                             String regularMarketOpen = regularMarketOpenNode.asText();
        //                             stockValues.add(regularMarketOpen);
        //                         } else {
        //                             stockValues.add("0.0");
        //                         }
                                


        //                         JsonNode averageDailyVolume3MonthNode = stockNode.get("averageDailyVolume3Month");
        //                         if (averageDailyVolume3MonthNode != null && !averageDailyVolume3MonthNode.isNull()) {
        //                             String averageDailyVolume3Month = averageDailyVolume3MonthNode.asText();
        //                             stockValues.add(averageDailyVolume3Month);
        //                         } else {
        //                             stockValues.add("0.0");
        //                         }
                                


        //                         JsonNode averageDailyVolume10DayNode = stockNode.get("averageDailyVolume10Day");
        //                         if (averageDailyVolume10DayNode != null && !averageDailyVolume10DayNode.isNull()) {
        //                             String averageDailyVolume10Day = averageDailyVolume10DayNode.asText();
        //                             stockValues.add(averageDailyVolume10Day);
        //                         } else {
        //                             stockValues.add("0.0");
        //                         }
                                

        //                         JsonNode fiftyTwoWeekLowChangeNode = stockNode.get("fiftyTwoWeekLowChange");
        //                         if (fiftyTwoWeekLowChangeNode != null && !fiftyTwoWeekLowChangeNode.isNull()) {
        //                             String fiftyTwoWeekLowChange = fiftyTwoWeekLowChangeNode.asText();
        //                             stockValues.add(fiftyTwoWeekLowChange);
        //                         } else {
        //                             stockValues.add("0.0");
        //                         }
                                


        //                         JsonNode fiftyTwoWeekRangeNode = stockNode.get("fiftyTwoWeekRange");
        //                         if (fiftyTwoWeekRangeNode != null && !fiftyTwoWeekRangeNode.isNull()) {
        //                             String fiftyTwoWeekRange = fiftyTwoWeekRangeNode.asText();
        //                             stockValues.add(fiftyTwoWeekRange);
        //                         } else {
        //                             stockValues.add("0.0");
        //                         }
                                

        //                         JsonNode fiftyTwoWeekHighChangeNode = stockNode.get("fiftyTwoWeekHighChange");
        //                         if (fiftyTwoWeekHighChangeNode != null && !fiftyTwoWeekHighChangeNode.isNull()) {
        //                             String fiftyTwoWeekHighChange = fiftyTwoWeekHighChangeNode.asText();
        //                             stockValues.add(fiftyTwoWeekHighChange);
        //                         } else {
        //                             stockValues.add("0.0");
        //                         }
                                

        //                         JsonNode fiftyTwoWeekHighChangePercentNode = stockNode.get("fiftyTwoWeekHighChangePercent");
        //                         if (fiftyTwoWeekHighChangePercentNode != null && !fiftyTwoWeekHighChangePercentNode.isNull()) {
        //                             String fiftyTwoWeekHighChangePercent = fiftyTwoWeekHighChangePercentNode.asText();
        //                             stockValues.add(fiftyTwoWeekHighChangePercent);
        //                         } else {
        //                             stockValues.add("0.0");
        //                         }
                                


        //                         JsonNode fiftyTwoWeekLowNode = stockNode.get("fiftyTwoWeekLow");
        //                         if (fiftyTwoWeekLowNode != null && !fiftyTwoWeekLowNode.isNull()) {
        //                             String fiftyTwoWeekLow = fiftyTwoWeekLowNode.asText();
        //                             stockValues.add(fiftyTwoWeekLow);
        //                         } else {
        //                             stockValues.add("0.0");
        //                         }
                                


        //                         JsonNode fiftyTwoWeekHighNode = stockNode.get("fiftyTwoWeekHigh");
        //                         if (fiftyTwoWeekHighNode != null && !fiftyTwoWeekHighNode.isNull()) {
        //                             String fiftyTwoWeekHigh = fiftyTwoWeekHighNode.asText();
        //                             stockValues.add(fiftyTwoWeekHigh);
        //                         } else {
        //                             stockValues.add("0.0");
        //                         }
                                


        //                         JsonNode symbolNode = stockNode.get("symbol");
        //                         if (symbolNode != null && !symbolNode.isNull()) {
        //                             String symbol = symbolNode.asText();
        //                             stockValues.add(symbol);
        //                         } else {
        //                             stockValues.add("0.0");
        //                         }
                                

        //                         action = this.actionService.CreateStock(stockValues);
        //                     }

        //                 }

        //             }
        //             cont += 1;
        //         }

                
        //     } catch (Exception e) {
        //         e.printStackTrace();
        //         return ResponseEntity.status(500).body("Erro na logica da API Barpi - " + cont);
        //     }

        // } catch (Exception e) {
        //     e.printStackTrace();
        //     return ResponseEntity.status(500).body("Erro na API");
        // }



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
    




    public List<String> volatility() {


        

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
