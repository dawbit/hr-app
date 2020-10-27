package com.hr.app.models.api_helpers;

public class AddNewHrCommandDto {
    private long hrUserId;

    public long getHrUserId() {
        return hrUserId;
    }

    public void setHrUserId(long hrUserId) {
        this.hrUserId = hrUserId;
    }

    public AddNewHrCommandDto(long hrUserId) {
        this.hrUserId = hrUserId;
    }
}
