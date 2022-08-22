package com.newper.entity;

import com.newper.converter.ConvertList;
import com.newper.converter.ConvertMap;
import com.newper.entity.common.BaseEntity;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.*;

@Entity
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Category extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cateIdx;
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parentCategory", cascade = CascadeType.ALL)
    @OrderBy(value = "cateOrder asc")
    private List<Category> categoryList;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CATE_PARENT_IDX", referencedColumnName = "cateIdx")
    private Category parentCategory;
    

    private String cateType;
    private int	cateDepth;
    private String cateName;
    private String cateNick;
    private String cateIcon;
    private String cateThumbnail;
    private String cateImage;
    private int cateOrder;

    @Enumerated(EnumType.STRING)
    private CateSpec cateSpec;
    private List<String> cateSpecList;

    private String cateMemo;
    /**
     * 중분류에서 사용하는 고시정보 json
     */
    @Builder.Default
    @Convert(converter = ConvertList.class)
    private List<Map<String, Object>> cateInfo = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category", cascade = CascadeType.DETACH)
    private List<Product> categoryProductList;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "brand", cascade = CascadeType.DETACH)
    private List<Product> brandProductList;

    /** order 업데이트.*/
    public void updateCategoryOrder(int cateOrder){
        if(getCateOrder() < 0){
            setCateOrder(cateOrder * -1);
        }else{
            setCateOrder(cateOrder);
        }
    }



}


