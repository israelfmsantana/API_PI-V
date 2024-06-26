package com.israelsantana.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.israelsantana.demo.models.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

}
