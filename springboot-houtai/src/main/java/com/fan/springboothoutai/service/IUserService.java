package com.fan.springboothoutai.service;

import com.fan.springboothoutai.common.Result;
import com.fan.springboothoutai.controller.dto.UserDto;
import com.fan.springboothoutai.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 主键	 服务类
 * </p>
 *
 * @author zhangyifan
 * @since 2023-07-12
 */
public interface IUserService extends IService<User> {

    User login(UserDto userDto);

    Result register(UserDto userDto);

    Result<User> findUserByUsername(String username);
}
