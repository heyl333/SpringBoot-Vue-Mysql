<template>
  <div id="app">
<!--    <img src="./assets/logo.png">-->
<!--    <router-link to="/main">main</router-link>-->
<!--    <router-link to="/content">content</router-link>-->
<!--    <router-link to="/select_demo">select_demo</router-link>-->
    <router-view/>
  </div>
</template>

<script>
export default {
  name: 'App'
}
</script>

<style>
html,body {
  margin: 0;
  padding: 0;
}
#app {
  height: 100vh;
}
</style>
