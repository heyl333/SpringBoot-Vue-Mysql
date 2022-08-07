<template>
  <div>
    <div>
      <h1 align="center" style="margin-bottom: 40px">项目进度情况展示</h1>
    </div>
    <div style="margin: 10px 0">
      <el-button type="primary" @click="add">新增</el-button>
    </div>

    <div style="margin: 10px 0">
      项目编号
      <el-input v-model="searchProjectNo" placeholder="请输入内容" style="width: 20%;margin-right: 30px" clearable></el-input>
      项目名称
      <el-input v-model="searchProjectName" placeholder="请输入内容" style="width: 20%;margin-right: 30px" clearable></el-input>
      测试经理
      <el-input v-model="searchTestmanager" placeholder="请输入内容" style="width: 20%;margin-right: 30px" clearable></el-input>
      <el-button type="primary" style="margin-left: 5px" @click="load">搜索</el-button>
    </div>
    <el-table
      :data="tableData"
      style="width: 100%"
      :row-class-name="tableRowClassName">
      <el-table-column
        prop="projectNo"
        label="项目编号"
      >
      </el-table-column>
      <el-table-column
        prop="projectName"
        label="项目名称"
      >
      </el-table-column>
      <el-table-column
        prop="testmanager"
        label="测试经理"
      >
      </el-table-column>
      <el-table-column
        prop="testSystem"
        label="系统"
      >
      </el-table-column>
      <el-table-column
        prop="completeStatus"
        label="完成情况"
      >
      </el-table-column>
      <el-table-column
        label="操作"
      >
        <template slot-scope="scope">
          <el-button @click="handleEdit(scope.row)" type="danger" size="small">编辑</el-button>
          <el-popconfirm title="确定删除吗？" @confirm="handleDelete(scope.row.projectNo)">
            <template #reference>
              <el-button size="mini" type="danger">删除</el-button>
            </template>
          </el-popconfirm>

        </template>
      </el-table-column>
    </el-table>
    <div class="block" style="margin: 10px 0">
      <span class="demonstration">完整功能</span>
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="currentPage"
        :page-sizes="[5, 10, 20]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
      </el-pagination>
      <!--      加入弹窗-->
      <el-dialog  align="center" title="项目进度信息录入" :visible.sync="dialogVisible" width="80%">
        <el-form :model="form" label-width="120px"  >
          <el-row inline="true">
            <el-col :span="8">
              <el-form-item label="项目编号" >
                <!--                利用数组[]，可以实现绑定多个值-->
                <!--             新增时，从projectInfo取值   -->
                <template v-if="this.options_projectNo.length >=1">
                  <el-select   v-model="projectInfo" placeholder="项目编号" style="float: left">
                    <el-option v-for="item in this.options_projectNo" :key="item.id" :label="item.projectNo" :value="[item.projectNo , item.projectName,item.testSystem,item.testmanager]"></el-option>
                  </el-select>
                </template>
                <!--            更新时，项目名称从this.form取值-->
                <template v-else>
                  <el-select   v-model="this.form.projectNo" placeholder="项目编号" style="float: left" disabled="true">
                    <el-option  :key="this.form.projectNo" :label="this.form.projectNo" :value="this.form.projectName"></el-option>
                  </el-select>
                </template>
              </el-form-item>
            </el-col>

            <el-col :span="16">
              <el-form-item label="项目名称">
                <!--             希望实现的功能，projectName根据projectNo自动反显；利用v-model的双向绑定功能-->
                <!--             更新时，从projectInfo取值   -->
                <template v-if="projectInfo.length > 1">
                  <el-input  v-model="projectInfo[1]" suffix-icon="el-icon-project"></el-input>
                </template>
                <!--            更新时，项目名称从this.form取值-->
                <template v-else>
                  <el-input  v-model="this.form.projectName" suffix-icon="el-icon-project" :disabled="true"></el-input>
                </template>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row inline="true">
            <el-col :span="8">
              <el-form-item label="测试系统" >
                <template v-if="projectInfo.length > 1">
                  <el-select  v-model="projectInfo[2]" placeholder="测试系统" style="float: left;width: 80%" disabled="true">
                  </el-select>
                </template>
                <template v-else>
                  <el-select  v-model="this.form.testSystem" placeholder="测试系统" style="float: left;width: 80%" disabled="true">
                  </el-select>
                </template>

              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="测试经理">
                <template v-if="projectInfo.length > 1">
                  <el-select v-model="projectInfo[3]" placeholder="测试经理" style="float: left;width: 80%" disabled="true">
                  </el-select>
                </template>
                <template v-else>
                  <el-select  v-model="this.form.testmanager" placeholder="测试经理" style="float: left;width: 80%" disabled="true">
                  </el-select>
                </template>

              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="完成情况">
                <el-select v-model="form.completeStatus" placeholder="完成情况" style="float: left;width: 80%">
                  <el-option label="已完成" value="已完成"></el-option>
                  <el-option label="第一轮测试" value="第一轮测试"></el-option>
                  <el-option label="第二轮测试" value="第二轮测试"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
