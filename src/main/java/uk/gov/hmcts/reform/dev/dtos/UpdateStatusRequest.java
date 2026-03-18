package uk.gov.hmcts.reform.dev.dtos;

import uk.gov.hmcts.reform.dev.models.TaskStatus;

public class UpdateStatusRequest {
    private TaskStatus status;

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }
}
