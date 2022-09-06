<template>
  <div class="login">
    <div class="login_info">
      <div class="login_title">
        <img src="../assets/img/logo.png" alt="" />
        <div class="logo_text">哇塞电影 - 登录</div>
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

        <div class="forget">
          <el-tooltip content="请联系管理员" placeholder="bottom" effect="dark">
            <el-link class="login_link" :underline="false">忘记密码?</el-link>
          </el-tooltip>
        </div>

        <div class="btn">
          <el-button class="login-form-button" type="primary" @click="getLogin"
            >SIGN IN</el-button
          >
        </div>

        <div class="login_footer">
          <el-link :underline="false" class="rg" href="Register"
            >还没注册账号?去注册
            <i class="el-icon-right"></i>
          </el-link>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { Login } from "../api/user";

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

      // this.$axios
      //   .post(this.$store.state.url + "/api/user/login", LoginData)
      //   .then((res) => {
      //     if (res.data.success == true) {
      //       this.$message({
      //         message: "登陆成功",
      //         type: "success",
      //       });
      //       localStorage.setItem("token", res.data.data.token);
      //       localStorage.setItem("uid", res.data.data.user.id);
      //       this.$router.push("/");
      //     }
      //   });

      Login(LoginData).then((res) => {
        // console.log(res);
        if (res.code == 200) {
          if (res.data.success === "success") {
            localStorage.setItem("token", res.data.token); //localStorage 用于长久保存整个网站的数据，保存的数据没有过期时间，直到手动去删除。
            localStorage.setItem("uid", res.data.user.id); //保存set  读取get
            this.$message({
              message: res.data.msg,
              type: "success",
            });
            this.$router.push("/");
          } else {
            this.$message({
              message: res.data.msg,
              type: "warning",
            });
          }
        } else {
          this.$message({
            message: res.msg,
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
  width: 100%;
}
.login_footer {
  height: 50px;
  line-height: 50px;
  text-align: center;
}
.login_footer .rg {
  font-weight: bold;
  font-size: 16px;
}
</style>
