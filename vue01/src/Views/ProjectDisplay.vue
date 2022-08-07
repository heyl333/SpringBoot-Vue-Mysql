<template>
  <div>
    <div style="margin: 10px 0">
      <el-button @click="exportdata" type="primary">导出</el-button>
    </div>

    <div style="margin: 10px 0">
<!--      项目编号-->
<!--      <el-input v-model="searchProjectNo" placeholder="请输入内容" style="width: 20%;margin-right: 30px" clearable></el-input>-->
<!--      项目名称-->
<!--      <el-input v-model="searchProjectName" placeholder="请输入内容" style="width: 20%;margin-right: 30px" clearable></el-input>-->
<!--      项目类型-->
<!--      <el-input v-model="searchProjectType" placeholder="请输入内容" style="width: 20%;margin-right: 30px" clearable></el-input>-->
<!--  项目时间取投产时间    -->
      项目年份
      <el-input v-model="searchProjectTime" placeholder="请输入内容" style="width: 20%;margin-right: 30px" clearable></el-input>
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
        :formatter="formatter_time"
        prop="productTime"
        label="投产时间"
      >
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
    handleSizeChange (pageSize) { // 改变每页显示的数目
      this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange (pageNum) { // 改变当前页码
      this.currentPage = pageNum
      this.load()
    },
    //  用于页面加载时，将数据库数据载入
    load () {
      this.$axios.get('/api/projectdisplay',
        { params: {
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          searchProjectTime: this.searchProjectTime }
        }
      ).then(res => {
        // this.tableData = res.data.data.records.forEach(item => { item.testAdmitIn = (item.projectTimes.length > 0) ? item.projectTimes[0].testAdmitIn : '' })
        res.data.data.records.forEach(item => { item.productTime = (item.projectTimes.length > 0) ? item.projectTimes[0].productTime : '' })
        this.tableData = res.data.data.records
        // console.log('tabledata')
        // console.log(this.tableData)
        this.total = res.data.data.total
      })
    },
    // 设置前台日期
    formatter_time (row, column, cellValue, index) {
      console.log(cellValue)
      if (cellValue) {
        const t = new Date(cellValue) // row 表示一行数据, updateTime 表示要格式化的字段名称
        let year = t.getFullYear()
        let month = t.getMonth() + 1
        let day = t.getDate()
        const newTime =
          year +
          '-' +
          (month < 10 ? '0' + month : month) +
          '-' +
          (day < 10 ? '0' + day : day)
        return newTime
      }
    },
    exportdata () {
      this.$message.closeAll()
      this.$message.info('导出中，请稍后~')
      this.export()
    },
    export () {
      // 获取后台数据接口

    }

  },
  data () {
    return {
      // searchProjectNo: '',
      // searchProjectName: '',
      // searchProjectType: '',
      searchProjectTime: '',
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
