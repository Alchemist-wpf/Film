<template>
  <div class="st">
    <el-table :data="tableData" style="width: 100%">
      <el-table-column prop="name" width="120" label="电影名">
        <template slot-scope="scope">
          <img :src="scope.row.cover" width="100" height="100" class="cover" />
          <p>{{ scope.row.name }}</p>
        </template>
      </el-table-column>
      <el-table-column label="上映时间" prop="releaseTime" width="120">
      </el-table-column>
      <el-table-column label="类型" prop="type" width="120"> </el-table-column>
      <el-table-column label="上架状态" width="120" prop="status">
        <template slot-scope="scope">
          <p v-if="scope.row.status == 1">
            <el-button type="success" plain>上架</el-button>
          </p>
          <p v-if="scope.row.status == 2">
            <el-button type="warning" plain>下架</el-button>
          </p>
        </template>
      </el-table-column>
      <el-table-column label="地区" width="120" prop="region">
      </el-table-column>
      <el-table-column label="电影时长" prop="duration" width="120">
        <template slot-scope="scope">
          <el-button type="primary" plain size="mini"
            >{{ scope.row.duration }} 分钟</el-button
          >
        </template>
      </el-table-column>
      <el-table-column align="right">
        <!-- <template slot="header" width="300px">
          <el-button type="primary" size="mini" class="btn1" @click="Search"
            >搜索</el-button
          >
          <el-input
            v-model="search"
            size="mini"
            placeholder="输入关键字搜索"
            class="ipt1"
          />
        </template> -->
        <template slot-scope="scope">
          <el-button type="primary" @click="handleChange(scope.row)"
            >修改</el-button
          >
          <el-button type="success" @click="handle1(scope.row)">排片</el-button>
          <el-button type="danger" @click="handleDelete(scope.row)"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>
    <el-dialog title="提示" :visible.sync="dialogVisible" width="30%">
      <span>确认删除？</span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="Submit1()">确 定</el-button>
      </span>
    </el-dialog>

    <el-dialog title="排片" :visible.sync="dialogFormVisible1">
      <el-form ref="form" :model="form" label-width="80px">
        <el-form-item label="电影名称">
          <el-input v-model="form.name"></el-input>
        </el-form-item>
        <el-form-item label="开放座位">
          <el-input-number
            v-model="form.seatNumber"
            :min="1"
            :max="200"
            label="开放座位"
          ></el-input-number>
        </el-form-item>
        <el-form-item label="放映类型">
          <el-select v-model="form.type" placeholder="请选择">
            <el-option label="2D放映" value="2D放映"></el-option>
            <el-option label="3D放映" value="3D放映"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="开始时间">
          <el-date-picker
            v-model="form.startTime"
            type="datetime"
            placeholder="选择日期时间"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="结束时间">
          <el-date-picker
            v-model="form.endTime"
            type="datetime"
            placeholder="选择日期时间"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="电影票价">
          <el-input-number
            v-model="form.price"
            :min="1"
            :max="200"
            label="电影票价"
          ></el-input-number>
        </el-form-item>
        <el-form-item label="排片人">
          <el-input v-model="form.founder"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="back1">取 消</el-button>
        <el-button type="primary" @click="Submit3()">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="修改电影信息" :visible.sync="dialogFormVisible2">
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
        <el-form-item label="选择图片">
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
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm('ruleForm')"
            >立即创建</el-button
          >
          <el-button @click="back">取 消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
