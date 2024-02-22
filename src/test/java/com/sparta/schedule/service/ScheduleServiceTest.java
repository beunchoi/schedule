package com.sparta.schedule.service;

import com.sparta.schedule.dto.ScheduleRequestDto;
import com.sparta.schedule.dto.ScheduleResponseDto;
import com.sparta.schedule.entity.Schedule;
import com.sparta.schedule.entity.User;
import com.sparta.schedule.repository.ScheduleRepository;
import com.sparta.schedule.security.UserDetailsImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.sparta.schedule.entity.UserRoleEnum.USER;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ScheduleServiceTest {
    @Mock
    ScheduleRepository scheduleRepository;

    UserDetailsImpl userDetails = new UserDetailsImpl(new User("tester", "1234", "asd@email.com", USER));

    @Test
    @DisplayName("일정 등록")
    void createSchedule() {
        // Given
        String title = "test";
        String contents = "123123";
        ScheduleRequestDto requestDto = new ScheduleRequestDto(title, contents);

        Schedule schedule = new Schedule(requestDto, userDetails);
        ScheduleService scheduleService = new ScheduleService(scheduleRepository);

        given(scheduleRepository.save(any(Schedule.class))).willReturn(schedule);

        // When
        ScheduleResponseDto scheduleResponseDto = scheduleService.createSchedule(requestDto, userDetails);

        // Then
        assertEquals("tester", scheduleResponseDto.getUsername());
        assertEquals("test", scheduleResponseDto.getTitle());
        assertEquals("123123", scheduleResponseDto.getContents());
    }


    @Test
    @DisplayName("일정 수정")
    void updateSchedule() {
        // Given
        Long id = 3L;
        String title = "test수정";
        String contents = "123123수정";
        ScheduleRequestDto requestDto = new ScheduleRequestDto(title, contents);

        Schedule schedule = new Schedule(requestDto, userDetails);
        ScheduleService scheduleService = new ScheduleService(scheduleRepository);

        given(scheduleRepository.findById(id)).willReturn(Optional.of(schedule));

        // When
        scheduleService.updateSchedule(id, requestDto, userDetails);

        // Then
        assertEquals("test수정",schedule.getTitle());
        assertEquals("123123수정",schedule.getContents());
    }

    @Test
    @DisplayName("일정 완료")
    void completeSchedule() {
        // Given
        Long id = 3L;
        String title = "test수정";
        String contents = "123123수정";
        ScheduleRequestDto requestDto = new ScheduleRequestDto(title, contents);

        Schedule schedule = new Schedule(requestDto, userDetails);
        ScheduleService scheduleService = new ScheduleService(scheduleRepository);

        given(scheduleRepository.findById(id)).willReturn(Optional.of(schedule));

        // When
        scheduleService.completeSchedule(id);

        // Then
        assertEquals(true, schedule.isComplete());
    }
}
