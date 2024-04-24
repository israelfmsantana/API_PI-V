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
@Table(name = Portfolio.TABLE_NAME)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Portfolio {

    public static final String TABLE_NAME = "portfolio";

    @Id
    @Column(name = "id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "action_id", nullable = false, updatable = false)
    private Action action;

    @Column(name = "symbol", length = 100, nullable = true, unique = false)
    @Size(min = 2, max = 100)
    private String symbolAction;
    
}
