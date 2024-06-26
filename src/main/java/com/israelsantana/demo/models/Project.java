package com.israelsantana.demo.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = Project.TABLE_NAME)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Project {
    public static final String TABLE_NAME = "have_pal";

    @Id
    @Column(name = "id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 100, nullable = false)
    @Size(min = 2, max = 100)
    @NotBlank
    private String name;

    @Column(name = "description", length = 300, nullable = false)
    @Size(min = 2, max = 300)
    @NotBlank
    private String description;

}
