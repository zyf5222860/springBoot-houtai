package com.fan.springboothoutai.controller.dto;

import cn.hutool.core.annotation.Alias;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserDto implements Serializable {
    /**
     * 用户名
     */
    @Alias("用户名")
    private String username;
    @Alias("密码")
    private String password;
    @Alias("昵称")
    private String nickname;
    @Alias("密码")
    private String avatarUrl;
    @Alias("邮箱")
    private String email;
    @Alias("电话")
    private String phone;
}
