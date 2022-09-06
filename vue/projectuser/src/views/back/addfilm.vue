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
        <el-form-item label="电影名称" prop="name">
          <el-input v-model="ruleForm.name"></el-input>
        </el-form-item>
        <el-form-item label="上映地区" prop="region">
          <el-select v-model="ruleForm.region" placeholder="请选择上映地区">
            <el-option label="全部" value="全部"></el-option>
            <el-option label="内地" value="内地"></el-option>
            <el-option label="香港" value="香港"></el-option>
            <el-option label="台湾" value="台湾"></el-option>
            <el-option label="美国" value="美国"></el-option>
            <el-option label="韩国" value="韩国"></el-option>
            <el-option label="日本" value="日本"></el-option>
            <el-option label="泰国" value="泰国"></el-option>
            <el-option label="印度" value="印度"></el-option>
            <el-option label="法国" value="法国"></el-option>
            <el-option label="英国" value="英国"></el-option>
            <el-option label="德国" value="德国"></el-option>
            <el-option label="其他" value="其他"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="电影时长" prop="duration">
          <el-input-number
            v-model="ruleForm.duration"
            :min="1"
            :max="999"
            label="电影时长"
          ></el-input-number>
        </el-form-item>
        <el-form-item label="上映时间" required>
          <el-col :span="11">
            <el-form-item prop="releaseTime">
              <el-date-picker
                type="date"
                placeholder="选择上映时间"
                v-model="ruleForm.releaseTime"
                style="width: 100%"
              ></el-date-picker>
            </el-form-item>
          </el-col>
        </el-form-item>
        <el-form-item label="电影类型" prop="type">
          <el-radio-group v-model="ruleForm.type">
            <el-radio label="全部" name="type"></el-radio>
            <el-radio label="家庭" name="type"></el-radio>
            <el-radio label="惊悚" name="type"></el-radio>
            <el-radio label="科幻" name="type"></el-radio>
            <el-radio label="爱情" name="type"></el-radio>
            <el-radio label="动作" name="type"></el-radio>
            <el-radio label="喜剧" name="type"></el-radio>
            <el-radio label="恐怖" name="type"></el-radio>
            <el-radio label="悬疑" name="type"></el-radio>
            <el-radio label="犯罪" name="type"></el-radio>
            <el-radio label="冒险" name="type"></el-radio>
            <el-radio label="战争" name="type"></el-radio>
            <el-radio label="历史" name="type"></el-radio>
            <el-radio label="武侠" name="type"></el-radio>
            <el-radio label="传记" name="type"></el-radio>
            <el-radio label="古装" name="type"></el-radio>
            <el-radio label="其他" name="type"></el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="内容简介" prop="introduction">
          <el-input type="textarea" v-model="ruleForm.introduction"></el-input>
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
      <p>电影封面</p>
      <p>只能上传png图片</p>
    </div>
  </div>
</template>
<script>
import { AddFilm } from "../../api/film";
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
        name: "",
        region: "",
        duration: "",
        releaseTime: "",
        type: "",
        introduction: "",
      },
      rules: {
        name: [{ required: true, message: "请输入电影名", trigger: "blur" }],
        region: [
          { required: true, message: "请选择上映地区", trigger: "change" },
        ],
        duration: [
          {
            required: true,
            message: "请输入电影时长",
            trigger: "change",
          },
        ],
        releaseTime: [
          {
            type: "date",
            required: true,
            message: "请选择上映时间",
            trigger: "change",
          },
        ],
        type: [
          { required: true, message: "请选择电影类型", trigger: "change" },
        ],
        introduction: [
          { required: true, message: "请填写内容简介", trigger: "blur" },
        ],
      },
      fileName: "",
      imageUrl: "",
    };
  },
  methods: {
    beforeAvatarUpload(file) {
      const isPNG = file.type === "image/png";
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isPNG) {
        this.$message.error("上传头像图片只能是 PNG 格式!");
      }
      if (!isLt2M) {
        this.$message.error("上传头像图片大小不能超过 2MB!");
      }
      return isPNG && isLt2M;
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
            name: this.ruleForm.name,
            releaseTime: this.ruleForm.releaseTime,
            type: this.ruleForm.type,
            region: this.ruleForm.region,
            cover: this.fileName,
            introduction: this.ruleForm.introduction,
            duration: this.ruleForm.duration,
          };
          console.log(data);
          AddFilm(data).then((res) => {
            this.$message({
              message: "添加成功",
              type: "success",
            });
            this.ruleForm = {};
          });
        } else {
          this.$message({
            message: "添加失败",
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
    width: 600px;
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
      height: 240px;
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