<template>
  <div class="cart">
    <span>购物车</span>
    <div>
      <el-card class="goods" v-for="(item, index) in mainlist" :key="index">
        <el-checkbox
          :checked="isExists(item.cart.id)"
          class="good_check"
          @change="checkedOne(item.cart.id)"
        ></el-checkbox>
        <img :src="item.film.cover" alt="" />
        <div class="good_title">《{{ item.film.name }}》</div>
        <div class="good_content">
          <p>座位: {{ item.cart.seats }}</p>
          <p>手机号码: {{ item.cart.phone }}</p>
          <p>开场时间:</p>
          <span class="red">{{ item.arrangement.startTime }}</span>
        </div>
        <div class="good_price">￥{{ item.cart.price }}</div>
        <button class="good_delete" @click="handledelete(index)">删除</button>
      </el-card>

      <div class="results" v-if="mainlist.length != 0">
        <el-checkbox class="res_check" @change="checkedAll()">全选</el-checkbox>
        <el-button type="danger" class="res_confirm" @click="submitCart"
          >全部结算</el-button
        >
        <div class="res_amount">合计： ￥{{ totalall }}</div>
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
          <el-button type="primary" @click="submitPay">支 付 成 功</el-button>
        </span>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import { MyCart, AddMyOrder } from "@/api/personal";
import config from "@/config.js";
export default {
  data() {
    return {
      centerDialogVisible: false,
      totalprie: "",
      isCheckedAll: false,
      mainlistIds: [],
      mainlist: [
        // {
        //   id: "1",
        //   url_show: require("../../assets/filmImg/shouyiren.jpg"),
        //   name: "受益人",
        //   position: "30",
        //   phone: "1321343",
        //   time: "2022-01-20 - 14:25:23",
        //   price: "30.5",
        //   status: "0",
        // },
        // {
        //   id: "2",
        //   url_show: require("../../assets/filmImg/shouyiren.jpg"),
        //   name: "受益人",
        //   position: "30",
        //   phone: "1321343",
        //   time: "2022-01-20 - 14:25:23",
        //   price: "30.5",
        //   status: "0",
        // },
      ],
    };
  },
  mounted() {
    this.QMyCart();
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
      console.log(str);
      return str;
    },
    QMyCart() {
      MyCart(localStorage.getItem("uid")).then((res) => {
        if (res.code == 200) {
          //console.log(res.data)
          for (var i = 0; i < res.data.length; i++) {
            res.data[i].film.cover = config.API_URL + res.data[i].film.cover;
            //数字转字符串
            //res.data[i].cart.id=res.data[i].cart.id+"";
            // res.data[i].arrangement.startTime = this.formatDate(
            //   res.data[i].arrangement.startTime
            // );
          }
          this.mainlist = res.data;
        }
      });
    },
    isExists(id) {
      let idIndex = -1;
      for (var i = 0; i < this.mainlistIds.length; i++) {
        if (id == this.mainlistIds[i]) {
          idIndex = i;
          break;
        }
      }
      if (idIndex == -1) {
        return false;
      } else {
        return true;
      }
    },
    checkedOne(id) {
      //拿到对应下标
      //let idIndex = this.mainlistIds.indexOf(id);
      //console.log(id);
      let idIndex = -1;
      for (var i = 0; i < this.mainlistIds.length; i++) {
        if (id == this.mainlistIds[i]) {
          idIndex = i;
          break;
        }
      }

      if (idIndex >= 0) {
        // 如果已经包含了该id, 则去除(单选按钮由选中变为非选中状态)
        this.mainlistIds.splice(idIndex, 1);
      } else {
        // 选中该checkbox
        this.mainlistIds.push(id);
      }
    },
    checkedAll() {
      this.isCheckedAll = !this.isCheckedAll;
      if (this.isCheckedAll) {
        // 全选时
        this.mainlistIds = [];
        this.mainlist.forEach(function (item) {
          this.mainlistIds.push(item.cart.id);
        }, this);
      } else {
        this.mainlistIds = [];
      }
    },
    handledelete(index) {
      for (var i = 0; i < this.mainlist.length; i++) {
        var indexid = this.mainlistIds.indexOf(this.mainlist[index].cart.id);
        if (indexid >= 0) {
          this.mainlistIds.splice(indexid, 1);
        }
      }
      this.mainlist.splice(index, 1);
    },

    submitCart() {
      if (this.mainlistIds.length > 0) {
        this.$confirm(
          "请您仔细确认订单金额为" + this.totalprie + "元，是否继续？",
          "提示",
          {
            comfirmButtonText: "确认支付",
            cancelButtonText: "取消支付",
            type: "success",
            center: true,
          }
        )
          .then(() => {
            //调用后端代码，使用批处理效果
            let data = {
              ids: this.mainlistIds,
              uid: localStorage.getItem("uid"),
              phone: this.mainlist[0].cart.phone,
              aid: this.mainlist[0].arrangement.id,
              price: this.totalprie,
              status: 0,
            };
            AddMyOrder(data).then((res) => {
              //获得数据库的返回
              console.log(res);
            });
            console.log(this.mainlistIds);
            for (let i = 0; i <= this.mainlistIds.length; i++) {
              for (let j = 0; j <= this.mainlist.length; j++) {
                if (this.mainlist[j].cart.id == this.mainlistIds[i]) {
                  //什么意思
                  //0，表示正常的购物车项，没有加入到订单中
                  //2，表示已经加入到订单中，不用再显示了
                  //3，表示异常的购物车项，
                  this.mainlist[j].cart.status = 2;
                  this.handledelete(j); //删除选中的内容
                  this.centerDialogVisible = true;
                }
              }
            }
          })
          .catch(() => {
            for (let i = 0; i < this.mainlistIds.length; i++) {
              for (let j = 0; j < this.mainlist.length; j++) {
                if (this.mainlist[j].cart.id == this.mainlistIds[i]) {
                  this.mainlist[j].cart.status = 3;
                  this.handledelete(j);
                }
              }
            }
            this.$message({
              type: "warning",
              message: "用户已取消支付，请前往我的订单支付",
            });
          });
      }
    },

    submitPay() {
      this.centerDialogVisible = false;
      this.$message({
        message: "支付成功！",
        type: "success",
      });
    },
  },
  computed: {
    totalall() {
      let total = 0;
      for (var i = 0; i < this.mainlist.length; i++) {
        if (this.mainlistIds.indexOf(this.mainlist[i].cart.id) >= 0) {
          total += Number(this.mainlist[i].cart.price);
        }
      }
      this.totalprie = total;
      return total;
    },
  },
};
</script>

