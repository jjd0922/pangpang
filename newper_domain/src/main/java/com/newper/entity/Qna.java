package com.newper.entity;

import com.newper.constant.QnaType1;
import com.newper.constant.QnaType2;
import com.newper.entity.common.BaseEntityWithoutBy;
import com.newper.exception.MsgException;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Entity
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Qna extends BaseEntityWithoutBy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long qnaIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="QNA_OGG_IDX", referencedColumnName = "oggIdx")
    private OrderGsGroup orderGsGroup;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="QNA_CU_IDX", referencedColumnName = "cuIdx")
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="QNA_QNAA_IDX", referencedColumnName = "qnaaIdx")
    private QnaAnswer qnaAnswer;

    @Enumerated(EnumType.STRING)
    private QnaType1 qnaType1;
    @Enumerated(EnumType.STRING)
    private QnaType2 qnaType2;
    private String qnaMail;
    private boolean qnaMailAlarm;
    private String qnaPhone;
    private boolean qnaPhoneAlarm;
    private String qnaTitle;
    private String qnaContent;
    private List<String> qnaJson;

    @PrePersist
    @PreUpdate
    public void preSave() {
        if (getQnaMail().equals("") && isQnaMailAlarm()) {
            throw new MsgException("답변알림을 받으시려면 이메일주소를 입력해주세요.");
        } else if (getQnaPhone().equals("") && isQnaPhoneAlarm()) {
            throw new MsgException("답변알림을 받으시려면 휴대폰 번호를 입력해 주세요.");
        } else if (!StringUtils.hasText(getQnaTitle())) {
            throw new MsgException("문의 제목을 입력해주세요");
        } else if (!StringUtils.hasText(getQnaContent())) {
            throw new MsgException("문의 내용을 입력해주세요");
        }
    }

    /** qnaMail, qnaPhone setting */
    public void setParam(Map<String,Object> map) {
        // qnaMail
        String mail = "";
        if (StringUtils.hasText(map.get("email").toString())) {
            String pattern = "\\w+\\.\\w+(\\.\\w+)?";
            if (!Pattern.matches(pattern, map.get("domain").toString())) {
                throw new MsgException("정확한 이메일을 입력해 주세요");
            }
            mail = map.get("email") + "@" + map.get("domain");
        }
        setQnaMail(mail);
        // qnaPhone
        String phone = map.get("phone1").toString() + map.get("phone2") + map.get("phone3");
        setQnaPhone(phone);
    }

    /** 1:1문의 업데이트*/
    public void updateQna(Qna newQna) {
        setQnaMailAlarm(newQna.isQnaMailAlarm());
        setQnaPhoneAlarm(newQna.isQnaPhoneAlarm());
        setQnaType1(newQna.getQnaType1());
        setQnaType2(newQna.getQnaType2());
        setQnaTitle(newQna.getQnaTitle());
        setQnaContent(newQna.getQnaContent());
    }

    /** 휴대폰 번호 - 붙인 포맷으로 가져오기*/
    public String getPhoneFormat(){
        return getQnaPhone().replaceAll("(\\d{3})(\\d{3,4})(\\d{4})", "$1-$2-$3");
    }

    /** 휴대폰 번호 3등분 끊어서 가져오기*/
    public String[] getPhoneArr() {
        return getPhoneFormat().split("-");
    }

    /** 작성일 format '-' > '.' 변환*/
    public String getDateFormatDot() {
        return getCreatedDate().toString().replaceAll("-", ".");
    }

}
