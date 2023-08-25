package com.fan.springboothoutai.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.Quarter;
import com.fan.springboothoutai.common.Result;
import com.fan.springboothoutai.controller.dto.UserDto;
import com.fan.springboothoutai.entity.EchartDemo;
import com.fan.springboothoutai.entity.User;
import com.fan.springboothoutai.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;

/**
 * 定义的图片展示
 */
@RestController
@RequestMapping("/echart")
@Slf4j
public class EchartController {
    @Resource
    private IUserService userService;

    /**
     * 登录接口
     *
     * @param
     * @return
     */
    @GetMapping("/members")
    public Result  login() {
        List<User> list = userService.list();
        int q1 =0;
        int q2 =0;
        int q3 =0;
        int q4 =0;
        Map<String,Integer> memMap = new HashMap<String,Integer>();
        for (User user : list) {
            Date creatTime = user.getCreatTime();
            Quarter quarter = DateUtil.quarterEnum(creatTime);
            switch (quarter){

                case Q1: q1 += 1;memMap.put("q1",q1);break;
                case Q2: q2 += 1;memMap.put("q2",q2);break;
                case Q3: q3 += 1;memMap.put("q3",q3);break;
                case Q4: q4 += 1;memMap.put("q4",q4);break;
                default: break;
            }
         }
        // 创建对象数组
        EchartDemo[] array = new EchartDemo[memMap.size()];

        int index = 0;
        for (Map.Entry<String, Integer> entry : memMap.entrySet()) {
            EchartDemo echartDemo = new EchartDemo();
            echartDemo.setName(entry.getKey());
            echartDemo.setValue(entry.getValue());
            array[index] = echartDemo;
            index++;
        }

        return Result.ok(array);
    }
}
