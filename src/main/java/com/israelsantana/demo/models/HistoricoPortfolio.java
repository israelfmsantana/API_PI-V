package com.israelsantana.demo.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = HistoricoPortfolio.TABLE_NAME)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class HistoricoPortfolio {

    public static final String TABLE_NAME = "historicoPortfolio";

    @Id
    @Column(name = "id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "sellAction_id", nullable = false, updatable = false)
    private Action sellAction;

    @Column(name = "sellSymbol", length = 100, nullable = true, unique = false)
    @Size(min = 2, max = 100)
    private String sellSymbolAction;

    @Column(name = "sellValueStock", nullable = true, unique = false)
    private Double sellValueStock;

    @Column(name = "sellValuePurchased", nullable = true, unique = false)
    private Double sellValuePurchased;

    @Column(name = "sellNumberStockPurchased", nullable = true, unique = false)
    private Double sellNumberStockPurchased;

    @Column(name = "sellDatePurchased", nullable = true, unique = false)
    private String sellDatePurchased;

    @Column(name = "sellDate", nullable = true, unique = false)
    private String sellDate;

    @Column(name = "sellValueStockSell", nullable = true, unique = false)
    private Double sellValueStockSell;

    @Column(name = "valorGanho", nullable = true, unique = false)
    private Double valorGanho;
    



    @ManyToOne
    @JoinColumn(name = "buyAction_id", nullable = false, updatable = false)
    private Action buyAction;

    @Column(name = "buySymbol", length = 100, nullable = true, unique = false)
    @Size(min = 2, max = 100)
    private String buySymbolAction;

    @Column(name = "buyValueStock", nullable = true, unique = false)
    private Double buyValueStock;

    @Column(name = "buyValuePurchased", nullable = true, unique = false)
    private Double buyValuePurchased;

    @Column(name = "buyNumberStockPurchased", nullable = true, unique = false)
    private Double buyNumberStockPurchased;

    @Column(name = "buyDate", nullable = true, unique = false)
    private String buyDate;
}