<!--      冒烟测试用例执行情况    -->
          <div style="margin: 20px 0">
            <font>冒烟测试进度情况</font>
          </div>
          <el-row inline="true">
            <el-col :span="8">
              <el-form-item label="用例总数">
                <el-input v-model="form.totalCaseNumber0" placeholder="累计编写用例数" style="width: 80%;float: left"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="反向用例数">
                <el-input v-model="form.abnolmalCaseNumber0" placeholder="反向用例数" style="width: 80%;float: left" ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="反向用例比">
                <el-input v-model="calAbnolmalCaseRate0" placeholder="反向用例比" style="width: 80%;float: left" disabled="true"> </el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row inline="true">
            <el-col :span="8">
              <el-form-item label="已执行用例">
                <el-input v-model="form.carryOutNumber0" placeholder="已执行用例数" style="width: 80%;float: left" > </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="通过用例数">
                <el-input v-model="form.passCaseNumber0" placeholder="通过用例数" style="width: 80%;float: left" > </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="用例通过率">
                <el-input v-model="calCasePassedRate0" placeholder="用例通过率" style="width: 80%;float: left" disabled="true"> </el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row inline="true">
            <el-col :span="8">
              <el-form-item label="整体执行率">
                <el-input v-model="calCarryOutRate0" placeholder="整体执行率" style="width: 80%;float: left" disabled="true"> </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="累计缺陷数">
                <el-input v-model="form.totalBugs0" placeholder="累计缺陷数" style="width: 80%;float: left" > </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="解决缺陷数">
                <el-input v-model="form.solvedBugs0" placeholder="解决缺陷数" style="width: 80%;float: left" > </el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row inline="true">
            <el-col :span="8">
              <el-form-item label="项目缺陷密度">
                <el-input v-model="calProjectBugsDensity0" placeholder="解决缺陷数" style="width: 80%;float: left" disabled="true"> </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="每百用例缺陷">
                <el-input v-model="calPercentBugsNumber0" placeholder="每百用例缺陷" style="width: 80%;float: left" disabled="true"> </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="测试人数">
                <el-input v-model="form.testPersonNumber0" placeholder="测试人数" style="width: 80%;float: left" > </el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row inline="true">
            <el-col :span="8">
              <el-form-item label="项目周期（月）">
                <el-input v-model="form.projectPeriod0" placeholder="月数" style="width: 80%;float: left" > </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="每人月用例数">
                <el-input v-model="calPersonMonthCaseNumber0" placeholder="每人月用例数" style="width: 80%;float: left" disabled="true"> </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="每人月缺陷数">
                <el-input v-model="calPersonMonthBugsNumber0" placeholder="每人月缺陷数" style="width: 80%;float: left" disabled="true"> </el-input>
              </el-form-item>
            </el-col>
          </el-row>

          <!--      第一轮测试用例执行情况    -->
          <div style="margin: 20px 0">
            <font>第一轮测试进度情况</font>
          </div>
          <el-row inline="true">
            <el-col :span="8">
              <el-form-item label="用例总数">
                <el-input v-model="form.totalCaseNumber1" placeholder="累计编写用例数" style="width: 80%;float: left"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="反向用例数">
                <el-input v-model="form.abnolmalCaseNumber1" placeholder="反向用例数" style="width: 80%;float: left" ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="反向用例比">
                <el-input v-model="calAbnolmalCaseRate1" placeholder="反向用例比" style="width: 80%;float: left" disabled="true"> </el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row inline="true">
            <el-col :span="8">
              <el-form-item label="已执行用例">
                <el-input v-model="form.carryOutNumber1" placeholder="已执行用例数" style="width: 80%;float: left" > </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="通过用例数">
                <el-input v-model="form.passCaseNumber1" placeholder="通过用例数" style="width: 80%;float: left" > </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="用例通过率">
                <el-input v-model="calCasePassedRate1" placeholder="用例通过率" style="width: 80%;float: left" disabled="true"> </el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row inline="true">
            <el-col :span="8">
              <el-form-item label="整体执行率">
                <el-input v-model="calCarryOutRate1" placeholder="整体执行率" style="width: 80%;float: left" disabled="true"> </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="累计缺陷数">
                <el-input v-model="form.totalBugs1" placeholder="累计缺陷数" style="width: 80%;float: left" > </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="解决缺陷数">
                <el-input v-model="form.solvedBugs1" placeholder="解决缺陷数" style="width: 80%;float: left" > </el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row inline="true">
            <el-col :span="8">
              <el-form-item label="项目缺陷密度">
                <el-input v-model="calProjectBugsDensity1" placeholder="解决缺陷数" style="width: 80%;float: left" disabled="true"> </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="每百用例缺陷">
                <el-input v-model="calPercentBugsNumber1" placeholder="每百用例缺陷" style="width: 80%;float: left" disabled="true"> </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="测试人数">
                <el-input v-model="form.testPersonNumber1" placeholder="测试人数" style="width: 80%;float: left" > </el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row inline="true">
            <el-col :span="8">
              <el-form-item label="项目周期（月）">
                <el-input v-model="form.projectPeriod1" placeholder="月数" style="width: 80%;float: left" > </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="每人月用例数">
                <el-input v-model="calPersonMonthCaseNumber1" placeholder="每人月用例数" style="width: 80%;float: left" disabled="true"> </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="每人月缺陷数">
                <el-input v-model="calPersonMonthBugsNumber1" placeholder="每人月缺陷数" style="width: 80%;float: left" disabled="true"> </el-input>
              </el-form-item>
            </el-col>
          </el-row>

