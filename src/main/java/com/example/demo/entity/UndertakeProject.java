package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("undertakeproject")
@Data
public class UndertakeProject {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private String projectNo;
    private String projectName;
    private String testSystem;
    private String testmanager;
    private String projectType;
    private String masterSlave;
    private String developmentDep;
    private String domesticForeign;
    private String fp;
    private String descrip;
    private String performanceTest;
    private String safetyTest;
    private String interfaceTest;
}
