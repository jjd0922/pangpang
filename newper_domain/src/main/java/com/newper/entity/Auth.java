package com.newper.entity;

import com.newper.converter.ConvertList;
import com.newper.entity.common.BaseEntity;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

@Entity
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "AUTH")
@ToString
public class Auth{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer authIdx;

    private String authName;
    private String authMenu;
    private String authMask;

}
