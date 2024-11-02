package com.pangpang.controller.rest;

import com.pangpang.dto.ReturnMap;
import com.pangpang.entity.*;
import com.pangpang.exception.MsgException;
import com.pangpang.mapper.BoardMapper;
import com.pangpang.mapper.CustomerMapper;
import com.pangpang.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@RequestMapping(value = "/api/board/")
@RestController
@RequiredArgsConstructor
public class ApiBoardController {

    private final BoardRepo boardRepo;
    private final CategoryRepo categoryRepo;
    private final CustomerRepo customerRepo;
    private final BoardMapper boardMapper;
    private final CustomerMapper customerMapper;
    private final BoardRequestRepo boardRequestRepo;
    private final ReviewRepo reviewRepo;

    @PostMapping("register.ajax")
    public ReturnMap boardRegister(@RequestBody Map<String,Object> map) {
        ReturnMap rm = new ReturnMap();
        if(map.isEmpty()){
            throw new MsgException("데이터 없음");
        }
        Board board = null;
        Customer customer = customerRepo.getReferenceById(Long.parseLong(map.get("CU_IDX").toString()));
        Category category = categoryRepo.getReferenceById(Long.parseLong(map.get("CATE_IDX").toString()));
        if(customer == null){
            throw new MsgException("회원정보가 없습니다.");
        }
        if(category == null){
            throw new MsgException("지역정보가 잘못 입력되었습니다.");
        }
        LocalDate startDate = LocalDate.parse(map.get("BD_START_DATE").toString(), DateTimeFormatter.ISO_DATE);
        LocalDate endDate = LocalDate.parse(map.get("BD_END_DATE").toString(), DateTimeFormatter.ISO_DATE);
        if(map.containsKey("BD_IDX")){
            if(!map.get("BD_IDX").equals("")){
                board = boardRepo.findById(Long.parseLong(map.get("BD_IDX").toString())).get();
                if(board.getCustomer().getCuIdx() != customer.getCuIdx()){
                    throw new MsgException("게시판 수정 권한이 없습니다.");
                }
                board.setBdTitle(map.get("BD_TITLE").toString());
                board.setBdLink(map.get("BD_LINK").toString());
                board.setCategory(category);
                board.setBdCnt(Integer.parseInt(map.get("BD_CNT").toString()));
                board.setBdStartDate(startDate);
                board.setBdEndDate(endDate);
                board.setBdRate(Integer.parseInt(map.get("BD_RATE").toString()));
                board.setBdContent(map.get("BD_CONTENT").toString());
                boardRepo.save(board);
            }
        }else{
            board = Board.builder()
                         .bdTitle(map.get("BD_TITLE").toString())
                         .bdLink(map.get("BD_LINK").toString())
                    .category(category)
                    .customer(customer)
                    .bdCnt(Integer.parseInt(map.get("BD_CNT").toString()))
                    .bdStartDate(startDate)
                    .bdEndDate(endDate)
                    .bdRate(Integer.parseInt(map.get("BD_RATE").toString()))
                    .bdContent(map.get("BD_CONTENT").toString())
                    .bdDisplay(true)
                    .bdState("ING")
                    .build();
                boardRepo.save(board);

                BoardRequest boardRequest = BoardRequest.builder()
                        .board(board)
                        .customer(customer)
                        .brState("ACCEPT")
                        .build();
                boardRequestRepo.save(boardRequest);
        }
        if(board == null){
            throw new MsgException("데이터 생성 불가");
        }
        rm.put("bd_idx",board.getBdIdx());
        return rm;
    }

