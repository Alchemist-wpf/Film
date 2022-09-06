<template>
  <div class="set">
    <div class="basic_aside">
      <el-form
        :model="ruleForm"
        :rules="rules"
        ref="ruleForm"
        label-width="100px"
        class="demo-ruleForm"
      >
        <el-form-item label="密码" prop="password">
          <el-input v-model="ruleForm.password"></el-input>
        </el-form-item>
        <el-form-item label="昵称" prop="nickname">
          <el-input v-model="ruleForm.nickname"></el-input>
        </el-form-item>
        <el-form-item label="性别" prop="gender">
          <el-select v-model="ruleForm.gender" placeholder="请选择性别">
            <el-option label="男" value="男"></el-option>
            <el-option label="女" value="女"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="电话" prop="phone">
          <el-input v-model="ruleForm.phone"></el-input>
        </el-form-item>
        <el-form-item label="入职部门" prop="department">
          <el-radio-group v-model="ruleForm.department">
            <el-radio label="客服部"></el-radio>
            <el-radio label="运营部"></el-radio>
            <el-radio label="宣传部"></el-radio>
            <el-radio label="策划部"></el-radio>
            <el-radio label="人事部"></el-radio>
            <el-radio label="监督部"></el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm('ruleForm')"
            >立即创建</el-button
          >
          <el-button @click="resetForm('ruleForm')">重置</el-button>
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
      <p>头像</p>
      <p>只能上传jpg图片</p>
    </div>
  </div>
</template>
<script>
import { Modify, WorkerInfo, LogoutWorker } from "../../api/customer";
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
      ruleForm: {
        password: "",
        nickname: "",
        gender: "",
        phone: "",
        department: "",
      },
      rules: {
        password: [
          { required: true, message: "请输入密码", trigger: "blur" },
          {
            min: 3,
            max: 11,
            message: "长度在 3 到 11 个字符",
            trigger: "blur",
          },
        ],
        nickname: [
          { required: true, message: "请输入用户名", trigger: "blur" },
          {
            min: 3,
            max: 11,
            message: "长度在 3 到 11 个字符",
            trigger: "blur",
          },
        ],
        gender: [{ required: true, message: "请选择性别", trigger: "change" }],
        phone: [
          { required: true, message: "请输入用户名", trigger: "blur" },
          { min: 11, max: 11, message: "长度在11 个字符", trigger: "blur" },
        ],
        department: [
          { required: true, message: "请选择部门", trigger: "change" },
        ],
      },
      fileName: "",
      imageUrl: "",
    };
  },
  mounted() {
    this.queryWorker();
  },
  methods: {
    queryWorker() {
      console.log(this.wid);
      if (localStorage.getItem("wid") !== null) {
        WorkerInfo(localStorage.getItem("wid")).then((res) => {
          this.ruleForm = res.data;
        });
      }
    },
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
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          let data = {
            id: this.ruleForm.id,
            username: this.ruleForm.username,
            nickname: this.ruleForm.nickname,
            password: this.ruleForm.password,
            gender: this.ruleForm.gender,
            avatar: this.fileName,
            phone: this.ruleForm.phone,
            department: this.ruleForm.department,
            entry: 1,
          };
          Modify(data).then((res) => {
            this.$message({
              message: "更新成功",
              type: "success",
            });
            this.$router.push("/login1");
            LogoutWorker(localStorage.getItem("wid")).then((res) => {
              if (res.code == 200) {
                console.log(res.data.token);
                localStorage.setItem("token", res.data.token);
                localStorage.clear(); //清理本地存储
              }
              this.$router.push("/login1");
            });
          });
        } else {
          this.$message({
            message: "更新失败",
            type: "error",
          });
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
  },
};
</script>

<style lang="scss">
.set {
  float: left;
  width: 100%;
  //   height: 800px;
  //   margin: 0 auto;
  //   border: 1px solid rgb(158, 156, 156);
  textarea {
    resize: none;
  }
  .basic_aside {
    // width: 400px;
    float: left;
    width: 680px;
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
    float: left;
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