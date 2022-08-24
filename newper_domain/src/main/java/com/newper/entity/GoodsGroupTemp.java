package com.newper.entity;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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

    private String ggtType;

    private LocalDate ggtDate;
}
