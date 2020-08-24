package com.example.demo.interruptor;

import com.example.demo.anno.Authentication;
import com.example.demo.contrlloer.Contrlloer;
import com.example.demo.entity.X;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @Author: XF
 * @Date: 2020/6/5 11:46
 */
public class AuthenticationInterruptor implements HandlerInterceptor {
    @Autowired
    X x;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("AuthenticationInterruptor : preHandle");
        if(!(handler instanceof HandlerMethod)){
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod)handler;
        Method method = handlerMethod.getMethod();
        Contrlloer contrlloer = (Contrlloer) handlerMethod.getBean();
        String s = (String) method.invoke(contrlloer);
        Authentication authentication = handlerMethod.getMethodAnnotation(Authentication.class);
        /*if(authentication != null){
            String perm = authentication.authUrl();
            response.setCharacterEncoding("UTF-8");//这句话的意思，是告诉servlet用UTF-8转码，而不是用默认的ISO8859
            response.setContentType("text/html;charset=utf-8");//这句话的意思，是让浏览器用utf8来解析返回的数据
            PrintWriter outputStream = response.getWriter();
            Map<String,Object> map = new HashMap<>();
            map.put("msg","无权限!");
            map.put("code","500");
            outputStream.println(map);
            outputStream.flush();
            outputStream.close();
            return false;
        }*/
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("AuthenticationInterruptor : postHandle");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("AuthenticationInterruptor : afterCompletion");
    }
}
