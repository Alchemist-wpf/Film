<template>
  <div class="login">
    <div class="login_info">
      <div class="login_title">
        <img src="../assets/img/logo.png" alt="" />
        <div class="logo_text">哇塞电影 - 注册</div>
      </div>

      <div class="login_form">
        <p>Username</p>
        <el-input
          v-model="userName"
          placeholder="账号"
          @blur="isExistUserName"
        ></el-input>
        <p>Password</p>
        <el-input
          placeholder="密码"
          v-model="passWord"
          show-password
        ></el-input>
        <p>Check Password</p>
        <el-input
          placeholder="密码"
          v-model="checkpassWord"
          show-password
        ></el-input>
        <p>邮箱</p>
        <el-input
          v-model="email"
          placeholder="请输入邮箱地址"
          @blur="checkEmail"
          :style="isPassEmail ? '' : 'border:1px solid red'"
        ></el-input>
        <el-button
          type="info"
          icon="el-icon-message"
          circle
          @click="sendEmail"
        ></el-button>
        <p>邮箱验证码</p>
        <el-input v-model="emailCode" placeholder="请输入验证码"></el-input>
        <div class="agree">
          <el-checkbox v-model="checked"
            ><p>我已同意“哇塞电影”用户注册协议</p></el-checkbox
          >
        </div>

        <div class="btn">
          <el-button class="login-form-button" type="primary" @click="Register"
            >SIGN UP</el-button
          >
        </div>

        <div class="login_footer">
          <el-link :underline="false" class="rg" href="Login"
            >我有账号?去登录
            <i class="el-icon-right"></i>
          </el-link>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { Register, SendEmailCode, IsExistUserName } from "@/api/user";
export default {
  data() {
    return {
      userName: "",
      passWord: "",
      checkpassWord: "",
      email: "",
      emailCode: "",
      checked: false,
      isPassEmail: true,
    };
  },
  methods: {
    isExistUserName() {
      const vo = {
        email: this.userName,
      };
      console.log(vo);
      IsExistUserName(vo).then((res) => {
        console.log(res.data);
        if (res.code == 200) {
          if (res.data.success === "success") {
            this.$message({
              message: res.data.msg,
              type: "success",
            });
          } else {
            this.$message.error(res.data.msg);
          }
        }
      });
    },
    checkEmail() {
      const mailReg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;
      if (mailReg.test(this.email)) {
        this.isPassEmail = true;
      } else {
        this.isPassEmail = false;
      }
      console.log(this.isPassEmail);
    },
    sendEmail() {
      if (this.isPassEmail) {
        const emailVo = {
          email: this.email,
        };
        console.log(emailVo);
        SendEmailCode(emailVo).then((res) => {
          console.log(res.data);
          if (res.code == 200) {
            this.$message({
              message: res.data.msg,
              type: "success",
            });
            localStorage.setItem("code", res.data.code);
          }
        });
      } else {
        this.$message({
          message: "邮箱格式不正确",
          type: "warning",
        });
      }
    },
    Register() {
      if (this.userName.length < 6) {
        this.$message({
          message: "请输入不少于6位的用户名",
          type: "warning",
        });
        return;
      }
      if (this.checked == false) {
        this.$message({
          message: '请勾选 我已同意"哇塞电影"用户注册协议',
          type: "warning",
        });
        return;
      }
      if (this.passWord !== this.checkpassWord) {
        this.$message({
          message: "您两次输入的密码不同",
          type: "warning",
        });
        return;
      }
      const registerInfo = {
        username: this.userName,
        password: this.passWord,
        email: this.email,
        token: localStorage.getItem("code"),
        emailCode: this.emailCode,
      };

      Register(registerInfo).then((res) => {
        if (res.code == 200) {
          if (res.data.success === "success") {
            this.$message({
              message: res.data.msg,
              type: "success",
            });
            this.$router.push("/login");
          } else {
            this.$message({
              message: res.data.msg,
              type: "warning",
            });
          }
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
.agree {
  float: left;
  height: 40px;
  line-height: 60px;
  margin-bottom: 20px;
}
.agree p {
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
