<template>
  <div class="box" style="padding: 50px">
    <el-table :data="tableData" style="width: 100%">
      <el-table-column prop="updateTime" label="日期" width="180">
      </el-table-column>
      <el-table-column prop="uid" label="评价用户id" width="180">
      </el-table-column>
      <el-table-column prop="content" label="评语" width="380">
      </el-table-column>
      <el-table-column label="满意度" prop="type">
        <template slot-scope="scope">
          <p v-if="scope.row.type == '满意'">
            <el-button type="info" plain>满意</el-button>
          </p>
          <p v-if="scope.row.type == '非常满意'">
            <el-button type="success" plain>非常满意</el-button>
          </p>
          <p v-if="scope.row.type == '不满意'">
            <el-button type="danger" plain>不满意</el-button>
          </p>
        </template></el-table-column
      >
    </el-table>
  </div>
</template>

<script>
import { MyCritic } from "@/api/customer";
export default {
  data() {
    return {
      tableData: [
        // { date: "2022-00-11 10:22:20", critic: "XXX", status: "1" },
        // { date: "2022-00-11 10:22:20", critic: "XXX", status: "2" },
        // { date: "2022-00-11 10:22:20", critic: "XXX", status: "3" },
        // { date: "2022-00-11 10:22:20", critic: "XXX", status: "2" },
      ],
    };
  },
  mounted() {
    this.QMyCritic();
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
    QMyCritic() {
      MyCritic(localStorage.getItem("wid")).then((res) => {
        if (res.code == 200) {
          for (var i = 0; i < res.data.length; i++) {
            res.data[i].updateTime = this.formatDate(res.data[i].updateTime);
          }
          console.log(res.data);
          this.tableData = res.data;
        }
      });
    },
  },
};
</script>

<style>
.btn {
  width: 120px;
  float: right;
}
</style>