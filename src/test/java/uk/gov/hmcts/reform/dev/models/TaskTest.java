package uk.gov.hmcts.reform.dev.models;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class TaskTest {
    private String status = "TODO";

    @Test
    void TestTaskCreation() {

        Task task = new Task();
        LocalDateTime dueDate = LocalDateTime.now();
        task.setId(1);
        task.setTitle("Test Task");
        task.setDescription("This is a test task.");
        task.setDueDate(dueDate);
        task.setStatus(status);

        assert task.getId() == 1;
        assert task.getTitle().equals("Test Task");
        assert task.getDescription().equals("This is a test task.");
        assert task.getStatus().equals(status);
        assert task.getDueDate().equals(dueDate);
    }
}
