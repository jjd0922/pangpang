package com.newper.entity;

import com.newper.entity.common.Address;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
@Entity
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Warehouse {
    @Id
    private Integer whIdx;

    private String whName;

    @Embedded
    private Address address;

}
