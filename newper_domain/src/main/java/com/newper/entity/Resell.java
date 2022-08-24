package com.newper.entity;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Resell {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer rsIdx;

    private LocalDate rsDate;

    private LocalTime reTime;


}
