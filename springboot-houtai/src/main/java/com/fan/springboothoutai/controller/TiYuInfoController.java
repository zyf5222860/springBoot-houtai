package com.fan.springboothoutai.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fan.springboothoutai.common.Result;
import com.fan.springboothoutai.controller.dto.TiyuInfoDto;
import com.fan.springboothoutai.entity.User;
import com.fan.springboothoutai.utils.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

import com.fan.springboothoutai.service.IInfoService;
import com.fan.springboothoutai.entity.TiyuInfo;


/**
 * <p>
 * 主键	 前端控制器
 * </p>
 *
 * @author zhangyifan
 * @since 2023-08-14
 */
@Slf4j
@RestController
@RequestMapping("/info")
@CrossOrigin
public class TiYuInfoController {

    @Resource
    private IInfoService infoService;

    @PostMapping
    public Boolean save(@RequestBody TiyuInfo info) {
        return infoService.saveOrUpdate(info);
    }

    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
        return infoService.removeById(id);
    }


    @PostMapping("/queryTiyuInfo")
    public Page<TiyuInfo> findPage(@RequestBody TiyuInfoDto tiyuInfoDto) {
        QueryWrapper<TiyuInfo> queryWrapper = new QueryWrapper<>();
        Integer id = tiyuInfoDto.getId();
        Integer pageNum = tiyuInfoDto.getPageNum();
        Integer pageSize = tiyuInfoDto.getPageSize();
//        queryWrapper.orderByAsc("id");
        if (Strings.isNotEmpty(tiyuInfoDto.getTeam())) {
            queryWrapper.like("team", tiyuInfoDto.getTeam());
        }
        if (Strings.isNotEmpty(tiyuInfoDto.getPlayer())) {
            queryWrapper.like("player", tiyuInfoDto.getPlayer());
        }
        if (Strings.isNotEmpty(tiyuInfoDto.getKeyWord())) {
            queryWrapper.like("key_word", tiyuInfoDto.getKeyWord());
        }

        User currentAdmin = TokenUtils.getCurrentAdmin();
        log.info(JSONObject.toJSONString(currentAdmin));
        return infoService.page(new Page<>(pageNum, pageSize), queryWrapper);
     }

    /**
     * 新增球员信息
     * @param user
     * @return
     */
    @PostMapping("/saveOrUpdate")
    public Result saveOrUpdate(@RequestBody TiyuInfo tiyuInfo) {
        boolean flag = infoService.saveOrUpdate(tiyuInfo);
        if (flag){
            return Result.build(0,"保存成功");
        }
        else{
            return Result.build(-200,"保存失败");
        }
    }

}

