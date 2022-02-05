package com.macdevelop.todoapp.reports;

import com.macdevelop.todoapp.model.event.TaskDone;
import com.macdevelop.todoapp.model.event.TaskEvent;
import com.macdevelop.todoapp.model.event.TaskUnDone;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChangedTaskEventListener {
    private final PersistedTaskEventRepository repository;

    @Async
    @EventListener
    public void on(TaskDone event) {
        onChanged(event);
    }

    @Async
    @EventListener
    public void on(TaskUnDone event) {
        onChanged(event);
    }

    private void onChanged(final TaskEvent event) {
        log.info("Got " + event);
        repository.save(new PersistedTaskEvent(event));
    }
}
