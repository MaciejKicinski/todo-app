package com.macdevelop.todoapp.controller;

import com.macdevelop.todoapp.model.Task;
import com.macdevelop.todoapp.model.TaskRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RepositoryRestController
class TaskController {
    private final TaskRepository repository;

    @GetMapping(value = "/tasks", params = {"!sort", "!page", "!size"})
    ResponseEntity<List<Task>> readAllTasks() {
        log.warn("Exposing all the tasks!");
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping(value = "/tasks")
    ResponseEntity<List<Task>> readAllTasks(Pageable page) {
        log.info("Custom pageable");
        return ResponseEntity.ok(repository.findAll(page).getContent());
    }
}
