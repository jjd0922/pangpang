package com.newper.service;

import com.newper.dto.ParamMap;
import com.newper.entity.Notice;
import com.newper.exception.MsgException;
import com.newper.repository.NoticeRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final NoticeRepo noticeRepo;

    /** 공지사항 등록/수정 */
    @Transactional
    public void saveNotice(ParamMap paramMap) {
        Notice noticeParam = paramMap.mapParam(Notice.class);
        if(paramMap.get("ntIdx") == null){
            noticeRepo.saveAndFlush(noticeParam);
        }else{
            Notice notice = noticeRepo.findById(paramMap.getLong("ntIdx")).orElseThrow(()-> new MsgException("존재하지 않는 공지사항입니다."));
            notice.setNtTitle(noticeParam.getNtTitle());
            notice.setNtDisplay(noticeParam.getNtDisplay());
            notice.setNtContent(noticeParam.getNtContent());
            notice.setNtTop(noticeParam.getNtTop());
        }
    }
}
