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
    private Integer col;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "C_CU_IDX", referencedColumnName = "cuIdx")
    private  Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "C_SP_IDX", referencedColumnName = "spIdx")
    private  ShopProduct shopProduct;

    @CreatedDate
    @Column(updatable = false)
    private LocalDate createdDate;
    @CreatedDate
    @Column(updatable = false)
    private LocalTime createdTime;
}
