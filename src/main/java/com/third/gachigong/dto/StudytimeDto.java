package com.third.gachigong.dto;

import com.third.gachigong.entity.MemberEntity;
import com.third.gachigong.entity.StudytimeEntity;
import lombok.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StudytimeDto {
    private Long id;
    private MemberEntity member;
    private String subject;
    private String color;
    private Duration studyTime;
    private LocalDate date = LocalDate.now();

    public StudytimeEntity toEntity(){
        StudytimeEntity entity = new StudytimeEntity(id, member, subject, color, studyTime, date);

        // memberId가 null이 아닌 경우에만 MemberEntity를 설정
        if (this.member.getId() != null) {
            MemberEntity memberEntity = new MemberEntity();
            memberEntity.setId(this.member.getId());
            entity.setMember(memberEntity);
        }

        return entity;
    }

    public static StudytimeDto toStudytimeDto(StudytimeEntity studytimeEntity){
        StudytimeDto studytimeDto = new StudytimeDto();
        studytimeDto.setId(studytimeEntity.getId());
        studytimeDto.setMember(studytimeEntity.getMember());
        studytimeDto.setSubject(studytimeEntity.getSubject());
        studytimeDto.setColor(studytimeEntity.getColor());
        studytimeDto.setStudyTime(studytimeEntity.getStudyTime());
        studytimeDto.setDate(studytimeEntity.getDate());
        return studytimeDto;
    }
}
