package uk.gov.hmcts.reform.dev.services;

import org.springframework.stereotype.Service;
import uk.gov.hmcts.reform.dev.models.Task;
import uk.gov.hmcts.reform.dev.models.TaskStatus;
import uk.gov.hmcts.reform.dev.repositories.TaskRepository;

import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task createTask(Task newTask) {
        newTask.setStatus("TODO");
        return taskRepository.save(newTask);
    }

    public Task getTaskById(int taskId) {
        return taskRepository.findById(taskId).orElseThrow(() -> new RuntimeException("Task not found"));
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public void updateStatus(int taskId, TaskStatus newStatus) {
        Task existingTask = taskRepository.findById(taskId)
            .orElseThrow(() -> new RuntimeException("Task not found"));
        try {
            existingTask.setStatus(newStatus.name());
            taskRepository.save(existingTask);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid status: " + newStatus.name());
        }
    }

    public void deleteTask(int taskId) {
        taskRepository.deleteById(taskId);
    }


}
