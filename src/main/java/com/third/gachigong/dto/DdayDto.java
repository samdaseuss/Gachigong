package com.third.gachigong.dto;

import com.third.gachigong.entity.DdayEntity;
import com.third.gachigong.entity.MemberEntity;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DdayDto {
    private Long id;
    private MemberEntity member;
    private LocalDate date = LocalDate.now();
    private String content;

    public static DdayDto toDdayDto(DdayEntity ddayEntity){
        DdayDto ddayDto = new DdayDto();
        ddayDto.setId(ddayEntity.getId());
        ddayDto.setMember(ddayEntity.getMember());
        ddayDto.setDate(ddayEntity.getDate());
        ddayDto.setContent(ddayEntity.getContent());
        return ddayDto;
    }

}
