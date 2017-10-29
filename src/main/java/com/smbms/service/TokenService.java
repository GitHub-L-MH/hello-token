package com.smbms.service;

import com.smbms.pojo.User;

import java.security.PrivateKey;

/**
 * Token端口
 */
public interface TokenService {
    /**
     * 会话有效时间
     */
    public final  static int SESSION_TIMEOUT = 2*60*60;

    /**
     * 创建token字符串
     * @param userAgent
     * @param user
     * @return
     */
    public String generateToken(String userAgent, User user);

    /**
     * 保存到redis中
     * @param token
     * @param user
     */
    public void saveToken(String token,User user);

    /**
     * 获取 User
     * @param token
     * @return
     */
    public User load(String token);

}
