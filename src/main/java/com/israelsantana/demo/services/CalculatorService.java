package com.israelsantana.demo.services;

import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.israelsantana.demo.models.Calculator;
import com.israelsantana.demo.models.dto.CalculatorCreateDTO;
import com.israelsantana.demo.models.dto.CalculatorUpdateDTO;
import com.israelsantana.demo.models.enums.ProfileEnum;
import com.israelsantana.demo.models.projection.CalculatorProjection;
import com.israelsantana.demo.repositories.CalculatorRepository;
import com.israelsantana.demo.security.UserSpringSecurity;
import com.israelsantana.demo.services.exceptions.AuthorizationException;
import com.israelsantana.demo.services.exceptions.DataBindingViolationException;
import com.israelsantana.demo.services.exceptions.ObjectNotFoundException;

@Service
public class CalculatorService {
    
    @Autowired
    private CalculatorRepository calculatorRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ActionService actionService;

    public Calculator findById(Long id) {
        Calculator calculator = this.calculatorRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(
                "Calculator not found! Id: " + id + ", Type: " + Calculator.class.getName()));

        return calculator;
    }

    public List<Calculator> findAll() {
            
        List<Calculator> calculators = this.calculatorRepository.findAll();
        return calculators;
    }

    public List<CalculatorProjection> findAllByUser_ids(List<Long> user_ids) {
        List<CalculatorProjection> calculatorProjections = this.calculatorRepository.findAllByUserIdIn(user_ids);
        return calculatorProjections;
    }


    public List<CalculatorProjection> findAllByAction_ids(List<Long> action_ids) {

        List<CalculatorProjection> calculatorProjections = this.calculatorRepository.findAllByActionIdIn(action_ids);
        return calculatorProjections;
    }

    @Transactional
    public Calculator create( Calculator obj) {

        Calculator calculator = new Calculator(null,obj.getUser(),obj.getAction(),obj.getSymbolAction(), obj.getValueStock(), obj.getDate());
        calculator = this.calculatorRepository.save(calculator);

        return calculator;
    }


    @Transactional
    public Calculator update(Calculator obj) {

        Calculator newObj = findById(obj.getId());
        newObj.setAction(obj.getAction());
        newObj.setUser(obj.getUser());
        newObj.setSymbolAction(obj.getSymbolAction());
        newObj.setDate(obj.getDate());
        newObj.setValueStock(obj.getValueStock());

        return this.calculatorRepository.save(newObj);
    }


    public void delete(Long id) {

        findById(id);
        try {
            this.calculatorRepository.deleteById(id);
        } catch (Exception e) {
            throw new DataBindingViolationException("Cannot delete as there are related entities!");
        }
    }


    public Calculator fromDTO(@Valid CalculatorCreateDTO obj) {
        Calculator calculator = new Calculator();
        calculator.setUser(this.userService.findById(obj.getUserId()));
        calculator.setAction(this.actionService.findById(obj.getActionId()));
        calculator.setSymbolAction(obj.getSymbolAction());
        calculator.setDate(obj.getDate());
        calculator.setValueStock(obj.getValueStock());
        return calculator;
    }

    public Calculator fromDTO(@Valid CalculatorUpdateDTO obj) {
        Calculator calculator = new Calculator();
        calculator.setId(obj.getId());
        calculator.setUser(this.userService.findById(obj.getUserId()));
        calculator.setAction(this.actionService.findById(obj.getActionId()));
        calculator.setSymbolAction(obj.getSymbolAction());
        calculator.setDate(obj.getDate());
        calculator.setValueStock(obj.getValueStock());
        return calculator;
    }
}
