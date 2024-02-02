package com.sparta.schedule.entity;

import com.sparta.schedule.dto.ScheduleRequestDto;
import com.sparta.schedule.jwt.UserDetailsImpl;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

@Entity // JPA가 관리할 수 있는 Entity 클래스 지정
@Getter
@Setter
@Table(name = "schedule") // 매핑할 테이블의 이름을 지정
@NoArgsConstructor
public class Schedule extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "username", nullable = false)
    private String username;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "contents", nullable = false, length = 500)
    private String contents;

    public Schedule(ScheduleRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        this.username = userDetails.getUsername();
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
    }

    public void update(ScheduleRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
    }
}