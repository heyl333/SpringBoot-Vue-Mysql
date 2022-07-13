import Vue from 'vue'
import App from './App'
import router from './router'

import ElementUI, {
  Radio,
  Aside,
  Header,
  Main,
  Menu,
  Submenu,
  MenuItem,
  MenuItemGroup,
  Dropdown,
  Form,
  FormItem,
  Col,
  Input,
  Row

} from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import store from './store'
import './assets/css/global.css'
import axios from 'axios'

Vue.use(ElementUI)
Vue.use(Radio)
Vue.use(Aside)
Vue.use(Header)
Vue.use(Main)
Vue.use(Menu)
Vue.use(Submenu)
Vue.use(MenuItem)
Vue.use(MenuItemGroup)
Vue.use(Dropdown)
Vue.use(Form)
Vue.use(FormItem)
Vue.use(Col)
Vue.use(Input)
Vue.use(Row)

Vue.config.productionTip = false

/* eslint-disable no-new */
new Vue({
  el: '#app',
  store,
  router,
  components: { App },
  template: '<App/>',
  render: h => h(App)
})

/*
axios这部分后期可以优化下写到一个文件里边去，再从这个文件引用
* */
Vue.prototype.$axios = axios
// axios.defaults.baseURL = '/api'// 使每次请求都会带一个 /api 前缀

// 设置拦截器
axios.interceptors.request.use(config => {
  config.headers['Content-Type'] = 'application/json;charset=utf-8' // 设置请求头
  // 取出sessionStorage里边的信息，如果为空，不能跳转到相应页面（例如：未登录不能跳转到页面）
  let userJson = sessionStorage.getItem('user')
  if (!userJson) {
    router.push('/login')
  }
  return config
})
