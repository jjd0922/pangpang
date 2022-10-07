package com.newper.controller.view;

import com.newper.component.ShopSession;
import com.newper.constant.EgMenu;
import com.newper.repository.EventGroupRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;

@RequiredArgsConstructor
@RestController
@RequestMapping("/event/")
public class EventController {

    @Autowired
    private ShopSession shopSession;

    private final EventGroupRepo eventGroupRepo;

    @GetMapping("{egIdx}")
    public ModelAndView eventDetail(@PathVariable("egIdx") Long egIdx){
        ModelAndView mav = new ModelAndView("mainMenu/event_egIdx");

        mav.addObject("eventGroup",eventGroupRepo.findByegIdx(egIdx));
        return mav;
    }

    /** 이벤트 /기획전 menu load */
    @PostMapping("{order}/{menu}.ajax")
    public ModelAndView secondSortEvent(@PathVariable("order") String order, @PathVariable("menu") String menu){
        ModelAndView mav = new ModelAndView("part/banner :: banner_2");
        if(order.equals("open")){
            if(!menu.equals("all")){
                for(EgMenu egMenu : EgMenu.values()){
                    if(menu.equals(egMenu.name())){
                        mav.addObject("eventGroupList", eventGroupRepo.findEventGroupByShop_shopIdxAndEgStateTrueAndEgMenuAndEgCloseDateAfter(shopSession.getShopIdx(),egMenu, LocalDate.now() ));
                    }
                }
            }else{
                mav.addObject("eventGroupList", eventGroupRepo.findEventGroupByShop_shopIdxAndEgStateTrueAndEgCloseDateAfter(shopSession.getShopIdx(), LocalDate.now() ));
            }
        }else if(order.equals("close")){
            if(!menu.equals("all")){
                for(EgMenu egMenu : EgMenu.values()){
                    if(menu.equals(egMenu.name())){
                        mav.addObject("eventGroupList", eventGroupRepo.findEventGroupByShop_shopIdxAndEgStateTrueAndEgMenuAndEgCloseDateBefore(shopSession.getShopIdx(),egMenu, LocalDate.now() ));
                    }
                }
            }else{
                mav.addObject("eventGroupList", eventGroupRepo.findEventGroupByShop_shopIdxAndEgStateTrueAndEgCloseDateBefore(shopSession.getShopIdx(), LocalDate.now() ));
            }
        }
        return mav;
    }

}
