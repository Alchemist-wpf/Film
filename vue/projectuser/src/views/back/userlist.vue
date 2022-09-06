<template>
  <div class="st">
    <el-table :data="tableData" style="width: 100%">
      <el-table-column type="expand">
        <template slot-scope="props">
          <el-form label-position="left" class="demo-table-expand">
            <el-form-item label="用户ID:">
              <span>{{ props.row.id }}</span>
            </el-form-item>
            <el-form-item label="用户名:">
              <span>{{ props.row.username }}</span>
            </el-form-item>
            <el-form-item label="密码:">
              <span>{{ props.row.password }}</span>
            </el-form-item>
            <el-form-item label="头像URL:">
              <span>{{ props.row.avatar }}</span>
            </el-form-item>
            <el-form-item label="昵称:">
              <span>{{ props.row.nickname }}</span>
            </el-form-item>
            <el-form-item label="邮箱:">
              <span>{{ props.row.email }}</span>
            </el-form-item>
            <el-form-item label="性别:">
              <span>{{ props.row.gender }}</span>
            </el-form-item>
            <el-form-item label="生日:">
              <span>{{ props.row.birthday }}</span>
            </el-form-item>
            <el-form-item label="创建时间:">
              <span>{{ props.row.createTime }}</span>
            </el-form-item>
            <el-form-item label="更新时间:">
              <span>{{ props.row.updateTime }}</span>
            </el-form-item>
          </el-form>
        </template>
      </el-table-column>
      <el-table-column label="用户 ID" prop="id"> </el-table-column>
      <el-table-column label="用户名" prop="username"> </el-table-column>
      <el-table-column label="昵称" prop="nickname"> </el-table-column>
      <el-table-column label="注册时间" prop="createTime"> </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button size="mini" type="danger" @click="handleDelete(scope.row)"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>
    <el-dialog title="提示" :visible.sync="dialogVisible" width="30%">
      <span>确定要删除此用户？</span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="confirm()">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { AllUser, userDelete } from "@/api/user";
export default {
  data() {
    return {
      tableData: [],
      dialogVisible: false,
      data: [],
    };
  },
  mounted() {
    this.QAllUser();
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
      //console.log(str);
      return str;
    },
    QAllUser() {
      AllUser().then((res) => {
        if (res.code == 200) {
          for (var i = 0; i < res.data.length; i++) {
            res.data[i].createTime = this.formatDate(res.data[i].createTime);
            res.data[i].updateTime = this.formatDate(res.data[i].updateTime);
            res.data[i].birthday = this.formatDate(res.data[i].birthday);
          }
          this.tableData = res.data;
        }
      });
    },
    handleDelete(row) {
      this.dialogVisible = true;
      this.data = row;
      console.log(this.data);
    },
    confirm() {
      userDelete(this.data).then((res) => {
        if (res.code == 200) {
          this.$message({
            message: "删除成功",
            type: "success",
          });
          this.dialogVisible = false;
        }
      });
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
</style>