package com.fan.springboothoutai.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fan.springboothoutai.common.Result;
import com.fan.springboothoutai.entity.User;
import com.fan.springboothoutai.utils.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

import com.fan.springboothoutai.service.IRoleService;
import com.fan.springboothoutai.entity.Role;


/**
 * <p>
 * 主键	 前端控制器
 * </p>
 *
 * @author zhangyifan
 * @since 2023-08-25
 */
@RestController
@RequestMapping("/role")
@Slf4j
public class RoleController {


    @Resource
    private IRoleService roleService;

    @PostMapping("/saveOrUpdate")
    public Result save(@RequestBody Role role) {
        boolean flag = roleService.saveOrUpdate(role);
        if (flag){
            return Result.build(0,"保存成功");
        }
        else{
            return Result.build(-200,"保存失败");
        }
     }

    @PostMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
        return roleService.removeById(id);
    }

    @GetMapping
    public List<Role> findAll() {
        return roleService.list();
    }

    @GetMapping("/{id}")
    public List<Role> findOne(@PathVariable Integer id) {
        return roleService.list();
    }

    @GetMapping("/page")
    public Page<Role> findPage(@RequestParam Integer pageNum,
                               @RequestParam Integer pageSize,
                               @RequestParam(defaultValue = "") String name,
                               @RequestParam(defaultValue = "") String description
                       ) {
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("id");
        if (Strings.isNotEmpty(name)) {
            queryWrapper.like("name", name);
        }
        if (Strings.isNotEmpty(description)) {
            queryWrapper.like("description", description);
        }
        User currentAdmin = TokenUtils.getCurrentAdmin();
        log.info(JSONObject.toJSONString(currentAdmin));
        return roleService.page(new Page<>(pageNum, pageSize), queryWrapper);
    }
    @PostMapping("/batch/delete")
    public Boolean deleteBatch(@RequestBody List<Integer> ids) {
        return roleService.removeBatchByIds(ids);
    }

}

