package com.newper.entity;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder

public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CART_CU_IDX", referencedColumnName = "cuIdx")
    private Customer customer;

    private String cartSpo;
    private int cartCnt;

}
