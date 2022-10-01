package com.newper.controller.view;

import com.newper.component.KakaoLogin;
import com.newper.component.NaverLogin;
import com.newper.component.NiceApi;
import com.newper.component.ShopSession;
import com.newper.constant.SaType;
import com.newper.dto.ParamMap;
import com.newper.entity.AesEncrypt;
import com.newper.service.CustomerService;
import com.newper.service.SelfAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/customer/")
@RequiredArgsConstructor
public class CustomerController {

    @Autowired
    ShopSession shopSession;

    private final NiceApi niceApi;
    private final NaverLogin naverLogin;
    private final KakaoLogin kakaoLogin;
    private final SelfAuthService selfAuthService;
    private final CustomerService customerService;

    /** auth - 회원가입 안내 */
    @GetMapping(value = "joinWelcome")
    public ModelAndView joinWelcome(){
        ModelAndView mav = new ModelAndView("customer/joinWelcome");
        return mav;
    }
    /** 아이디/비밀번호 찾기 */
    @GetMapping(value = "findCustomer")
    public ModelAndView findCustomer(){
        ModelAndView mav = new ModelAndView("customer/findCustomer");
        return mav;
    }
    /** 아이디/비밀번호 찾기 각 스텝 load*/
    @PostMapping(value = "findCustomer/step/{type}.load")
    public ModelAndView findCustomerStep(@PathVariable("type") String type, ParamMap paramMap){
        ModelAndView mav = new ModelAndView("customer/findCustomer_step :: " + type);
        if (type.equals("findComplete")) {
            if (paramMap.containsKey("cuPw")) {
                customerService.resetPw(paramMap);
                mav.addObject("pwReset", true);
            } else {
                mav.addObject("pwReset", false);
            }
        }
        if (paramMap.containsKey("CU_ID")) {
            mav.addObject("data", paramMap.getMap());
        }
        return mav;
    }

    /** auth - 회원가입 정보입력 */
    @GetMapping(value = "join")
    public ModelAndView join(){
        ModelAndView mav = new ModelAndView("customer/join");
        return mav;
    }
    /** 나이스 본인 인증 팝업 띄우기(회원가입)*/
    @GetMapping(value = "auth/request/{type}")
    public ModelAndView authRequest(@PathVariable("type") String type, HttpServletRequest request){
        ModelAndView mav = new ModelAndView("customer/auth_request");
        // nice요청생성
        String callback = (request.isSecure()?"https://":"http://") + request.getServerName()+":"+request.getServerPort()+"/customer/auth/response/"+type;
        Long saIdx = selfAuthService.insertSa(SaType.valueOf(type.toUpperCase()));
        Map<String, Object> niceReq = niceApi.getNiceSendData(callback, saIdx.toString());
        mav.addObject("data", niceReq);
        // 요청정보 db update
        selfAuthService.updateSaReq(saIdx, niceReq);
        return mav;
    }
    
    /** nice 본인인증 응답 데이터 받는 곳*/
    @GetMapping(value = "auth/response/{type}")
    public ModelAndView authResponse(@PathVariable("type") String type, ParamMap paramMap) {
        ModelAndView mav = new ModelAndView("customer/auth_response");
        // nice 응답 데이터
        Map<String, Object> niceReturn = niceApi.getNiceReturn(paramMap);
        mav.addObject("nice", niceReturn);
        // 요청정보 db update
        Long saIdx = selfAuthService.updateSaRes(niceReturn);
        AesEncrypt aes = new AesEncrypt();
        mav.addObject("saIdx", aes.encryptRandom(saIdx.toString()));
        mav.addObject("type", type);
        return mav;
    }
    
    /** auth - 회원가입 완료 */
    @GetMapping(value = "joinComplete")
    public ModelAndView joinComplete() {
        ModelAndView mav = new ModelAndView("customer/joinComplete");
        return mav;
    }

    /** 네이버 로그인 팝업*/
    @GetMapping(value = "auth/naver/request")
    public ModelAndView naverRequest(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("customer/auth_naver_request");
        // naver 요청 정보
        String callback = (request.isSecure()?"https://":"http://") + request.getServerName()+":"+request.getServerPort();
        Map<String, Object> req = naverLogin.request(callback);
        mav.addObject("naverReq", req);
        return mav;
    }

    /** 네이버 로그인 > 네이버 회원 profile 받아오기*/
    @GetMapping(value = "auth/naver/response")
    public ModelAndView naverAccess(ParamMap paramMap, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("customer/auth_naver_response");
        // naver 응답 데이터
        String callback = (request.isSecure()?"https://":"http://") + request.getServerName()+":"+request.getServerPort();
        Map<String, Object> profile = naverLogin.getProfile(paramMap, callback);
        System.out.println("profile = " + profile);
        // 기존 회원과 ci값 대조 후 로그인/회원가입 처리

        return mav;
    }

    /** 카카오 로그인 팝업*/
    @GetMapping(value = "auth/kakao/request")
    public ModelAndView kakaoRequest(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("customer/auth_kakao_request");
        // kakao 요청 정보
        String callback = (request.isSecure()?"https://":"http://") + request.getServerName()+":"+request.getServerPort();
        Map<String, Object> req = kakaoLogin.request(callback);
        mav.addObject("req",req);
        return mav;
    }

    /** 카카오 로그인 응답 */
    @GetMapping(value = "auth/kakao/response")
    public ModelAndView kakaoResponse(ParamMap paramMap, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("customer/auth_kakao_response");

        String callback = (request.isSecure()?"https://":"http://") + request.getServerName()+":"+request.getServerPort();
        Map<String, Object> res = kakaoLogin.getProfile(paramMap, callback);
        System.out.println("res = " + res);
        return mav;
    }
}
