package com.newper.entity;

import com.newper.converter.ConvertList;
import com.newper.entity.common.BaseEntity;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private List<String> cateSpecList;

    private String cateMemo;
    /**
     * 중분류에서 사용하는 고시정보 json
     */
    @Builder.Default
    private Map<String, Object> cateInfo = new HashMap<>();

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


