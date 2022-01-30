package com.macdevelop.todoapp.model.projection;

import com.macdevelop.todoapp.model.Project;
import com.macdevelop.todoapp.model.ProjectStep;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.List;

public class ProjectWriteModel {
    @NotBlank(message = "Project's description must not be empty")
    private String description;
    @Valid
    private List<ProjectStep> steps;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ProjectStep> getSteps() {
        return steps;
    }

    public void setSteps(List<ProjectStep> steps) {
        this.steps = steps;
    }

    public Project toProject() {
        var result = new Project();
        result.setDescription(description);
        steps.forEach(step -> step.setProject(result));
        result.setProjectSteps(new HashSet<>(steps));
        return result;
    }
}
