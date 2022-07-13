<template>
  <div style="width: 100%;height: 100vh;background-color: darkblue;overflow: hidden">
    <div style="width: 400px;margin: 150px auto">
<!--      欢迎登录标语-->
      <div style="color: #cccccc;text-align: center;font-size: 30px;padding: 30px 0">欢迎登录</div>
<!--      用户名密码输入框-->
      <el-form ref="form" :model="form" :rules="rules" >
        <el-form-item prop="username">
          <el-input prefix-icon="el-icon-user-solid" v-model="form.username"></el-input>
        </el-form-item>
        <el-form-item  prop="password">
          <el-input prefix-icon="el-icon-lock" v-model="form.password" show-password></el-input>
        </el-form-item>
        <el-form-item>
          <el-button style="width: 100%" type="primary" @click="login">登录</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Login',
  data () {
    return {
      form: {},
      rules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' }
        ]
      }
    }
  },
  created () {
    sessionStorage.removeItem('user')
  },
  methods: {
    login () {
      // 使用validate函数验证通过规则后才会发送请求
      this.$refs['form'].validate((valid) => {
        if (valid) {
          this.$axios.post('/api/user/login', this.form).then(res => {
            if (res.data.code === '0') {
              this.$message(
                {
                  type: 'success',
                  message: '登录成功'
                })
              sessionStorage.setItem('user', JSON.stringify(res.data)) // 缓存用户信息
              this.$router.push('/') // 设置登录成功后要跳转到主页
            } else {
              this.$message(
                {
                  type: 'error',
                  message: res.data.msg
                }
              )
            }
          })
        } else {
          console.log('error submit!!')
          return false
        }
      })
    }
  }
}
</script>

<style scoped>

</style>