    @GetMapping("search-list.ajax")
    public ReturnMap boardSearchList(@RequestParam(value = "CATE_IDX",required = false) String CATE_IDX,
                                     @RequestParam(value = "CU_IDX",required = false) String CU_IDX,
                                     @RequestParam(value = "BD_STATE",required = false) String BD_STATE,
                                     @RequestParam(value = "BD_RATE",required = false) String BD_RATE,
                                     @RequestParam(value = "BD_START_DATE",required = false) String BD_START_DATE,
                                     @RequestParam(value = "LIMIT",required = false) String LIMIT,
                                     @RequestParam(value = "PAGE",required = false) String PAGE
                                    ){
        ReturnMap rm = new ReturnMap();
        int limit = Integer.parseInt(LIMIT);
        if(PAGE.equals("0")){
            throw new MsgException("PAGE는 1 이상 숫자만 입력 가능합니다.");
        }
        int limit_page = (Integer.parseInt(PAGE)-1)*Integer.parseInt(LIMIT);
        List<Map<String, Object>> list = boardMapper.selectBoardList(CATE_IDX,CU_IDX,BD_STATE,BD_RATE,BD_START_DATE,limit,limit_page);
        rm.put("list",list);
        return rm;
    }
    @GetMapping("detail.ajax")
    public ReturnMap boardDetail(@RequestParam(value = "BD_IDX",required = false) String BD_IDX,
                                     @RequestParam(value = "CU_IDX",required = false) String CU_IDX
    ){
        ReturnMap rm = new ReturnMap();
        if(BD_IDX.equals("") || BD_IDX == null){
            throw new MsgException("게시판 idx 입력바랍니다.");
        }
        if(CU_IDX.equals("") || CU_IDX == null){
            throw new MsgException("로그인 회원 idx 입력바랍니다.");
        }
        Map<String, Object> map = boardMapper.selectBoardDetail(BD_IDX);
        Map<String, Object> LEADER_INFO =  customerMapper.selectCustomerInfo(CU_IDX);

        map.put("LEADER_INFO",LEADER_INFO);
        rm.put("list",map);
        return rm;
    }

