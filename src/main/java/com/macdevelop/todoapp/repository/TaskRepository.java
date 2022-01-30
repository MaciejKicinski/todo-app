package com.macdevelop.todoapp.repository;

import com.macdevelop.todoapp.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface TaskRepository {
    List<Task> findAll();

    Page<Task> findAll(Pageable page);

    Optional<Task> findById(Integer id);

    Set<Task> findAllByGroup_Id(Integer id);

    boolean existsById(Integer id);

    boolean existsByDoneIsFalseAndGroup_Id(Integer id);

    List<Task> findByDone(@Param("state") boolean done);

    Task save(Task entity);


}
