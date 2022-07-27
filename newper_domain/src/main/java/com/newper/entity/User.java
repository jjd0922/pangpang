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
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer uIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "U_COM_IDX", referencedColumnName = "shopIdx")
    private Company company;
}
