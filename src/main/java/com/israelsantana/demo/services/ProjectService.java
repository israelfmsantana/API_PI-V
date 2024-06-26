package com.israelsantana.demo.services;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.israelsantana.demo.models.Project;
import com.israelsantana.demo.models.dto.ProjectCreateDTO;
import com.israelsantana.demo.models.dto.ProjectUpdateDTO;
import com.israelsantana.demo.repositories.ProjectRepository;
import com.israelsantana.demo.services.exceptions.DataBindingViolationException;
import com.israelsantana.demo.services.exceptions.ObjectNotFoundException;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;


    public Project findById(Long id) {
        Project project = this.projectRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(
                "Project not found! Id: " + id + ", Type: " + Project.class.getName()));

        return project;
    }


    public List<Project> findAll() {
        List<Project> projects = this.projectRepository.findAll();
        return projects;
    }



    @Transactional
    public Project create( Project obj) {
        Project project = new Project(null, obj.getName(), obj.getDescription());
        project = this.projectRepository.save(project);

        return project;
    }


    @Transactional
    public Project update(Project obj) {
        Project newObj = findById(obj.getId());
        newObj.setName(obj.getName());
        newObj.setDescription(obj.getDescription());

        return this.projectRepository.save(newObj);
    }


    public void delete(Long id) {
        findById(id);
        try {
            this.projectRepository.deleteById(id);
        } catch (Exception e) {
            throw new DataBindingViolationException("Cannot delete as there are related entities!");
        }
    }


    public Project fromDTO(@Valid ProjectCreateDTO obj) {
        Project project = new Project();
        project.setName(obj.getName());
        project.setDescription(obj.getDescription());
        return project;
    }

    public Project fromDTO(@Valid ProjectUpdateDTO obj) {
        Project project = new Project();
        project.setId(obj.getId());
        project.setName(obj.getName());
        project.setDescription(obj.getDescription());
        return project;
    }

}
