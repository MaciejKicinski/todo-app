//package com.macdevelop.todoapp.controller.rest;
//
//import com.macdevelop.todoapp.model.Task;
//import com.macdevelop.todoapp.model.projection.GroupReadModel;
//import com.macdevelop.todoapp.model.projection.GroupWriteModel;
//import com.macdevelop.todoapp.repository.TaskGroupRepository;
//import com.macdevelop.todoapp.repository.TaskRepository;
//import com.macdevelop.todoapp.service.TaskGroupService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.ResponseEntity;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.*;
//
//import java.net.URI;
//import java.util.List;
//import java.util.Set;
//
//@Slf4j
//@RequiredArgsConstructor
//@RestController
//@RequestMapping("/groups")
//public class TaskGroupRestController {
//    private final TaskGroupService taskGroupService;
//    private final TaskGroupRepository repository;
//    private final TaskRepository taskRepository;
//
//    @PostMapping
//    ResponseEntity<GroupReadModel> createGroup(@RequestBody @Validated GroupWriteModel group) {
//        GroupReadModel result = taskGroupService.createGroup(group);
//        return ResponseEntity.created(URI.create("/" + result.getId())).body(result);
//    }
//
//    @GetMapping
//    ResponseEntity<List<GroupReadModel>> readAll() {
//        return ResponseEntity.ok(taskGroupService.readAll());
//    }
//
//    @Transactional
//    @PatchMapping("/{id}")
//    ResponseEntity<?> toggleGroup(@PathVariable int id) {
//        taskGroupService.toggleGroup(id);
//        return ResponseEntity.noContent().build();
//    }
//
//    @GetMapping("/tasks/{id}")
//    ResponseEntity<Set<Task>> readTasksFromGroup(@PathVariable int id) {
//        return ResponseEntity.ok().body(taskRepository.findAllByGroup_Id(id));
//    }
//
//    @ExceptionHandler(IllegalArgumentException.class)
//    ResponseEntity<?> handleIllegalArgument(IllegalArgumentException e) {
//        return ResponseEntity.notFound().build();
//    }
//
//    @ExceptionHandler(IllegalStateException.class)
//    ResponseEntity<?> handleIllegalState(IllegalStateException e) {
//        return ResponseEntity.badRequest().body(e.getMessage());
//    }
//
//}
