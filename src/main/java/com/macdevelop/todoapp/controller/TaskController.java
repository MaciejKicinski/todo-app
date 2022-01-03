package com.macdevelop.todoapp.controller;

import com.macdevelop.todoapp.model.TaskRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@AllArgsConstructor
@RepositoryRestController
class TaskController {
    private final TaskRepository repository;

    @GetMapping("/tasks")
    ResponseEntity<?> readAllTasks() {
        log.warn("Exposing all the tasks!");
        return ResponseEntity.ok(repository.findAll());
    }
}
