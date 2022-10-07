package com.newper.controller.rest;

import com.newper.dto.ParamMap;
import com.newper.mapper.ShopProductMapper;
import com.newper.service.ShopProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/product/")
public class ProductRestController {

    private final ShopProductMapper shopProductMapper;
    private final ShopProductService shopProductService;

    @PostMapping("best/{scateIdx}.load")
    public List<Map<String,Object>> todayBestShopProduct(@PathVariable(value = "scateIdx", required = false) Integer scateIdx){
        List<Map<String,Object>> returnMapList = new ArrayList<>();


        return returnMapList;
    }
    /** mainsection 카테고리 메인섹션 상품 조회 */
    @PostMapping(value = "category/{msIdx}/{scateIdx}.ajax")
    public ModelAndView recommendCategoryProduct(@PathVariable("msIdx") Long msIdx
                    , @PathVariable("scateIdx") Integer scateIdx){
        ModelAndView mav = new ModelAndView("mainSection/mainSection_product :: categoryProduct");
        Map<String,Object> map = new HashMap<>();
        map.put("msIdx", msIdx);
        map.put("scateIdx", scateIdx);
        List<Map<String,Object>> mainsectionscateProductList = shopProductMapper.selectscateMainSectionProductList(map);
        mav.addObject("shopProductList", mainsectionscateProductList);
        return mav;
    }
    /** 카테고리 별 상품 리스트 */
    @PostMapping(value = "category.ajax")
    public ModelAndView productCategoryList(ParamMap paramMap){
        ModelAndView mav = new ModelAndView("part/thumbnailList :: thumbanil-default-map-list");

//        Map<String,Object> result = shopProductService.selectShopProductListBySearch(paramMap);
//        mav.addObject("shopProductList", result.get("shopProductList"));
//        mav.addObject("count", result.get("count"));
        List<Map<String,Object>> shopProductList = shopProductMapper.selectCategoryProductList(paramMap.getMap());
        mav.addObject("shopProductList", shopProductList);
        return mav;
    }
}
