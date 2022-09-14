package com.newper.controller.rest;

import com.newper.component.MenuList;
import com.newper.component.SessionInfo;
import com.newper.constant.MenuType;
import com.newper.constant.UState;
import com.newper.controller.NoLogin;
import com.newper.dto.ParamMap;
import com.newper.dto.ReturnMap;
import com.newper.entity.Menu;
import com.newper.entity.User;
import com.newper.mapper.MenuMapper;
import com.newper.mapper.UserMapper;
import com.newper.repository.MenuRepo;
import com.newper.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class MainRestController {

    private final MenuRepo menuRepo;
    private final UserRepo userRepo;
    private final UserMapper userMapper;
    private final MenuMapper menuMapper;
    @Autowired
    private SessionInfo sessionInfo;

    /** menu list update*/
    @PostConstruct
    @GetMapping("updateMenu.ajax")
    public void updateMenu(){
        List<Menu> menuList = menuRepo.findMenuAll(MenuType.SHOP);

        MenuList.menus = menuList;
    }
    /** 로그인 처리*/
    @NoLogin
    @PostMapping(value = "login.ajax")
    public ReturnMap login(ParamMap paramMap){
        ReturnMap rm = new ReturnMap();

        String pw = paramMap.getString("pw");
        Map<String, Object> userMap = userMapper.selectUserLogin(paramMap.getString("id"), pw);
        if(userMap == null || userMap.isEmpty()){
            rm.setMessage("존재하지 않는 ID입니다");
        }else{
            String uState=userMap.get("U_STATE")+"";
            if(uState.equals(UState.NORMAL.name())){
                if ("1".equals(userMap.get("PW_CHECK")+"")) {
                    sessionInfo.login(userMap);

                    rm.setLocation(menuMapper.selectSubMenuUrlByAuth(sessionInfo.getAuthIdx()+""));
                }else{
                    rm.setMessage("잘못된 비밀번호입니다");
                }
            }else {
                rm.setMessage("사용 중지된 아이디 입니다");
            }
        }

        return rm;
    }

    /** 로그인 pass */
    @NoLogin
    @GetMapping(value = "login.pass")
    public ModelAndView loginPass(ParamMap paramMap){
        ModelAndView mav = new ModelAndView("main/home");

        User user  = userRepo.findUserByuId(paramMap.getString("id"));
        Map<String,Object> userMap = new HashMap<>();
        userMap.put("U_IDX", user.getUIdx());
        userMap.put("U_ID", user.getUId());
        userMap.put("U_AUTH_IDX", user.getAuth().getAuthIdx());
        sessionInfo.login(userMap);

        return mav;
    }
}
