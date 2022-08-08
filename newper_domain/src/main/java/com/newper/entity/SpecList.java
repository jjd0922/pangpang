package com.newper.entity;


import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class SpecList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer speclList;

    private String speclName;

    private String speclValue;





}