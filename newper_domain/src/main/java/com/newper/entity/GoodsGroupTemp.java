package com.newper.entity;

import com.newper.constant.GgtType;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class GoodsGroupTemp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ggtIdx;

    @Enumerated(EnumType.STRING)
    private GgtType ggtType;

    @Builder.Default
    private LocalDate ggtDate = LocalDate.now();
}
