package com.hr.app.models;

public class UserToDepartmentModel {

    long userId;
    long departmentId;

    protected UserToDepartmentModel(){};

    public UserToDepartmentModel(long userId, long departmentId) {
        this.userId = userId;
        this.departmentId = departmentId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(long departmentId) {
        this.departmentId = departmentId;
    }
}
