<template>
  <div class="login">
    <div class="login_info">
      <div class="login_title">
        <img src="../assets/img/logo.png" alt="" />
        <div class="logo_text">哇塞电影 - 员工登录</div>
      </div>

      <div class="login_form">
        <p>Username</p>
        <el-input v-model="userName" placeholder="账号"></el-input>
        <p>Password</p>
        <el-input
          placeholder="密码"
          v-model="passWord"
          show-password
        ></el-input>

        <div class="btn">
          <el-button class="login-form-button" type="primary" @click="getLogin"
            >登录客户端</el-button
          >
        </div>
        <div class="btn">
          <el-button class="login-form-button" type="primary" @click="getLogin1"
            >登录后台</el-button
          >
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { Login } from "../api/customer";

export default {
  data() {
    return {
      userName: "",
      passWord: "",
      checked: "",
    };
  },
  methods: {
    getLogin() {
      const LoginData = {
        username: this.userName,
        password: this.passWord,
      };
      Login(LoginData).then((res) => {
        // console.log(res);
        if (res.code == 200) {
          if (res.data.success === "success") {
            localStorage.setItem("token", res.data.token); //localStorage 用于长久保存整个网站的数据，保存的数据没有过期时间，直到手动去删除。
            localStorage.setItem("wid", res.data.worker.id); //保存set  读取get
            this.$message({
              message: res.data.msg,
              type: "success",
            });
            console.log(localStorage.getItem("wid"));
            this.$router.push("/customer");
          } else {
            this.$message({
              message: res.data.msg,
              type: "warning",
            });
          }
        } else {
          this.$message({
            message: res.data.msg,
            type: "warning",
          });
        }
      });
    },
    getLogin1() {
      const LoginData = {
        username: this.userName,
        password: this.passWord,
      };
      Login(LoginData).then((res) => {
        // console.log(res);
        if (res.code == 200) {
          if (res.data.success === "success") {
            localStorage.setItem("token", res.data.token); //localStorage 用于长久保存整个网站的数据，保存的数据没有过期时间，直到手动去删除。
            localStorage.setItem("wid", res.data.worker.id); //保存set  读取get
            this.$message({
              message: res.data.msg,
              type: "success",
            });
            console.log(localStorage.getItem("wid"));
            this.$router.push("/back");
          } else {
            this.$message({
              message: res.data.msg,
              type: "warning",
            });
          }
        } else {
          this.$message({
            message: res.data.msg,
            type: "warning",
          });
        }
      });
    },
  },
};
</script>

<style scoped>
.login {
  width: 100%;
  height: 100%;
}
.login_info {
  width: 500px;
  margin: 50px auto;
}
.login_title {
  height: 75px;
}
.login_title img {
  float: left;
  width: 75px;
  height: 75px;
}
.logo_text {
  line-height: 75px;
  font-size: 30px;
  font-weight: bold;
  float: left;
  margin-left: 50px;
}
.login_form {
  text-align: left;
  font-size: 18px;
  color: #91949c;
  font-weight: bold;
}
.login_form p {
  height: 60px;
  line-height: 60px;
}
.forget {
  float: right;
  margin: 20px 0;
  height: 30px;
  line-height: 30px;
}
.forget .login_link {
  font-size: 16px;
  color: #91949c;
  font-weight: bold;
}
.login-form-button {
  font-weight: bold;
  width: 48%;
  margin-top: 40px;
  margin-right: 10px;
  float: left;
}
</style>
