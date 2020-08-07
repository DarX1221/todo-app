package com.example.todoapp.controller;

import com.example.todoapp.logic.ProjectService;
import com.example.todoapp.model.Project;
import com.example.todoapp.model.ProjectStep;
import com.example.todoapp.model.projection.ProjectWriteModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/projects")
public class ProjectController {
    private final ProjectService service;

    public ProjectController(final ProjectService service) {
        this.service = service;
    }

    @GetMapping
    String showProjects(Model model) {
        final ProjectWriteModel projectToEdit = new ProjectWriteModel();
        projectToEdit.setDescription("Test!");
        model.addAttribute("project", projectToEdit);
        return "projects";
    }

    @PostMapping
    String addProject(
            @ModelAttribute("project") final ProjectWriteModel current,
            Model model) {
        model.addAttribute("project", current);
        model.addAttribute("message", "Dodano pakiet");
        System.out.println("++++++++++++++> Wyowłano addProject()!!!");
        service.save(current);
        return "projects";
    }

    @PostMapping(params = "addStep")
    String addProjectStep(final @ModelAttribute("project") ProjectWriteModel current) {
        int size = current.getSteps().size();
        current.getSteps().add(new ProjectStep());
        System.out.println("---------------> Wyowłano addProjectStep()!!!");
        return "projects";
    }

    @ModelAttribute("projects")
    List<Project> getProjects() {
        return service.readAll();
    }
}
