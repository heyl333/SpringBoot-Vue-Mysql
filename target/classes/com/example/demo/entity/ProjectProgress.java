package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@TableName("projectprogress")
@Data
public class ProjectProgress {
//    @TableId(value = "id",type = IdType.AUTO)
    private String projectNo;
    private String projectName;
    private String testSystem;
    private String testmanager;
    private String completeStatus;

    private Integer totalCaseNumber0;
    private Integer abnolmalCaseNumber0;
    private Integer carryOutNumber0;
    private Integer passCaseNumber0;
    private Integer totalBugs0;
    private Integer solvedBugs0;
    private Integer testPersonNumber0;
    private Integer projectPeriod0;

    private Integer totalCaseNumber1;
    private Integer abnolmalCaseNumber1;
    private Integer carryOutNumber1;
    private Integer passCaseNumber1;
    private Integer totalBugs1;
    private Integer solvedBugs1;
    private Integer testPersonNumber1;
    private Integer projectPeriod1;

    private Integer totalCaseNumber2;
    private Integer abnolmalCaseNumber2;
    private Integer carryOutNumber2;
    private Integer passCaseNumber2;
    private Integer totalBugs2;
    private Integer solvedBugs2;
    private Integer testPersonNumber2;
    private Integer projectPeriod2;

}
