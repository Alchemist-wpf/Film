<template>
  <div class="set">
    <div class="basic_aside">
      <p class="text">基本设置</p>
      <el-form ref="form" :model="form" label-width="80px">
        <el-form-item>
          <p>昵称</p>
          <el-input v-model="form.nickname"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <p>密码</p>
          <el-input type="password" v-model="form.password"> </el-input>
        </el-form-item>
        <el-form-item>
          <p>性别</p>
          <el-radio-group v-model="form.gender">
            <el-radio label="男"></el-radio>
            <el-radio label="女"></el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item>
          <p>出生年月</p>
          <el-col :span="11">
            <el-date-picker
              type="date"
              placeholder="选择日期"
              v-model="form.birthday"
              style="width: 100%"
            ></el-date-picker>
          </el-col>
        </el-form-item>

        <el-form-item prop="email">
          <p>邮箱</p>
          <el-input v-model="form.email"></el-input>
        </el-form-item>
        <el-form-item>
          <p>个人简介</p>
          <el-input type="textarea" v-model="form.info"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onSubmit">更新基本信息</el-button>
        </el-form-item>
      </el-form>
    </div>
    <div class="picture">
      <div>
        <el-upload
          class="avatar-uploader"
          action="http://localhost:8888/api/user/upload"
          :show-file-list="false"
          :on-success="handleAvatarSuccess"
          :before-upload="beforeAvatarUpload"
        >
          <img v-if="imageUrl" :src="imageUrl" class="avatar" />
          <i v-else class="el-icon-plus avatar-uploader-icon"></i>
        </el-upload>
      </div>
      <!-- <el-button type="primary" class="btn">点击上传</el-button> -->
      <p>只能上传jpg图片</p>
    </div>
  </div>
</template>


<script>
import { ModifySelf, UserInfo, LogoutUser } from "@/api/user";
export default {
  data() {
    return {
      imageUrl: "",
      fileName: "",
      form: {},
      oldPs: "",
    };
  },
  mounted() {
    this.queryUser();
  },
  methods: {
    beforeAvatarUpload(file) {
      const isJPG = file.type === "image/jpeg";
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG) {
        this.$message.error("上传头像图片只能是 JPG 格式!");
      }
      if (!isLt2M) {
        this.$message.error("上传头像图片大小不能超过 2MB!");
      }
      return isJPG && isLt2M;
    },
    handleAvatarSuccess(res, file) {
      console.log(res.data.fileName);
      this.fileName = res.data.fileName;
      this.imageUrl = URL.createObjectURL(file.raw);
    },
    onSubmit() {
      if (this.fileName == "") {
        this.fileName = this.imageUrl;
        this.fileName = this.fileName.slice(21);
      }
      console.log(this.form.password);
      console.log(this.fileName.length);
      let data = {
        avatar: this.fileName,
        nickname: this.form.nickname,
        email: this.form.email,
        birthday: this.form.birthday,
        gender: this.form.gender,
        info: this.form.info,
        id: localStorage.getItem("uid"),
      };
      if (this.form.password !== this.oldPs) {
        data.password = this.form.password;
      }
      console.log(data);

      ModifySelf(data).then((res) => {
        console.log(res.data);
        this.$message({
          type: "success",
          message: "个人信息更新成功，请重新登录",
        });
        this.$router.push("/login");
        LogoutUser(localStorage.getItem("uid")).then((res) => {
          if (res.code == 200) {
            console.log(res.data.token);
            localStorage.setItem("token", res.data.token);
            localStorage.clear(); //清理本地存储
          }
          this.$router.push("/login");
        });
      });
    },
    queryUser() {
      if (localStorage.getItem("uid") !== null) {
        UserInfo().then((res) => {
          console.log(res);
          this.form = res.data;
          this.imageUrl = this.form.avatar;
          this.oldPs = this.form.password;
        });
      }
    },
  },
};
</script>

<style lang="scss">
.set {
  float: left;
  width: 830px;
  height: 800px;
  //   margin: 0 auto;
  //   border: 1px solid rgb(158, 156, 156);
  textarea {
    resize: none;
  }
  .basic_aside {
    width: 400px;
    float: left;
    margin: 20px;
    .text {
      font-size: 28px;
      margin-left: 80px;
      height: 80px;
      line-height: 80px;
    }
  }
  .picture {
    float: right;
    margin: 70px;
    div {
      width: 120px;
      height: 100px;
      background: pink;
      //   margin-left: 20px;
    }
    .btn {
      margin-top: 40px;
      width: 120px;
    }
    p {
      height: 50px;
      line-height: 50px;
      text-align: center;
      font-size: 14px;
      color: gray;
    }
  }
}
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409eff;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 120px;
  height: 120px;
  line-height: 120px;
  text-align: center;
}
.avatar {
  width: 120px;
  height: 120px;
  display: block;
}
</style>