<template>
  <div style="height: 50px;line-height: 50px;border-bottom: 1px solid #ccc;display: flex">
    <div style="width: 200px;padding-left: 30px;font-weight: bold;color: #3a8ee6">项目管理系统</div>
    <div style="flex: 1"></div>
    <div style="width: 100px">
      <el-dropdown trigger="click">
        <span class="el-dropdown-link">
          张无忌<i class="el-icon-arrow-down el-icon--right"></i>
        </span>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item>个人信息</el-dropdown-item>
<!--      在给Vue组件（el-dropdown-item就是一个组件）绑定事件的时候，必须加上navtive修饰符才能生效，navtive可以监听根元素的原生事件-->
          <el-dropdown-item @click.native="$router.push('/login')">退出系统</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>

  </div>
</template>

<script>
export default {
  methods: {
    push () {
      this.$router.push('/login')
    }
  },
  watch: {
    $route () {
    }
  }
}
</script>

<style>

</style>
