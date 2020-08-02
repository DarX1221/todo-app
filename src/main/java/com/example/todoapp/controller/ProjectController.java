package com.example.todoapp.controller;

import com.example.todoapp.model.ProjectStep;
import com.example.todoapp.model.projection.ProjectWriteModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/projects")
public class ProjectController {

    @GetMapping
    String showProjects(Model model) {
        ProjectWriteModel projectToEdit = new ProjectWriteModel();
        projectToEdit.setDescription("Test!");
        model.addAttribute("project", projectToEdit);
        return "projects";
    }

    @PostMapping
    String addProjectStep(@ModelAttribute("project") ProjectWriteModel model) {
        model.getSteps().add(new ProjectStep());
        return "projects";
    }
}
