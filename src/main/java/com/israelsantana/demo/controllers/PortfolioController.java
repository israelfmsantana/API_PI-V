package com.israelsantana.demo.controllers;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.israelsantana.demo.models.Action;
import com.israelsantana.demo.models.HistoricoPortfolio;
import com.israelsantana.demo.models.Portfolio;
import com.israelsantana.demo.models.User;
import com.israelsantana.demo.models.dto.PortfolioCreateDTO;
import com.israelsantana.demo.models.dto.PortfolioGetAllDTO;
import com.israelsantana.demo.models.dto.PortfolioUpdateDTO;
import com.israelsantana.demo.models.projection.PortfolioProjection;
import com.israelsantana.demo.services.ActionService;
import com.israelsantana.demo.services.HistoricoPortfolioService;
import com.israelsantana.demo.services.PortfolioService;
import com.israelsantana.demo.services.UserService;

@RestController
@RequestMapping("/portfolio")
@Validated
public class PortfolioController {
    
    @Autowired
    private PortfolioService portfolioService;

    @Autowired
    private HistoricoPortfolioService historicoPortfolioService;

    @Autowired
    private UserService userService;

    @Autowired
    private ActionService actionService;


    // Authentication required
    @GetMapping("/{id}")
    public ResponseEntity<Portfolio> findById(@PathVariable Long id) {
        Portfolio obj = this.portfolioService.findById(id);
        return ResponseEntity.ok(obj);
    }

    // Authentication required
    // @GetMapping()
    // public ResponseEntity<List<Portfolio>> findAll() {
    //     List<Portfolio> portfolios = this.portfolioService.findAll();
    //     return ResponseEntity.ok().body(portfolios);
    // }

    @GetMapping()
    public List<PortfolioGetAllDTO> findAll() {
        return this.portfolioService.findAll().stream().map(portfolio -> {
            PortfolioGetAllDTO dto = new PortfolioGetAllDTO();
            dto.setSymbolAction(portfolio.getSymbolAction());
            dto.setValueStock(portfolio.getValueStock());
            dto.setValuePurchased(portfolio.getValuePurchased());
            dto.setNumberStockPurchased(portfolio.getNumberStockPurchased());
            dto.setUpdateLastStock(portfolio.getUpdateLastStock());
            return dto;
        }).collect(Collectors.toList());

    }

