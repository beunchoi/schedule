package com.sparta.schedule.dto;

import com.sparta.schedule.entity.Schedule;
import jakarta.persistence.Column;
import lombok.Getter;

import java.time.LocalDateTime;
@Getter
public class ScheduleResponseDto {
    private Long id;
    private String username;
    private String title;
    private String contents;
    private boolean complete;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;


    public ScheduleResponseDto(Schedule saveSchedule) {
        this.id = saveSchedule.getId();
        this.username = saveSchedule.getUsername();
        this.title = saveSchedule.getTitle();
        this.contents = saveSchedule.getContents();
        this.complete = saveSchedule.isComplete();
        this.createdAt = saveSchedule.getCreatedAt();
        this.modifiedAt = saveSchedule.getModifiedAt();
    }
}
