package com.fan.springboothoutai.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.log.Log;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fan.springboothoutai.common.Result;
import com.fan.springboothoutai.common.ResultCodeEnum;
import com.fan.springboothoutai.controller.dto.UserDto;
import com.fan.springboothoutai.entity.User;
import com.fan.springboothoutai.exception.ServiceException;
import com.fan.springboothoutai.mapper.UserMapper;
import com.fan.springboothoutai.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


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

    private static final Log LOG = Log.get();

    @Override
    public User login(UserDto userDto) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",userDto.getUsername());
        queryWrapper.eq("password",userDto.getPassword());
        User one = null;
        try {
            one = getOne(queryWrapper);
            if (one != null){
                 return one;
            }else {
                throw new ServiceException(Result.loginError());
            }
        } catch (Exception e) {
            LOG.error(e.getMessage());
            e.printStackTrace();
        }
        return one;
    }

    @Override
    public Result register(UserDto userDto) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",userDto.getUsername()); //手机号作为为唯一标识
        User one = null;
        Result result = null;
        try {
            one = getOne(queryWrapper);
            if (one != null){
                result =  Result.registerError();
            }else {
                one = new User();
                one.setUsername(userDto.getUsername());
                one.setPassword(userDto.getPassword());
                //判断是否是邮箱
                String username = userDto.getUsername();
                boolean isEmailAddress = isEmailAddress(username);
                if (isEmailAddress){
                    LOG.info("注册的为邮箱地址");
                    one.setEmail(one.getUsername());
                }else {
                    LOG.info("注册的为手机号码");
                    one.setPhone(one.getUsername());
                }

                save(one);
                result = Result.registerSuccess();
            }
        } catch (Exception e) {
            LOG.error(e.getMessage());
            e.printStackTrace();
        }
        return  result ;

    }

    @Override
    public Result<User> findUserByUsername(String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username); //手机号作为为唯一标识
        User one = null;
        one = getOne(queryWrapper);
        Result result = null;
        if (one != null){
            result = Result.build(one, ResultCodeEnum.QUERY_SECCESS_BYNAME);
        }
        else {
            result = Result.build(one, ResultCodeEnum.QUERY_Fail_BYNAME);
        }
        return result;
    }

    /**
     * 判断是否传入是邮箱
     * @param username
     * @return
     */
    private boolean isEmailAddress(String username) {
        // 正则表达式匹配电子邮件地址的模式
        String emailAddressPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

        // 创建 Pattern 对象
        Pattern pattern = Pattern.compile(emailAddressPattern);

        // 创建 Matcher 对象
        Matcher matcher = pattern.matcher(username);

        // 判断是否匹配成功
        return matcher.matches();
     }
}
