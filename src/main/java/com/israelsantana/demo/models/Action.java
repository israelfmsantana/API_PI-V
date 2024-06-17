package com.israelsantana.demo.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = Action.TABLE_NAME)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Action {

    public static final String TABLE_NAME = "actions";
    
    @Id
    @Column(name = "id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "currency", length = 100, nullable = true, unique = false)
    @Size(min = 2, max = 100)
    private String currency;

    @Column(name = "twoHundredDayAverage", nullable = true, unique = false)
    private Double twoHundredDayAverage;

    @Column(name = "twoHundredDayAverageChange", nullable = true, unique = false)
    private Double twoHundredDayAverageChange;

    @Column(name = "twoHundredDayAverageChangePercent", nullable = true, unique = false)
    private Double twoHundredDayAverageChangePercent;

    @Column(name = "marketCap", nullable = true, unique = false)
    private Long marketCap;

    @Column(name = "shortName", length = 100, nullable = true, unique = false)
    @Size(min = 2, max = 100)
    private String shortName;

    @Column(name = "longName", length = 100, nullable = true, unique = false)
    @Size(min = 2, max = 100)
    private String longName;

    @Column(name = "regularMarketChange", nullable = true, unique = false)
    private Double regularMarketChange;

    @Column(name = "regularMarketChangePercent", nullable = true, unique = false)
    private Double regularMarketChangePercent;

    @Column(name = "regularMarketTime", length = 100, nullable = true, unique = false)
    @Size(min = 2, max = 100)
    private String regularMarketTime;

    @Column(name = "regularMarketPrice", nullable = true, unique = false)
    private Double regularMarketPrice;

    @Column(name = "regularMarketDayHigh", nullable = true, unique = false)
    private Double regularMarketDayHigh;

    @Column(name = "regularMarketDayRange", length = 100, nullable = true, unique = false)
    @Size(min = 2, max = 100)
    private String regularMarketDayRange;

    @Column(name = "regularMarketDayLow", nullable = true, unique = false)
    private Double regularMarketDayLow;

    @Column(name = "regularMarketVolume", nullable = true, unique = false)
    private Double regularMarketVolume;

    @Column(name = "regularMarketPreviousClose", nullable = true, unique = false)
    private Double regularMarketPreviousClose;

    @Column(name = "regularMarketOpen", nullable = true, unique = false)
    private Double regularMarketOpen;

    @Column(name = "averageDailyVolume3Month", nullable = true, unique = false)
    private Double averageDailyVolume3Month;

    @Column(name = "averageDailyVolume10Day", nullable = true, unique = false)
    private Double averageDailyVolume10Day;

    @Column(name = "fiftyTwoWeekLowChange", nullable = true, unique = false)
    private Double fiftyTwoWeekLowChange;

    @Column(name = "fiftyTwoWeekRange", length = 100, nullable = true, unique = false)
    @Size(min = 2, max = 100)
    private String fiftyTwoWeekRange;

    @Column(name = "fiftyTwoWeekHighChange", nullable = true, unique = false)
    private Double fiftyTwoWeekHighChange;

    @Column(name = "fiftyTwoWeekHighChangePercent", nullable = true, unique = false)
    private Double fiftyTwoWeekHighChangePercent;

    @Column(name = "fiftyTwoWeekLow", nullable = true, unique = false)
    private Double fiftyTwoWeekLow;

    @Column(name = "fiftyTwoWeekHigh", nullable = true, unique = false)
    private Double fiftyTwoWeekHigh;

    @Column(name = "symbol", length = 100, nullable = true, unique = false)
    @Size(min = 2, max = 100)
    private String symbol;
    


    @OneToMany(mappedBy = "action")
    @JsonProperty(access = Access.WRITE_ONLY)
    private List<Portfolio> portfolios = new ArrayList<Portfolio>();

    @OneToMany(mappedBy = "action")
    @JsonProperty(access = Access.WRITE_ONLY)
    private List<Calculator> calculators = new ArrayList<Calculator>();

    @OneToMany(mappedBy = "sellAction")
    @JsonProperty(access = Access.WRITE_ONLY)
    private List<HistoricoPortfolio> HistoricoPortfolioSell = new ArrayList<HistoricoPortfolio>();

    @OneToMany(mappedBy = "buyAction")
    @JsonProperty(access = Access.WRITE_ONLY)
    private List<HistoricoPortfolio> HistoricoPortfolioBuy = new ArrayList<HistoricoPortfolio>();


    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class BarpiData {
        private List<Action> results;

        public List<Action> getResults() {
            return results;
        }

        public void setResults(List<Action> results) {
            this.results = results;
        }
    }



    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class SymbolValuePair {
        public String symbol;
        public Double value;
    }



    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class SymbolValueReturn {
        public String symbol;
        public Double value;
    }
}
