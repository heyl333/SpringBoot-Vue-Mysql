package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@TableName("projecttime")
@Data
public class ProjectTime {
//    @TableId(value = "id",type = IdType.AUTO)
    private String projectNo;
    private String projectName;
    private String testSystem;
    private String testmanager;
    // 也可以使用注解 @JSONFormat(pattern = "yyyy-MM-dd",timeZone="GMT+8") //东八区
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date testAdmitIn;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date testAdmitOut;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date productTime;
    private String completeStatus;
}
