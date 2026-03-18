package uk.gov.hmcts.reform.dev.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import uk.gov.hmcts.reform.dev.models.Task;

import java.time.LocalDateTime;
import java.util.Optional;

@DataJpaTest
public class TaskRepositoryTest {
    @Autowired
    private TaskRepository taskRepository;

    @Test
    void should_find_task_by_id() {
        Task task = new Task();
        task.setTitle("Test Task");
        task.setDescription("Test Description");
        task.setCreatedDate(LocalDateTime.now());
        task.setDueDate(LocalDateTime.now().plusDays(1));
        Task savedTask = taskRepository.save(task);

        Optional<Task> found = taskRepository.findById(savedTask.getId());
        assert(found.isPresent());
        assert(found.get().getTitle().equals("Test Task"));
        assert(found.get().getDescription().equals("Test Description"));
    }

    @Test
    void should_find_all_tasks() {
         Task task1 = new Task();
         task1.setTitle("Task 1");
         task1.setDescription("Description 1");
         task1.setCreatedDate(LocalDateTime.now());
         task1.setDueDate(LocalDateTime.now().plusDays(1));
         taskRepository.save(task1);

         Task task2 = new Task();
         task2.setTitle("Task 2");
         task2.setDescription("Description 2");
         task2.setCreatedDate(LocalDateTime.now());
         task2.setDueDate(LocalDateTime.now().plusDays(2));
         taskRepository.save(task2);

         assert (taskRepository.findAll().size() == 2);
     }

     @Test
    void should_delete_task_by_id() {
         Task task = new Task();
         task.setTitle("Task to Delete");
         task.setDescription("Description");
         task.setCreatedDate(LocalDateTime.now());
         task.setDueDate(LocalDateTime.now().plusDays(1));
         Task savedTask = taskRepository.save(task);

         taskRepository.deleteById(savedTask.getId());
         Optional<Task> deletedTask = taskRepository.findById(savedTask.getId());
         assert(deletedTask.isEmpty());
     }
}
