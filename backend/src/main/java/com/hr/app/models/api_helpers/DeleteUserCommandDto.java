package com.hr.app.models.api_helpers;

public class DeleteUserCommandDto {
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public DeleteUserCommandDto(long id) {
        this.id = id;
    }
}
