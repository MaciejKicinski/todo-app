package com.macdevelop.todoapp.service;

import com.macdevelop.todoapp.model.Task;
import com.macdevelop.todoapp.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@RequiredArgsConstructor
@Service
public class TaskService {
    private final TaskRepository repository;

    @Async
    public CompletableFuture<List<Task>> findAllAsync() {
        log.info("Supply async!");
        return CompletableFuture.supplyAsync(repository::findAll);
    }
}
