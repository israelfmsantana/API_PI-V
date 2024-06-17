package com.israelsantana.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.israelsantana.demo.models.Action;

@Repository
public interface ActionRepository extends JpaRepository<Action, Long> {
    
    @Transactional(readOnly = true)
    List<Action> findBySymbol(String symbol);
}
