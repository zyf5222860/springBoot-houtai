package com.fan.springboothoutai.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 主键	
 * </p>
 *
 * @author zhangyifan
 * @since 2023-08-25
 */
@Getter
@Setter
  @TableName("sys_menu")
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

      /**
     * 主键
     */
        @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      /**
     * 名称
     */
      private String name;

      /**
     * 路径
     */
      private String path;

      /**
     * 图标
     */
      private String icon;

      /**
     * 描述
     */
      private String description;


  /**
   * 子级
   */
  @TableField(exist = false)
  private List<Menu> children;


  /*
   * 父节点
   */
  private Integer pid;

}
