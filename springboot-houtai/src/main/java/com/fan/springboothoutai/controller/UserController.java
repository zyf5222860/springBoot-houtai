package com.fan.springboothoutai.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.logging.log4j.util.Strings;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

import com.fan.springboothoutai.service.IUserService;
import com.fan.springboothoutai.entity.User;


/**
 * <p>
 * 主键	 前端控制器
 * </p>
 *
 * @author zhangyifan
 * @since 2023-07-12
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private IUserService userService;

    @PostMapping("/saveOrUpdate")
    public Boolean save(@RequestBody User user) {
        return userService.saveOrUpdate(user);
    }

    @PostMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
        return userService.removeById(id);
    }

    @PostMapping("/batch/delete")
    public Boolean deleteBatch(@RequestBody List<Integer> ids) {
        return userService.removeBatchByIds(ids);
    }

    @GetMapping
    public List<User> findAll() {
        return userService.list();
    }

    @GetMapping("/{id}")
    public List<User> findOne(@PathVariable Integer id) {
        return userService.list();
    }

    @GetMapping("/page")
    public Page<User> findPage(@RequestParam Integer pageNum,
                               @RequestParam Integer pageSize,
                               @RequestParam(defaultValue = "") String username,
                               @RequestParam(defaultValue = "") String nickname,
                               @RequestParam(defaultValue = "") String email,
                               @RequestParam(defaultValue = "") String address,
                               @RequestParam(defaultValue = "") String phone) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("id");
        if (Strings.isNotEmpty(username)) {
            queryWrapper.like("username", username);
        }
        if (Strings.isNotEmpty(nickname)) {
            queryWrapper.like("nickname", nickname);
        }
        if (Strings.isNotEmpty(email)) {
            queryWrapper.like("email", email);
        }
        if (Strings.isNotEmpty(address)) {
            queryWrapper.like("address", address);
        }
        if (Strings.isNotEmpty(phone)) {
            queryWrapper.like("phone", phone);
        }
        return userService.page(new Page<>(pageNum, pageSize),queryWrapper);
    }

}

