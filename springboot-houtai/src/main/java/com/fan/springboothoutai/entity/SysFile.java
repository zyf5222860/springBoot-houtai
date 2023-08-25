package com.fan.springboothoutai.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("sys_file")
public class SysFile {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private String type;
    private long size;
    private String url;
    private Boolean isDel;
    private Boolean enable;
    private String md5;
}
