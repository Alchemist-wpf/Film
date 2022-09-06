<template>
  <div class="st">
    <div class="block">
      <el-carousel indicator-position="outside" height="300px">
        <el-carousel-item v-for="(item, index) in posterList" :key="index">
          <img :src="item.url" alt="" width="100%" height="100%" />
        </el-carousel-item>
      </el-carousel>
    </div>
    <el-button type="primary" class="btn" @click="Submit()"
      >上传轮播海报</el-button
    >

    <el-table :data="tableData" style="width: 100%">
      <el-table-column label="上传时间" prop="updateTime"> </el-table-column>
      <el-table-column label="标题" prop="title"> </el-table-column>
      <el-table-column label="地址浏览" prop="url" width="300px">
      </el-table-column>
      <el-table-column label="上架状态" prop="status">
        <template slot-scope="scope">
          <p v-if="scope.row.status == 1">
            <el-button type="success" plain @click="change1(scope.row)"
              >上架</el-button
            >
          </p>
          <p v-if="scope.row.status == 0">
            <el-button type="warning" plain @click="change2(scope.row)"
              >下架</el-button
            >
          </p>
        </template>
      </el-table-column>
      <el-table-column>
        <template slot-scope="scope">
          <el-button type="danger" @click="handleDelete(scope.row)"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>

    <el-dialog title="轮播海报" :visible.sync="dialogFormVisible">
      <el-form :model="form">
        <el-form-item label="海报标题" :label-width="formLabelWidth">
          <el-input v-model="form.title" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="上架状态" :label-width="formLabelWidth">
          <el-select v-model="form.status" placeholder="请选择上架状态">
            <el-option label="上架" value="1"></el-option>
            <el-option label="下架" value="0"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="选择图片" :label-width="formLabelWidth">
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
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="Confirm()">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="提示" :visible.sync="dialogVisible1" width="30%">
      <span>确定要删除这个电影海报？</span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible1 = false">取 消</el-button>
        <el-button type="primary" @click="Confirm2()">确 定</el-button>
      </span>
    </el-dialog>

    <el-dialog title="提示" :visible.sync="dialogVisible2" width="30%">
      <span>这是一段信息</span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible2 = false">取 消</el-button>
        <el-button type="primary" @click="Confirm3()">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
import {
  ListAllPoster,
  AddPoster,
  AllPoster,
  DelPoster,
  UpdatePoster,
  deleteAllPoster,
} from "@/api/film";
export default {
  data() {
    return {
      posterList: [],
      tableData: [],
      dialogFormVisible: false,
      dialogVisible1: false,
      dialogVisible2: false,
      form: {
        title: "",
        status: "",
      },
      formLabelWidth: "120px",
      fileName: "",
      imageUrl: "",
      deldata: [],
    };
  },
  mounted() {
    this.posterQuery();
    this.QAllPoster();
  },
  methods: {
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
      if (date.getHours() < 10) {
        str += "0";
      }
      str += date.getHours() + ":";

      if (date.getMinutes() < 10) {
        str += "0";
      }
      str += date.getMinutes() + ":";
      if (date.getSeconds() < 10) {
        str += "0";
      }
      str += date.getSeconds();
      //console.log(str);
      return str;
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
    posterQuery() {
      ListAllPoster(this.status).then((res) => {
        if (res.code == 200) {
          this.posterList = res.data;
        }
      });
    },
    Submit() {
      this.dialogFormVisible = true;
    },
    Confirm() {
      console.log(this.form);
      let data = {
        title: this.form.title,
        status: this.form.status,
        url: this.fileName,
      };
      AddPoster(data).then((res) => {
        this.$message({
          message: "添加成功",
          type: "success",
        });
        setTimeout(() => {
          location.reload();
        });
        this.fileName = "";
        this.imageUrl = "";
        this.form.status = "";
        this.form.title = "";
      });
      this.dialogFormVisible = false;
    },
    QAllPoster() {
      AllPoster().then((res) => {
        if (res.code == 200) {
          for (var i = 0; i < res.data.length; i++) {
            // res.data[i].updateTime = this.formatDate(res.data[i].updateTime);
          }
          this.tableData = res.data;
        }
      });
    },
    handleDelete(row) {
      this.dialogVisible1 = true;
      this.deldata = row;
      console.log(this.deldata);
    },
    Confirm2() {
      DelPoster(this.deldata).then((res) => {
        this.$message({
          message: "删除成功",
          type: "success",
        });
        this.dialogVisible1 = false;
        setTimeout(() => {
          location.reload();
        });
      });
    },

    change1(row) {
      row.status = 0;
      console.log(row);
      UpdatePoster(row).then((res) => {
        this.$message({
          message: "修改状态成功",
          type: "success",
        });
      });
    },
    change2(row) {
      row.status = 1;
      UpdatePoster(row).then((res) => {
        this.$message({
          message: "修改状态成功",
          type: "success",
        });
      });
    },
    Delete() {
      this.dialogVisible2 = true;
    },
    Confirm3() {
      deleteAllPoster().then((res) => {
        this.$message({
          message: "删除成功",
          type: "success",
        });
        this.dialogVisible2 = false;
      });
    },
  },
};
</script>
<style scoped lang="scss">
.st {
  margin: 0 auto;
  overflow: auto;
  height: 1500px;
}
.btn {
  width: 150px;
  margin-left: 30px;
}
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
.picture {
  float: left;
  div {
    width: 300px;
    height: 140px;
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
    display: block;
    float: left;
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
  width: 300px;
  height: 140px;
  line-height: 120px;
  text-align: center;
}
.avatar {
  width: 100%;
  height: 100%;
  display: block;
}
</style>
