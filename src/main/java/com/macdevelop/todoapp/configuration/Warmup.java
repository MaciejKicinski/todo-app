package com.macdevelop.todoapp.configuration;

import com.macdevelop.todoapp.model.Task;
import com.macdevelop.todoapp.model.TaskGroup;
import com.macdevelop.todoapp.repository.TaskGroupRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Component
class Warmup implements ApplicationListener<ContextRefreshedEvent> {
    private final TaskGroupRepository groupRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info("Application warmup after context refreshed.");
        final String description = "ApplicationContextEvent";
        if (!groupRepository.existsByDescription(description)) {
            log.info("No required group found! Adding it!");
            var group = new TaskGroup();
            group.setDescription(description);
            group.setTasks(Set.of(
                    new Task("ContextClosedEvent",null,group),
                    new Task("ContextRefreshedEvent",null,group),
                    new Task("ContextStoppedEvent",null,group),
                    new Task("ContextStartedEvent",null,group)
            ));
            groupRepository.save(group);
        }
    }
}
