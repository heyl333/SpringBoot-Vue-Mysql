package com.example.demo.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.ProjectTime;
import com.example.demo.entity.UndertakeProject;
import com.example.demo.mapper.ProductTimeMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/projecttime")
public class ProductTimeController {
    @Resource
    ProductTimeMapper projecttimeMapper;

    // 用户新增
    @PostMapping
    public Result<?> save(@RequestBody ProjectTime projecttime) {
        try {
            projecttimeMapper.insert(projecttime);
            return Result.success();
        } catch (Exception e) {
            return Result.error("-1","新增失败");
        }

    }

    // 用户更新
    @PutMapping
    public Result<?> put(@RequestBody ProjectTime projecttime) {
        try {
            UpdateWrapper<ProjectTime> updatewrap = new UpdateWrapper<>();
            updatewrap.eq("project_no",projecttime.getProjectNo());
            projecttimeMapper.update(projecttime,updatewrap);
            return Result.success();
        } catch (Exception e) {
            return Result.error("-2","更新失败");
        }
    }

    // 删除用户,通过projectNo删除用户
    @DeleteMapping("/{projectNo}")
    public Result<?> delete(@PathVariable String projectNo) {
        try {
            LambdaQueryWrapper<ProjectTime> wrapper = Wrappers.<ProjectTime>lambdaQuery();
            if (StringUtils.isNotBlank(projectNo)) {
                wrapper.eq(ProjectTime::getProjectNo,projectNo);
            }
            projecttimeMapper.delete(wrapper);
            return Result.success();
        } catch (Exception e) {
            return Result.error("-3","删除失败");
        }
    }

    // 分页查询
    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search) {
        LambdaQueryWrapper<ProjectTime> wrapper = Wrappers.<ProjectTime>lambdaQuery();
        if (StringUtils.isNotBlank(search)) {
            wrapper.like(ProjectTime::getProjectNo, search);
        }
        Page<ProjectTime> projecttimePage = projecttimeMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return Result.success(projecttimePage);
    }

    // 获取projecttime表的projectNo值；查询所有的projectNo
    @GetMapping("/projectNo/{projectNo}")
    public Result<?> findProjectNo(@PathVariable(value = "projectNo") String projectNo) {
        try {
            LambdaQueryWrapper<ProjectTime> wrapper = Wrappers.<ProjectTime>lambdaQuery();
            wrapper.eq(ProjectTime::getProjectNo,projectNo);
            List<ProjectTime> projectTimesProjectNo = projecttimeMapper.selectList(wrapper);
            return Result.success(projectTimesProjectNo);
        } catch ( Exception e) {
            return Result.error("-4","查询失败");
        }

    }


}

