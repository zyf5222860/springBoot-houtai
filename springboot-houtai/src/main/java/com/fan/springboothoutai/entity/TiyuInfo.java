package com.fan.springboothoutai.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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
 * @since 2023-08-14
 */
@Getter
@Setter
@TableName("tiyu_info")
public class TiyuInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 球队名
     */
    private String team;

    /**
     * 球员
     */
    private String player;

    /**
     * 关键字
     */
    private String keyWord;

    /**
     * 信息
     */
    private String info;

    private LocalDateTime creatTime;


}
