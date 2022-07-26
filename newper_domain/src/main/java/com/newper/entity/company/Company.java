package com.newper.entity.company;

import com.newper.constant.ComState;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer comIdx;

    private String comType;     // 사업자분류
    private String comMid;      // MID

    @Enumerated(EnumType.STRING)
    private ComState comState;  // 상태

    private String comName;     // 상호법인명
    private String comCeo;      // 대표자명
    private String comNum;      // 사업자번호
    private String comNick;     // 거래처약칭
    private String comTel;      // 전화번호, 회사대표번호
    private String comFax;      // 팩스
    private String comBank;     // 은행코드
    private String comAccount;  // 계좌번호

    // ? Address는 값타입 생성 ?
    private String post;        // 우편번호, 주소
    private String addr1;
    private String addr2;
    private String addr3;
    private String addr4;

    private String comNumFile;  // 사업자 등록증
    private String comAccountFile;  //통장사본
    private String comAs;       // AS 책임정보
    private String comAsNum;    // 외주 제조사 AS번호
    private String comMemo;
    private String comModifiedMemo;

    // 등록일 등등... pre?
    @CreatedBy
    @Column(updatable = false)
    private String createdBy;

    @CreatedDate
    @Column(updatable = false)
    private LocalDate createdDate;

    @CreatedDate
    @Column(updatable = false)
    private LocalTime createdTime;

    @LastModifiedBy
    private String modifiedBy;
    @LastModifiedDate
    private LocalDate modifiedDate;
    @LastModifiedDate
    private LocalDateTime modifiedTime;

}
