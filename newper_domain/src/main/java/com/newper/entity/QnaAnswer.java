package com.newper.entity;

import com.newper.entity.common.CreatedEntity;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;

@Entity
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class QnaAnswer extends CreatedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long qnaaIdx;

    private String qnaaContent;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "qnaAnswer", cascade = CascadeType.ALL)
    private List<Qna> qnaList;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "qnaAnswer", cascade = CascadeType.ALL)
    private List<QnaSp> qnaSpList;
}
