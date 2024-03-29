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
        },
        {
          path: 'productTime',
          name: 'productTime',
          component: () => import('../Views/ProjectTime')
        },
        {
          path: 'productProgress',
          name: 'productProgress',
          component: () => import('../Views/ProjectProgress')
        },
        {
          path: 'projectDisplay',
          name: 'projectDisplay',
          component: () => import('../Views/ProjectDisplay')
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
