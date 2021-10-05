<template>
  <div class="login" style="width: 100%;height: 100vh;overflow: hidden">
    <div style="width:300px;margin: 150px auto">
      <div style="color: #cccccc;font-size: 30px;text-align: center; padding: 30px 0">
        欢迎注册
      </div>
      <el-form ref="form" :model="form" :rules="rules">
        <el-form-item prop="username">
          <el-input prefix-icon="el-icon-user-solid" v-model="form.username" size="normal"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input prefix-icon="el-icon-lock" v-model="form.password" size="normal" show-password></el-input>
        </el-form-item>

        <el-form-item>
          <el-input prefix-icon="el-icon-lock" v-model="form.confirm" size="normal" show-password></el-input>
        </el-form-item>
        <el-form-item>
          <el-button style="width:100%" type="primary" @click="register()">注册</el-button>
        </el-form-item>

        <el-form-item>
          <el-button style="width:100%" type="primary" @click="$router.push('/login')">前往登录</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>

import request from "../utils/request";

export default {
  name: "Login",
  data() {
    return {
      form: {},
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
  methods: {
    register() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          if (this.form.password !== this.form.confirm) {
            this.$message({
              type: "error",
              message: "两次密码不一致！",
            })
            return;
          }
          request.post("http://localhost:9090/user/register", this.form).then(res => {
            if (res.code === '0') {
              this.$message({
                type: "success",
                message: "注册成功",
              })
              this.$router.push("/login");  // 登录成功后跳转到主页
            } else {
              this.$message({
                type: "error",
                message: reg.msg,
              })
            }
          });
        }
      })

    }
  }
}
</script>

<style scoped>

</style>