package com.israelsantana.demo.services;

import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.israelsantana.demo.models.Portfolio;
import com.israelsantana.demo.models.dto.PortfolioCreateDTO;
import com.israelsantana.demo.models.dto.PortfolioUpdateDTO;
import com.israelsantana.demo.models.enums.ProfileEnum;
import com.israelsantana.demo.models.projection.PortfolioProjection;
import com.israelsantana.demo.repositories.PortfolioRepository;
import com.israelsantana.demo.security.UserSpringSecurity;
import com.israelsantana.demo.services.exceptions.AuthorizationException;
import com.israelsantana.demo.services.exceptions.DataBindingViolationException;
import com.israelsantana.demo.services.exceptions.ObjectNotFoundException;

@Service
public class PortfolioService {
    
    @Autowired
    private PortfolioRepository portfolioRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ActionService actionService;

    public Portfolio findById(Long id) {
        Portfolio portfolio = this.portfolioRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(
                "Portfolio not found! Id: " + id + ", Type: " + Portfolio.class.getName()));

    

        return portfolio;
    }

    // Authentication required
    public List<Portfolio> findAll() {
        // UserSpringSecurity userSpringSecurity = UserService.authenticated();
        // if (Objects.isNull(userSpringSecurity))
        //     throw new AuthorizationException("Access denied!");
            
        List<Portfolio> portfolios = this.portfolioRepository.findAll();
        return portfolios;
    }

    // Authentication required
    public List<PortfolioProjection> findAllByUser_ids(List<Long> user_ids) {
        UserSpringSecurity userSpringSecurity = UserService.authenticated();
        if (Objects.isNull(userSpringSecurity))
            throw new AuthorizationException("Access denied!");

        List<PortfolioProjection> portfolioProjections = this.portfolioRepository.findAllByUserIdIn(user_ids);
        return portfolioProjections;
    }

    // Authentication required
    public List<PortfolioProjection> findAllByPal_ids(List<Long> pal_ids) {
        UserSpringSecurity userSpringSecurity = UserService.authenticated();
        if (Objects.isNull(userSpringSecurity))
            throw new AuthorizationException("Access denied!");

        List<PortfolioProjection> portfolioProjections = this.portfolioRepository.findAllByActionIdIn(pal_ids);
        return portfolioProjections;
    }


    // Authentication required ADMIN
    public List<Portfolio> findAllByUser_login_Admin() {
        UserSpringSecurity userSpringSecurity = UserService.authenticated();
        if (Objects.isNull(userSpringSecurity) || !userSpringSecurity.hasRole(ProfileEnum.ADMIN) )
            throw new AuthorizationException("Access denied!");


        List<Portfolio> portfolios = this.portfolioRepository.findAll();
        return portfolios;
    }

    @Transactional
    public Portfolio create( Portfolio obj) {

        Portfolio portfolio = new Portfolio(null,obj.getUser(),obj.getAction(),obj.getSymbolAction(), obj.getValueStock(), obj.getValuePurchased(), obj.getNumberStockPurchased(), obj.getUpdateLastStock());
        portfolio = this.portfolioRepository.save(portfolio);

        return portfolio;
    }


    @Transactional
    public Portfolio update(Portfolio obj) {

        Portfolio newObj = findById(obj.getId());
        newObj.setAction(obj.getAction());
        newObj.setUser(obj.getUser());
        newObj.setNumberStockPurchased(obj.getNumberStockPurchased());
        newObj.setSymbolAction(obj.getSymbolAction());
        newObj.setUpdateLastStock(obj.getUpdateLastStock());
        newObj.setValueStock(obj.getValueStock());
        newObj.setValuePurchased(obj.getValuePurchased());

        return this.portfolioRepository.save(newObj);
    }

    // Authentication required ADMIN
    public void delete(Long id) {
        UserSpringSecurity userSpringSecurity = UserService.authenticated();
        if (Objects.isNull(userSpringSecurity) || !userSpringSecurity.hasRole(ProfileEnum.ADMIN))
            throw new AuthorizationException("Access denied!");

        findById(id);
        try {
            this.portfolioRepository.deleteById(id);
        } catch (Exception e) {
            throw new DataBindingViolationException("Cannot delete as there are related entities!");
        }
    }


    public Portfolio fromDTO(@Valid PortfolioCreateDTO obj) {
        Portfolio portfolio = new Portfolio();
        portfolio.setUser(this.userService.findById(obj.getUserId()));
        portfolio.setAction(this.actionService.findById(obj.getActionId()));
        portfolio.setSymbolAction(obj.getSymbolAction());
        portfolio.setNumberStockPurchased(obj.getNumberStockPurchased());
        portfolio.setUpdateLastStock(obj.getUpdateLastStock());
        portfolio.setValueStock(obj.getValueStock());
        portfolio.setValuePurchased(obj.getValuePurchased());
        return portfolio;
    }

    public Portfolio fromDTO(@Valid PortfolioUpdateDTO obj) {
        Portfolio portfolio = new Portfolio();
        portfolio.setId(obj.getId());
        portfolio.setUser(this.userService.findById(obj.getUserId()));
        portfolio.setAction(this.actionService.findById(obj.getActionId()));
        portfolio.setSymbolAction(obj.getSymbolAction());
        portfolio.setNumberStockPurchased(obj.getNumberStockPurchased());
        portfolio.setUpdateLastStock(obj.getUpdateLastStock());
        portfolio.setValueStock(obj.getValueStock());
        portfolio.setValuePurchased(obj.getValuePurchased());
        return portfolio;
    }
}
