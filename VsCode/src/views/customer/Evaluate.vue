<template>
  <div class="box" style="padding: 50px">
    <el-table :data="tableData" style="width: 100%">
      <el-table-column prop="createAt" label="日期" width="180">
      </el-table-column>
      <el-table-column prop="content" label="评语" width="180">
      </el-table-column>
      <el-table-column label="" prop="status">
        <template scope="scope">
          <p v-if="scope.row.type == '不满意'">
            <el-button type="warning" plain class="btn"> 不满意 </el-button>
          </p>
          <p v-if="scope.row.type == '非常满意'">
            <el-button type="success" plain class="btn">非常满意</el-button>
          </p>
          <p v-if="scope.row.type == '一般'">
            <el-button type="primary" plain class="btn"> 一 般 </el-button>
          </p>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import { MyCritic } from "@/api/lmessage";
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
    QMyCritic() {
      MyCritic().then((res) => {
        if (res.code == 200) {
          console.log(res)
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