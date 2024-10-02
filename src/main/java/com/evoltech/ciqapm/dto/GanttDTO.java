package com.evoltech.ciqapm.dto;

import com.evoltech.ciqapm.model.Actividad;

import java.util.Objects;

public class GanttDTO {
    private String taskId;
    private String taskName;
    private String resource;
    private String startDate;
    private String endDate;
    private Integer duration;
    private Integer pctComplete;

    public GanttDTO(String taskId, String taskName, String resource, String startDate, String endDate, Integer duration, Integer pctComplete) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.resource = resource;
        this.startDate = startDate;
        this.endDate = endDate;
        this.duration = duration;
        this.pctComplete = pctComplete;
    }

    public GanttDTO() {
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getPctComplete() {
        return pctComplete;
    }

    public void setPctComplete(Integer pctComplete) {
        this.pctComplete = pctComplete;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GanttDTO ganttDTO)) return false;
        return Objects.equals(getTaskName(), ganttDTO.getTaskName());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getTaskName());
    }
}