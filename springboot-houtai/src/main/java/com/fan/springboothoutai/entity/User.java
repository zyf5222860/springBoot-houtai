package com.fan.springboothoutai.entity;

import cn.hutool.core.annotation.Alias;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 主键
 * </p>
 *
 * @author zhangyifan
 * @since 2023-07-12
 */

@Data
@TableName("sys_user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    @Alias("id")
    private Integer id;

    /**
     * 用户名
     */
    @Alias("用户名")
    private String username;
    @Alias("密码")
    private String password;
    @Alias("昵称")
    private String nickname;
    @Alias("邮箱")
    private String email;
    @Alias("电话")
    private String phone;
    @Alias("地址")
    private String address;
    @Alias("创建时间")
    private Date creatTime;
    @Alias("头像地址")
    @TableField("avatarUrl")
    private String avatarUrl;

}
