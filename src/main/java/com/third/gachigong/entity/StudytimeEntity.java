//package com.third.gachigong.entity;
//
////import com.third.gachigong.dto.StudytimeDto;
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import java.time.Duration;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//
//@Entity
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@Table(name = "studytime")
//public class StudytimeEntity {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne
//    @JoinColumn(name = "member_id", nullable = false)
//    private MemberEntity member;
//
//    @Column(nullable = false)
//    private String subject;
//
//    @Column(nullable = false)
//    private String color;
//
//    @Column(name = "study_time")
//    private Duration studyTime;
//
//    @Column(name = "date")
//    private LocalDate date;
//
//    public static StudytimeEntity toStudytimeEntity(StudytimeDto studytimeDto){
//        StudytimeEntity studytimeEntity = new StudytimeEntity();
//        studytimeEntity.setId(studytimeDto.getId());
//        studytimeEntity.setMember(studytimeDto.getMember());
//        studytimeEntity.setSubject(studytimeDto.getSubject());
//        studytimeEntity.setColor(studytimeDto.getColor());
//        studytimeEntity.setStudyTime(studytimeDto.getStudyTime());
//        studytimeEntity.setDate(studytimeDto.getDate());
//        return studytimeEntity;
//    }
//}