<style scoped>
.cart {
  width: 100%;
  height: 100%;
  overflow: hidden;
}
.cart span {
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
.good_check {
  position: absolute;
  top: 65px;
  left: 15px;
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
.goods .good_content .red {
  position: absolute;
  top: 10px;
  left: 30px;
  color: red;
  font-size: 13px;
  width: 300px;
}
.goods .good_price {
  font-size: 18px;
  color: red;
  position: absolute;
  top: 60px;
  left: 600px;
}
.goods .good_delete {
  font-size: 18px;
  border: none;
  background-color: #fff;
  position: absolute;
  top: 60px;
  left: 700px;
  color: red;
  cursor: pointer;
}
.results {
  width: 800px;
  background-color: rgb(145, 148, 156);
  margin: 15px auto;
  height: 50px;
}
.res_check {
  height: 50px;
  line-height: 50px;
  margin-left: 20px;
  letter-spacing: 5px;
  color: black;
}
.res_confirm {
  height: 50px;
  float: right;
  border-radius: 0;
  background-color: rgb(243, 77, 65);
  color: black;
  font-size: 20px;
  text-align: center;
}
.res_amount {
  height: 50px;
  line-height: 50px;
  float: right;
  margin-right: 30px;
}
.el-dialog .c-img {
  width: 100%;
  height: 100%;
}
</style>