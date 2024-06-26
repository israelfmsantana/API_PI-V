package com.israelsantana.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.israelsantana.demo.models.AboutMe;

@Repository
public interface AboutMeRepository extends JpaRepository<AboutMe, Long> {
    
    @Transactional(readOnly = true)
    AboutMe findByName(String name);
}
