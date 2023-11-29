package com.third.gachigong.entity;

import com.third.gachigong.dto.DdayDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "dday")
public class DdayEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="member_id")
    private MemberEntity member;

    @Column
    private LocalDate date;

    @Column
    private String content;

    public static DdayEntity toDdayEntity(DdayDto ddayDto){
        DdayEntity ddayEntity = new DdayEntity();
        ddayEntity.setId(ddayDto.getId());
        ddayEntity.setMember(ddayDto.getMember());
        ddayEntity.setDate(ddayDto.getDate());
        ddayEntity.setContent(ddayDto.getContent());
        return ddayEntity;
    }
}
