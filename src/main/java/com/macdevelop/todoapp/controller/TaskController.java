package com.macdevelop.todoapp.controller;

import com.macdevelop.todoapp.model.Task;
import com.macdevelop.todoapp.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping(path = "/v2")
class TaskController {
    private final TaskRepository repository;

    @RequestMapping(value = "/tasks", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity<List<Task>> readAllTasks2(@PageableDefault(value = 2) Pageable page) {
        log.info("---------Get all Tasks with params endpoint reached.---------");
        return ResponseEntity.ok(repository.findAll(page).getContent());
    }

    @RequestMapping(value = "/tasks", params = {"!sort", "!page", "!size"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity<List<Task>> readAllTasks2() {
        log.debug("Get all Tasks without params endpoint reached.");
        return ResponseEntity.ok(repository.findAll());
    }

    @RequestMapping(value = "/tasks/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity<Task> getTaskById(@PathVariable int id) {
        log.debug("Get all Tasks with params endpoint.");
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @RequestMapping(value = "/tasks", method = RequestMethod.POST)
    @ResponseBody
    ResponseEntity<?> createTask(@RequestBody @Valid Task toCreate) {
        Task result = repository.save(toCreate);
        return ResponseEntity.created(URI.create("/" + result.getId())).body(result);
    }

    @RequestMapping(value = "/tasks/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity<?> updateTask(@PathVariable int id, @RequestBody @Valid Task toUpdate) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.findById(id)
                .ifPresent(task -> {
                    task.updateFrom(toUpdate);
                    repository.save(task);
                });
        return ResponseEntity.noContent().build();
    }
}
