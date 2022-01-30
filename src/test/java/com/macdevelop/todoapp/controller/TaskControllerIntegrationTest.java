package com.macdevelop.todoapp.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import com.macdevelop.todoapp.model.Task;
import com.macdevelop.todoapp.repository.TaskRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("integration")
public class TaskControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TaskRepository repository;

    @Test
    void httpGet_returnGivenTask() throws Exception {
        // given
        int id = repository.save(new Task("foo", LocalDateTime.now())).getId();
        // when + then
        mockMvc.perform(get("/tasks/" + id))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void httpGet_returnAllTask() throws Exception {
        // when + then
        mockMvc.perform(get("/tasks"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void httpPost_createTask() throws Exception {
        // given
        var json = generateJsonOfTaskObjectWithDescription("test");
        // when + then
        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void httpPut_updateNotExistingTask() throws Exception {
        // given
        var json = generateJsonOfTaskObjectWithDescription("test");
        int id = anyInt();
        // when + then
        mockMvc.perform(put("/tasks/"+id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void httpPut_updateExistingTask() throws Exception {
        // given
        int id = repository.save(new Task("foo", LocalDateTime.now())).getId();
        var json = generateJsonOfTaskObjectWithDescription("test");
        log.info(json);
        // when + then
        mockMvc.perform(put("/tasks/"+id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void httpPut_updateExistingTaskWithEmptyDescription() throws Exception {
        // given
        int id = repository.save(new Task("foo", LocalDateTime.now())).getId();
        var json = generateJsonOfTaskObjectWithDescription("");
        log.info(json);
        // when + then
        mockMvc.perform(put("/tasks/"+id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void httpPatch_toggleNotExistingTask() throws Exception {
        //given
        int id = anyInt();
        // when + then
        mockMvc.perform(patch("/tasks/" + id))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void httpPatch_toggleExistingTask() throws Exception {
        //given
        int id = repository.save(new Task("foo", LocalDateTime.now())).getId();
        // when + then
        mockMvc.perform(patch("/tasks/" + id))
                .andExpect(status().is2xxSuccessful());
    }

    private String generateJsonOfTaskObjectWithDescription(String desc) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(ImmutableMap.builder()
                        .put("description", desc)
                        .put("deadLine","2022-01-10T23:59:59.999")
                        .build());
    }
}
