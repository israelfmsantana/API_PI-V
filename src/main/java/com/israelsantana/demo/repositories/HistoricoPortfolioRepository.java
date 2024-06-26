package com.israelsantana.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.israelsantana.demo.models.HistoricoPortfolio;

@Repository
public interface HistoricoPortfolioRepository extends JpaRepository<HistoricoPortfolio, Long> {

}
