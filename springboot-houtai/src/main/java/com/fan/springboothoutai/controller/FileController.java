package com.fan.springboothoutai.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fan.springboothoutai.common.Result;
import com.fan.springboothoutai.common.ResultCodeEnum;
import com.fan.springboothoutai.entity.SysFile;
import com.fan.springboothoutai.mapper.FileMapper;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * 文件上传接口
 */
@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private FileMapper fileMapper;

    @Value("${files.upload.path}")
    private String fileUploadPath;
    @Value("${files.upload.url}")
    private String fileUploadUrl;

    @PostMapping("/upload")
    public String upload(@RequestParam MultipartFile file) throws IOException {

        String originalFilename = file.getOriginalFilename();//获取原始名称
        String type = FileUtil.extName(originalFilename);
        long size = file.getSize();
        //先检查上层层级文件是否存在
        File uploadParentFile = new File(fileUploadPath);
        if (!uploadParentFile.exists()) {
            uploadParentFile.mkdir();
        }
        //定义文件唯一标识码
        String uuid = IdUtil.fastSimpleUUID();
        String fileUuid = uuid + StrUtil.DOT + type;
        File uploadFile = new File(fileUploadPath + fileUuid);

        String url = null; //本地存储路径
        String md5 = null; //文件的md5
        //把获取到的文件存储到磁盘目录
        file.transferTo(uploadFile);
        //获取文件MD5，防止文件重复，占用磁盘空间
        md5 = SecureUtil.md5(uploadFile);
        //从数据库查询是否有此md5的数据
        SysFile sysFile = getFileByMd5(md5);
        if (sysFile != null) {
            url = sysFile.getUrl();
            //由于文件存在，删除刚才上传的文件
            uploadFile.delete();
        }else {
            url = fileUploadUrl + fileUuid ;
        }
        SysFile dbFile = new SysFile();
        //存储到数据库
        dbFile.setName(originalFilename);
        dbFile.setType(type);
        dbFile.setSize(size / 1024);//除以1024 单位为KB
        dbFile.setUrl(url);
        dbFile.setMd5(md5);
        dbFile.setIsDel(false);
        fileMapper.insert(dbFile);
        return url;
    }

    /**
     * 更新启用状态
     */
    @PostMapping("/updateFileStatus")
    private Result updateFileStatus(@RequestBody SysFile file) {
        int i = fileMapper.updateById(file);
        if (i==1){
            return Result.build(200,"更新成功");
        }else {
            return Result.build(-1,"更新失败");
        }
    }

    /**
     * 通过md5获取文件
     *
     * @param md5
     * @return
     */
    private SysFile getFileByMd5(String md5) {
        QueryWrapper<SysFile> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("md5", md5);
        List<SysFile> sysFiles = fileMapper.selectList(queryWrapper);
        return sysFiles.size() == 0 ? null : sysFiles.get(0);
    }


    @GetMapping("/{fileUuid}")
    public void download(@PathVariable String fileUuid, HttpServletResponse response) throws IOException {
        //根据文件标识码获取文件
        File uploadPath = new File(fileUploadPath + fileUuid);
        //设置输出流的格式
        ServletOutputStream outputStream = response.getOutputStream();
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileUuid, "UTF-8"));
        response.setContentType("application/octet-stream");
        //调取文件的字节流
        outputStream.write(FileUtil.readBytes(uploadPath));
        outputStream.flush();
        outputStream.close();
    }

    /**
     * 分页查询
     * @param pageNum
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public Result<Page<SysFile>> findPage(@RequestParam Integer pageNum,
                                          @RequestParam Integer pageSize,
                                          @RequestParam(defaultValue = "") String name) {
        QueryWrapper<SysFile> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del",false);//查询未删除记录
        queryWrapper.orderByAsc("id");
        if (Strings.isNotEmpty(name)) {
            queryWrapper.like("name", name);
        }
        Page<SysFile> sysFilePage = fileMapper.selectPage(new Page<>(pageNum, pageSize), queryWrapper);
        return Result.build(sysFilePage, ResultCodeEnum.SUCCESS);
    }


    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        QueryWrapper<SysFile> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id",ids);
        List<SysFile> sysFiles = fileMapper.selectList(queryWrapper);
        for (SysFile sysFile : sysFiles) {
            sysFile.setIsDel(true);
            fileMapper.updateById(sysFile);
        }
        return Result.build(200,"删除成功");
    }

}


