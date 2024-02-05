package com.sparta.schedule.repository;

import com.sparta.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findAllByOrderByModifiedAtDesc();
    List<Schedule> findAllByContentsContainsOrderByModifiedAtDesc(String keyword);
    List<Schedule> findSchedulesById(Long id);
}
