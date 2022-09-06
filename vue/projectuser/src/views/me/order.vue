<template>
  <!-- 订单超时 1 支付成功2  订单异常3 等待支付0 -->
  <div class="order">
    <span>我的订单</span>
    <div v-loading="loading">
      <el-card class="goods" v-for="(item, index) in goodList" :key="index">
        <img :src="item.film.cover" alt="" />
        <div class="good_title">《{{ item.film.name }}》</div>
        <div class="good_content">
          <p>座位: {{ item.order.seats }}</p>
          <p>下单时间: {{ item.order.createTime }}</p>
          <p v-if="item.order.status === 2">支付时间: {{ item.order.payAt }}</p>
        </div>
        <div class="good_price">￥{{ item.order.price }}</div>

        <el-button
          type="text"
          @click="handlePay(item, index)"
          v-if="item.order.status === 0"
          class="good_situation blue"
        >
          等待支付
        </el-button>
        <el-tooltip
          class="item"
          effect="dark"
          content="请联系工作人员查询您的订单"
          placement="top"
        >
          <div v-if="item.order.status === 3" class="good_situation orange">
            订单异常
          </div>
        </el-tooltip>
        <div class="good_situation green" v-if="item.order.status === 2">
          支付成功
        </div>
        <div class="good_situation red" v-if="item.order.status === 1">
          已被撤销
        </div>
        <div class="good_situation blue" v-if="item.order.status === 4">
          等待支付
        </div>
      </el-card>
    </div>
    <el-dialog
      title="扫码支付"
      :show-close="false"
      width="30%"
      :visible.sync="centerDialogVisible"
    >
      <div>
        <img src="../../assets/img/c.jpeg" alt="" class="c-img" />
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitPay(item, index)"
          >支 付 成 功</el-button
        >
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { MyOrder } from "@/api/personal";
import config from "@/config.js";
export default {
  data() {
    return {
      loading: false,
      centerDialogVisible: false,
      goodList: [
        // {
        //   show: require("../../assets/filmImg/lihuanying.jpg"),
        //   name: "你好！ 李焕英",
        //   seat: "30号",
        //   price: 30.5,
        //   status: 3,
        //   orderTime: "2022-01-20 - 14:25:23",
        // },
        // {
        //   show: require("../../assets/filmImg/21座桥.jpg"),
        //   name: "21座桥",
        //   seat: "30号",
        //   price: 30.5,
        //   status: 2,
        //   orderTime: "2022-01-20 - 14:25:23",
        //   payTime: "2022-01-20 - 14:25:23",
        // },
        // {
        //   show: require("../../assets/filmImg/shouyiren.jpg"),
        //   name: "受益人",
        //   seat: "30号",
        //   price: 30.5,
        //   status: 0,
        //   orderTime: "2022-01-20 - 14:25:23",
        // },
      ],
    };
  },
  mounted() {
    this.QMyOrder();
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
      str += date.getMinutes();
      //console.log(str);
      return str;
    },
    QMyOrder() {
      MyOrder(localStorage.getItem("uid")).then((res) => {
        if (res.code == 200) {
          console.log(res.data);
          for (var i = 0; i < res.data.length; i++) {
            res.data[i].film.cover = config.API_URL + res.data[i].film.cover;
            // res.data[i].order.createTime = this.formatDate(
            //   res.data[i].order.createTime
            // );
            // res.data[i].order.payAt = this.formatDate(res.data[i].order.payAt);
          }
          this.goodList = res.data;
        }
      });
    },
    handlePay(item, index) {
      this.open(item, index);
    },
    submitPay(item, index) {
      this.centerDialogVisible = false;
      this.$message({
        message: "支付成功！",
        type: "success",
      });
    },
    open(item, index) {
      this.$confirm(
        "请您仔细确认订单金额为" + item.price + "元, 是否继续?",
        "提示",
        {
          confirmButtonText: "确认支付",
          cancelButtonText: "取消支付",
          type: "success",
          center: true,
        }
      )
        .then(() => {
          // this.loading = true;
          this.centerDialogVisible = true;
        })
        .catch(() => {
          this.$message({
            type: "warning",
            message: "用户已取消支付",
          });
        });
    },
  },
};
</script>

<style scoped>
.order {
  width: 100%;
  height: 100%;
  overflow: auto;
}
.order span {
  margin-left: 30px;
  margin-top: 50px;
  margin-bottom: 30px;
  font-size: 20px;
  display: block;
}
.goods {
  width: 800px;
  height: 150px;
  margin: 15px auto;
  border: 1px solid rgb(230, 230, 230);
  position: relative;
}
.goods img {
  width: 90px;
  position: absolute;
  top: 15px;
  left: 40px;
}
.goods .good_title {
  position: absolute;
  top: 20px;
  left: 160px;
  font-size: 18px;
}
.goods .good_content {
  position: absolute;
  top: 50px;
  left: 170px;
  height: 30px;
  line-height: 30px;
  font-size: 13px;
  color: rgb(170, 189, 214);
}
.goods .good_price {
  font-size: 18px;
  color: red;
  position: absolute;
  top: 60px;
  left: 600px;
}
.good_situation {
  font-size: 18px;
  position: absolute;
  top: 60px;
  left: 700px;
}
.orange {
  color: orange;
}
.red {
  color: red;
}
.blue {
  color: skyblue;
}
.green {
  color: lightgreen;
}
.el-dialog .c-img {
  width: 100%;
  height: 100%;
}
</style>