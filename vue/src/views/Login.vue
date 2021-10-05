<template >
  <div  class="login"  style="width: 100%;height: 100vh;overflow: hidden;" >
    <div style="width:300px;margin: 150px auto">
      <div style="color: #cccccc;font-size: 30px;text-align: center; padding: 30px 0">
        欢迎登录
      </div>
      <el-form ref="form" :model="form" :rules="rules">
        <el-form-item prop="username">
          <el-input prefix-icon="el-icon-user-solid" v-model="form.username" size="normal"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input prefix-icon="el-icon-lock" v-model="form.password" size="normal" show-password></el-input>
        </el-form-item>
        <el-form-item>
          <div style="display:flex">
            <el-input prefix-icon="el-icon-key" v-model="form.validCode" style="width:50%" placeholder="请输入验证码"></el-input>
            <ValidCode @input="createValidCode"></ValidCode>
          </div>
        </el-form-item>
        <el-form-item>
          <el-button style="width:100%" type="primary" @click="login()">登录</el-button>
        </el-form-item>
        <el-form-item>
          <el-button style="width:100%" type="primary" @click="$router.push('/register')">前往注册</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>

import request from "../utils/request";
import ValidCode from "../components/ValidCode"

export default {
  name: "Login",
  components:{
    ValidCode
  },
  data() {
    return {
      url:'../assets/b1.jpg',
      form: {},
      validCode:"",
      rules: {
        username: [
          {
            required: true,
            message: '请输入用户名',
            trigger: 'blur',
          }
        ],
        password: [
          {
            required: true,
            message: '请输入密码',
            trigger: 'blur',
          }
        ],
      }

    }
  },
  created(){
    sessionStorage.removeItem("user")
  },
  methods: {
    createValidCode(data){
      this.validCode = data;
    },
    login() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          if (!this.form.validCode) {
            this.$message.error("请填写验证码")
            return
          }
          if(this.form.validCode.toLowerCase() !== this.validCode.toLowerCase()) {
            this.$message.error("验证码错误")
            return
          }
          request.post("http://localhost:9090/user/login", this.form).then(res => {
            if (res.code === '0') {
              this.$message({
                type: "success",
                message: "登录成功"
              })
              sessionStorage.setItem("user", JSON.stringify(res.data))  // 缓存用户信息
              this.$router.push("/course")  //登录成功之后进行页面的跳转，跳转到主页
            } else {
              this.$message({
                type: "error",
                message: res.msg
              })
            }
          })
        }
      })
    }
    }
  }
</script>

<style scoped>

</style>