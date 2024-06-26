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

import com.israelsantana.demo.models.Project;
import com.israelsantana.demo.models.dto.ProjectCreateDTO;
import com.israelsantana.demo.models.dto.ProjectUpdateDTO;
import com.israelsantana.demo.services.ProjectService;

@RestController
@RequestMapping("/project")
@Validated
public class ProjectController {

    @Autowired
    private ProjectService projectService;


    @GetMapping("/{id}")
    public ResponseEntity<Project> findById(@PathVariable Long id) {
        Project project = this.projectService.findById(id);
        return ResponseEntity.ok(project);
    }


    @GetMapping()
    public ResponseEntity<List<Project>> findAll() {
        List<Project> projects = this.projectService.findAll();
        return ResponseEntity.ok().body(projects);
    }


    @PostMapping
    @Validated
    public ResponseEntity<Void> create(@Valid @RequestBody ProjectCreateDTO obj) {
        Project project = this.projectService.fromDTO(obj);
        Project newProject = this.projectService.create(project);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(newProject).toUri();
        return ResponseEntity.created(uri).build();
    }

    // Authentication required ADMIN
    @PutMapping("/{id}")
    @Validated
    public ResponseEntity<Void> update(@Valid @RequestBody ProjectUpdateDTO obj, @PathVariable Long id) {
        obj.setId(id);
        Project project = this.projectService.fromDTO(obj);
        this.projectService.update(project);
        return ResponseEntity.noContent().build();
    }

    // Authentication required ADMIN
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.projectService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
