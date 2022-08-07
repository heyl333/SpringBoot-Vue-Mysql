package com.example.demo.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.ProjectTime;
import com.example.demo.entity.UndertakeProject;
import com.example.demo.mapper.UndertakeProjectMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/projectdisplay")
public class ProjectDisplayController {
    @Resource
    UndertakeProjectMapper undertakeprojectMapper;

    // 用户查询
    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
//                              @RequestParam(defaultValue = "") String searchProjectNo,
//                              @RequestParam(defaultValue = "") String searchProjectName,
//                              @RequestParam(defaultValue = "") String searchProjectType
                              @RequestParam(defaultValue = "2022") String searchProjectTime) {
        LambdaQueryWrapper<ProjectTime> wrapper = Wrappers.<ProjectTime>lambdaQuery();
//        if (StringUtils.isNotBlank(searchProjectNo)) {
//            wrapper.like(UndertakeProject::getProjectNo, searchProjectNo);
//        }
//        if (StringUtils.isNotBlank(searchProjectName)) {
//            wrapper.like(UndertakeProject::getProjectName, searchProjectName);
//        }
//        if (StringUtils.isNotBlank(searchProjectType)) {
//            wrapper.like(UndertakeProject::getProjectType, searchProjectType);
//        }
        if (StringUtils.isNotBlank(searchProjectTime)) {
            wrapper.like(ProjectTime::getProductTime,searchProjectTime);
        }

//        Page<UndertakeProject> undertakeprojectPage = undertakeprojectMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        Page<UndertakeProject> proTime = undertakeprojectMapper.findProTime(new Page<>(pageNum, pageSize),searchProjectTime);
        return Result.success(proTime);
    }

    // projectNo查询,提供接口给其他页面调用
    @GetMapping("/projectNo")
    public Result<?> findProjectNo(@RequestParam(defaultValue = "") String projectNo) {
        LambdaQueryWrapper<UndertakeProject> wrapper = Wrappers.<UndertakeProject>lambdaQuery();
        List<UndertakeProject> undertakeProjectsProjectNo = undertakeprojectMapper.selectList(wrapper);
        return Result.success(undertakeProjectsProjectNo);
    }
}

