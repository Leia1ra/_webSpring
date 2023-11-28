package com.multi.campus.controller;

import com.multi.campus.service.UsersService;
import com.multi.campus.vo.UsersVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class UsersController {
    // 클래스, 인터페이스를 객체를 생성해주는 어노테이션
    // @Autowired, @Inject
    @Autowired
    UsersService service;

    // 회원가입 폼
    @GetMapping("/users/userForm")
    public String userForm(){
        return "users/usersForm";
    }

    @GetMapping("/users/idCheck")
    public ModelAndView idCheck(String userid){
        System.out.println("userid -> " + userid);

        // DB 조회
        // SELECT count(userid) FROM users WHERE userid=?
        int result = service.idCheck(userid);

        ModelAndView mav = new ModelAndView();
        mav.addObject("result1", result);
        mav.addObject("userid1", userid);
        mav.setViewName("users/idCheck");

        return mav;
    }

    /*@PostMapping("/users/userFromOk")*/
    @RequestMapping(value = "/users/userFormOk", method = RequestMethod.POST)
    public ModelAndView usersFormOk(UsersVO vo){
        ModelAndView mav = new ModelAndView();
        try{
            int result = service.usersInsert(vo);
            if(result>0){/*성공*/
                mav.setViewName("redirect:login");
            } else {/*실패*/ //jsp -> history.back()
                mav.setViewName("users/usersFormResult");
            }
        }catch (Exception e){
            e.printStackTrace();
            mav.setViewName("users/usersFormResult");
        }

        return mav;
    }

    @RequestMapping(value = "/users/login", method = RequestMethod.GET)
    public String login(){
        return "users/login";
    }

    @PostMapping("/users/loginOk")
    public ModelAndView loginOk(String userid, String userpwd, HttpSession session){
        ModelAndView mav= new ModelAndView();
        // 인자로 login.jsp의 userid, userpwd의 input된 값을 받아옴
        UsersVO logvo = service.loginSelect(userid, userpwd);

        if(logvo != null){ // 로그인 성공
            session.setAttribute("logId", logvo.getUserid());
            session.setAttribute("logName", logvo.getUsername());
            session.setAttribute("logStatus", "Y");
            mav.setViewName("redirect:/");
        } else { // 로그인 실패
            // 같은 클래스라면 그냥 login만 써도 됨
            mav.setViewName("redirect:login");
        }
        return mav;
    }

    // 로그아웃
    @GetMapping("/users/logout")
    public ModelAndView logout(HttpSession session){
        session.invalidate();

        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/");
        return mav;
    }
}
