<template>
  <div>
    <el-menu
      style="width: 200px;min-height: calc(100vh - 50px)"
      default-active="2"
      class="el-menu-vertical-demo"
      @open="handleOpen"
      @close="handleClose">
<!--      项目管理栏-->
      <el-submenu index="1">
        <template slot="title">
          <i class="el-icon-location"></i>
          <span>项目管理</span>
        </template>
        <el-submenu index="1-1">
          <template slot="title">项目承接</template>
        </el-submenu>
        <el-submenu index="1-2">
          <template slot="title">项目时间</template>
        </el-submenu>
        <el-submenu index="1-3">
          <template slot="title">项目进展</template>
        </el-submenu>
        <el-submenu index="1-4">
          <template slot="title">项目人员及工作量</template>
        </el-submenu>
      </el-submenu>
<!--      人员信息栏-->
      <el-submenu index="2">
        <template slot="title">
          <i class="el-icon-document"></i>
          <span>人员管理</span>
        </template>
        <el-submenu index="2-1">
          <template slot="title">人员基本信息</template>
        </el-submenu>
        <el-submenu index="2-2">
          <template slot="title">工作量信息</template>
        </el-submenu>
      </el-submenu>
<!--       项目展示栏-->
      <el-submenu index="3">
        <template slot="title">
          <i class="el-icon-menu"></i>
          <span>项目展示</span>
        </template>
        <el-submenu index="3-1">
          <template slot="title">项目信息展示</template>
        </el-submenu>
      </el-submenu>
    </el-menu>
  </div>
</template>

<script>
export default {
  name: 'Aside'
}
</script>

<style scoped>

</style>
