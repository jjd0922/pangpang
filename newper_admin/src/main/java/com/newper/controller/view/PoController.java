package com.newper.controller.view;

import com.newper.dto.ParamMap;
import com.newper.entity.*;
import com.newper.exception.MsgException;
import com.newper.mapper.PoMapper;
import com.newper.repository.*;
import com.newper.service.PoService;
import com.newper.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


@RequestMapping(value = "/po/")
@Controller
@RequiredArgsConstructor
public class PoController {
    private final PoService poService;

    private final PoRepo poRepo;

    private final PoProductRepo poProductRepo;

    private final HiworksRepo hiworksRepo;

    private final EstimateRepo estimateRepo;
    private final ProductService productService;
    private final PoMapper poMapper;

    /** 발주품의 페이지 **/
    @GetMapping(value = "")
    public ModelAndView po(){
        ModelAndView mav = new ModelAndView("po/po");
        return mav;
    }

    /** 발주품의 생성 페이지 */
    @GetMapping(value = "poPop")
    public ModelAndView poPop(){
        ModelAndView mav = new ModelAndView("po/poPop");

        return mav;
    }

    /** 발주품의 상세 & 수정 페이지 */
    @GetMapping(value = "poPop/{poIdx}")
    public ModelAndView poPopDetail(@PathVariable long poIdx){
        ModelAndView mav = new ModelAndView("po/poPop");
        mav.addObject("po", poRepo.findPoByPoIdx((int) poIdx));
        mav.addObject("poProduct", poMapper.selectPoProductByPoIdx(poIdx));
        return mav;
    }

    /** 견적서 관리 페이지 **/
    @GetMapping(value = "estimate")
    public ModelAndView estimate(){
        ModelAndView mav = new ModelAndView("po/estimate");
        return mav;
    }

    /** 견적서 관리 페이지 **/
    @GetMapping(value = "estimatePop")
    public ModelAndView estimateNew(){
        ModelAndView mav = new ModelAndView("po/estimatePop");
        return mav;
    }

    /** 견적서 생성 **/
    @PostMapping(value = "estimatePop")
    public ModelAndView estimateNewPost(ParamMap paramMap, MultipartFile peFile){
        ModelAndView mav = new ModelAndView("main/alertMove");
        if (peFile.getSize() == 0) {
            throw new MsgException ("파일을 첨부해 주세요");
        }
        Integer peIdx = poService.saveEstimate(paramMap, peFile);

        mav.addObject("loc", "/po/estimatePop/" + peIdx);
        mav.addObject("msg", "견적서 등록 완료");
        return mav;
    }

    /** 견적서 상세 & 수정 페이지 **/
    @GetMapping(value = "estimatePop/{peIdx}")
    public ModelAndView estimateDetail(@PathVariable Integer peIdx){
        ModelAndView mav = new ModelAndView("po/estimatePop");
        mav.addObject("estimate", estimateRepo.findEstimateByPeIdx(peIdx));
        mav.addObject("product", poService.selectEstimateProduct(peIdx));
        return mav;
    }

    /** 견적서 수정 */
    @PostMapping(value = "estimatePop/{peIdx}")
    public ModelAndView estimatePopModify(ParamMap paramMap, MultipartFile peFile, @PathVariable Integer peIdx){
        ModelAndView mav = new ModelAndView("main/alertMove");
        poService.updateEstimate(paramMap, peFile, peIdx);

        mav.addObject("loc", "/po/estimatePop/" + peIdx);
        mav.addObject("msg", "견적서 수정 완료");
        return mav;
    }
    /** 발주 관리 페이지*/
    @GetMapping("approved")
    public ModelAndView approved(){
        ModelAndView mav = new ModelAndView("po/approved");

        return mav;
    }
}
