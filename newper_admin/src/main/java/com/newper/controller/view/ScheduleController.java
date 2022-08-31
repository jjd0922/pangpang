package com.newper.controller.view;

import com.newper.dto.ParamMap;
import com.newper.dto.ReturnDatatable;
import com.newper.entity.Schedule;
import com.newper.repository.ScheduleRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/schedule/")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleRepo scheduleRepo;

    @GetMapping("")
    public ModelAndView schedule() {
        ModelAndView mav = new ModelAndView("/schedule/schedule");

        return mav;
    }
    
    @GetMapping("schedulePop")
    public ModelAndView popNewSchedule() {
        ModelAndView mav = new ModelAndView("/schedule/schedulePop");
    
        return mav;
    }
    
    @GetMapping("schedulePop/{sIdx}")
    public ModelAndView popScheduleDetail(@PathVariable Integer sIdx) {
        ModelAndView mav = new ModelAndView("/schedule/schedulePop");

        Schedule schedule = scheduleRepo.findScheduleBysIdx(sIdx);
        mav.addObject("schedule", schedule);

        // 확인사항 값
        List<Map<String, String>> sCheck = schedule.getSCheck();
        for (int i = 0; i < sCheck.size(); i++) {
            mav.addObject("checkList" + (i+1), sCheck.get(i));
        }

        return mav;
    }

}
