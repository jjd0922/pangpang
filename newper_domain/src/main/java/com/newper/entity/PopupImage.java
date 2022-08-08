package com.newper.entity;

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
public class PopupImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer popiIdx;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POPI_POP_IDX", referencedColumnName = "popIdx")
    private Popup popup;

    private String popiImage;

    private String popiImageMobile;

    private String popiUrl;

}
