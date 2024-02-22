package com.sparta.schedule.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor

public class ScheduleRequestDto {
    private String title;
    private String contents;

    public ScheduleRequestDto(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }
}
