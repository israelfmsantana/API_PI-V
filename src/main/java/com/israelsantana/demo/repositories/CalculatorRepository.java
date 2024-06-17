package com.israelsantana.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.israelsantana.demo.models.Calculator;
import com.israelsantana.demo.models.projection.CalculatorProjection;

@Repository
public interface CalculatorRepository extends JpaRepository<Calculator, Long> {

    List<CalculatorProjection> findAllByUserIdIn(List<Long> ids);

    List<CalculatorProjection> findAllByActionIdIn(List<Long> ids);
}
