package uk.gov.hmcts.reform.dev.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import uk.gov.hmcts.reform.dev.dtos.UpdateStatusRequest;
import uk.gov.hmcts.reform.dev.models.Task;
import uk.gov.hmcts.reform.dev.services.TaskService;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaskControllerTest {

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    private static final String TASK_TITLE_1 = "Task Title";
    private static final String TASK_DESCRIPTION_1 = "Task Description";
    private static final String TASK_STATUS_1 = "TODO";
    private static final int TASK_ID_1 = 1;

    private static final String TASK_TITLE_2 = "Task Title 2";
    private static final String TASK_DESCRIPTION_2 = "Task Description 2";
    private static final String TASK_STATUS_2 = "DONE";
    private static final int TASK_ID_2 = 2;

    private Task task1;
    private Task task2;

    @BeforeEach
    void setUp() {
        task1 = new Task();
        task1.setId(TASK_ID_1);
        task1.setTitle(TASK_TITLE_1);
        task1.setDescription(TASK_DESCRIPTION_1);
        task1.setStatus(TASK_STATUS_1);

        task2 = new Task();
        task2.setId(TASK_ID_2);
        task2.setTitle(TASK_TITLE_2);
        task2.setDescription(TASK_DESCRIPTION_2);
        task2.setStatus(TASK_STATUS_2);
    }
    @Test
    void shouldCreateTask() {
        when(taskService.createTask(task1)).thenReturn(task1);
        ResponseEntity<Task> response = taskController.createTask(task1);
        assert response.getStatusCode().is2xxSuccessful();
        assert response.getBody() != null;
        assert response.getBody().getTitle().equals(TASK_TITLE_1);
    }

    @Test
    void shouldFetchTaskById() {
        when(taskService.getTaskById(TASK_ID_1)).thenReturn(task1);
        ResponseEntity<Task> response = taskController.getTaskById(TASK_ID_1);
        assert response.getStatusCode().is2xxSuccessful();
        assert response.getBody() != null;
        assert response.getBody().getTitle().equals(TASK_TITLE_1);
    }

    @Test
    void shouldThrowExceptionWhenTaskNotFound() {
        when(taskService.getTaskById(999)).thenThrow(new RuntimeException("Task not found"));
        try {
            taskController.getTaskById(999);
            assert false; // Should not reach here
        } catch (RuntimeException e) {
            assert e.getMessage().equals("Task not found");
        }
    }

    @Test
    void shouldReturnAllTasks() {
        when(taskService.getAllTasks()).thenReturn(List.of(task1, task2));
        ResponseEntity<List<Task>> response = taskController.getAllTasks();
        assert response.getStatusCode().is2xxSuccessful();
        assert response.getBody() != null;
        assert response.getBody().size() == 2;
    }

    @Test
    void shouldUpdateTaskStatus() {
        UpdateStatusRequest request = new UpdateStatusRequest();
        request.setStatus(uk.gov.hmcts.reform.dev.models.TaskStatus.DONE);
        taskController.updateStatus(TASK_ID_1, request);
        verify(taskService).updateStatus(TASK_ID_1, uk.gov.hmcts.reform.dev.models.TaskStatus.DONE);
    }

    @Test
    void shouldDeleteTask() {
        taskController.deleteTask(TASK_ID_1);
        verify(taskService).deleteTask(TASK_ID_1);
    }
}
