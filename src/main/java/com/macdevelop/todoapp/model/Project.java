package com.macdevelop.todoapp.model;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@NoArgsConstructor
@Entity
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "Project description must not be empty")
    private String description;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "project")
    Set<TaskGroup> taskGroups;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "project")
    Set<ProjectStep> projectSteps;

    public int getId() {
        return id;
    }

    void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    void setDescription(String description) {
        this.description = description;
    }

    public Set<TaskGroup> getTaskGroups() {
        return taskGroups;
    }

    void setTaskGroups(Set<TaskGroup> taskGroups) {
        this.taskGroups = taskGroups;
    }

    public Set<ProjectStep> getProjectSteps() {
        return projectSteps;
    }

    void setProjectSteps(Set<ProjectStep> projectSteps) {
        this.projectSteps = projectSteps;
    }
}
