<template>
  <div class="set">
    <div class="basic_aside">
      <p class="text">基本设置</p>
      <el-form ref="form" :model="form" label-width="80px">
        <el-form-item style="margin-bottom:15px">
          <p>用户名</p>
          <el-input v-model="form.name"></el-input>
        </el-form-item>
        <el-form-item style="margin-bottom:15px">
          <p>昵称</p>
          <el-input v-model="form.nickname"></el-input>
        </el-form-item>
        <el-form-item prop="pass" style="margin-bottom:15px">
          <p>密码</p>
          <el-input
            type="password"
            v-model="ruleForm.pass"
            autocomplete="off"
          ></el-input>
        </el-form-item>
        <el-form-item style="margin-bottom:15px">
          <p>性别</p>
          <el-radio-group v-model="form.sex">
            <el-radio label="男"></el-radio>
            <el-radio label="女"></el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item style="margin-bottom:15px">
          <p>电话号码</p>
          <el-input v-model="form.number"></el-input>
        </el-form-item>
        <el-form-item style="margin-bottom:20px">
          <p>入职部门</p>
          <el-radio-group v-model="form.part">
            <el-radio label="客服部"></el-radio>
            <el-radio label="运营部"></el-radio>
            <el-radio label="宣传部"></el-radio>
            <el-radio label="策划部"></el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item style="margin-bottom:15px">
          <el-button type="primary" @click="onSubmit">更新基本信息</el-button>
          <el-button>取消</el-button>
        </el-form-item>
      </el-form>
      <br><br><br><br>
    </div>
    <div class="picture">
      <div></div>
      <el-button type="primary" class="btn">点击上传</el-button>
      <p>只能上传jpg/png图片</p>
    </div>
  </div>
</template>
<script>
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
      ruleForm: { pass: "aaaa" },
      form: {
        nickname: "XXX",
        name: "XXX",
        number: "136XXX",
        sex: "",
      },
    };
  },
  methods: {
    onSubmit() {
      console.log("submit!");
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
    p{
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
</style>