<!--     第二轮     -->
          <div style="margin: 20px 0">
            <font>第二轮测试进度情况</font>
          </div>
          <el-row inline="true">
            <el-col :span="8">
              <el-form-item label="用例总数">
                <el-input v-model="form.totalCaseNumber2" placeholder="累计编写用例数" style="width: 80%;float: left"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="反向用例数">
                <el-input v-model="form.abnolmalCaseNumber2" placeholder="反向用例数" style="width: 80%;float: left" ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="反向用例比">
                <el-input v-model="calAbnolmalCaseRate2" placeholder="反向用例比" style="width: 80%;float: left" disabled="true"> </el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row inline="true">
            <el-col :span="8">
              <el-form-item label="已执行用例">
                <el-input v-model="form.carryOutNumber2" placeholder="已执行用例数" style="width: 80%;float: left" > </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="通过用例数">
                <el-input v-model="form.passCaseNumber2" placeholder="通过用例数" style="width: 80%;float: left" > </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="用例通过率">
                <el-input v-model="calCasePassedRate2" placeholder="用例通过率" style="width: 80%;float: left" disabled="true"> </el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row inline="true">
            <el-col :span="8">
              <el-form-item label="整体执行率">
                <el-input v-model="calCarryOutRate2" placeholder="整体执行率" style="width: 80%;float: left" disabled="true"> </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="累计缺陷数">
                <el-input v-model="form.totalBugs2" placeholder="累计缺陷数" style="width: 80%;float: left" > </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="解决缺陷数">
                <el-input v-model="form.solvedBugs2" placeholder="解决缺陷数" style="width: 80%;float: left" > </el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row inline="true">
            <el-col :span="8">
              <el-form-item label="项目缺陷密度">
                <el-input v-model="calProjectBugsDensity2" placeholder="解决缺陷数" style="width: 80%;float: left" disabled="true"> </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="每百用例缺陷">
                <el-input v-model="calPercentBugsNumber2" placeholder="每百用例缺陷" style="width: 80%;float: left" disabled="true"> </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="测试人数">
                <el-input v-model="form.testPersonNumber2" placeholder="测试人数" style="width: 80%;float: left" > </el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row inline="true">
            <el-col :span="8">
              <el-form-item label="项目周期（月）">
                <el-input v-model="form.projectPeriod2" placeholder="月数" style="width: 80%;float: left" > </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="每人月用例数">
                <el-input v-model="calPersonMonthCaseNumber2" placeholder="每人月用例数" style="width: 80%;float: left" disabled="true"> </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="每人月缺陷数">
                <el-input v-model="calPersonMonthBugsNumber2" placeholder="每人月缺陷数" style="width: 80%;float: left" disabled="true"> </el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>

        <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="save">确 定</el-button>
  </span>
      </el-dialog>
    </div>
  </div>
