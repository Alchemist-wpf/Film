<template>
  <div class="order_aside">
    <p class="texttop">我的订单</p>
    <div class="box" v-for="(item, index) in mainlist" :key="index">
      <div class="img">
        <img :src="item.film.cover" alt="" />
      </div>
      <div class="text">
        <p class="name">《{{ item.film.name }}》</p>
        <p>订购座位：{{ item.order.seats }}号</p>
        <p>下单时间：{{ item.order.createAt }}</p>
        <p v-if="item.status == 2">支付时间：{{ item.order.payAt }}</p>
      </div>
      <span class="price"> ￥{{ item.order.price }}</span>

      <span v-if="item.order.status == 2" class="status" style="color: green"
        >支付成功</span
      >
      <span
        v-if="item.order.status == 3"
        class="status"
        style="color: blue; cursor: pointer"
        @click="handlesubmit(item)"
        >等待支付</span
      >
      <el-tooltip content="请联系管理人员" placement="top">
        <span
          v-if="item.order.status == 1"
          class="status"
          style="color: orange; cursor: pointer"
          >订单异常</span
        >
      </el-tooltip>

      <el-dialog
        title="扫码支付"
        :show-close="false"
        width="30%"
        :visible.sync="centerDialogVisible"
      >
        <div>
          <img src="../../assets/img/c.jpeg" alt="" style="width: 100%" />
        </div>
        <span slot="footer">
          <el-button type="primary" @click="centerDialogVisible = false"
            >支付成功</el-button
          >
        </span>
      </el-dialog>
    </div>
  </div>
</template>
<script>
import { MyOrder } from "@/api/personal";
import  config from '@/config.js';
export default {
  data() {
    return {
      centerDialogVisible: false,
      mainlist: [
        // {
        //   url_show: require("../../assets/filmImg/shouyiren.jpg"),
        //   name: "受益人",
        //   position: "30",
        //   time: "2022-01-20 - 14:25:23",
        //   price: "30.5",
        //   status: "1",
        //   paytime: "2022-01-20 - 14:25:23",
        // },
        // {
        //   url_show: require("../../assets/filmImg/shouyiren.jpg"),
        //   name: "受益人",
        //   position: "30",
        //   time: "2022-01-20 - 14:25:23",
        //   price: "30.5",
        //   status: "2",
        //   paytime: "2022-01-20 - 14:25:23",
        // },
        // {
        //   url_show: require("../../assets/filmImg/shouyiren.jpg"),
        //   name: "受益人",
        //   position: "30",
        //   time: "2022-01-20 - 14:25:23",
        //   price: "30.5",
        //   status: "3",
        //   paytime: "2022-01-20 - 14:25:23",
        // },
      ],
    };
  },
  mounted() {
    this.QMyOrder();
  },
  methods: {
    handlesubmit(item) {
      if (true) {
        this.$confirm(
          "请您仔细确认订单金额为" + item.order.price + "元，是否继续？",
          "提示",
          {
            comfirmButtonText: "确认支付",
            cancelButtonText: "取消支付",
            type: "success",
            center: true,
          }
        )
          .then(() => {
            item.order.status = 2;
            this.centerDialogVisible = true;
          })
          .catch(() => {
            this.$message({
              type: "warning",
              message: "用户已取消支付，请前往我的订单支付",
            });
          });
      }
    },
    QMyOrder() {
      MyOrder(localStorage.getItem("uid")).then((res) => {
        if (res.code == 200) {
					for(var i=0;i<res.data.length;i++){
						res.data[i].film.cover=config.API_URL+res.data[i].film.cover;
					}
          this.mainlist = res.data;
        }
      });
    },
  },
};
</script>

<style lang="scss">
.order_aside {
  float: left;
  width: 750px;
  margin: 40px;
  .texttop {
    font-size: 22px;
    //   margin-left: 30px;
    height: 80px;
    line-height: 80px;
  }
  .box {
    margin-left: 10px;
    margin-bottom: 20px;
    width: 730px;
    height: 150px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.12), 0 0 6px rgba(0, 0, 0, 0.04);
    .img {
      display: inline-block;
      margin: 20px 40px;
      img {
        width: 90px;
        height: 110px;
      }
    }
    .text {
      display: inline-block;
      // background: yellow;
      position: relative;
      bottom: 20px;
      .name {
        font-size: 20px;
        height: 40px;
        line-height: 40px;
        color: black;
      }
      p {
        font-size: 15px;
        color: gainsboro;
        height: 30px;
        line-height: 30px;
      }
    }
    .price {
      position: relative;
      bottom: 80px;
      left: 20px;
      font-size: 20px;
    }
    .status {
      position: relative;
      bottom: 80px;
      left: 100px;
      font-size: 20px;
      color: orange;
    }
  }
}
</style>