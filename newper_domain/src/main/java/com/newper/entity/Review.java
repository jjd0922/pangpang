package com.newper.entity;

import com.newper.entity.common.BaseEntityWithoutBy;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Review extends BaseEntityWithoutBy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rIdx;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="R_SP_IDX", referencedColumnName = "spIdx")
    private ShopProduct shopProduct;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="R_O_IDX", referencedColumnName = "oIdx")
    private Orders orders;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="R_CU_IDX", referencedColumnName = "cuIdx")
    private Customer customer;

    @Builder.Default
    private List<String> rJson = new ArrayList<>();

    private Integer rStar;
    private String rContent;

    public void updateReview(Review newReview) {
        setRStar(newReview.getRStar());
        setRContent(newReview.getRContent());
    }
}
