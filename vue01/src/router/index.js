import Vue from 'vue'
import Router from 'vue-router'
Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Layout',
      component: () => import('../layout/Layout'),
      redirect: '/springbootuser', // 重定向跳转路由
      children: [ // 嵌套路由
        {
          path: 'undertakeProjects',
          name: 'UndertakeProjects',
          component: () => import('../Views/UndertakeProjects')
        },
        {
          path: 'springbootUser',
          name: 'SpringBootUser',
          component: () => import('../Views/SpringBootUser')
        }
      ]
    },
    {
      path: '/login',
      name: 'Login',
      component: () => import('../Views/Login')
    }
  ]
})
