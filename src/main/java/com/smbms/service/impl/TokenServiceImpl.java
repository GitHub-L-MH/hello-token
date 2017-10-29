package com.smbms.service.impl;

import com.alibaba.fastjson.JSON;
import com.smbms.pojo.User;
import com.smbms.service.TokenService;
import com.smbms.tools.RedisAPI;
import com.smbms.tools.SecurityUtil;
import com.smbms.tools.UserAgentUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.ToLongBiFunction;

@Service("tokenService")
public class TokenServiceImpl implements TokenService {

    @Autowired(required = false)
    private RedisAPI redisAPI;

    @Override
    public String generateToken(String userAgent, User user) {
        //0，前缀
        StringBuilder tokenSb = new StringBuilder("token:");
        //1. 设备标识
        String device = UserAgentUtil.CheckAgent(userAgent);
        tokenSb.append(device + "-");
        //2. userCode加密
        tokenSb.append(SecurityUtil.md5Hex3(user.getUserCode()) + "-");
        //3. id
        tokenSb.append(user.getId() + "-");
        //4. createDate
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String createDate = sdf.format(new Date());
        tokenSb.append(createDate + "-");
        //5. 设备标识 userAgent 加密后，截取的前6为字符
        String random = SecurityUtil.md5Hex3(userAgent).substring(0, 6);
        tokenSb.append(random);
        return tokenSb.toString();
    }

    @Override
    public void saveToken(String token, User user) {
        String jsonString = JSON.toJSONString(user);
        redisAPI.set(token,TokenService.SESSION_TIMEOUT,jsonString);
    }

    @Override
    public User load(String token) {
        User user = JSON.parseObject(redisAPI.get(token), User.class);
        return user;
    }

}
