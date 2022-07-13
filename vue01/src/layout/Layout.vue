<template>
  <div>
    <!--    头部-->
    <Header />
    <!--    侧边栏-->
    <div style="display: flex">
      <Aside />
      <!--      内容-->
      <router-view style="flex: 1"/>
    </div>
  </div>
</template>

<script>

import Header from '../components/Header'
import Aside from '../components/Aside'

export default {
  name: 'Layout',
  components: {
    Header,
    Aside
  }
}
</script>

<style>
html,body {
  margin: 0;
  padding: 0;
}
</style>
