package com.macdevelop.todoapp.controller;

import com.macdevelop.todoapp.model.Task;
import com.macdevelop.todoapp.model.projection.GroupReadModel;
import com.macdevelop.todoapp.model.projection.GroupTaskWriteModel;
import com.macdevelop.todoapp.model.projection.GroupWriteModel;
import com.macdevelop.todoapp.repository.TaskGroupRepository;
import com.macdevelop.todoapp.repository.TaskRepository;
import com.macdevelop.todoapp.service.TaskGroupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/groups")
public class TaskGroupController {
    private final TaskGroupService taskGroupService;
    private final TaskGroupRepository repository;
    private final TaskRepository taskRepository;

    @GetMapping(produces = MediaType.TEXT_HTML_VALUE)
    String showGroups(Model model) {
        model.addAttribute("group", new GroupWriteModel());
        return "groups";
    }

    @PostMapping(produces = MediaType.TEXT_HTML_VALUE, consumes =MediaType.APPLICATION_FORM_URLENCODED_VALUE )
    String addGroup(@ModelAttribute("group") @Valid GroupWriteModel current,
                    BindingResult bindingResult,
                    Model model
    ) {
        if (bindingResult.hasErrors()) {
            return "groups";
        }
        taskGroupService.createGroup(current);
        model.addAttribute("group", new GroupWriteModel());
        model.addAttribute("groups", getGroups());
        model.addAttribute("message", "Dodano grupe!");
        return "groups";
    }

    @PostMapping(params = "addTask",produces = MediaType.TEXT_HTML_VALUE)
    String addGroupTask(@ModelAttribute("group") GroupWriteModel current) {
        current.getTasks().add(new GroupTaskWriteModel());
        return "groups";
    }

    @ResponseBody
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<GroupReadModel> createGroup(@RequestBody @Validated GroupWriteModel group) {
        GroupReadModel result = taskGroupService.createGroup(group);
        return ResponseEntity.created(URI.create("/" + result.getId())).body(result);
    }

    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<GroupReadModel>> readAllGroups() {
        return ResponseEntity.ok(taskGroupService.readAll());
    }

    @ResponseBody
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Set<Task>> readAllTasksFromGroup(@PathVariable int id) {
        return ResponseEntity.ok().body(taskRepository.findAllByGroup_Id(id));
    }

    @ResponseBody
    @Transactional
    @PatchMapping(value = "/{id}")
    ResponseEntity<?> toggleGroup(@PathVariable int id) {
        taskGroupService.toggleGroup(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    ResponseEntity<?> handleIllegalArgument(IllegalArgumentException e) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(IllegalStateException.class)
    ResponseEntity<?> handleIllegalState(IllegalStateException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ModelAttribute("groups")
    private List<GroupReadModel> getGroups() {
        return taskGroupService.readAll();
    }
}
