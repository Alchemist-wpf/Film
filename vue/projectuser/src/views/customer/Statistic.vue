<template>
  <div class="st">
    <el-table :data="tableData" style="width: 100%">
      <el-table-column type="expand">
        <template slot-scope="props">
          <el-form label-position="left" class="demo-table-expand">
            <el-form-item label="订单ID:">
              <span>{{ props.row.order.id }}</span>
            </el-form-item>
            <el-form-item label="用户ID:">
              <span>{{ props.row.user.id }}</span>
            </el-form-item>
            <el-form-item label="电影ID:">
              <span>{{ props.row.film.id }}</span>
            </el-form-item>
            <el-form-item label="场次ID:">
              <span>{{ props.row.arrangement.id }}</span>
            </el-form-item>
            <el-form-item label="电影名:">
              <span>{{ props.row.film.name }}</span>
            </el-form-item>
            <el-form-item label="座位号:">
              <span>{{ props.row.order.seats }}</span>
            </el-form-item>
            <el-form-item label="订单金额:">
              <span>{{ props.row.order.price }}</span>
            </el-form-item>
            <el-form-item label="下单时间:">
              <span>{{ props.row.order.createTime }}</span>
            </el-form-item>
            <el-form-item label="支付时间:">
              <span>{{ props.row.order.payAt }}</span>
            </el-form-item>
          </el-form>
        </template>
      </el-table-column>
      <el-table-column label="订单 ID" prop="order.id"> </el-table-column>
      <el-table-column label="电影名" prop="film.name"> </el-table-column>
      <el-table-column label="订购座位" prop="order.seats"> </el-table-column>
      <el-table-column label="订单金额" prop="order.price"> </el-table-column>
      <el-table-column label="订单状态" prop="order.status">
        <template slot-scope="scope">
          <p v-if="scope.row.order.status == 1">
            <el-button type="warning" plain>已被撤销</el-button>
          </p>
          <p v-if="scope.row.order.status == 2">
            <el-button type="success" plain>支付成功</el-button>
          </p>
          <p v-if="scope.row.order.status == 3">
            <el-button type="primary" plain>订单异常</el-button>
          </p>
          <p v-if="scope.row.order.status == 4">
            <el-button type="warning" plain>等待支付</el-button>
          </p>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import { AllOrder } from "@/api/customer";
export default {
  data() {
    return {
      tableData: [
        // {
        //   id: "12987122",
        //   name: "XXXX",
        //   seat: "14号",
        //   price: "35",
        //   status: "1",
        //   userid: "343534543",
        //   filmid: "5325435454",
        //   ccId: "3",
        //   time: "2022-01-19 14:28:05",
        //   zftime: "2022-01-19 14:28:05",
        // },
        // {
        //   id: "12987122",
        //   name: "XXXX",
        //   seat: "14号",
        //   price: "35",
        //   status: "2",
        //   userid: "343534543",
        //   filmid: "5325435454",
        //   ccId: "3",
        //   time: "2022-01-19 14:28:05",
        //   zftime: "2022-01-19 14:28:05",
        // },
        // {
        //   id: "12987122",
        //   name: "XXXX",
        //   seat: "14号",
        //   price: "35",
        //   status: "3",
        //   userid: "343534543",
        //   filmid: "5325435454",
        //   ccId: "3",
        //   time: "2022-01-19 14:28:05",
        //   zftime: "2022-01-19 14:28:05",
        // },
        // {
        //   id: "12987122",
        //   name: "XXXX",
        //   seat: "14号",
        //   price: "35",
        //   status: "1",
        //   userid: "343534543",
        //   filmid: "5325435454",
        //   ccId: "3",
        //   time: "2022-01-19 14:28:05",
        //   zftime: "2022-01-19 14:28:05",
        // },
      ],
    };
  },
  mounted() {
    this.QAllOrder();
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
    QAllOrder() {
      AllOrder().then((res) => {
        if (res.code == 200) {
          // for (let i = 0; i < res.data.length; i++) {
          //   res.data[i].order.createTime = this.formatDate(
          //     res.data[i].order.createTime
          //   );
          //   res.data[i].order.payAt = this.formatDate(res.data[i].order.payAt);
          // }
          this.tableData = res.data;
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