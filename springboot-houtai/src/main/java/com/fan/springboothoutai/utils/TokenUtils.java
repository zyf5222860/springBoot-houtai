package com.fan.springboothoutai.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fan.springboothoutai.entity.User;
import com.fan.springboothoutai.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
@Component
@Slf4j
public class TokenUtils {

    private static IUserService staticAdminService;

    @Resource
    private IUserService adminService;

    @PostConstruct
    public void setUserService() {
        staticAdminService = adminService;
    }


    /**
     * 生成token
     * @return
     */
    public static String genToken(String userId,String sign){
        return JWT.create().withAudience(userId).  //userId作为载荷
                withExpiresAt(DateUtil.offsetHour(new Date(),2)).  //2小时过期
                sign(Algorithm.HMAC256(sign));  //password作为登录的密钥

    }


    /**
     * 获取当前登录的用户信息
     *
     * @return user对象
     *  /admin?token=xxxx
     */
    public static User getCurrentAdmin() {
        String token = null;
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            token = request.getHeader("token");
            if (StrUtil.isNotBlank(token)) {
                token = request.getParameter("token");
            }
            if (StrUtil.isBlank(token)) {
                log.error("获取当前登录的token失败， token: {}", token);
                return null;
            }
            String adminId = JWT.decode(token).getAudience().get(0);
            return staticAdminService.getById(Integer.valueOf(adminId));
        } catch (Exception e) {
            log.error("获取当前登录的管理员信息失败, token={}", token,  e);
            return null;
        }
    }


}
