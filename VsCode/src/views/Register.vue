<template>
  <div class="login">
    <div class="login_info">
      <div class="login_title">
        <img src="../assets/img/logo.png" alt="" />
        <div class="login_text">哇塞电影-注册</div>
      </div>

      <div class="login_form">
        <p>Username</p>
        <el-input v-model="userName" placeholder="账号" @blur="isExistUserName"></el-input>
        <p>PassWord</p>
        <el-input
          v-model="passWord"
          placeholder="密码"
          show-password
        ></el-input>
        <p>Check PassWord</p>
        <el-input
          v-model="checkpassWord"
          placeholder="密码"
          show-password
        ></el-input>
				<!-- 邮箱地址 -->
			<p>邮箱</p>
        <el-input
          v-model="email"
          placeholder="请输入邮箱地址,获得邮箱验证码"
					@blur="checkEmail"
					:style="isPassEmail ? '' : 'border:1px solid red;'"
        ></el-input><el-button type="info" icon="el-icon-message" circle @click="sendEmail"></el-button>
	<!-- 邮箱地址 -->
			<p>邮箱验证码</p>
        <el-input
          v-model="emailCode"
          placeholder="请输入验证码"
        ></el-input>
        <div class="agree">
          <el-checkbox v-model="agree" label="我已同意“哇塞电影用户注册协议”"></el-checkbox>
          <!-- <el-link class="login_link" :underline="false">我已同意“哇塞电影用户注册协议”</el-link> -->
        </div>

        <div class="btn">
          <el-button type="primary" class="login-form-button" @click="postRegister"
            >SIGN IN</el-button
          >
        </div>

        <div class="login_footer">
          <el-link class="rg" :underline="false" href="/"
            >我有账号 去登陆
            <i class="el-icon-right"></i>
          </el-link>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { Register,SendEmailCode,IsExistUserName } from '../api/user';
export default {
  name: "register",
  data() {
    return {
      userName: "",
      passWord: "",
      checkpassWord:'',
      agree:false,
			email:'',
			emailCode:'',
			isPassEmail:true,
    };
  },
  methods:{
		//判断用户名是否存在
		isExistUserName(){
			const vo = {
				email:this.userName,
			}
			console.log(vo);
			IsExistUserName(vo).then((res)=>{
				console.log(res.data);
				if(res.code==200){
					if(res.data.success==="success"){
						this.$message({
							message:res.data.msg,
							type:"success"
						});
					}else{
						this.$message.error(res.data.msg);
					}
				}
			});
			
		}		,
		//邮件输入框的失去焦点时间
		checkEmail(){
			const mailReg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/ ;
			if(mailReg.test(this.email)){
				this.isPassEmail=true;
			}else{
				this.isPassEmail=false;
			}
			console.log(this.isPassEmail);
		},
		
		//发送email验证码
		sendEmail(){
			//客户端对传入的邮箱内容，进行基本的邮箱格式验证。验证成功后发消息到服务端。
			//https://www.jb51.net/article/67628.htm
		
			if(this.isPassEmail){
				const emailVo={
					email:this.email,
				};
				console.log(emailVo);
				
				//如何发送
				SendEmailCode(emailVo).then((res)=>{
					console.log(res.data);
					if(res.code==200){
						this.$message({
							message:res.data.msg,
							type:"success"
						})
						//保存验证码
						localStorage.setItem("code",res.data.code);
					}
				});
			}else{
				this.$message({
					message:'邮箱格式不正确',
					type:"warning"
				})
				
			}
		},
		//注册
    postRegister(){
      if(this.userName.length<6){
        this.$message({
          message:"请输入不少于6位的用户名",
          type:"warning"
        })
        retuen;
      }
      if(this.agree==false){
         this.$message({
          message:"请勾选",
          type:"warning"
        })
        return;
      }
      if(this.passWord!=this.checkpassWord){
         this.$message({
          message:"密码不一致",
          type:"warning"
        })
        return;
      }
      const RegisterData={
        username:this.userName,
        password:this.passWord,
				email:this.email,
				token:localStorage.getItem("code"),
				emailCode:this.emailCode,
      }
      Register(RegisterData).then((res)=>{
        if(res.code==200){
          this.$message({
          message:res.data.msg,
          type:"success"
        })
        this.$router.push("/login")
        }
      })
    }
  }
};
</script>

<style lang="scss" scoped>
	
.login {

  width: 100%;
  height: 100%;
  .login_info {
    width: 500px;
    margin: 50px auto;
    .login_title {
      height: 75px;
      img {
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
    }
    .login_form {
      margin-top: 30px;
      font-size: 16px;
      color: rgb(184, 183, 183);
      font-weight: bold;
      p {
        text-align: left;
        height: 50px;
        line-height: 50px;
      }
	/* 样式不起作用
		.noPass{
			border-color: red;
		}
    */   .agree {
        float: left;
        margin-top: 15px;
      }

      .btn {
        margin-top: 80px;
        .login-form-button {
          font-weight: bold;
          width: 100%;
        }
      }

      .login_footer {
        height: 50px;
        line-height: 50px;
        text-align: center;
        font-weight: bold;
        font-size: 16px;
        .rg {
          font-weight: bold;
          font-size: 16px;
          margin-top: 35px;
        }
      }
    }
  }
}
// .login_info {
//   width: 500px;
//   margin: 50px auto;
// }
// .login_title {
//   height: 75px;
// }
// .login_title img {
//   width: 75px;
//   height: 75px;
//   float: left;
//   margin-right: 20px;
// }
// .login_text {
//   float: left;
//   line-height: 75px;
//   font-size: 30px;
//   float: left;
//   font-weight: bold;
//   margin-left: 50px;
// }
// .login_form {
//   margin-top: 30px;
//   font-size: 16px;
//   color: rgb(184, 183, 183);
//   font-weight: bold;
// }
// .login_form p {
//   text-align: left;
//   height: 50px;
//   line-height: 50px;
// }
// .agree {
//   float: left;
//   margin-top: 15px;
// }
// .agree .login_link {
//   font-size: 16px;
//   color: gray;
// }
// .btn {
//   margin-top: 80px;
// }
// .login-form-button {
//   font-weight: bold;
//   width: 100%;
// }
// .login_footer {
//   height: 50px;
//   line-height: 50px;
//   text-align: center;
//   font-weight: bold;
//   font-size: 16px;
// }
// .login_footer .rg {
//   font-weight: bold;
//   font-size: 16px;
//   margin-top: 35px;
// }
</style>
