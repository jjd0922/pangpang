package com.newper.entity;

import com.newper.constant.ComState;
import com.newper.entity.common.Address;
import com.newper.entity.common.ModifiedEntity;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Company extends ModifiedEntity {

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
    @Embedded
    private Address address;

    private String comNumFile;  // 사업자 등록증
    private String comAccountFile;  //통장사본
    private String comAs;       // AS 책임정보
    private String comAsNum;    // 외주 제조사 AS번호
    private String comMemo;
//    private String comModifiedMemo;

}
