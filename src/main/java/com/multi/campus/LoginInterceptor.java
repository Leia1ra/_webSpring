package com.multi.campus;

import org.springframework.lang.Nullable;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

// Interceptor 클래스는 HandlerInterceptor클래스를 상속받아야 한다.
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Override // 컨트롤러가 호출되기 전에 실행하는 메소드
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 로그인 유뮤 확인하여 로그인 된 경우 호출한 매핑주소로 이동하면 되고,
        // 로그인 안된 경우 로그인 폼으로 이동하도록 실행을 변경한다.
        HttpSession session = request.getSession();
        String logStatus = (String)session.getAttribute("logStatus");
        if(logStatus==null || !logStatus.equals("Y")){
            // 로그인이 안된경우 -> 가던길을 멈추고 로그인 페이지로 이동시킴
            response.sendRedirect(request.getContextPath()+"/users/login");

            // 반환형이 false면 새로운 주소로 이동한다.
            return false;
        }

        // 반환형이 true면 원래 매핑으로 지속
        return true;
        // return super.preHandle(request, response, handler);
    }


    @Override // 컨트롤러를 실행 후 view페이지를 이동하기 전에 실행되는 메소드
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler,
                           @Nullable ModelAndView mav) throws Exception {
        super.postHandle(request, response, handler, mav);
    }


    @Override // 컨트롤러 실행 후 호출되는 메소드
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler,
                                @Nullable Exception e) throws Exception {
        super.afterCompletion(request, response, handler, e);
    }
}
