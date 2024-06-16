package com.israelsantana.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.israelsantana.demo.models.HistoricoPortfolio;
import com.israelsantana.demo.repositories.HistoricoPortfolioRepository;
import com.israelsantana.demo.services.exceptions.ObjectNotFoundException;

@Service
public class HistoricoPortfolioService {
    
    @Autowired
    private HistoricoPortfolioRepository historicoPortfolioRepository;


    public HistoricoPortfolio findById(Long id) {
        HistoricoPortfolio historicoPortfolio = this.historicoPortfolioRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(
                "Historico Portfolio not found! Id: " + id + ", Type: " + HistoricoPortfolio.class.getName()));

        return historicoPortfolio;
    }

   
    public List<HistoricoPortfolio> findAll() {
            
        List<HistoricoPortfolio> historicoPortfolios = this.historicoPortfolioRepository.findAll();
        return historicoPortfolios;
    }


    @Transactional
    public HistoricoPortfolio create( HistoricoPortfolio obj) {

        HistoricoPortfolio historicoPortfolio = new HistoricoPortfolio(null,obj.getUser(),obj.getSellAction(),obj.getSellSymbolAction(), obj.getSellValueStock(), obj.getSellValuePurchased(), obj.getSellNumberStockPurchased(), obj.getSellDatePurchased(), obj.getSellDate(), obj.getSellValueStockSell(),obj.getValorGanho(),obj.getBuyAction(),obj.getBuySymbolAction(), obj.getBuyValueStock(), obj.getBuyValuePurchased(), obj.getBuyNumberStockPurchased(), obj.getBuyDate());
        historicoPortfolio = this.historicoPortfolioRepository.save(historicoPortfolio);

        return historicoPortfolio;
    }

    // Authentication required
    // @Transactional
    // public Portfolio update(Portfolio obj) {
    //     UserSpringSecurity userSpringSecurity = UserService.authenticated();
    //     if (Objects.isNull(userSpringSecurity))
    //         throw new AuthorizationException("Access denied!");

    //     Portfolio newObj = findById(obj.getId());
    //     newObj.setAction(obj.getAction());
    //     newObj.setUser(obj.getUser());
    //     newObj.setNumberStockPurchased(obj.getNumberStockPurchased());
    //     newObj.setSymbolAction(obj.getSymbolAction());
    //     newObj.setUpdateLastStock(obj.getUpdateLastStock());
    //     newObj.setValueStock(obj.getValueStock());
    //     newObj.setValuePurchased(obj.getValuePurchased());

    //     return this.portfolioRepository.save(newObj);
    // }

    // Authentication required ADMIN
    // public void delete(Long id) {
    //     UserSpringSecurity userSpringSecurity = UserService.authenticated();
    //     if (Objects.isNull(userSpringSecurity) || !userSpringSecurity.hasRole(ProfileEnum.ADMIN))
    //         throw new AuthorizationException("Access denied!");

    //     findById(id);
    //     try {
    //         this.portfolioRepository.deleteById(id);
    //     } catch (Exception e) {
    //         throw new DataBindingViolationException("Cannot delete as there are related entities!");
    //     }
    // }

    // private Boolean userHasAction(UserSpringSecurity userSpringSecurity, Portfolio portfolio) {
    //     return portfolio.getUser().getId().equals(userSpringSecurity.getId());
    // }


    // public Portfolio fromDTO(@Valid PortfolioCreateDTO obj) {
    //     Portfolio portfolio = new Portfolio();
    //     portfolio.setUser(this.userService.findById(obj.getUserId()));
    //     portfolio.setAction(this.actionService.findById(obj.getActionId()));
    //     portfolio.setSymbolAction(obj.getSymbolAction());
    //     portfolio.setNumberStockPurchased(obj.getNumberStockPurchased());
    //     portfolio.setUpdateLastStock(obj.getUpdateLastStock());
    //     portfolio.setValueStock(obj.getValueStock());
    //     portfolio.setValuePurchased(obj.getValuePurchased());
    //     return portfolio;
    // }

    // public Portfolio fromDTO(@Valid PortfolioUpdateDTO obj) {
    //     Portfolio portfolio = new Portfolio();
    //     portfolio.setId(obj.getId());
    //     portfolio.setUser(this.userService.findById(obj.getUserId()));
    //     portfolio.setAction(this.actionService.findById(obj.getActionId()));
    //     portfolio.setSymbolAction(obj.getSymbolAction());
    //     portfolio.setNumberStockPurchased(obj.getNumberStockPurchased());
    //     portfolio.setUpdateLastStock(obj.getUpdateLastStock());
    //     portfolio.setValueStock(obj.getValueStock());
    //     portfolio.setValuePurchased(obj.getValuePurchased());
    //     return portfolio;
    // }
}
