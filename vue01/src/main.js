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
import axios from 'axios'
import './assets/css/global.css'

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

Vue.prototype.$axios = axios
// axios.defaults.baseURL = '/api'// 使每次请求都会带一个 /api 前缀
