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
public class MainSectionSp {

    @EmbeddedId
    private MainSectionSpEmbedded msspIdx;

    /** 순서*/
    private int msspOrder;
}
