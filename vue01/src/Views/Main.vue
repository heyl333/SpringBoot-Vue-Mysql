<template>
  <el-container style="height:100%">
    <el-aside width="auto">
      <Common-aside> </Common-aside>
    </el-aside>
    <el-container>
      <el-header>
        <common-header> </common-header>
      </el-header>
      <el-main>
<!--        展示Main嵌套的子路由-->
        <router-view> </router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import CommonAside from '../components/CommonAside'
import CommonHeader from '../components/CommonHeader'

export default {
  name: 'Home',
  components: {
    CommonAside,
    CommonHeader
  }
}
</script>

<style lang="less" scoped>
.el-header{
  background-color: #333333;
}
.el-main{
  padding-top: 0;
}

</style>
