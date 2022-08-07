package com.example.demo.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.ProjectProgress;
import com.example.demo.mapper.ProjectProgressMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/projectprogress")
public class ProjectProgressController {
    @Resource
    ProjectProgressMapper projectprogressMapper;

    // 用户新增
    @PostMapping
    public Result<?> save(@RequestBody ProjectProgress projectprogress) {
        try {
            projectprogressMapper.insert(projectprogress);
            return Result.success();
        } catch (Exception e) {
            return Result.error("-1","新增失败");
        }

    }

    // 用户更新
    @PutMapping
    public Result<?> put(@RequestBody ProjectProgress projectprogress) {
        try {
            UpdateWrapper<ProjectProgress> updatewrap = new UpdateWrapper<>();
            updatewrap.eq("project_no",projectprogress.getProjectNo());
            updatewrap.set("complete_status",projectprogress.getCompleteStatus());

            updatewrap.set("total_case_number0",projectprogress.getTotalCaseNumber0());
            updatewrap.set("abnolmal_case_number0",projectprogress.getAbnolmalCaseNumber0());
            updatewrap.set("carry_out_number0",projectprogress.getCarryOutNumber0());
            updatewrap.set("pass_case_number0",projectprogress.getPassCaseNumber0());
            updatewrap.set("total_bugs0",projectprogress.getTotalBugs0());
            updatewrap.set("solved_bugs0",projectprogress.getSolvedBugs0());
            updatewrap.set("test_person_number0",projectprogress.getTestPersonNumber0());
            updatewrap.set("project_period0",projectprogress.getProjectPeriod0());

            updatewrap.set("total_case_number1",projectprogress.getTotalCaseNumber1());
            updatewrap.set("abnolmal_case_number1",projectprogress.getAbnolmalCaseNumber1());
            updatewrap.set("carry_out_number1",projectprogress.getCarryOutNumber1());
            updatewrap.set("pass_case_number1",projectprogress.getPassCaseNumber1());
            updatewrap.set("total_bugs1",projectprogress.getTotalBugs1());
            updatewrap.set("solved_bugs1",projectprogress.getSolvedBugs1());
            updatewrap.set("test_person_number1",projectprogress.getTestPersonNumber1());
            updatewrap.set("project_period1",projectprogress.getProjectPeriod1());

            updatewrap.set("total_case_number2",projectprogress.getTotalCaseNumber2());
            updatewrap.set("abnolmal_case_number2",projectprogress.getAbnolmalCaseNumber2());
            updatewrap.set("carry_out_number2",projectprogress.getCarryOutNumber2());
            updatewrap.set("pass_case_number2",projectprogress.getPassCaseNumber2());
            updatewrap.set("total_bugs2",projectprogress.getTotalBugs2());
            updatewrap.set("solved_bugs2",projectprogress.getSolvedBugs2());
            updatewrap.set("test_person_number2",projectprogress.getTestPersonNumber2());
            updatewrap.set("project_period2",projectprogress.getProjectPeriod2());

            projectprogressMapper.update(null,updatewrap);
            return Result.success();
        } catch (Exception e) {
            return Result.error("-2","更新失败");
        }
    }

    // 删除用户,通过projectNo删除用户
    @DeleteMapping("/{projectNo}")
    public Result<?> delete(@PathVariable String projectNo) {
        try {
            LambdaQueryWrapper<ProjectProgress> wrapper = Wrappers.<ProjectProgress>lambdaQuery();
            if (StringUtils.isNotBlank(projectNo)) {
                wrapper.eq(ProjectProgress::getProjectNo,projectNo);
            }
            projectprogressMapper.delete(wrapper);
            return Result.success();
        } catch (Exception e) {
            return Result.error("-3","删除失败");
        }
    }

    // 分页查询
    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String searchProjectNo,
                              @RequestParam(defaultValue = "") String searchProjectName,
                              @RequestParam(defaultValue = "") String searchTestmanager) {
        LambdaQueryWrapper<ProjectProgress> wrapper = Wrappers.<ProjectProgress>lambdaQuery();
        if (StringUtils.isNotBlank(searchProjectNo)) {
            wrapper.like(ProjectProgress::getProjectNo, searchProjectNo);
        }
        if (StringUtils.isNotBlank(searchProjectName)) {
            wrapper.like(ProjectProgress::getProjectName, searchProjectName);
        }
        if (StringUtils.isNotBlank(searchTestmanager)) {
            wrapper.like(ProjectProgress::getTestmanager, searchTestmanager);
        }
        Page<ProjectProgress> projecttimePage = projectprogressMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return Result.success(projecttimePage);
    }

    // 获取projectprogress表的projectNo值；查询所有的projectNo
    @GetMapping("/projectNo/{projectNo}")
    public Result<?> findProjectNo(@PathVariable(value = "projectNo") String projectNo) {
        try {
            LambdaQueryWrapper<ProjectProgress> wrapper = Wrappers.<ProjectProgress>lambdaQuery();
            wrapper.eq(ProjectProgress::getProjectNo,projectNo);
            List<ProjectProgress> projectTimesProjectNo = projectprogressMapper.selectList(wrapper);
            if (projectTimesProjectNo.size() < 1) {
                return Result.error("-4","查询失败"); // 查询不到数据
            }
            return Result.success(projectTimesProjectNo);
        } catch ( Exception e) {
            return Result.error("-4","查询失败");
        }

    }


}

