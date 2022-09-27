package com.newper.controller.view;

import com.newper.dto.ParamMap;
import com.newper.entity.Goods;
import com.newper.entity.Location;
import com.newper.entity.LocationMove;
import com.newper.entity.Product;
import com.newper.exception.MsgException;
import com.newper.mapper.LocationMapper;
import com.newper.repository.GoodsRepo;
import com.newper.repository.LocationMoveRepo;
import com.newper.repository.LocationRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/stock/")
public class StockController {

    private final LocationMoveRepo locationMoveRepo;

    private final LocationRepo locationRepo;

    private LocationMapper locationMapper;

    private final GoodsRepo goodsRepo;

    /**적재관리 메인페이지*/
    @GetMapping("load")
    public ModelAndView loading() {
        ModelAndView mav = new ModelAndView("/stock/load");
        return mav;
    }

    /**적재관리 > 재고인수대기 버튼 > 자산처리 팝업 페이지*/
    @GetMapping("load/wait")
    public ModelAndView stockWaiting() {
        ModelAndView mav = new ModelAndView("/stock/load_wait");
        return mav;
    }

    /**적재관리 > 재고인수(적재) 버튼 > 재고적재 페이지*/
    @GetMapping("load/take")
    public ModelAndView stockTaking(ParamMap paramMap) {
        ModelAndView mav = new ModelAndView("/stock/load_take");
        mav.addObject("gIdxs", paramMap.getString("gIdxs"));
        mav.addObject("count", paramMap.getString("gIdxs").split(",").length);
        return mav;
    }

    /**적재관리 > 바코드_재고인수(적재) 버튼 > 재고적재 페이지*/
    @GetMapping("load/barcode")
    public ModelAndView stockBarcode() {
        ModelAndView mav = new ModelAndView("/stock/load_barcode");
        mav.addObject("barcode", " ");
        return mav;
    }

    /**재고현황 메인페이지 > 로케이션별 재고현황 조회*/
    @GetMapping("")
    public ModelAndView stock() {
        ModelAndView mav = new ModelAndView("/stock/stock");
        return mav;
    }
    
    /**재고현황 > 해당 로케이션의 재고자산 상세*/
    @GetMapping("goods")
    public ModelAndView goodsInLocation(ParamMap paramMap, int gsIdx ,int locIdx ) {
        System.out.println(paramMap.getMap());
        ModelAndView mav = new ModelAndView("/stock/goods");
        mav.addObject("gsIdx",gsIdx);
        mav.addObject("locIdx",locIdx);
/*        Map<String, Object> map = new HashMap<>();
        map.put("gsIdx",gsIdx);
        map.put("locIdx",locIdx);*/
        return mav;
    }
    
    /**창고이동관리 메인페이지*/
    @GetMapping("move")
    public ModelAndView stockMove() {
        ModelAndView mav = new ModelAndView("/stock/move");
        return mav;
    }

    /**창고이동관리 > 창고이동등록 페이지*/
    @GetMapping("move/pop")
    public ModelAndView stockMovePop(ParamMap paramMap) {
        ModelAndView mav = new ModelAndView("/stock/move_pop");
        mav.addObject("whIdx","whIdx");
        return mav;
    }

    /**창고이동관리 > 창고이동 조회/수정 페이지*/
    @GetMapping("move/pop/{lmIdx}") //pathvariable 부분 임시, 오브젝트도 임시.(추후 수정~)
    public ModelAndView stockMovePopDetail(@PathVariable long lmIdx,Integer locIdx) {
        ModelAndView mav = new ModelAndView("/stock/move_pop");
        LocationMove locationMove=locationMoveRepo.findLocationMoveByLmIdx(lmIdx);
        Location location=locationRepo.findLocationByLocIdx(locIdx);
        mav.addObject("locationMove", locationMove);

        //   location.getGoodsList().get(locationMove.getLocation2().getLocIdx());
//        locationMapper.insertLocationMoveGoods(locationMove.getLmIdx(),location.getGoodsList().get(locationMove.getLocation2().getLocIdx().intValue()));


//        Location location=locationRepo.getReferenceById(locationMove.getLocation2().getLocIdx());
       /* location.setLocIdx(locationMove.getLocation2().getLocIdx());*/
/*        location.setWarehouse(locationMove.getLocation2().getWarehouse());
        location.setGoodsList(locationMove.getLocation2().getGoodsList());*/

//       Goods goods = goodsRepo.findById(gIdx).orElseThrow(() -> new MsgException("존재하지 않는 자산입니다."));

        mav.addObject("location",location);
//        mav.addObject("goods", locationMapper.selectLocationMoveGoods(gIdx));
//        mav.addObject("goods", locationMapper.selectLocationMoveGoods(lmIdx));
//        mav.addObject("location", location);
        return mav;
    }

    /**재고관리 피킹관리 페이지*/
    @GetMapping("picking")
    public ModelAndView picking() {
        ModelAndView mav = new ModelAndView("/stock/picking");
        return mav;
    }
}
