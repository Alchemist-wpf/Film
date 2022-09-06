<template>
  <div class="set">
    <div class="basic_aside">
      <p class="text">基本设置</p>
      <el-form ref="form" :model="form" label-width="80px">
        <el-form-item style="margin-bottom: 15px">
          <p>用户名</p>
          <el-input v-model="form.username"></el-input>
        </el-form-item>
        <el-form-item style="margin-bottom: 15px">
          <p>昵称</p>
          <el-input v-model="form.nickname"></el-input>
        </el-form-item>
        <el-form-item prop="password" style="margin-bottom: 15px">
          <p>密码</p>
          <el-input
            type="password"
            v-model="form.password"
            autocomplete="off"
          ></el-input>
        </el-form-item>
        <el-form-item style="margin-bottom: 15px">
          <p>性别</p>
          <el-radio-group v-model="form.gender">
            <el-radio label="男"></el-radio>
            <el-radio label="女"></el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item style="margin-bottom: 15px">
          <p>电话号码</p>
          <el-input v-model="form.phone"></el-input>
        </el-form-item>
        <el-form-item style="margin-bottom: 20px">
          <p>入职部门</p>
          <el-radio-group v-model="form.department">
            <el-radio label="客服部"></el-radio>
            <el-radio label="运营部"></el-radio>
            <el-radio label="宣传部"></el-radio>
            <el-radio label="策划部"></el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item style="margin-bottom: 15px">
          <el-button type="primary" @click="onSubmit">更新基本信息</el-button>
          <el-button>取消</el-button>
        </el-form-item>
      </el-form>
      <br /><br /><br /><br />
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
      <p>只能上传jpg/png图片</p>
    </div>
  </div>
</template>
<script>
import { Modify } from "../../api/customer";
export default {
  data() {
    return {
      dynamicValidateForm: {
        domains: [
          {
            value: "",
          },
        ],
        email: "",
      },
      form: {
        id: localStorage.getItem("wid"),
      },
      fileName: "",
      imageUrl: "",
    };
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
      let data = {
        username: this.form.username,
        nickname: this.form.nickname,
        password: this.form.password,
        gender: this.form.gender,
        avatar: this.fileName,
        id: localStorage.getItem("wid"),
        phone: this.form.phone,
        department: this.form.department,
      };
      Modify(data).then((res) => {
        this.$message({
          message: "设置成功",
          type: "success",
        });
      });
    },
  },
};
</script>

<style lang="scss">
.set {
  float: left;
  width: 830px;
  //   height: 800px;
  //   margin: 0 auto;
  //   border: 1px solid rgb(158, 156, 156);
  textarea {
    resize: none;
  }
  .basic_aside {
    // width: 400px;
    float: left;
    margin: 20px;
    p {
      color: gray;
    }
    .text {
      font-size: 22px;
      margin-left: 80px;
      height: 40px;
      line-height: 40px;
    }
  }
  .picture {
    float: right;
    margin: 70px;
    div {
      width: 140px;
      height: 140px;
      background: white;
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
  width: 100%;
  height: 100%;
  display: block;
}
</style>