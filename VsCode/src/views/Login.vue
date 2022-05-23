<template>
  <div class="login">
    <div class="login_info">
      <div class="login_title">
        <img src="../assets/img/logo.png" alt="" />
        <div class="login_text">哇塞电影-登录</div>
      </div>

      <div class="login_form">
        <p>Username</p>
        <el-input v-model="userName" placeholder="账号"></el-input>
        <p>PassWord</p>
        <el-input
          v-model="passWord"
          placeholder="密码"
          show-password
        ></el-input>

        <div class="forget">
          <el-tooltip content="请联系管理员" placement="bottom" effect="dark">
            <el-link class="login_link" :underline="false">忘记密码</el-link>
          </el-tooltip>
        </div>

        <div class="btn">
          <el-button
            type="primary"
            class="login-form-button"
            @click="getLogin()"
            >SIGN IN</el-button
          >
        </div>

        <div class="login_footer">
          <el-link class="rg" :underline="false" href="/register"
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
  name: "login",
  data() {
    return {
      userName: "",
      passWord: "",
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
						if(res.data.success==="success"){
						localStorage.setItem("token", res.data.token); //localStorage 用于长久保存整个网站的数据，保存的数据没有过期时间，直到手动去删除。
						localStorage.setItem("uid", res.data.user.id);//保存set  读取get
						this.$message({
							message: res.data.msg,
							type: "success",
						});
						this.$router.push("/home");
					}else{
						this.$message({
							message: res.data.msg,
							type: "warning",
						});
						
					}
        }else{
          this.$message({
            message: res.msg,
            type: "warning",
          });
        }
      });

      // this.$axios
      //   .post(this.$store.state.url + "/api/user/login", LoginDate)
      //   .then((res) => {
      //     if (res.data.code == 200) {
      //       this.$message({
      //         message: "登陆成功",
      //         type: "success",
      //       });
      //       localStorage.setItem("token",res.data.data.token)
      //       localStorage.setItem("uid",res.data.data.user.id)
      //       this.$router.push("/home");
      //     }
      //   });
    },
  },
};
</script>



<style scoped>
/* scoped 以表示它的样式作用于当下的模块 */
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
  width: 75px;
  height: 75px;
  float: left;
  margin-right: 20px;
}
.login_text {
  float: left;
  line-height: 75px;
  font-size: 30px;
  float: left;
  font-weight: bold;
  margin-left: 50px;
}
.login_form {
  margin-top: 30px;
  font-size: 16px;
  color: rgb(184, 183, 183);
  font-weight: bold;
}
.login_form p {
  text-align: left;
  height: 50px;
  line-height: 50px;
}
.forget {
  float: right;
  margin-top: 8px;
}
.forget .login_link {
  font-size: 16px;
  color: gray;
}
.btn {
  margin-top: 50px;
}
.login-form-button {
  font-weight: bold;
  width: 100%;
}
.login_footer {
  height: 50px;
  line-height: 50px;
  text-align: center;
  font-weight: bold;
  font-size: 16px;
}
.login_footer .rg {
  font-weight: bold;
  font-size: 16px;
  margin-top: 35px;
}
</style>
