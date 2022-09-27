package com.newper.controller.view;

import com.newper.component.ShopSession;
import com.newper.constant.SpState;
import com.newper.entity.ShopProduct;
import com.newper.exception.MsgException;
import com.newper.repository.ShopProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping(value = "/shopProduct/")
@Controller
@RequiredArgsConstructor
public class ShopProductController {

    @Autowired
    private ShopSession shopSession;

    private final ShopProductRepo shopProductRepo;

    @GetMapping("{idx}")
    public ModelAndView idx(@PathVariable("idx") long spIdx){
        ModelAndView mav = new ModelAndView("shopProduct/idx");

        ShopProduct shopProduct = shopProductRepo.findById(spIdx).orElseThrow(() -> new MsgException("존재하지 않는 상품입니다"));
        if( shopProduct.getShop().getShopIdx().longValue() != shopSession.getShopIdx().longValue() ){
            //해당 분양몰에서 판매하는 상품인지 check
            throw new MsgException("존재하지 않는 상품입니다");
        }


        if( !shopProduct.isShow()){
            throw new MsgException("현재 볼 수 없는 상품입니다");
        }


        return mav;
    }
}
