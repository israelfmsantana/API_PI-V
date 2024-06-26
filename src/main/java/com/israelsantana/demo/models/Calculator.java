package com.israelsantana.demo.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
<<<<<<<< HEAD:src/main/java/com/israelsantana/demo/models/Project.java
@Table(name = Project.TABLE_NAME)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Project {
    public static final String TABLE_NAME = "have_pal";
========
@Table(name = Calculator.TABLE_NAME)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Calculator {

    public static final String TABLE_NAME = "calculator";
>>>>>>>> c595f6557fb785bfb03855f857697dcc1f14a9b2:src/main/java/com/israelsantana/demo/models/Calculator.java

    @Id
    @Column(name = "id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 100, nullable = false)
    @Size(min = 2, max = 100)
    @NotBlank
    private String name;

<<<<<<<< HEAD:src/main/java/com/israelsantana/demo/models/Project.java
    @Column(name = "description", length = 300, nullable = false)
    @Size(min = 2, max = 300)
    @NotBlank
    private String description;
========
    @ManyToOne
    @JoinColumn(name = "action_id", nullable = false, updatable = false)
    private Action action;
>>>>>>>> c595f6557fb785bfb03855f857697dcc1f14a9b2:src/main/java/com/israelsantana/demo/models/Calculator.java

    @Column(name = "symbol", length = 100, nullable = true, unique = false)
    @Size(min = 2, max = 100)
    private String symbolAction;

    @Column(name = "valueStock", nullable = true, unique = false)
    private Double valueStock;

    @Column(name = "date", nullable = true, unique = false)
    private String date;
    
}
