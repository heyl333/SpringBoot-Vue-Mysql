<template>
  <div>
    <el-menu
      style="width: 200px;min-height: calc(100vh - 50px)"
      :default-active="path"
      class="el-menu-vertical-demo"
      router
      @open="handleOpen"
      @close="handleClose">
<!--   :default-active,这里的 :是v-bind的缩写，用于动态绑定data里边的变量    -->
      <!--  default-active可设置哪个index高亮，这里设置跳转到相应路径时index对应路径的label高亮    -->
      <!--  router可用于设置页面跳转，此时就不需要在组件里边写 @click=‘router.push’  -->
<!--      项目管理栏-->
      <el-submenu index="1">
        <template slot="title">
          <i class="el-icon-location"></i>
          <span>项目管理</span>
        </template>
        <el-menu-item index="/undertakeProjects">项目承接</el-menu-item>
        <el-menu-item index="/productTime" >项目时间</el-menu-item>
        <el-menu-item index="/productProgress" >项目进度</el-menu-item>
        <el-menu-item index="1-4" >项目人员及工作量</el-menu-item>
      </el-submenu>
<!--      人员信息栏-->
      <el-submenu index="2">
        <template slot="title">
          <i class="el-icon-document"></i>
          <span>人员管理</span>
        </template>
        <el-menu-item index="2-1" >人员基本信息</el-menu-item>
        <el-menu-item index="2-2" >工作量信息</el-menu-item>
      </el-submenu>
<!--       项目展示栏-->
      <el-submenu index="3">
        <template slot="title">
          <i class="el-icon-menu"></i>
          <span>项目展示</span>
        </template>
        <el-menu-item index="3-1" >项目信息展示</el-menu-item>
      </el-submenu>
<!--      用户管理（管理员权限）-->
      <el-submenu index="4">
        <template slot="title">
          <i class="el-icon-user"></i>
          <span>用户管理</span>
        </template>
        <el-menu-item index="/springbootUser" >用户管理</el-menu-item>
      </el-submenu>
    </el-menu>
  </div>
</template>

<script>
export default {
  name: 'Aside',
  methods: {
    handleOpen () {

    },
    handleClose () {

    }
  },
  data () {
    return {
      path: this.$route.path // 设置默认高亮的菜单
    }
  },
  created () { // 钩子函数
  }
}
</script>

<style scoped>

</style>
