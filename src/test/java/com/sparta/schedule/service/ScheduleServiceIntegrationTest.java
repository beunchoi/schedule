package com.sparta.schedule.service;

import com.sparta.schedule.dto.ScheduleRequestDto;
import com.sparta.schedule.dto.ScheduleResponseDto;
import com.sparta.schedule.entity.User;
import com.sparta.schedule.security.UserDetailsImpl;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.sparta.schedule.entity.UserRoleEnum.USER;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ScheduleServiceIntegrationTest {

    @Autowired
    ScheduleService scheduleService;

    ScheduleResponseDto createdSchedule = null;
    Long updatedId = 0L;
    UserDetailsImpl userDetails = new UserDetailsImpl(new User("tester", "1234", "asd@email.com", USER));

    @Test
    @Order(1)
    @DisplayName("일정 등록하기")
    void test1() {
        // given
        String title = "오늘 할 일";
        String contents = "운동";
        ScheduleRequestDto requestDto = new ScheduleRequestDto(title, contents);

        // when
        ScheduleResponseDto responseDto = scheduleService.createSchedule(requestDto, userDetails);

        // then
        assertNotNull(responseDto.getId());
        assertEquals(title, responseDto.getTitle());
        assertEquals(contents, responseDto.getContents());
        this.createdSchedule = responseDto;
    }

    @Test
    @Order(2)
    @DisplayName("일정 내용 변경하기")
    void test2() {
        // given
        Long id = this.createdSchedule.getId();
        String title = "오늘 할 일";
        String contents = "운동 수정1";
        ScheduleRequestDto requestDto = new ScheduleRequestDto(title, contents);

        // when
        Long update = scheduleService.updateSchedule(id, requestDto, userDetails);

        // then
        assertNotNull(update);
        assertEquals(this.createdSchedule.getId(), update);
        this.updatedId = update;
    }

    @Test
    @Order(3)
    @DisplayName("일정 내용 변경되었는지 조회 및 테스트")
    void test3() {
        // given
        Long id = this.updatedId;

        // when
        List<ScheduleResponseDto> schedule = scheduleService.getSchedulesById(id);

        // then
        ScheduleResponseDto updatedSchedule = schedule.stream()
                .filter(scheduleResponseDto -> scheduleResponseDto.getId().equals(updatedId))
                .findFirst()
                .orElse(null);

        assertNotNull(schedule);
        assertNotEquals(this.createdSchedule.getContents(), updatedSchedule.getContents());
    }
}

