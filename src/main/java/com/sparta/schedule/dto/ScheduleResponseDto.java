package com.sparta.schedule.dto;

import com.sparta.schedule.entity.Schedule;
import jakarta.persistence.Column;

import java.time.LocalDateTime;

public class ScheduleResponseDto {
    private Long id;
    private String username;
    private String contents;
    private String title;
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public ScheduleResponseDto(Schedule saveSchedule) {
        this.id = saveSchedule.getId();
        this.username = saveSchedule.getUsername();
        this.contents = saveSchedule.getContents();
        this.title = saveSchedule.getTitle();
        this.password = saveSchedule.getPassword();
        this.createdAt = saveSchedule.getCreatedAt();
        this.modifiedAt = saveSchedule.getModifiedAt();
    }
}
