<template>
  <div>
    <div style="margin: 10px 0">
      <el-button type="primary" @click="add">新增</el-button>
      <el-button type="primary">导入</el-button>
      <el-button type="primary">导出</el-button>
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
        prop="id"
        label="ID"
        sortable
        >
      </el-table-column>
      <el-table-column
        prop="username"
        label="用户名"
        >
      </el-table-column>
      <el-table-column
        prop="password"
        label="密码"
      >
      </el-table-column>
      <el-table-column
        prop="nickName"
        label="昵称"
      >
      </el-table-column>
      <el-table-column
        prop="age"
        label="年龄"
      >
      </el-table-column>
      <el-table-column
        prop="sex"
        label="性别"
      >
      </el-table-column>
      <el-table-column
        prop="address"
        label="地址">
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
      <el-dialog title="提示" :visible.sync="dialogVisible" width="30%">

        <el-form :model="form" label-width="120px">
            <el-form-item label="用户名">
              <el-input v-model="form.username" style="width: 80%"></el-input>
            </el-form-item>
          <el-form-item label="密码">
            <el-input v-model="form.password" style="width: 80%"></el-input>
          </el-form-item>
          <el-form-item label="昵称">
            <el-input v-model="form.nickName" style="width: 80%"></el-input>
          </el-form-item>
          <el-form-item label="年龄">
            <el-input v-model="form.age" style="width: 80%"></el-input>
          </el-form-item>
          <el-form-item label="性别">
            <el-radio v-model="form.sex" label="男">男</el-radio>
            <el-radio v-model="form.sex" label="女">女</el-radio>
            <el-radio v-model="form.sex" label="未知">未知</el-radio>
          </el-form-item>
          <el-form-item label="地址">
            <el-input type="textarea" v-model="form.address" style="width: 80%"></el-input>
          </el-form-item>
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
      this.$axios.delete('/api/user/' + id).then(res => { // 删除接口
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
    },
    //  用于post请求新增数据
    save () {
      if (this.form.id) { // 更新
        this.$axios.put('/api/user', this.form).then(res => {
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
                message: res.msg
              }
            )
          }
          this.dialogVisible = false // 关闭弹窗
          this.load() // 刷新页面
        })
      } else { // 新增
        this.$axios.post('/api/user', this.form).then(res => {
          console.log(res)
          if (res.data.code === '0') {
            this.$message({
              type: 'success',
              message: '新增成功'
            })
          } else {
            this.$message({
              type: 'error',
              message: res.msg
            })
          }
          this.dialogVisible = false // 关闭弹窗
          this.load() // 刷新页面
        })
      }
    },
    //  用于页面加载时，将数据库数据载入
    load () {
      this.$axios.get('/api/user',
        { params: {
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          search: this.search}
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
      search: '',
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
