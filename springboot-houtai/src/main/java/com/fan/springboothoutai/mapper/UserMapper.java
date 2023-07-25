package com.fan.springboothoutai.mapper;

import com.fan.springboothoutai.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 主键	 Mapper 接口
 * </p>
 *
 * @author zhangyifan
 * @since 2023-07-12
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
