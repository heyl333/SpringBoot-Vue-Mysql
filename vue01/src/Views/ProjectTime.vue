<template>
  <div>
    <div style="margin: 10px 0">
      <el-button type="primary" @click="add">新增</el-button>
<!--      <el-button type="primary">导入</el-button>-->
<!--      <el-button type="primary">导出</el-button>-->
    </div>

    <div style="margin: 10px 0">
      <el-input v-model="search" placeholder="请输入内容" style="width: 20%" clearable></el-input>
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
      <el-dialog  align="center" title="项目时间信息录入" :visible.sync="dialogVisible" width="80%">
        <el-form :model="form" label-width="120px"  >
          <el-row inline="true">
            <el-col :span="12">
              <el-form-item label="项目编号" >
<!--                利用数组[]，可以实现绑定多个值-->
<!--             新增时，从projectInfo取值   -->
                <template v-if="this.options_projectNo.length >=1">
                  <el-select   v-model="projectInfo" placeholder="项目编号" style="float: left">
                    <el-option v-for="item in this.options_projectNo" :key="item.id" :label="item.projectNo" :value="[item.projectNo , item.projectName]"></el-option>
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

            <el-col :span="12">
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
            <el-col :span="12">
              <el-form-item label="测试系统" >
                <el-select  v-model="form.testSystem" placeholder="测试系统" style="float: left">
                  <el-option  label="OIBS" value="OIBS"></el-option>
                  <el-option  label="ITS"  value="ITS"></el-option>
                  <el-option  label="PAIC" value="PAIC"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="测试经理">
                <el-select v-model="form.testmanager" placeholder="测试经理" style="float: left">
                  <el-option label="TestManager1" value="TestManager1"></el-option>
                  <el-option label="TestManager2" value="TestManager2"></el-option>
                  <el-option label="TestManager3" value="TestManager3"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row inline="true">
            <el-col :span="12">
              <el-form-item label="准入时间" >
                <el-date-picker
                  v-model="form.testAdmitIn"
                  type="date"
                  value-format="yyyy-MM-dd"
                  placeholder="选择日期"
                  style="float: left"
                >
<!--            style="float: left"用于设置控件对齐      -->
                </el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="准出时间">
                <el-date-picker
                  v-model="form.testAdmitOut"
                  type="date"
                  value-format="yyyy-MM-dd"
                  placeholder="选择日期"
                  style="float: left"
                >
                </el-date-picker>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row inline="true">
            <el-col :span="12">
              <el-form-item label="投产时间" >
                <el-date-picker
                  v-model="form.productTime"
                  type="date"
                  value-format="yyyy-MM-dd"
                  placeholder="选择日期"
                  style="float: left"
                >
                </el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="完成情况">
                <el-select v-model="form.completeStatus" placeholder="完成情况" style="float: left">
                  <el-option label="已完成" value="已完成"></el-option>
                  <el-option label="第一轮测试" value="第一轮测试"></el-option>
                  <el-option label="第二轮测试" value="第二轮测试"></el-option>
                </el-select>
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
      this.$axios.delete('/api/projecttime/' + projectNo).then(res => { // 删除接口
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
    //  用于post请求新增数据  projecttime
    save () {
      // 提交前需要将projectNo和projectName赋值到form里边；
      // 补充对projectNo的校验：为空报错；
      // console.log(this.projectInfo)
      if (this.projectInfo.length > 1) {
        this.form.projectNo = this.projectInfo[0] // 获取projecctNo
        this.form.projectName = this.projectInfo[1] // 获取projecctName
      }
      console.log('this.form.projectNo')
      console.log(this.form.projectNo)
      // 判断当前的projectNo是否存在于projecttime表
      this.$axios.get('api/projecttime/projectNo/' + this.form.projectNo).then(res => {
        // res.data.code === '0'说明已有projectNo，更新；否则，新增
        if (res.data.code === '0') { // 更新
          this.$axios.put('/api/projecttime', this.form).then(res => {
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
            this.options_projectNo = []
            this.dialogVisible = false // 关闭弹窗
            this.load() // 刷新页面
          })
        } else { // 新增
          // console.log('start add')
          this.$axios.post('/api/projecttime', this.form).then(res => {
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
            this.options_projectNo = []
            this.dialogVisible = false // 关闭弹窗
            this.load() // 刷新页面
          })
        }
      })
    },
    //  用于页面加载时，将数据库数据载入
    load () {
      this.$axios.get('/api/projecttime',
        { params: {
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          search: this.search}
        }
      ).then(res => {
        // console.log(res)
        this.tableData = res.data.data.records
        console.log('res.data.data.records')
        console.log(res.data.data.records)
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
  // 希望使用computed实现projectName根据projectNo反显;
  // computed: {
  //   get_ProjectNo: function () {
  //     return this.options_projectNo.filter(item => {
  //       console.log(this.form.projectNo)
  //       return item.projectNo === this.form.projectNo
  //     })
  //   }
  // },
  data () {
    return {
      search: '',
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
