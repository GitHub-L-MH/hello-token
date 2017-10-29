package com.smbms.vo;

import java.io.Serializable;

/**
 * 前端token结果类
 */
public class TokenResultVo implements Serializable {
    private String token;//token数据
    private long expTime;//过期时间
    private long genTime;//创建时间

    public TokenResultVo() {
    }

    public TokenResultVo(String token, long expTime, long genTime) {
        this.token = token;
        this.expTime = expTime;
        this.genTime = genTime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getExpTime() {
        return expTime;
    }

    public void setExpTime(long expTime) {
        this.expTime = expTime;
    }

    public long getGenTime() {
        return genTime;
    }

    public void setGenTime(long genTime) {
        this.genTime = genTime;
    }
}
