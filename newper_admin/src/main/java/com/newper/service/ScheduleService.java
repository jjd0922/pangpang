package com.newper.service;

import com.newper.component.AdminBucket;
import com.newper.component.Common;
import com.newper.constant.SState;
import com.newper.dto.ParamMap;
import com.newper.entity.Company;
import com.newper.entity.Schedule;
import com.newper.exception.MsgException;
import com.newper.repository.ScheduleRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScheduleService {

    private final ScheduleRepo scheduleRepo;

    /**영업활동정보 등록 처리후 idx반환*/
    @Transactional
    public Integer saveSchedule(ParamMap paramMap, MultipartFile file1, MultipartFile file2, MultipartFile file3) {
        // paramMap으로 스케쥴 구성
        Schedule schedule = setScheduleByParamMap(paramMap);

        // 파일업로드 후 schedule에 셋팅
        String scheFilePath1 = "";
        String scheFileName1 = "";
        if (!file1.isEmpty()) {
            scheFilePath1 = Common.uploadFilePath(file1, "schedule/", AdminBucket.SECRET);
            scheFileName1 = file1.getOriginalFilename();
        }
        String scheFilePath2 = "";
        String scheFileName2 = "";
        if (!file2.isEmpty()) {
            scheFilePath2 = Common.uploadFilePath(file2, "schedule/", AdminBucket.SECRET);
            scheFileName2 = file2.getOriginalFilename();
        }
        String scheFilePath3 = "";
        String scheFileName3 = "";
        if (!file3.isEmpty()) {
            scheFilePath3 = Common.uploadFilePath(file3, "schedule/", AdminBucket.SECRET);
            scheFileName3 = file3.getOriginalFilename();
        }
        schedule.setSFile1(scheFilePath1);
        schedule.setSFileName1(scheFileName1);
        schedule.setSFile2(scheFilePath2);
        schedule.setSFileName2(scheFileName2);
        schedule.setSFile3(scheFilePath3);
        schedule.setSFileName3(scheFileName3);

        // schedule insert
        Schedule savedSchedule = scheduleRepo.save(schedule);
        return savedSchedule.getSIdx();
    }

    /**영업활동 수정*/
    @Transactional
    public Integer updateSchedule(Integer sIdx, ParamMap paramMap, MultipartFile file1, MultipartFile file2, MultipartFile file3) {
        // 기존 schedule find
        Schedule oldSchedule = scheduleRepo.findById(sIdx).orElseThrow(() -> new MsgException("존재하지 않는 영업활동입니다."));
        
        // paramMap으로 새로운 schedule 구성
        paramMap.remove("sIdx");
        Schedule scheduleParam = setScheduleByParamMap(paramMap);
        
        // 미팅일, 미팅시각이 바뀌었는지 확인
        LocalDate paramDate = LocalDate.parse(paramMap.getString("sDate"));
        LocalTime paramTime = LocalTime.parse(paramMap.getString("sTime"));
        boolean isRescheduled = !oldSchedule.getSDate().isEqual(paramDate) || !oldSchedule.getSTime().equals(paramTime);

        // 미팅일, 미팅시각이 바뀐경우 > 기존 schedule의 sState를 '일정변경'으로 바꾸고, 미팅일시만 바뀐 새로운 schedule 생성
        if (isRescheduled) {
            scheduleParam.rescheduled(oldSchedule); // 원래 있던 파일 경로, 이름보존
            oldSchedule.setSState(SState.RESCHEDULE); // 이전 영업활동정보의 상태변경
            updateScheduleFiles(scheduleParam, file1, file2, file3); // 파일수정

            Schedule savedNewSche = scheduleRepo.save(scheduleParam);
            return savedNewSche.getSIdx();
            
        } else { // 미팅일, 미팅시각은 바뀌지 않고 다른 정보들이 수정된 경우
            oldSchedule.updateSchedule(scheduleParam);
            updateScheduleFiles(oldSchedule, file1, file2, file3); // 파일수정

            return oldSchedule.getSIdx();
        }
    }

    /**paramMap으로 가져온 값들을 schedule 세팅*/
    public Schedule setScheduleByParamMap(ParamMap paramMap) {
        Schedule schedule = paramMap.mapParam(Schedule.class);

        // 기존거래처인 경우 comIdx와 schedule 연결
        if (StringUtils.hasText(paramMap.getString("comIdx"))) {
            Company company = Company.builder().comIdx(paramMap.getInt("comIdx")).build();
            schedule.setCompany(company);
        }

        // date타입들 set
        schedule.setSDate(LocalDate.parse(paramMap.getString("sDate")));
        schedule.setSTime(LocalTime.parse(paramMap.getString("sTime")));
        if (StringUtils.hasText(paramMap.getString("sCompletionDate"))) {
            schedule.setSCompletionDate(LocalDate.parse(paramMap.getString("sCompletionDate")));
        }

        // 미팅 후 확인사항 List로
        List<Map<String, String>> sCheck = new ArrayList<>();
        if (StringUtils.hasText(paramMap.getString("checkList1")) || StringUtils.hasText(paramMap.getString("checkList2"))) {
            if (StringUtils.hasText(paramMap.getString("checkList1"))) {
                Map<String,String> checkMap1 = new HashMap<>();
                checkMap1.put("checkText",paramMap.getString("checkList1"));
                checkMap1.put("checked", paramMap.getString("isDone1"));
                sCheck.add(checkMap1);
            }

            if (StringUtils.hasText(paramMap.getString("checkList2"))) {
                Map<String,String> checkMap2 = new HashMap<>();
                checkMap2.put("checkText",paramMap.getString("checkList2"));
                checkMap2.put("checked", paramMap.getString("isDone2"));
                sCheck.add(checkMap2);
            }
        }
        schedule.setSCheck(sCheck);
        return schedule;
    }

    /**영업활동정보의 파일 수정*/
    public void updateScheduleFiles(Schedule schedule, MultipartFile file1, MultipartFile file2, MultipartFile file3) {
        if (!file1.isEmpty()) {
            String filePath1 = Common.uploadFilePath(file1, "schedule/", AdminBucket.SECRET);
            schedule.setSFile1(filePath1);
            schedule.setSFileName1(file1.getOriginalFilename());
        }
        if (!file2.isEmpty()) {
            String filePath2 = Common.uploadFilePath(file2, "schedule/", AdminBucket.SECRET);
            schedule.setSFile2(filePath2);
            schedule.setSFileName2(file2.getOriginalFilename());
        }
        if (!file3.isEmpty()) {
            String filePath3 = Common.uploadFilePath(file3, "schedule/", AdminBucket.SECRET);
            schedule.setSFile3(filePath3);
            schedule.setSFileName3(file3.getOriginalFilename());
        }
    }
}