    @GetMapping("/carregar")
    public List<JsonNode> findAllCarregar() {
        ObjectMapper mapper = new ObjectMapper();
        List<JsonNode> listJson = new ArrayList<>();
        String jsonData = "[\n" +
            "    {\n" +
            "        \"symbolAction\": \"BIOM3\",\n" +
            "        \"valueStock\": 18.37,\n" +
            "        \"valuePurchased\": 100.0,\n" +
            "        \"numberStockPurchased\": 5.443658138268916,\n" +
            "        \"updateLastStock\": \"2024/06/11\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"symbolAction\": \"SYNE3\",\n" +
            "        \"valueStock\": 9.36,\n" +
            "        \"valuePurchased\": 100.0,\n" +
            "        \"numberStockPurchased\": 10.683760683760685,\n" +
            "        \"updateLastStock\": \"2024/06/11\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"symbolAction\": \"GFSA3\",\n" +
            "        \"valueStock\": 5.31,\n" +
            "        \"valuePurchased\": 100.0,\n" +
            "        \"numberStockPurchased\": 18.832391713747647,\n" +
            "        \"updateLastStock\": \"2024/06/11\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"symbolAction\": \"PSSA3\",\n" +
            "        \"valueStock\": 29.94,\n" +
            "        \"valuePurchased\": 100.0,\n" +
            "        \"numberStockPurchased\": 3.34001336005344,\n" +
            "        \"updateLastStock\": \"2024/06/11\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"symbolAction\": \"AMZO34\",\n" +
            "        \"valueStock\": 47.15,\n" +
            "        \"valuePurchased\": 100.0,\n" +
            "        \"numberStockPurchased\": 2.1208907741251326,\n" +
            "        \"updateLastStock\": \"2024/06/11\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"symbolAction\": \"RAPT4\",\n" +
            "        \"valueStock\": 11.2,\n" +
            "        \"valuePurchased\": 100.0,\n" +
            "        \"numberStockPurchased\": 8.928571428571429,\n" +
            "        \"updateLastStock\": \"2024/06/11\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"symbolAction\": \"SNAG11\",\n" +
            "        \"valueStock\": 10.04,\n" +
            "        \"valuePurchased\": 100.0,\n" +
            "        \"numberStockPurchased\": 9.9601593625498,\n" +
            "        \"updateLastStock\": \"2024/06/11\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"symbolAction\": \"DIVO11\",\n" +
            "        \"valueStock\": 91.1,\n" +
            "        \"valuePurchased\": 100.0,\n" +
            "        \"numberStockPurchased\": 1.0976948408342482,\n" +
            "        \"updateLastStock\": \"2024/06/11\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"symbolAction\": \"BOVX11\",\n" +
            "        \"valueStock\": 13.01,\n" +
            "        \"valuePurchased\": 100.0,\n" +
            "        \"numberStockPurchased\": 7.686395080707149,\n" +
            "        \"updateLastStock\": \"2024/06/11\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"symbolAction\": \"RURA11\",\n" +
            "        \"valueStock\": 9.76,\n" +
            "        \"valuePurchased\": 100.0,\n" +
            "        \"numberStockPurchased\": 10.245901639344263,\n" +
            "        \"updateLastStock\": \"2024/06/11\"\n" +
            "    }\n" +
            "]";        
        try {

            List<Portfolio> portfolios = portfolioService.findAll();
            if (portfolios.isEmpty()) {
                User user = userService.findById(1L);
                Action action1 = actionService.findBySymbolStock("BIOM3");
                Portfolio obj1 = new Portfolio(null, user , action1,"BIOM3",18.37, 100.0,5.443658138268916, "2024/06/11");
                portfolioService.create(obj1);

                Action action2 = actionService.findBySymbolStock("SYNE3");
                Portfolio obj2 = new Portfolio(null,user,action2,"SYNE3",9.36, 100.0, 10.683760683760685, "2024/06/11");
                portfolioService.create(obj2);

                Action action3 = actionService.findBySymbolStock("GFSA3");
                Portfolio obj3 = new Portfolio(null, user,action3,"GFSA3",5.31, 100.0, 18.832391713747647, "2024/06/11");
                portfolioService.create(obj3);

                Action action4 = actionService.findBySymbolStock("PSSA3");
                Portfolio obj4 = new Portfolio(null, user,action4,"PSSA3",29.94, 100.0, 3.34001336005344, "2024/06/11");
                portfolioService.create(obj4);

                Action action5 = actionService.findBySymbolStock("AMZO34");
                Portfolio obj5 = new Portfolio( null,user,action5,"AMZO34",47.15, 100.0, 2.1208907741251326, "2024/06/11");
                portfolioService.create(obj5);

                Action action6 = actionService.findBySymbolStock("RAPT4");
                Portfolio obj6 = new Portfolio(null,user,action6,"RAPT4",11.2, 100.0, 8.928571428571429, "2024/06/11");
                portfolioService.create(obj6);

                Action action7 = actionService.findBySymbolStock("SNAG11");
                Portfolio obj7 = new Portfolio(null,user,action7,"SNAG11",10.04, 100.0, 9.9601593625498, "2024/06/11");
                portfolioService.create(obj7);

                Action action8 = actionService.findBySymbolStock("DIVO11");
                Portfolio obj8 = new Portfolio(null,user,action8,"DIVO11",91.1, 100.0, 1.0976948408342482, "2024/06/11");
                portfolioService.create(obj8);

                Action action9 = actionService.findBySymbolStock("BOVX11");
                Portfolio obj9 = new Portfolio(null,user,action9,"BOVX11",13.01, 100.0, 7.686395080707149, "2024/06/11");
                portfolioService.create(obj9);

                Action action10 = actionService.findBySymbolStock("RURA11");
                Portfolio obj10 = new Portfolio(null,user,action10,"RURA11",9.76, 100.0, 10.245901639344263, "2024/06/11");
                portfolioService.create(obj10);
            }
            

            JsonNode rootNode = mapper.readTree(jsonData);
            if (rootNode.isArray()) {
                for (JsonNode node : rootNode) {
                    listJson.add(node);
                }
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            // Aqui você pode lidar com a exceção de outra forma, como retornando uma mensagem de erro ou lançando uma exceção personalizada
        }

        return listJson;
    }




    @GetMapping("/atualizar")
    public List<JsonNode> findAllAtualizar() {
        ObjectMapper mapper = new ObjectMapper();
        List<JsonNode> listJson = new ArrayList<>();
        String jsonData = "[\n" +
            "    {\n" +
            "        \"symbolAction\": \"BIOM3\",\n" +
            "        \"valueStock\": 18.37,\n" +
            "        \"valuePurchased\": 100.0,\n" +
            "        \"numberStockPurchased\": 5.443658138268916,\n" +
            "        \"updateLastStock\": \"2024/06/11\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"symbolAction\": \"SYNE3\",\n" +
            "        \"valueStock\": 9.36,\n" +
            "        \"valuePurchased\": 100.0,\n" +
            "        \"numberStockPurchased\": 10.683760683760685,\n" +
            "        \"updateLastStock\": \"2024/06/11\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"symbolAction\": \"GFSA3\",\n" +
            "        \"valueStock\": 5.31,\n" +
            "        \"valuePurchased\": 100.0,\n" +
            "        \"numberStockPurchased\": 18.832391713747647,\n" +
            "        \"updateLastStock\": \"2024/06/11\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"symbolAction\": \"PSSA3\",\n" +
            "        \"valueStock\": 29.94,\n" +
            "        \"valuePurchased\": 100.0,\n" +
            "        \"numberStockPurchased\": 3.34001336005344,\n" +
            "        \"updateLastStock\": \"2024/06/11\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"symbolAction\": \"AMZO34\",\n" +
            "        \"valueStock\": 47.15,\n" +
            "        \"valuePurchased\": 100.0,\n" +
            "        \"numberStockPurchased\": 2.1208907741251326,\n" +
            "        \"updateLastStock\": \"2024/06/11\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"symbolAction\": \"RAPT4\",\n" +
            "        \"valueStock\": 11.2,\n" +
            "        \"valuePurchased\": 100.0,\n" +
            "        \"numberStockPurchased\": 8.928571428571429,\n" +
            "        \"updateLastStock\": \"2024/06/11\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"symbolAction\": \"SNAG11\",\n" +
            "        \"valueStock\": 10.04,\n" +
            "        \"valuePurchased\": 100.0,\n" +
            "        \"numberStockPurchased\": 9.9601593625498,\n" +
            "        \"updateLastStock\": \"2024/06/11\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"symbolAction\": \"DIVO11\",\n" +
            "        \"valueStock\": 91.1,\n" +
            "        \"valuePurchased\": 100.0,\n" +
            "        \"numberStockPurchased\": 1.0976948408342482,\n" +
            "        \"updateLastStock\": \"2024/06/11\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"symbolAction\": \"BOVX11\",\n" +
            "        \"valueStock\": 13.01,\n" +
            "        \"valuePurchased\": 100.0,\n" +
            "        \"numberStockPurchased\": 7.686395080707149,\n" +
            "        \"updateLastStock\": \"2024/06/11\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"symbolAction\": \"PETR4\",\n" +
            "        \"valueStock\": 30.74,\n" +
            "        \"valuePurchased\": 109.7336,\n" +
            "        \"numberStockPurchased\": 3.56973324658,\n" +
            "        \"updateLastStock\": \"2024/06/13\"\n" +
            "    }\n" +
            "]";        
        try {

            Portfolio portfolio = portfolioService.findById(10L);
            if (portfolio.getSymbolAction().equals("RURA11")) {
                User user = userService.findById(1L);
                Action action1 = actionService.findBySymbolStock("PETR4");
                Portfolio obj1 = new Portfolio(10L, user , action1,"PETR4",30.74, 109.7336,3.56973324658, "2024/06/11");
                portfolioService.update(obj1);

                
            }



            List<HistoricoPortfolio> historicoPortfolio = historicoPortfolioService.findAll();
            if (historicoPortfolio.isEmpty()) {
                User user = userService.findById(1L);
                Action sellAction = actionService.findBySymbolStock("RURA11");
                Action buyAction = actionService.findBySymbolStock("PETR4");
                HistoricoPortfolio obj = new HistoricoPortfolio(null, user , sellAction,"RURA11",9.76, 100.0,5.443658138268916, "2024/06/11","2024/06/13",10.71,9.7336,buyAction,"PETR4",30.74, 109.7336,3.56973324658,"2024/06/13");
                historicoPortfolioService.create(obj);

                
            }
            

            JsonNode rootNode = mapper.readTree(jsonData);
            if (rootNode.isArray()) {
                for (JsonNode node : rootNode) {
                    listJson.add(node);
                }
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            // Aqui você pode lidar com a exceção de outra forma, como retornando uma mensagem de erro ou lançando uma exceção personalizada
        }

        return listJson;
    }


    // Authentication required
    @GetMapping("/user/ids/{ids}")
    public ResponseEntity<List<PortfolioProjection>> findAllByUser_ids(@PathVariable List<Long> ids ) {
        List<PortfolioProjection> portfolioProjections = this.portfolioService.findAllByUser_ids(ids);
        return ResponseEntity.ok().body(portfolioProjections);
    }

    // Authentication required
    @GetMapping("/pal/ids/{ids}")
    public ResponseEntity<List<PortfolioProjection>> findAllByPal_ids(@PathVariable List<Long> ids ) {
        List<PortfolioProjection> portfolioProjections = this.portfolioService.findAllByPal_ids(ids);
        return ResponseEntity.ok().body(portfolioProjections);
    }

    // Authentication required ADMIN
    @GetMapping("/user_login_admin")
    public ResponseEntity<List<Portfolio>> findAllByUser_login_Admin() {
        List<Portfolio> portfolios = this.portfolioService.findAllByUser_login_Admin();
        return ResponseEntity.ok().body(portfolios);
    }



    // Authentication required ADMIN
    @PostMapping
    @Validated
    public ResponseEntity<Void> create(@Valid @RequestBody PortfolioCreateDTO obj) {
        Portfolio portfolio = this.portfolioService.fromDTO(obj);
        Portfolio newPortfolio = this.portfolioService.create(portfolio);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(newPortfolio).toUri();
        return ResponseEntity.created(uri).build();
    }

    // Authentication required ADMIN
    @PutMapping("/{id}")
    @Validated
    public ResponseEntity<Void> update(@Valid @RequestBody PortfolioUpdateDTO obj, @PathVariable Long id) {
        obj.setId(id);
        Portfolio portfolio = this.portfolioService.fromDTO(obj);
        this.portfolioService.update(portfolio);
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.portfolioService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
