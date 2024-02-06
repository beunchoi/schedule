package com.sparta.schedule.controller;

import com.sparta.schedule.dto.ScheduleRequestDto;
import com.sparta.schedule.dto.ScheduleResponseDto;
import com.sparta.schedule.jwt.UserDetailsImpl;
import com.sparta.schedule.service.ScheduleService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping("/schedules")
    public ScheduleResponseDto createSchedule(@RequestBody ScheduleRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return scheduleService.createSchedule(requestDto, userDetails);
    }

    @GetMapping("/schedules")
    public List<ScheduleResponseDto> getSchedules() {
        return scheduleService.getSchedules();
    }

    @GetMapping("/schedules/{id}")
    public List<ScheduleResponseDto> getSchedulesById(@PathVariable Long id) {
        return scheduleService.getSchedulesById(id);
    }

    @PutMapping("/schedules/{id}")
    public Long updateSchedule(@PathVariable Long id, @RequestBody ScheduleRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return scheduleService.updateSchedule(id, requestDto, userDetails);
    }

    @PatchMapping("/schedules/{id}")
    public Long completeSchedule(@PathVariable Long id) {
        return scheduleService.completeSchedule(id);
    }
}
