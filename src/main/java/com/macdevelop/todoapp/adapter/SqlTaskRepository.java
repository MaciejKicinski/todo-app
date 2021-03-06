package com.macdevelop.todoapp.adapter;

import com.macdevelop.todoapp.model.Task;
import com.macdevelop.todoapp.repository.TaskRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
interface SqlTaskRepository extends TaskRepository, JpaRepository<Task, Integer> {
    //todo
    @Override
    @Query(nativeQuery = true, value = "select count(*)>0 from tasks where id=:id")
    boolean existsById(@Param("id") Integer id);

    @Override
    boolean existsByDoneIsFalseAndGroup_Id(Integer id);

    @Override
    Set<Task> findAllByGroup_Id(Integer id);


}
