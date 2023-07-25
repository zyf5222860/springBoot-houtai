package com.fan.springboothoutai.service.impl;

import com.fan.springboothoutai.entity.User;
import com.fan.springboothoutai.mapper.UserMapper;
import com.fan.springboothoutai.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 主键	 服务实现类
 * </p>
 *
 * @author zhangyifan
 * @since 2023-07-12
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
