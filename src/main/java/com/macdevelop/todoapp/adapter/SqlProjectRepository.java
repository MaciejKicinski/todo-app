package com.macdevelop.todoapp.adapter;

import com.macdevelop.todoapp.model.Project;
import com.macdevelop.todoapp.repository.ProjectRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SqlProjectRepository extends ProjectRepository, JpaRepository<Project, Integer> {
    @Override
    @Query("from Project p join fetch p.projectSteps,p.taskGroups")
    List<Project> findAll();
}
