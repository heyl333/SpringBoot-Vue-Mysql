// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'

// eslint-disable-next-line import/no-duplicates
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
// eslint-disable-next-line import/no-duplicates
} from 'element-ui'
// eslint-disable-next-line import/no-duplicates,no-unused-vars
import {Button, Container, Select} from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import Common from './components/CommonAside'
import './assets/less/index.less'
import store from './store'
import axios from 'axios'

Vue.use(ElementUI)
Vue.use(Radio)
Vue.use(Container)
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
Vue.use(Common)

Vue.component(Button.name, Button)
Vue.component(Select.name, Select)

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

Vue.prototype.$axios = axios
// axios.defaults.baseURL = '/api'// 使每次请求都会带一个 /api 前缀
