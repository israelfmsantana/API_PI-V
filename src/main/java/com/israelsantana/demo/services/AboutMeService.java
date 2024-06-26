package com.israelsantana.demo.services;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.israelsantana.demo.models.AboutMe;
import com.israelsantana.demo.models.dto.AboutMeCreateDTO;
import com.israelsantana.demo.models.dto.AboutMeUpdateDTO;
import com.israelsantana.demo.repositories.AboutMeRepository;
import com.israelsantana.demo.services.exceptions.AuthorizationException;
import com.israelsantana.demo.services.exceptions.DataBindingViolationException;
import com.israelsantana.demo.services.exceptions.ObjectNotFoundException;

@Service
public class AboutMeService {
    
    @Autowired
    private AboutMeRepository aboutMeRepository;

    public AboutMe findById(Long id) {
        Optional<AboutMe> aboutMe = this.aboutMeRepository.findById(id);
        return aboutMe.orElseThrow(() -> new ObjectNotFoundException(
                "AboutMe not found! Id: " + id + ", Type: " + AboutMe.class.getName()));
    }


    public AboutMe findByName(String name) {
        AboutMe aboutMe = this.aboutMeRepository.findByName(name);
        if(Objects.isNull(aboutMe))
            throw new AuthorizationException("AboutMe not found! Name: " + name + ", Type: " + AboutMe.class.getName());

        return aboutMe;
    }


    public List<AboutMe> findAll() {
        List<AboutMe> aboutMes = this.aboutMeRepository.findAll();
        return aboutMes;
    }


    @Transactional
    public AboutMe create(AboutMe obj) {
        obj.setId(null);
        obj.setName(obj.getName());
        obj.setAge(obj.getAge());
        obj = this.aboutMeRepository.save(obj);

        return obj;
    }


    @Transactional
    public AboutMe update(AboutMe obj) {
        AboutMe newObj = findById(obj.getId());
        newObj.setName(obj.getName());
        newObj.setAge(obj.getAge());
        newObj = this.aboutMeRepository.save(newObj);

        return newObj;
    }


    public void delete(Long id) {
        findById(id);
        try {
            this.aboutMeRepository.deleteById(id);
        } catch (Exception e) {
            throw new DataBindingViolationException("Cannot delete as there are related entities!");
        }
    }

    public AboutMe fromDTO(@Valid AboutMeCreateDTO obj) {
        AboutMe aboutMe = new AboutMe();
        aboutMe.setName(obj.getName());
        aboutMe.setAge(obj.getAge());
        return aboutMe;
    }

    public AboutMe fromDTO(@Valid AboutMeUpdateDTO obj) {
        AboutMe aboutMe = new AboutMe();
        aboutMe.setId(obj.getId());
        aboutMe.setName(obj.getName());
        aboutMe.setAge(obj.getAge());
        return aboutMe;
    }
}
