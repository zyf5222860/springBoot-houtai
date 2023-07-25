package com.fan.springboothoutai.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
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
@Getter
@Setter
  @TableName("sys_user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

      /**
     * 主键
     */
        @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      /**
     * 用户名
     */
      private String username;

    private String password;

    private String nickname;

    private String email;

    private String phone;

    private String address;

    private LocalDateTime creatTime;


}
