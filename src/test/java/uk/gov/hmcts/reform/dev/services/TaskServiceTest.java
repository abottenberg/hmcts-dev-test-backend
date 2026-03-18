package uk.gov.hmcts.reform.dev.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.hmcts.reform.dev.models.Task;
import uk.gov.hmcts.reform.dev.repositories.TaskRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {
    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

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
        Task newTask = new Task();
        newTask.setTitle(TASK_TITLE_1);
        newTask.setDescription(TASK_DESCRIPTION_1);

        when(taskRepository.save(newTask)).thenReturn(task1);
        Task createdTask = taskService.createTask(newTask);

        assert (createdTask != null);
        assert (createdTask.getTitle().equals(TASK_TITLE_1));
        assert (createdTask.getDescription().equals(TASK_DESCRIPTION_1));
        assert (createdTask.getStatus().equals("TODO"));
    }

    @Test
    void shouldFindTaskById() {
        when(taskRepository.findById(1)).thenReturn(Optional.of(task1));
        Task task = taskService.getTaskById(1);

        assert (task != null);
        assert (task.getTitle().equals(TASK_TITLE_1));
        assert (task.getDescription().equals(TASK_DESCRIPTION_1));
        assert (task.getStatus().equals(TASK_STATUS_1));
    }

    @Test
    void shouldThrowExceptionWhenTaskNotFound() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> taskService.getTaskById(999));
        assert (exception.getMessage().equals("Task not found"));

    }

    @Test
    void shouldUpdateTaskStatus() {
        when(taskRepository.findById(1)).thenReturn(Optional.of(task1));
        taskService.updateStatus(1, uk.gov.hmcts.reform.dev.models.TaskStatus.DONE);

        assert (task1.getStatus().equals("DONE"));
    }

    @Test
    void shouldGetAllTasks() {
        when(taskRepository.findAll()).thenReturn(java.util.List.of(task1, task2));
        java.util.List<Task> tasks = taskService.getAllTasks();

        assert (tasks.size() == 2);
        assert (tasks.get(0).getTitle().equals(TASK_TITLE_1));
        assert (tasks.get(1).getTitle().equals(TASK_TITLE_2));
    }

    @Test
    void shouldDeleteTask() {
        taskService.deleteTask(1);
        verify(taskRepository).deleteById(1);

        when(taskRepository.findById(1)).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(RuntimeException.class, () -> taskService.getTaskById(1));
        assert (exception.getMessage().equals("Task not found"));
    }

}
