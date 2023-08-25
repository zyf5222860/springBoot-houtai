package com.fan.springboothoutai.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fan.springboothoutai.common.Result;
import com.fan.springboothoutai.entity.Menu;
import com.fan.springboothoutai.entity.User;
import com.fan.springboothoutai.service.IMenuService;
import com.fan.springboothoutai.utils.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;


/**
 * <p>
 * 主键	 前端控制器
 * </p>
 *
 * @author zhangyifan
 * @since 2023-08-25
 */
@RestController
@RequestMapping("/menu")
@Slf4j
public class MenuController {

    @Resource
    private IMenuService menuService;

    @PostMapping("/saveOrUpdate")
    public Result save(@RequestBody Menu menu) {
        boolean flag = menuService.saveOrUpdate(menu);
        if (flag) {
            return Result.build(0, "保存成功");
        } else {
            return Result.build(-200, "保存失败");
        }
    }

    @PostMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
        return menuService.removeById(id);
    }

    @GetMapping
    public Result findAll(@RequestParam(defaultValue = "") String name) {
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name",name);
        queryWrapper.orderByAsc("id");

        //查询所有数据
        List<Menu> list = menuService.list(queryWrapper);
        //找出pid为null的一级菜单
        List<Menu> parentNode = list.stream().filter(menu -> menu.getPid() == null).collect(Collectors.toList());
        //找出一级菜单的子菜单
        for (Menu menu : parentNode) {
            //id等于pid的就是2级菜单
            List<Menu> children = list.stream().filter(m -> m.getPid() == (menu.getId())).collect(Collectors.toList());
            menu.setChildren(children);

        }
        return Result.ok(parentNode);
    }

    @GetMapping("/{id}")
    public List<Menu> findOne(@PathVariable Integer id) {
        return menuService.list();
    }

    @GetMapping("/page")
    public Page<Menu> findPage(@RequestParam Integer pageNum,
                               @RequestParam Integer pageSize,
                               @RequestParam(defaultValue = "") String name) {
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("id");
        if (Strings.isNotEmpty(name)) {
            queryWrapper.like("name", name);
        }
        User currentAdmin = TokenUtils.getCurrentAdmin();
        log.info(JSONObject.toJSONString(currentAdmin));
        return menuService.page(new Page<>(pageNum, pageSize), queryWrapper);
    }

}

