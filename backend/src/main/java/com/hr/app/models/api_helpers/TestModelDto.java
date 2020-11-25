package com.hr.app.models.api_helpers;

public class TestModelDto {

    private String name;

    private boolean isPossibleToBack;

    private boolean isOpenForEveryone;

    private boolean isActive;

    private long timeForTestInMilis;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPossibleToBack() {
        return isPossibleToBack;
    }

    public void setPossibleToBack(boolean possibleToBack) {
        isPossibleToBack = possibleToBack;
    }

    public boolean isOpenForEveryone() {
        return isOpenForEveryone;
    }

    public void setOpenForEveryone(boolean openForEveryone) {
        isOpenForEveryone = openForEveryone;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public long getTimeForTestInMilis() {
        return timeForTestInMilis;
    }

    public void setTimeForTestInMilis(long timeForTestInMilis) {
        this.timeForTestInMilis = timeForTestInMilis;
    }

    public TestModelDto(String name, boolean isPossibleToBack, boolean isOpenForEveryone, boolean isActive, long timeForTestInMilis) {
        this.name = name;
        this.isPossibleToBack = isPossibleToBack;
        this.isOpenForEveryone = isOpenForEveryone;
        this.isActive = isActive;
        this.timeForTestInMilis = timeForTestInMilis;
    }
}
