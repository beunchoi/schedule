package com.sparta.schedule.service;

import com.sparta.schedule.dto.ScheduleRequestDto;
import com.sparta.schedule.dto.ScheduleResponseDto;
import com.sparta.schedule.entity.Schedule;
import com.sparta.schedule.jwt.UserDetailsImpl;
import com.sparta.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public ScheduleResponseDto createSchedule(ScheduleRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        // RequestDto -> Entity
        Schedule schedule = new Schedule(requestDto, userDetails);

        // DB 저장
        Schedule saveSchedule = scheduleRepository.save(schedule);

        // Entity -> ResponseDto
        ScheduleResponseDto scheduleResponseDto = new ScheduleResponseDto(saveSchedule);

        return scheduleResponseDto;
    }

    public List<ScheduleResponseDto> getSchedules() {
        // DB 조회
        return scheduleRepository.findAllByOrderByModifiedAtDesc().stream().map(ScheduleResponseDto::new).toList();
    }

    public List<ScheduleResponseDto> getSchedulesById(Long id) {
        return scheduleRepository.findSchedulesById(id).stream().map(ScheduleResponseDto::new).toList();
    }

    @Transactional
    public Long updateSchedule(Long id, ScheduleRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        // 해당 일정이 DB에 존재하는지 확인
        Schedule schedule = findSchedule(id);

        if(schedule.getUsername().equals(userDetails.getUsername())) {
            // schedule 내용 수정
            schedule.update(requestDto);
        }
        else {
            throw new IllegalArgumentException("수정할 수 없습니다");
        }

        return id;
    }

    @Transactional
    public Long completeSchedule(Long id) {
        // 해당 일정이 DB에 존재하는지 확인
        Schedule schedule = findSchedule(id);

        // schedule 완료
        schedule.setComplete(true);

        return id;
    }

    private Schedule findSchedule(Long id) {
        return scheduleRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 일정은 존재하지 않습니다.")
        );
    }
}