</template>

<style>
.el-table .warning-row {
  background: oldlace;
}

.el-table .success-row {
  background: #f0f9eb;
}
</style>

<script>

export default {
  created () {
    this.load()
  },
  methods: {
    tableRowClassName ({row, rowIndex}) {
      if (rowIndex % 2 === 1) {
        return 'warning-row'
      } else if (rowIndex % 2 === 0) {
        return 'success-row'
      }
      return ''
    },
    handleEdit (row) {
      this.options_projectNo = []
      this.projectInfo = []
      this.form = JSON.parse(JSON.stringify(row))
      this.dialogVisible = true
    },
    handleDelete (projectNo) {
      this.$axios.delete('/api/projectprogress/' + projectNo).then(res => { // 删除接口
        // console.log(projectNo)
        if (res.data.code === '0') {
          this.$message(
            {
              type: 'success',
              message: '删除成功'
            }
          )
        } else {
          this.$message(
            {
              type: 'error',
              message: res.msg
            }
          )
        }
        this.load() // 删除数据后，记得刷新页面
      })
    },
    handleSizeChange (pageSize) { // 改变每页显示的数目
      this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange (pageNum) { // 改变当前页码
      this.currentPage = pageNum
      this.load()
    },
    // calAbnolmalCaseRate: function () {
    //   this.form.abnolmalCaseRate = this.form.abnolmalCaseNumber / this.form.totalCaseNumber
    // },
    add () {
      this.dialogVisible = true
      this.form = {}
      this.projectInfo = [] // 重新将projectInfo置空
      // 获取undertake的projectNo(可以优化，不必每次都去获取)
      this.$axios.get('/api/undertakeproject/projectNo').then((res) => {
        // console.log(res)
        this.options_projectNo = res.data.data
      })
    },
    //  用于post请求新增数据  projectprogress
    save () {
      // 提交前需要将projectNo和projectName赋值到form里边；
      // 补充对projectNo的校验：为空报错；
      // console.log(this.projectInfo)
      if (this.projectInfo.length > 1) {
        this.form.projectNo = this.projectInfo[0] // 获取projecctNo
        this.form.projectName = this.projectInfo[1] // 获取projecctName
        this.form.testSystem = this.projectInfo[2] // 获取projecctName
        this.form.testmanager = this.projectInfo[3] // 获取projecctName
      }

      // 判断当前的projectNo是否存在于projectprogress表
      this.$axios.get('api/projectprogress/projectNo/' + this.form.projectNo).then(res => {
        if (res.data.code === '0') { // 更新
          this.$axios.put('/api/projectprogress', this.form).then(res => {
            // console.log(res)
            if (res.data.code === '0') {
              this.$message(
                {
                  type: 'success',
                  message: '更新成功'
                }
              )
            } else {
              this.$message(
                {
                  type: 'error',
                  message: res.data.msg
                }
              )
            }
            this.dialogVisible = false // 关闭弹窗
            this.load() // 刷新页面
          })
        } else { // 新增
          // console.log('start add')
          this.$axios.post('/api/projectprogress', this.form).then(res => {
            // console.log(res)
            if (res.data.code === '0') {
              this.$message({
                type: 'success',
                message: '新增成功'
              })
            } else {
              this.$message({
                type: 'error',
                message: res.data.msg
              })
            }
            this.dialogVisible = false // 关闭弹窗
            this.load() // 刷新页面
          })
        }
      })
    },
    //  用于页面加载时，将数据库数据载入
    load () {
      this.$axios.get('/api/projectprogress',
        { params: {
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          searchProjectNo: this.searchProjectNo,
          searchProjectName: this.searchProjectName,
          searchTestmanager: this.searchTestmanager
        }
        }
      ).then(res => {
        // console.log(res)
        this.tableData = res.data.data.records
        this.total = res.data.data.total
      })
    },
    open () {
      this.$confirm('确定删除吗?', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$message({
          type: 'success',
          message: '删除成功!'
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    }
  },
  // 用于计算反向用例比，执行用例比
  computed: {
    // 冒烟
    calAbnolmalCaseRate0: function () {
      if (!this.form.abnolmalCaseNumber0 || !this.form.totalCaseNumber0) {
        return 0
      }
      return this.form.abnolmalCaseNumber0 / this.form.totalCaseNumber0
    },
    calCasePassedRate0: function () {
      if (!this.form.passCaseNumber0 || !this.form.carryOutNumber0) {
        return 0
      }
      return this.form.passCaseNumber0 / this.form.carryOutNumber0
    },
    calCarryOutRate0: function () {
      if (!this.form.carryOutNumber0 || !this.form.totalCaseNumber0) {
        return 0
      }
      return this.form.carryOutNumber0 / this.form.totalCaseNumber0
    },
    calProjectBugsDensity0: function () {
      if (!this.form.totalCaseNumber0 || !this.form.totalBugs0) {
        return 0
      }
      return this.form.totalBugs0 / this.form.totalCaseNumber0
    },
    calPercentBugsNumber0: function () {
      if (!this.form.totalCaseNumber0 || !this.form.totalBugs0) {
        return 0
      }
      return this.form.totalBugs0 * 100 / this.form.totalCaseNumber0
    },
    calPersonMonthCaseNumber0: function () {
      if (!this.form.testPersonNumber0 || !this.form.totalCaseNumber0 || !this.form.projectPeriod0) {
        return 0
      }
      return this.form.totalCaseNumber0 / this.form.testPersonNumber0 / this.form.projectPeriod0
    },
    calPersonMonthBugsNumber0: function () {
      if (!this.form.testPersonNumber0 || !this.form.totalBugs0 || !this.form.projectPeriod0) {
        return 0
      }
      return this.form.totalBugs0 / this.form.testPersonNumber0 / this.form.projectPeriod0
    },
    // 第一轮
    calAbnolmalCaseRate1: function () {
      if (!this.form.abnolmalCaseNumber1 || !this.form.totalCaseNumber1) {
        return 0
      }
      return this.form.abnolmalCaseNumber1 / this.form.totalCaseNumber1
    },
    calCasePassedRate1: function () {
      if (!this.form.passCaseNumber1 || !this.form.carryOutNumber1) {
        return 0
      }
      return this.form.passCaseNumber1 / this.form.carryOutNumber1
    },
    calCarryOutRate1: function () {
      if (!this.form.carryOutNumber1 || !this.form.totalCaseNumber1) {
        return 0
      }
      return this.form.carryOutNumber1 / this.form.totalCaseNumber1
    },
    calProjectBugsDensity1: function () {
      if (!this.form.totalCaseNumber1 || !this.form.totalBugs1) {
        return 0
      }
      return this.form.totalBugs1 / this.form.totalCaseNumber1
    },
    calPercentBugsNumber1: function () {
      if (!this.form.totalCaseNumber1 || !this.form.totalBugs1) {
        return 0
      }
      return this.form.totalBugs1 * 100 / this.form.totalCaseNumber1
    },
    calPersonMonthCaseNumber1: function () {
      if (!this.form.testPersonNumber1 || !this.form.totalCaseNumber1 || !this.form.projectPeriod1) {
        return 0
      }
      return this.form.totalCaseNumber1 / this.form.testPersonNumber1 / this.form.projectPeriod1
    },
    calPersonMonthBugsNumber1: function () {
      if (!this.form.testPersonNumber1 || !this.form.totalBugs1 || !this.form.projectPeriod1) {
        return 0
      }
      return this.form.totalBugs1 / this.form.testPersonNumber1 / this.form.projectPeriod1
    },

    // 第二轮
    calAbnolmalCaseRate2: function () {
      if (!this.form.abnolmalCaseNumber2 || !this.form.totalCaseNumber2) {
        return 0
      }
      return this.form.abnolmalCaseNumber2 / this.form.totalCaseNumber2
    },
    calCasePassedRate2: function () {
      if (!this.form.passCaseNumber2 || !this.form.carryOutNumber2) {
        return 0
      }
      return this.form.passCaseNumber2 / this.form.carryOutNumber2
    },
    calCarryOutRate2: function () {
      if (!this.form.carryOutNumber2 || !this.form.totalCaseNumber2) {
        return 0
      }
      return this.form.carryOutNumber2 / this.form.totalCaseNumber2
    },
    calProjectBugsDensity2: function () {
      if (!this.form.totalCaseNumber2 || !this.form.totalBugs2) {
        return 0
      }
      return this.form.totalBugs2 / this.form.totalCaseNumber2
    },
    calPercentBugsNumber2: function () {
      if (!this.form.totalCaseNumber2 || !this.form.totalBugs2) {
        return 0
      }
      return this.form.totalBugs2 * 100 / this.form.totalCaseNumber2
    },
    calPersonMonthCaseNumber2: function () {
      if (!this.form.testPersonNumber2 || !this.form.totalCaseNumber2 || !this.form.projectPeriod2) {
        return 0
      }
      return this.form.totalCaseNumber2 / this.form.testPersonNumber2 / this.form.projectPeriod2
    },
    calPersonMonthBugsNumber2: function () {
      if (!this.form.testPersonNumber2 || !this.form.totalBugs2 || !this.form.projectPeriod2) {
        return 0
      }
      return this.form.totalBugs2 / this.form.testPersonNumber2 / this.form.projectPeriod2
    }
  },
  data () {
    return {
      searchProjectNo: '',
      searchProjectName: '',
      searchTestmanager: '',
      tableData: [

      ],
      total: 10,
      currentPage: 1,
      pageSize: 10,
      dialogVisible: false,
      form: {},
      options_projectNo: [],
      projectInfo: [] // 接受undertakeProject传过来的项目信息，用于select选择ProjectNo后，绑定projectNo和projectName; save时，需要重新设置form的projectNo和projectName
    }
  }
}
</script>

<style scoped>

</style>
