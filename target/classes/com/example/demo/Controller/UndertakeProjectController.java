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
import java.util.List;

@RestController
@RequestMapping("/undertakeproject")
public class UndertakeProjectController {
    @Resource
    UndertakeProjectMapper undertakeprojectMapper;

    // 用户新增
    @PostMapping
    public Result<?> save(@RequestBody UndertakeProject undertakeproject) {
        undertakeprojectMapper.insert(undertakeproject);
        return Result.success();
    }

    // 用户更新
    @PutMapping
    public Result<?> put(@RequestBody UndertakeProject undertakeproject) {
        undertakeprojectMapper.updateById(undertakeproject);
        return Result.success();
    }

    // 删除用户
    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        undertakeprojectMapper.deleteById(id);
        return Result.success();
    }

    // 用户查询
    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search) {
        LambdaQueryWrapper<UndertakeProject> wrapper = Wrappers.<UndertakeProject>lambdaQuery();
        if (StringUtils.isNotBlank(search)) {
            wrapper.like(UndertakeProject::getProjectNo, search);
        }
        Page<UndertakeProject> undertakeprojectPage = undertakeprojectMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return Result.success(undertakeprojectPage);
    }

    // projectNo查询,提供接口给其他页面调用
    @GetMapping("/projectNo")
    public Result<?> findProjectNo(@RequestParam(defaultValue = "") String projectNo) {
        LambdaQueryWrapper<UndertakeProject> wrapper = Wrappers.<UndertakeProject>lambdaQuery();
        List<UndertakeProject> undertakeProjectsProjectNo = undertakeprojectMapper.selectList(wrapper);
        return Result.success(undertakeProjectsProjectNo);
    }
}

