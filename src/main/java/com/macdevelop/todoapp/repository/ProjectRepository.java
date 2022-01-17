package com.macdevelop.todoapp.repository;

import com.macdevelop.todoapp.model.Project;
import com.macdevelop.todoapp.model.TaskGroup;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository {
    List<Project> findAll();

    Optional<Project> findById(Integer id);

    TaskGroup save(Project entity);
}
