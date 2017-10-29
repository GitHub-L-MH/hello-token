package com.smbms.controller;

import com.smbms.pojo.User;
import com.smbms.service.TokenService;
import com.smbms.service.UserService;
import com.smbms.vo.BaseResult;
import com.smbms.vo.HTTPStatus;
import com.smbms.vo.TokenResultVo;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;
    @Autowired
    private TokenService tokenService;

    @RequestMapping(value = "/getUserAgent.action",method = RequestMethod.GET)
    @ResponseBody
    public BaseResult getUserAgent(HttpServletRequest request){
        String userAgent = request.getHeader("User-Agent");
        UserAgent agent = UserAgent.parseUserAgentString(userAgent);
        //获取浏览器对象
        Browser browser = agent.getBrowser();
        //获取操作系统对象
        OperatingSystem operatingSystem = agent.getOperatingSystem();


        Map map = new HashMap();
        map.put("user-agent",userAgent);
        map.put("browser",browser);
        map.put("os",operatingSystem);
        map.put("browserName",browser.getName());
        map.put("osName",operatingSystem.getName());
        return buildSuccessResultInfo(map);
    }

    @RequestMapping(value = "/login.action",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult login(HttpServletRequest request,
                            @RequestParam(value = "userCode") String userCode,
                            @RequestParam(value = "userPassword") String userPassword){
        if (userCode != null && userPassword != null){
            Map<String,Object> map = new HashMap<>();
            map.put("userCode",userCode.trim());
            map.put("userPassword",userPassword.trim());
            User user = userService.getUserByMap(map);
            if (user != null){//用户OK了
                String userAgent = request.getHeader("User-Agent");
                String token = tokenService.generateToken(userAgent, user);
                TokenResultVo tokenResultVo = new TokenResultVo();
                tokenResultVo.setToken(token);
                tokenResultVo.setGenTime(System.currentTimeMillis());
                tokenResultVo.setExpTime(System.currentTimeMillis()+TokenService.SESSION_TIMEOUT * 1000);
                tokenService.saveToken(token,user);
                return buildSuccessResultInfo(tokenResultVo);
            } else {
                return buildFailedResultInfo("900","用户名或密码不正确",null);
            }
        }
        return buildFailedResultInfo("400","用户名或密码不能为空",null);
    }

    @RequestMapping("/login.html")
    public String login(){
        return "login";
    }

    @RequestMapping("/main.html")
    public String viewMain(){
        return "main";
    }
    @RequestMapping(value = "/admin/get/{id}",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult login(HttpServletRequest request,
                            @PathVariable("id") Integer id){
        User user = userService.getUserById(id);
        return buildSuccessResultInfo(user);
    }
}
