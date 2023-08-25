package com.fan.springboothoutai.controller.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
@Data
public class TiyuInfoDto implements Serializable {
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

    private Integer pageNum;

    private Integer pageSize;
}
