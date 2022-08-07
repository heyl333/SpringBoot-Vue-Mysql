package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.UndertakeProject;
import org.apache.ibatis.annotations.Param;

public interface UndertakeProjectMapper extends BaseMapper<UndertakeProject> {
    // 一对多查询
    Page<UndertakeProject> findProTime(Page<UndertakeProject> page, @Param("productTime") String productTime);
}
