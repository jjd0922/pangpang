package com.newper.entity;

import com.newper.constant.GiftgState;
import com.newper.entity.common.CreatedEntity;
import com.newper.exception.MsgException;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.List;

@Entity
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name="gift_group")
public class GiftGroup extends CreatedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long giftgIdx;

    private String giftgName;

    @Enumerated(EnumType.STRING)
    private GiftgState giftgState;

    private LocalDate giftgStartDate;
    private LocalDate giftgEndDate;
    private int giftgMoney;
    private int giftgCnt;
    private int giftgUsedCnt;

    @OneToMany(mappedBy = "giftGroup")
    private List<Gift> giftList;

    @PrePersist
    @PreUpdate
    public void preSave() {
        if (!StringUtils.hasText(getGiftgName())) {
            throw new MsgException("상품권 발행명을 입력해주세요.");
        } else if (getGiftgState() == null) {
            throw new MsgException("상품권 등록상태를 선택해주세요.");
        } else if (getGiftgStartDate() == null || getGiftgEndDate() == null) {
            throw new MsgException("올바른 상품권 유효기간을 설정해주세요.");
        } else if (getGiftgCnt() <= 0) {
            throw new MsgException("올바른 상품권 생산수량을 설정해주세요.");
        } else if (getGiftgMoney() <= 0) {
            throw new MsgException("올바른 상품권 금액을 입력해주세요.");
        }

    }
}
