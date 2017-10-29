package com.smbms.interceptor;

import com.smbms.pojo.User;
import com.smbms.service.TokenService;
import com.smbms.tools.RedisAPI;
import com.smbms.tools.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserInterceptor implements HandlerInterceptor {
    @Autowired(required = false)
    private RedisAPI redisAPI;

    @Autowired(required = false)
    private TokenService tokenService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        String userAgent = request.getHeader("User-Agent");
        if (redisAPI.exist(token)) {//如果存在
            //获取token的剩余时间////
            Long ttl = redisAPI.ttl(token);
            if (ttl < 10){
                throw new Exception("会话过期！");
            } else {
                String localDevice6 = SecurityUtil.md5Hex3(userAgent).substring(0, 6);
                String redisDevice6 = token.split("-")[4];
                if (localDevice6.equals(redisDevice6)){
                    User user = tokenService.load(token);
                    String localuserCode = token.split("-")[1];
                    String redisUserCode = SecurityUtil.md5Hex3(user.getUserCode());
                    if (localuserCode.equals(redisUserCode)){
                        return true;
                    }
                    return false;
                } else {
                    throw new Exception("存在异常登录，小心被盗");
                }
            }
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
