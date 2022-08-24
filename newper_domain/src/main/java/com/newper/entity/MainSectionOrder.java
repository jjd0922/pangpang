package com.newper.entity;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class MainSectionOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long msoIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MSO_MS_IDX", referencedColumnName = "msIdx")
    private MainSection mainSection;

    private String msoType;

    private Integer msoOrder;



}
