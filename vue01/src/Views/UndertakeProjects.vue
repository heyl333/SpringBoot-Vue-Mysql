<template>
  <div>
    <div style="margin: 10px 0">
      <el-button type="primary" @click="add">新增</el-button>
      <el-button type="primary">导入</el-button>
      <el-button type="primary">导出</el-button>
    </div>

    <div style="margin: 10px 0">
      项目编号
      <el-input v-model="searchProjectNo" placeholder="请输入内容" style="width: 20%;margin-right: 30px" clearable></el-input>
      项目名称
      <el-input v-model="searchProjectName" placeholder="请输入内容" style="width: 20%;margin-right: 30px" clearable></el-input>
      项目类型
      <el-input v-model="searchProjectType" placeholder="请输入内容" style="width: 20%;margin-right: 30px" clearable></el-input>
      <el-button type="primary" style="margin-left: 5px" @click="load">搜索</el-button>
    </div>
    <el-table
      :data="tableData"
      style="width: 100%"
      :row-class-name="tableRowClassName">
      <el-table-column
        prop="id"
        label="ID"
        sortable
      >
      </el-table-column>
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
        prop="projectType"
        label="项目类型"
      >
      </el-table-column>
      <el-table-column
        prop="masterSlave"
        label="主办协办"
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
          <el-popconfirm title="确定删除吗？" @confirm="handleDelete(scope.row.id)">
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
      <el-dialog  align="center" title="项目承接信息录入" :visible.sync="dialogVisible" width="80%">

        <el-form :model="form" label-width="120px"  >
          <el-row inline="true">
            <el-col :span="12">
              <el-form-item label="项目编号">
<!--            suffix-icon表示在后边加图标， el-icon-project图标不存在，它的作用在于增加宽度，使得与select组件对齐   -->
                <el-input v-model="form.projectNo" suffix-icon="el-icon-project" ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="项目名称">
                <el-input v-model="form.projectName" suffix-icon="el-icon-project"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row inline="true">
            <el-col :span="12">
              <el-form-item label="测试系统" >
                <el-select  v-model="form.testSystem" placeholder="测试系统" style="width: 100%">
                  <el-option label="OIBS" value="OIBS"></el-option>
                  <el-option   label="ITS" value="ITS"></el-option>
                  <el-option  label="PAIC" value="PAIC"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="测试经理">
                <el-select v-model="form.testmanager" placeholder="测试经理" style="width: 100%">
                  <el-option label="TestManager1" value="TestManager1"></el-option>
                  <el-option label="TestManager2" value="TestManager2"></el-option>
                  <el-option label="TestManager3" value="TestManager3"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row >
            <el-col :span="12">
              <el-form-item label="项目类型" >
                <el-select v-model="form.projectType" placeholder="项目类型" style="width: 100%">
                  <el-option label="科维" value="科维"></el-option>
                  <el-option label="科项" value="科项"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="主办协办">
                <el-select v-model="form.masterSlave" placeholder="主办协办" style="width: 100%">
                  <el-option label="主办" value="主办"></el-option>
                  <el-option label="协办" value="协办"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row >
            <el-col :span="12">
              <el-form-item label="开发部门" >
                <el-select v-model="form.developmentDep" placeholder="开发部门" style="width: 100%">
                  <el-option label="开发一部" value="开发一部"></el-option>
                  <el-option label="开发二部" value="开发二部"></el-option>
                  <el-option label="开发三部" value="开发三部"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="境内境外">
                <el-select v-model="form.domesticForeign" placeholder="境内境外" style="width: 100%">
                  <el-option label="境内" value="境内"></el-option>
                  <el-option label="境外" value="境外"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row >
            <el-col>
              <el-form-item label="fp(功能点)">
                <el-input type="textarea" v-model="form.fp" style="width: 100%"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row >
            <el-col>
              <el-form-item label="简介">
                <el-input type="textarea" v-model="form.descrip" style="width: 100%"></el-input>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row >
            <el-col :span="8">
              <el-form-item label="性能测试" >
                <el-select v-model="form.performanceTest" placeholder="承接情况" style="width: 100%">
                  <el-option label="承接" value="承接"></el-option>
                  <el-option label="不承接" value="不承接"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="安全测试" >
                <el-select v-model="form.safetyTest" placeholder="承接情况" style="width: 100%">
                  <el-option label="承接" value="承接"></el-option>
                  <el-option label="不承接" value="不承接"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="接口测试" >
                <el-select v-model="form.interfaceTest" placeholder="承接情况" style="width: 100%">
                  <el-option label="承接" value="承接"></el-option>
                  <el-option label="不承接" value="不承接"></el-option>
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
      this.form = JSON.parse(JSON.stringify(row))
      this.dialogVisible = true
    },
    handleDelete (id) {
      this.$axios.delete('/api/undertakeproject/' + id).then(res => { // 删除接口
        console.log(id)
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
              message: '删除失败'
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
    },
    //  用于post请求新增数据  undertakeproject
    save () {
      if (this.form.id) { // 更新
        this.$axios.put('/api/undertakeproject', this.form).then(res => {
          console.log(res)
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
                message: '更新失败'
              }
            )
          }
          this.dialogVisible = false // 关闭弹窗
          this.load() // 刷新页面
        })
      } else { // 新增
        this.$axios.post('/api/undertakeproject', this.form).then(res => {
          console.log(res)
          if (res.data.code === '0') {
            this.$message({
              type: 'success',
              message: '新增成功'
            })
          } else {
            this.$message({
              type: 'error',
              message: '新增失败'
            })
          }
          this.dialogVisible = false // 关闭弹窗
          this.load() // 刷新页面
        })
      }
    },
    //  用于页面加载时，将数据库数据载入
    load () {
      this.$axios.get('/api/undertakeproject',
        { params: {
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          searchProjectNo: this.searchProjectNo,
          searchProjectName: this.searchProjectName,
          searchProjectType: this.searchProjectType}
        }
      ).then(res => {
        console.log(res)
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
  data () {
    return {
      searchProjectNo: '',
      searchProjectName: '',
      searchProjectType: '',
      tableData: [

      ],
      total: 10,
      currentPage: 1,
      pageSize: 10,
      dialogVisible: false,
      form: {}
    }
  }
}
</script>

<style scoped>

</style>