    @GetMapping("btnState.ajax")
    public ReturnMap boardBtnState(@RequestParam(value = "BD_IDX",required = false) String BD_IDX,
                                 @RequestParam(value = "CU_IDX",required = false) String CU_IDX
    ){
        ReturnMap rm = new ReturnMap();
        Board board = boardRepo.findById(Long.parseLong(BD_IDX)).get();
        Customer customer = customerRepo.findById(Long.parseLong(CU_IDX)).get();
        BoardRequest boardRequest = boardRequestRepo.findByBoardAndCustomer(board,customer);
        Review review = reviewRepo.findByBoardAndCustomer(board,customer);
        String btnState = "";
        String cuType = "NORMAL";
        if(board.getCustomer().getCuIdx() == Long.parseLong(CU_IDX)){
            cuType = "LEADER";
            if(board.getBdState().equals("ING")){
                btnState = "모집 마감하기";
            }else if(board.getBdState().equals("COMPLETE")){
                btnState = "임장 완료하기";
            }else if(board.getBdState().equals("END") && review == null){
                btnState = "임장 후기 작성하기";
            }else if(board.getBdState().equals("COMPLETE") && review != null){
                btnState = "임장 후기 작성완료";
            }
        }else if(boardRequest != null){
           if(boardRequest.getBrState().equals("ACCEPT")){
               cuType = "LAD";
               if(board.getBdState().equals("ING")){
                   btnState = "채팅방 링크로 이동하기";
               }else if(board.getBdState().equals("COMPLETE") && review == null){
                   btnState = "임장 후기 작성하기";
               }else if(board.getBdState().equals("COMPLETE") && review != null){
                   btnState = "임장 후기 작성완료";
               }
           }else{
               if(board.getBdState().equals("ING")){
                   btnState = "동행 요청 완료";
               }else if(!board.getBdState().equals("ING")){
                   btnState = "모집 완료";
               }
           }
        }else{
            btnState = "모임 동행 요청하기";
        }
        rm.put("cuType",cuType);
        rm.put("btnState",btnState);
        return rm;
    }
    @PostMapping("request.ajax")
    public ReturnMap boardRequst(@RequestBody Map<String,Object> map){
        ReturnMap rm = new ReturnMap();
        if(map.get("BD_IDX").equals("") || map.get("BD_IDX") == null){
            throw new MsgException("게시판 idx 입력바랍니다.");
        }
        if(map.get("CU_IDX").equals("") || map.get("CU_IDX") == null){
            throw new MsgException("회원 idx 입력바랍니다.");
        }
        Board board = boardRepo.findById(Long.parseLong(map.get("BD_IDX").toString())).get();
        Customer customer = customerRepo.findById(Long.parseLong(map.get("CU_IDX").toString())).get();
        if(board == null){
            throw new MsgException("게시판 정보가 없습니다.");
        }
        if(customer == null){
            throw new MsgException("회원정보가 없습니다.");
        }
        BoardRequest boardRequest = boardRequestRepo.findByBoardAndCustomer(board,customer);
        if(boardRequest != null){
            throw new MsgException("이미 신청상태 입니다.");
        }else{
            boardRequest = BoardRequest.builder().board(board).customer(customer).brState("REQUEST").build();
            boardRequestRepo.save(boardRequest);
        }
        rm.setMessage("요청완료");
        return rm;
    }
    @GetMapping("request-list.ajax")
    public ReturnMap boardRequstList(@RequestParam(value = "BD_IDX",required = false) String BD_IDX){
        ReturnMap rm = new ReturnMap();
        if(BD_IDX.equals("") || BD_IDX == null){
            throw new MsgException("게시판 idx 입력바랍니다.");
        }
        List<Map<String, Object>> list = boardMapper.selectBoardRequestList(BD_IDX);

        rm.put("USER_INFO",list);
        return rm;
    }
    @PostMapping("review/register.ajax")
    public ReturnMap boardReviewRegister(@RequestBody Map<String,Object> map) {
        ReturnMap rm = new ReturnMap();
        if(map.isEmpty()){
            throw new MsgException("데이터 없음");
        }
        Review review = null;
        Customer customer = customerRepo.getReferenceById(Long.parseLong(map.get("CU_IDX").toString()));
        Board board = boardRepo.getReferenceById(Long.parseLong(map.get("BD_IDX").toString()));
        if(customer == null){
            throw new MsgException("회원정보가 없습니다.");
        }
        if(board == null){
            throw new MsgException("게시판 정보가 없습니다.");
        }
        if(map.containsKey("R_IDX")){
            if(!map.get("R_IDX").equals("")){
                review = reviewRepo.findById(Long.parseLong(map.get("R_IDX").toString())).get();
                if(review.getCustomer().getCuIdx() != customer.getCuIdx()){
                    throw new MsgException("수정 권한이 없습니다.");
                }
                review.setRGoodContent(map.get("R_GOOD_CONTENT").toString());
                review.setRGoodContent(map.get("R_BAD_CONTENT").toString());
                reviewRepo.save(review);
            }
        }else{
            review = Review.builder()
                    .board(board)
                    .customer(customer)
                    .rGoodContent(map.get("R_GOOD_CONTENT").toString())
                    .rBadContent(map.get("R_BAD_CONTENT").toString())
                    .rDisplay(true)
                    .build();
            reviewRepo.save(review);

        }
        if(review == null){
            throw new MsgException("데이터 생성 불가");
        }
        rm.put("r_idx",review.getRIdx());
        return rm;
    }

    @GetMapping("review/detail.ajax")
    public ReturnMap reviewDetail(@RequestParam(value = "R_IDX",required = false) String R_IDX,
                                 @RequestParam(value = "CU_IDX",required = false) String CU_IDX
    ){
        ReturnMap rm = new ReturnMap();
        if(R_IDX.equals("") || R_IDX == null){
            throw new MsgException("리뷰 idx 입력바랍니다.");
        }
        if(CU_IDX.equals("") || CU_IDX == null){
            throw new MsgException("로그인 회원 idx 입력바랍니다.");
        }
        Map<String, Object> map = boardMapper.selectReviewDetail(R_IDX);

        rm.put("list",map);
        return rm;
    }
}
