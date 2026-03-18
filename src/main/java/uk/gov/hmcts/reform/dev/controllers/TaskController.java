package uk.gov.hmcts.reform.dev.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import uk.gov.hmcts.reform.dev.dtos.UpdateStatusRequest;
import uk.gov.hmcts.reform.dev.models.Task;
import uk.gov.hmcts.reform.dev.services.TaskService;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping(value = "/create-task", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Task> createTask(@RequestBody Task newTask) {
        return ok(taskService.createTask(newTask));
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Task> getTaskById(@PathVariable int id) {
        return ok(taskService.getTaskById(id));
    }

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        return ok(taskService.getAllTasks());
    }

    @PostMapping("/{id}/update-status")
    public ResponseEntity<?> updateStatus(@PathVariable int id,
                                          @RequestBody UpdateStatusRequest request) {

        taskService.updateStatus(id, request.getStatus());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/delete")
    public ResponseEntity<?> deleteTask(@PathVariable int id) {
        taskService.deleteTask(id);
        return ResponseEntity.ok().build();
    }
}
