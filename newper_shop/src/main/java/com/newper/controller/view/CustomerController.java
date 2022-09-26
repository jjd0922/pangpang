package com.newper.controller.view;

import com.newper.component.NiceApi;
import com.newper.component.ShopSession;
import com.newper.dto.ParamMap;
import com.newper.entity.AesEncrypt;
import com.newper.repository.SelfAuthRepo;
import com.newper.service.SelfAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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
    private final SelfAuthService selfAuthService;
    private final SelfAuthRepo selfAuthRepo;

    /** auth - 회원가입 안내 */
    @GetMapping(value = "joinWelcome")
    public ModelAndView joinWelcome(){
        ModelAndView mav = new ModelAndView("customer/joinWelcome");
        return mav;
    }

    /** auth - 회원가입 정보입력 */
    @GetMapping(value = "join")
    public ModelAndView join(){
        ModelAndView mav = new ModelAndView("customer/join");

        return mav;
    }
    /** 나이스 본인 인증 팝업 띄우기*/
    @GetMapping(value = "auth/request")
    public ModelAndView authRequest(HttpServletRequest request){
        ModelAndView mav = new ModelAndView("customer/auth_request");
        // nice요청생성
        String callback = (request.isSecure()?"https://":"http://") + request.getServerName()+":"+request.getServerPort();
        Map<String, Object> niceReq = niceApi.getNiceSendData(callback);
        mav.addObject("data", niceReq);
        // 요청정보 db insert
        Long saIdx = selfAuthService.saveSelfAuth(niceReq);
        AesEncrypt ae = new AesEncrypt();
        shopSession.setSaIdx(ae.encryptRandom(saIdx.toString()));
        return mav;
    }
    
    /** nice 본인인증 응답 데이터 받는 곳*/
    @GetMapping(value = "auth/response")
    public ModelAndView authResponse(ParamMap paramMap) {
        ModelAndView mav = new ModelAndView("customer/auth_response");
        // nice 응답 데이터
        Map<String, Object> niceReturn = niceApi.getNiceReturn(paramMap);
        mav.addObject("nice", niceReturn);
        // 요청정보 db update
        selfAuthService.updateSelfAuth(niceReturn);
        return mav;
    }
    
    /** auth - 회원가입 완료 */
    @GetMapping(value = "joinComplete")
    public ModelAndView joinComplete() {
        ModelAndView mav = new ModelAndView("customer/joinComplete");
        return mav;
    }
}
