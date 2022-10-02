package com.newper.service;

import com.newper.component.AdminBucket;
import com.newper.component.Common;
import com.newper.component.SessionInfo;
import com.newper.constant.*;
import com.newper.dto.ParamMap;
import com.newper.entity.*;
import com.newper.entity.common.Address;
import com.newper.exception.MsgException;
import com.newper.repository.CalculateAdjustRepo;
import com.newper.repository.CalculateGroupRepo;
import com.newper.repository.OrdersGsRepo;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CalculateService {
    private final SessionInfo sessionInfo;
    private final CalculateGroupRepo calculateGroupRepo;
    private final CalculateAdjustRepo calculateAdjustRepo;
    private final OrdersGsRepo ordersGsRepo;



    /** 정산 조정 완료 */
    @Transactional
    public void saveAdjust(ParamMap paramMap) {
        CalculateGroup calculateGroup = null;
        OrderGs orderGs = null;
        if (paramMap.get("ccgIdx") != null) {
            calculateGroup = calculateGroupRepo.findById(paramMap.getInt("ccgIdx")).get();

            // 정산완료인건 수정 불가
            if (calculateGroup.getCcgConfirmState().equals(CcgConfirmState.COMPLETE)) {
                throw new MsgException("해당 정산건은 이미 정산이 완료되었습니다.");
            }
        } else {
            orderGs = ordersGsRepo.findById(Long.parseLong(paramMap.getString("ogIdx"))).get();

            // 정산완료인건 수정 불가
            if (orderGs.getOgCalConfirmState().equals(CcgConfirmState.COMPLETE)) {
                throw new MsgException("해당 정산건은 이미 정산이 완료되었습니다.");
            }
        }

        int ccgAdjustCost = 0;

        List<Long> ccaIdx = paramMap.getListLong("ccaIdx");
        List<String> ccaContent = paramMap.getList("ccaContent");
        List<String> ccaReason = paramMap.getList("ccaReason");
        List<Long> ccaCost = paramMap.getListLong("ccaCost");
        for (int i = 0; i < ccaIdx.size(); i++) {
            CalculateAdjust calculateAdjust = CalculateAdjust
                    .builder()
                    .ccaIdx(ccaIdx.get(i).intValue())
                    .ccaContent(ccaContent.get(i))
                    .ccaReason(ccaReason.get(i))
                    .ccaCost(ccaCost.get(i).intValue())
                    .build();

            if (paramMap.get("ccgIdx") != null) {
                calculateAdjust.setCalculateGroup(calculateGroup);
            } else {
                calculateAdjust.setOrderGs(orderGs);
            }

            ccgAdjustCost += ccaCost.get(i);

            calculateAdjustRepo.save(calculateAdjust);
        }

        if (paramMap.get("ccgIdx") != null) {
            calculateGroup.setCcgAdjustCost(ccgAdjustCost);
            calculateGroup.setCcgAdjust(CcgAdjust.ADJUST);
            calculateGroupRepo.save(calculateGroup);
        } else {
            orderGs.setOgCalAdjustCost(ccgAdjustCost);
            orderGs.setOgCalAdjust(CcgAdjust.ADJUST);
        }
    }


    /** 정산 조정 삭제 */
    @Transactional
    public void removeAdjust(ParamMap paramMap) {
        int adjustCost = 0;
        CalculateGroup calculateGroup = null;
        OrderGs orderGs = null;
        if (paramMap.get("ccgIdx") != null) {
            calculateGroup = calculateGroupRepo.findById(paramMap.getInt("ccgIdx")).get();

            // 정산완료인건 수정 불가
            if (calculateGroup.getCcgConfirmState().equals(CcgConfirmState.COMPLETE)) {
                throw new MsgException("해당 정산건은 이미 정산이 완료되었습니다.");
            }

            adjustCost = calculateGroup.getCcgAdjustCost();

            CalculateAdjust calculateAdjust = calculateAdjustRepo.findById(paramMap.getInt("ccaIdx")).get();
            adjustCost -= calculateAdjust.getCcaCost();

            calculateGroup.setCcgAdjustCost(adjustCost);

            if (adjustCost == 0) {
                calculateGroup.setCcgAdjust(CcgAdjust.NORMAL);
            }

            calculateAdjustRepo.deleteById(paramMap.getInt("ccaIdx"));
            calculateGroupRepo.save(calculateGroup);
        } else {
            orderGs = ordersGsRepo.findById(Long.parseLong(paramMap.getString("ogIdx"))).get();

            // 정산완료인건 수정 불가
            if (orderGs.getOgCalConfirmState().equals(CcgConfirmState.COMPLETE)) {
                throw new MsgException("해당 정산건은 이미 정산이 완료되었습니다.");
            }
            adjustCost = orderGs.getOgCalAdjustCost();

            CalculateAdjust calculateAdjust = calculateAdjustRepo.findById(paramMap.getInt("ccaIdx")).get();
            adjustCost -= calculateAdjust.getCcaCost();

            orderGs.setOgCalAdjustCost(adjustCost);

            if (adjustCost == 0) {
                orderGs.setOgCalAdjust(CcgAdjust.NORMAL);
            }

            calculateAdjustRepo.deleteById(paramMap.getInt("ccaIdx"));
            ordersGsRepo.save(orderGs);
        }

    }

    /** 정산상태 수정 */
    @Transactional
    public void updateConfirmState(ParamMap paramMap) {
        CalculateGroup calculateGroup = calculateGroupRepo.findById(paramMap.getInt("ccgIdx")).get();
        CcgConfirmState ccgConfirmState_ori = calculateGroup.getCcgConfirmState();
        CcgConfirmState ccgConfirmState_new = CcgConfirmState.valueOf(paramMap.getString("ccgConfirmState"));

        // 해당 정산건 마감 완료시 제한
        if (ccgConfirmState_ori.equals(CcgCloseState.COMPLETE)) {
            throw new MsgException("해당정산건은 마감되어 상태변경이 불가능합니다.");
        }

        // 정산확정인 상태에서 정산확정 처리가 들어올시
        if (ccgConfirmState_ori.equals(CcgConfirmState.COMPLETE) && ccgConfirmState_new.equals(CcgConfirmState.COMPLETE)) {
            throw new MsgException("이미 정산 확정상태입니다.");
        }

        // 정산취소인 상태에서 정산취소 처리가 들어올시
        if (ccgConfirmState_ori.equals(CcgConfirmState.CANCEL) && ccgConfirmState_new.equals(CcgConfirmState.CANCEL)) {
            throw new MsgException("이미 정산 확정취소상태입니다.");
        }

        //정산대기 상태에서 정산취소 처리가 들어올시
        if (ccgConfirmState_ori.equals(CcgConfirmState.WAIT) && ccgConfirmState_new.equals(CcgConfirmState.CANCEL)) {
            throw new MsgException("정산대기인 정산건은 취소 할 수 없습니다.");
        }

        calculateGroup.setCcgConfirmState(ccgConfirmState_new);
        calculateGroup.setCcgConfirmDate(LocalDate.now());
        calculateGroup.setCcgConfirmTime(LocalTime.now());
        calculateGroup.setCcgConfirmBy(sessionInfo.getId());
        calculateGroup.setCcgMemo(paramMap.getString("ccgMemo"));
        calculateGroupRepo.save(calculateGroup);
    }

    /** 정산상태 여러건 수정 */
    @Transactional
    public void updateConfirmStateArr(ParamMap paramMap) {
        String[] ccgIdx = paramMap.getString("ccgIdx").split(",");
        for (int i = 0; i < ccgIdx.length; i++) {
            CalculateGroup calculateGroup = calculateGroupRepo.findById(Integer.parseInt(ccgIdx[i])).get();
            CcgConfirmState ccgConfirmState_ori = calculateGroup.getCcgConfirmState();
            CcgConfirmState ccgConfirmState_new = CcgConfirmState.valueOf(paramMap.getString("ccgConfirmState"));

            // 해당 정산건 마감 완료시 제한
            if (ccgConfirmState_ori.equals(CcgCloseState.COMPLETE)) {
                throw new MsgException("해당정산건은 마감되어 상태변경이 불가능합니다.");
            }

            // 정산확정인 상태에서 정산확정 처리가 들어올시
            if (ccgConfirmState_ori.equals(CcgConfirmState.COMPLETE) && ccgConfirmState_new.equals(CcgConfirmState.COMPLETE)) {
                throw new MsgException("이미 정산 확정상태입니다.");
            }

            // 정산취소인 상태에서 정산취소 처리가 들어올시
            if (ccgConfirmState_ori.equals(CcgConfirmState.CANCEL) && ccgConfirmState_new.equals(CcgConfirmState.CANCEL)) {
                throw new MsgException("이미 정산 확정취소상태입니다.");
            }

            //정산대기 상태에서 정산취소 처리가 들어올시
            if (ccgConfirmState_ori.equals(CcgConfirmState.WAIT) && ccgConfirmState_new.equals(CcgConfirmState.CANCEL)) {
                throw new MsgException("정산대기인 정산건은 취소 할 수 없습니다.");
            }

            calculateGroup.setCcgConfirmState(ccgConfirmState_new);
            calculateGroup.setCcgConfirmDate(LocalDate.now());
            calculateGroup.setCcgConfirmTime(LocalTime.now());
            calculateGroup.setCcgConfirmBy(sessionInfo.getId());
            calculateGroup.setCcgMemo(paramMap.getString("ccgMemo"));
            calculateGroupRepo.save(calculateGroup);
        }
    }

    /** 마감상태 수정 */
    @Transactional
    public void updateCloseState(ParamMap paramMap) {
        CalculateGroup calculateGroup = calculateGroupRepo.findById(paramMap.getInt("ccgIdx")).get();
        CcgCloseState ccgCloseState_ori = calculateGroup.getCcgCloseState();
        CcgCloseState ccgCloseState_new = CcgCloseState.valueOf(paramMap.getString("ccgCloseState"));

        // 정상 미확정건 마감 처리 불가
        if (!calculateGroup.getCcgConfirmState().equals(CcgConfirmState.COMPLETE)) {
            throw new MsgException("정산미확정정 건으로 마감 처리 할 수 없습니다.");
        }

        // 해당 정산건 마감완료인데 마감완료요청시 불가
        if (ccgCloseState_ori.equals(CcgCloseState.COMPLETE) && ccgCloseState_new.equals(CcgCloseState.COMPLETE)) {
            throw new MsgException("이미 마감 완료된 정산건입니다.");
        }

        // 해당 정산건 마감취소인데 마감취소요청시 불가
        if (ccgCloseState_ori.equals(CcgCloseState.CANCEL) && ccgCloseState_new.equals(CcgCloseState.CANCEL)) {
            throw new MsgException("이미 마감 완료된 정산건입니다.");
        }

        // 해당 정산건 마감취대기인데인데 마감취소요청시 불가
        if (ccgCloseState_ori.equals(CcgCloseState.WAIT) && ccgCloseState_new.equals(CcgCloseState.CANCEL)) {
            throw new MsgException("마감 완료된 정산건만 마감 취소 할 수 있습니다.");
        }

        calculateGroup.setCcgCloseTime(LocalTime.now());
        calculateGroup.setCcgCloseDate(LocalDate.now());
        calculateGroup.setCcgCloseBy(sessionInfo.getId());
        calculateGroup.setCcgCloseState(ccgCloseState_new);
        calculateGroup.setCcgCloseMemo(paramMap.getString("ccgCloseMemo"));
        calculateGroupRepo.save(calculateGroup);
    }

    /** 마감상태 여러건 수정 */
    @Transactional
    public void updateCloseStateArr(ParamMap paramMap) {
        String[] ccgIdx = paramMap.getString("ccgIdx").split(",");

        for (int i = 0; i < ccgIdx.length; i++) {
            CalculateGroup calculateGroup = calculateGroupRepo.findById(Integer.parseInt(ccgIdx[i])).get();
            CcgCloseState ccgCloseState_ori = calculateGroup.getCcgCloseState();
            CcgCloseState ccgCloseState_new = CcgCloseState.valueOf(paramMap.getString("ccgCloseState"));

            // 정상 미확정건 마감 처리 불가
            if (!ccgCloseState_ori.equals(CcgConfirmState.COMPLETE)) {
                throw new MsgException("정산미확정정 건으로 마감 처리 할 수 없습니다.");
            }

            // 해당 정산건 마감완료인데 마감완료요청시 불가
            if (ccgCloseState_ori.equals(CcgCloseState.COMPLETE) && ccgCloseState_new.equals(CcgCloseState.COMPLETE)) {
                throw new MsgException("이미 마감 완료된 정산건입니다.");
            }

            // 해당 정산건 마감취소인데 마감취소요청시 불가
            if (ccgCloseState_ori.equals(CcgCloseState.CANCEL) && ccgCloseState_new.equals(CcgCloseState.CANCEL)) {
                throw new MsgException("이미 마감 완료된 정산건입니다.");
            }

            // 해당 정산건 마감취대기인데인데 마감취소요청시 불가
            if (ccgCloseState_ori.equals(CcgCloseState.WAIT) && ccgCloseState_new.equals(CcgCloseState.CANCEL)) {
                throw new MsgException("마감 완료된 정산건만 마감 취소 할 수 있습니다.");
            }

            calculateGroup.setCcgCloseTime(LocalTime.now());
            calculateGroup.setCcgCloseDate(LocalDate.now());
            calculateGroup.setCcgCloseBy(sessionInfo.getId());
            calculateGroup.setCcgCloseState(ccgCloseState_new);
            calculateGroup.setCcgCloseMemo(paramMap.getString("ccgCloseMemo"));
            calculateGroupRepo.save(calculateGroup);
        }
    }

    /** 매출 정산 처리 완료 */
    @Transactional
    public void updateConfirmStateSales(ParamMap paramMap) {
        String[] ogIdx = paramMap.getString("ogIdx").split(",");
        for (int i = 0; i < ogIdx.length; i++) {
            OrderGs orderGs = ordersGsRepo.findById(Long.parseLong(ogIdx[i])).get();
            OgCalConfirmState ogConfirmState_ori = orderGs.getOgCalConfirmState();
            OgCalConfirmState ogConfirmState_new = OgCalConfirmState.valueOf(paramMap.getString("ogCalConfirmState"));

            // 해당 정산건 마감 완료시 제한
            if (ogConfirmState_ori.equals(CcgCloseState.COMPLETE)) {
                throw new MsgException("해당정산건은 마감되어 상태변경이 불가능합니다.");
            }

            // 정산확정인 상태에서 정산확정 처리가 들어올시
            if (ogConfirmState_ori.equals(CcgConfirmState.COMPLETE) && ogConfirmState_new.equals(CcgConfirmState.COMPLETE)) {
                throw new MsgException("이미 정산 확정상태입니다.");
            }

            // 정산취소인 상태에서 정산취소 처리가 들어올시
            if (ogConfirmState_ori.equals(CcgConfirmState.CANCEL) && ogConfirmState_new.equals(CcgConfirmState.CANCEL)) {
                throw new MsgException("이미 정산 확정취소상태입니다.");
            }

            //정산대기 상태에서 정산취소 처리가 들어올시
            if (ogConfirmState_ori.equals(CcgConfirmState.WAIT) && ogConfirmState_new.equals(CcgConfirmState.CANCEL)) {
                throw new MsgException("정산대기인 정산건은 취소 할 수 없습니다.");
            }

            orderGs.setOgCalConfirmState(ogConfirmState_new);
            orderGs.setOgCalConfirmMemo(paramMap.getString("ogCalConfirmMemo"));
            orderGs.setOgCalConfirmBy(sessionInfo.getId());
            orderGs.setOgCalConfirmDate(LocalDate.now());
            ordersGsRepo.save(orderGs);
        }
    }

    /** 매출정산 마감 처리 */
    @Transactional
    public void updateCloseStateSales(ParamMap paramMap) {
        String[] ogIdx = paramMap.getString("ogIdx").split(",");
        for (int i = 0; i < ogIdx.length; i++) {
            OrderGs orderGs = ordersGsRepo.findById(Long.parseLong(ogIdx[i])).get();
            OgCalCloseState ogCalCloseState_ori = orderGs.getOgCalCloseState();
            OgCalCloseState ogCalCloseState_new = OgCalCloseState.valueOf(paramMap.getString("ogCalCloseState"));

            // 정상 미확정건 마감 처리 불가
            if (!ogCalCloseState_ori.equals(OgCalCloseState.COMPLETE)) {
                throw new MsgException("정산미확정정 건으로 마감 처리 할 수 없습니다.");
            }

            // 해당 정산건 마감완료인데 마감완료요청시 불가
            if (ogCalCloseState_ori.equals(OgCalCloseState.COMPLETE) && ogCalCloseState_new.equals(OgCalCloseState.COMPLETE)) {
                throw new MsgException("이미 마감 완료된 정산건입니다.");
            }

            // 해당 정산건 마감취소인데 마감취소요청시 불가
            if (ogCalCloseState_ori.equals(OgCalCloseState.CANCEL) && ogCalCloseState_new.equals(OgCalCloseState.CANCEL)) {
                throw new MsgException("이미 마감 완료된 정산건입니다.");
            }

            // 해당 정산건 마감취대기인데인데 마감취소요청시 불가
            if (ogCalCloseState_ori.equals(OgCalCloseState.WAIT) && ogCalCloseState_new.equals(OgCalCloseState.CANCEL)) {
                throw new MsgException("마감 완료된 정산건만 마감 취소 할 수 있습니다.");
            }

            orderGs.setOgCalCloseState(ogCalCloseState_new);
            orderGs.setOgCalCloseBy(sessionInfo.getId());
            orderGs.setOgCalCloseDate(LocalDate.now());
            orderGs.setOgCalCloseMemo(paramMap.getString("ogCalCloseMemo"));
            ordersGsRepo.save(orderGs);
        }
    }
}






// 정산 조정
//
//        int ogAdjustCost = 0;
//
//        List<Long> ccaIdx = paramMap.getListLong("ccaIdx");
//        List<String> ccaContent = paramMap.getList("ccaContent");
//        List<String> ccaReason = paramMap.getList("ccaReason");
//        List<Long> ccaCost = paramMap.getListLong("ccaCost");
//        for (int j = 0; j < ccaIdx.size(); j++) {
//        CalculateAdjust calculateAdjust = CalculateAdjust
//        .builder()
//        .orderGs(orderGs)
//        .ccaIdx(ccaIdx.get(i).intValue())
//        .ccaContent(ccaContent.get(i))
//        .ccaReason(ccaReason.get(i))
//        .ccaCost(ccaCost.get(i).intValue())
//        .build();
//
//        ccgAdjustCost += ccaCost.get(i);
//
//        calculateAdjustRepo.save(calculateAdjust);