import {
  ListAllFilm,
  deleteFilm,
  AddArrangement,
  updateFilm,
} from "@/api/film";
export default {
  data() {
    return {
      tableData: [],
      search: "",
      dialogVisible: false,
      dialogFormVisible2: false,
      dialogFormVisible1: false,
      data1: [],
      data2: [],
      form: {
        name: "",
        seatNumber: "",
        startTime: "",
        endTime: "",
        type: "",
        price: "",
        founder: "",
      },
      fid: "",
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
        date: [
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
  mounted() {
    this.QAllFilm();
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
    formatDate(dateint) {
      //日期格式化，
      //console.log(dateint);
      var date = new Date(parseInt(dateint));
      var str = date.getFullYear() + "-";
      if (date.getMonth() < 10) {
        str += "0";
      }
      str += date.getMonth() + 1 + "-";
      if (date.getDate() < 10) {
        str += "0";
      }
      str += date.getDate() + " ";
      return str;
    },
    QAllFilm() {
      ListAllFilm().then((res) => {
        if (res.code == 200) {
          for (let i = 0; i < res.data.length; i++) {
            res.data[i].releaseTime = this.formatDate(res.data[i].releaseTime);
          }
          this.tableData = res.data;
          console.log(this.imageUrl);
        }
      });
    },
    handleChange(row) {
      this.dialogFormVisible2 = true;
      this.ruleForm = row;
      this.ruleForm.id = row.id;
      this.imageUrl = row.cover;
    },

    //排片信息
    handle1(row) {
      this.dialogFormVisible1 = true;
      this.fid = row.id;
      console.log(this.data2);
    },
    p(s) {
      return s < 10 ? "0" + s : s;
    },
    Submit3() {
      this.data2 = this.form;
      let d = this.data2.startTime;
      let d2 = this.data2.endTime;
      let d1 =
        d.getFullYear() +
        "-" +
        this.p(d.getMonth() + 1) +
        "-" +
        this.p(d.getDate()) +
        " " +
        this.p(d.getHours()) +
        ":" +
        this.p(d.getMinutes()) +
        ":" +
        this.p(d.getSeconds());
      let d3 =
        d2.getFullYear() +
        "-" +
        this.p(d2.getMonth() + 1) +
        "-" +
        this.p(d2.getDate()) +
        " " +
        this.p(d2.getHours()) +
        ":" +
        this.p(d2.getMinutes()) +
        ":" +
        this.p(d2.getSeconds());
      this.data2.fid = this.fid;
      this.data2.startTime = d1;
      this.data2.endTime = d3;
      console.log(this.data2);
      AddArrangement(this.data2).then((res) => {
        this.$message({
          message: "排片成功",
          type: "success",
        });
        this.form = {};
        this.dialogFormVisible1 = false;
      });
    },
    back1() {
      this.form = {};
      this.dialogFormVisible1 = false;
    },

    //删除
    handleDelete(row) {
      this.dialogVisible = true;
      this.data1 = row;
    },
    Submit1() {
      deleteFilm(this.data1).then((res) => {
        this.$message({
          message: "删除成功",
          type: "success",
        });
        this.dialogVisible = false;
        setTimeout(() => {
          location.reload();
        });
      });
    },

    Search() {},

    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if (this.fileName == "") {
            this.fileName = this.imageUrl;
            this.fileName = this.fileName.slice(21);
          }
          let data3 = {
            name: this.ruleForm.name,
            releaseTime: this.ruleForm.releaseTime,
            type: this.ruleForm.type,
            region: this.ruleForm.region,
            cover: this.fileName,
            introduction: this.ruleForm.introduction,
            duration: this.ruleForm.duration,
            id: this.ruleForm.id,
          };
          console.log(data3);
          updateFilm(data3).then((res) => {
            this.$message({
              message: "更新成功",
              type: "success",
            });
            setTimeout(() => {
              location.reload();
            });
          });
        } else {
          this.$message({
            message: "更新失败",
            type: "error",
          });
        }
        this.fileName = "";
        this.imageUrl = "";
        this.dialogFormVisible2 = false;
      });
    },
    back() {
      this.fileName = "";
      this.imageUrl = "";
      this.dialogFormVisible2 = false;
    },
  },
};
</script>

<style lang="scss">
// .st {
//   margin-left: 20px;
//   .items {
//     .item {
//       .collapse-title {
//         flex: 1 0 90%; //位于左侧
//         //   flex: 0 1 54%;// 位于中间
//         order: 1;
//       }
//     }
//   }
// }
.demo-table-expand {
  font-size: 0;
}
.demo-table-expand label {
  width: 90px;
  color: #99a9bf;
}
.demo-table-expand .el-form-item {
  margin-right: 0;
  margin-bottom: 0;
  width: 50%;
}
.btn1 {
  float: right;
}
.ipt1 {
  float: right;
  width: 200px;
}
.cover {
  width: 100px;
  height: auto;
}
.picture {
  float: left;
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