package com.fan.springboothoutai.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fan.springboothoutai.common.Result;
import com.fan.springboothoutai.controller.dto.UserDto;
import com.fan.springboothoutai.entity.User;
import com.fan.springboothoutai.service.IUserService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;


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

    /**
     * 登录接口
     *
     * @param
     * @return
     */
    @PostMapping("/login")
    public Result<UserDto> login(@RequestBody UserDto userDto) {
        User user = userService.login(userDto);
        if (user != null) {
            BeanUtil.copyProperties(user, userDto);
            userDto.setAvatarUrl(user.getAvatarUrl());//这里不知道为什么这个属性赋值不上去
            return Result.loginSuccess(userDto);
        } else {
            return Result.loginError();
        }
    }


    /**
     * 注册接口
     *
     * @param
     * @return
     */
    @PostMapping("/register")
    public Result register(@RequestBody UserDto userDto) {
        Result result = userService.register(userDto);
        return result;
    }


    @PostMapping("/saveOrUpdate")
    public Result save(@RequestBody User user) {
        boolean flag = userService.saveOrUpdate(user);
        if (flag){
            return Result.build(0,"保存成功");
        }
        else{
            return Result.build(-200,"保存失败");
        }
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

    @GetMapping("/username/{username}")
    public Result<User> findUserByUsername(@PathVariable String username) {

        return userService.findUserByUsername(username);
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
        return userService.page(new Page<>(pageNum, pageSize), queryWrapper);
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception {

        //从数据库查出所有数据
        List<User> list = userService.list();

        //通过工具创建writer
        ExcelWriter writer = ExcelUtil.getWriter(true);
        //自定义标题名
//        writer.addHeaderAlias("username","用户名");
//        writer.addHeaderAlias("password","密码");
//        writer.addHeaderAlias("nickname","昵称");
//        writer.addHeaderAlias("email","邮箱");
//        writer.addHeaderAlias("phone","电话");
//        writer.addHeaderAlias("address","地址");
//        writer.addHeaderAlias("creatTime","创建时间");
        //一次性写出list内的对象到excel,使用默认样式，强制输出标题
        writer.write(list, true);
        // 设置所有列为自动宽度，不考虑合并单元格
//        AdaptiveWidthUtils.setSizeColumn(writer.getSheet(), 15);
        // 设置自适应宽度
        writer.autoSizeColumnAll();
        //设置浏览器响应格式
        response.setContentType("application/vnd.openxmlformats-officedoucment.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("用户信息", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;fileName=" + fileName + ".xlsx");
        ServletOutputStream outputStream = response.getOutputStream();
        writer.flush(outputStream, true);
        outputStream.close();
        writer.close();
    }


    /**
     * 导入
     */

    @PostMapping("/import")
    public boolean imp(MultipartFile file) throws Exception {
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        List<User> users = reader.readAll(User.class);
        userService.saveBatch(users);
        return true;

    }
}

