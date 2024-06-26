package com.israelsantana.demo.controllers;

import java.net.URI;
import java.util.List;

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

import com.israelsantana.demo.models.AboutMe;
import com.israelsantana.demo.models.dto.AboutMeCreateDTO;
import com.israelsantana.demo.models.dto.AboutMeUpdateDTO;
import com.israelsantana.demo.services.AboutMeService;

@RestController
@RequestMapping("/aboutMe")
@Validated
public class AboutMeController {
    
    @Autowired
    private AboutMeService aboutMeService;


    @GetMapping("/{id}")
    public ResponseEntity<AboutMe> findById(@PathVariable Long id) {
        AboutMe aboutMe = this.aboutMeService.findById(id);
        return ResponseEntity.ok().body(aboutMe);
    }


    @GetMapping("/name/{name}")
    public ResponseEntity<AboutMe> findByName (@PathVariable String name) {
        AboutMe aboutMe = this.aboutMeService.findByName(name);
        return ResponseEntity.ok().body(aboutMe);
    }


    @GetMapping()
    public ResponseEntity<List<AboutMe>> findAll() {
        List<AboutMe> aboutMes = this.aboutMeService.findAll();
        return ResponseEntity.ok().body(aboutMes);
    }


    @PostMapping
    @Validated
    public ResponseEntity<Void> create (@Valid @RequestBody AboutMeCreateDTO obj) {
        AboutMe aboutMe = this.aboutMeService.fromDTO(obj);
        AboutMe newAboutMe = this.aboutMeService.create(aboutMe);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(newAboutMe.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }


    @PutMapping("/{id}")
    @Validated
    public ResponseEntity<Void> update (@PathVariable Long id, @Valid @RequestBody AboutMeUpdateDTO obj) {
        obj.setId(id);
        AboutMe aboutMe = this.aboutMeService.fromDTO(obj);
        this.aboutMeService.update(aboutMe);
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete (@PathVariable Long id) {
       this.aboutMeService.delete(id);
       return ResponseEntity.noContent().build();
    }
}
