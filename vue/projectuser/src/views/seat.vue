<template>
  <div class="seat-main">
    <el-steps :active="2" style="padding-bottom: 50px" align-center>
      <el-step title="步骤 1" description="选择电影场次"></el-step>
      <el-step title="步骤 2" description="选择座次号"></el-step>
      <el-step title="步骤 3" description="加入购物车"></el-step>
      <el-step title="步骤 4" description="结算付款"></el-step>
    </el-steps>

    <div class="seat-content">
      <div class="seat-aside">
        <div style="display: flex">
          <img :src="film.cover" alt="" />
          <div>
            <div class="seat-aside-name">
              {{ film.name }}
            </div>
            <div class="seat-aside-text">类型: {{ film.type }}</div>
            <div class="seat-aside-text">地区: {{ film.region }}</div>
            <div class="seat-aside-text">时长: {{ film.duration }} 分钟</div>
          </div>
        </div>

        <div class="seat_aside_content">
          <div class="d1"><span>放映: </span>{{ arrangement.type }}</div>
          <div class="d1" style="color: #f56c6c">
            <span>开场: </span>
            {{ arrangement.startTime }}
          </div>
          <div class="d1"><span>散场: </span>{{ arrangement.endTime }}</div>
          <div class="d1"><span>票价: </span>￥{{ arrangement.price }}/张</div>
          <el-divider></el-divider>
          <div class="d1">
            已选座位:
            <el-tag
              v-for="(item, i) in userSelectSeats"
              :key="i"
              type="danger"
              style="margin-right: 5px"
              effect="plain"
            >
              {{ item }}号
            </el-tag>
          </div>
          <div class="d1" style="padding-top: 10px">
            总计:
            <span style="color: #f56c6c">￥ </span>
            <span style="color: #f56c6c; font-size: 25px; font-weight: bold">{{
              cart.price
            }}</span>
          </div>
          <el-divider></el-divider>
          <div class="btn">
            <el-input
              v-model="cart.phone"
              style="padding-top: 20px; padding-bottom: 30px"
              placeholder="请输入手机号码"
              clearable
            ></el-input>
            <el-button
              @click="submitSeat"
              class="add-cart-btn"
              type="danger"
              round
            >
              加入购物车</el-button
            >
          </div>
        </div>
      </div>

      <div class="hall seat-select">
        <div style="padding-left: 30px" class="seat-example">
          <div class="selectable-example example">
            <span>可选座位</span>
          </div>
          <div class="sold-example example">
            <span>已售座位</span>
          </div>
          <div class="selected-example example">
            <span>已选座位</span>
          </div>
        </div>

        <div class="seats-block">
          <div class="seats-container">
            <div class="screen-container" style="left: 5px">
              <div class="screen">荧幕中央</div>
              <div class="c-screen-line"></div>
            </div>

            <div class="seats-wrapper">
              <div style="padding: 0 40px; width: 500px; height: 200px">
                <span v-for="(item, index) in seats" :key="index">
                  <span v-if="item.status === 0" class="seat sold item" />
                  <span
                    v-if="item.status === 1"
                    class="seat selectable item"
                    @click="handleSelect(index)"
                  />
                  <span
                    v-if="item.status === 2"
                    class="seat selected item"
                    @click="handleDisSelect(index)"
                  />
                </span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { GetArrangementSeats, GetSelectedSeats, AddCart } from "../api/film";
import config from "@/config.js";
export default {
  data() {
    return {
      film: {},
      arrangement: {
        // type: "2D放映",
        // date: "2022-01-24",
        // startTime: "06:00:00",
        // endTime: "08:00:00",
        // price: 40,
      },
      uid: localStorage.getItem("uid"),
      userSelectSeats: [],
      cart: {
        uid: localStorage.getItem("uid"),
        aid: localStorage.getItem("arrangementId"),
        seats: "",
        phone: "",
        price: 0,
      },
      seats: [],
      SelectSeats: [],
    };
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
    submitSeat() {
      if (this.checkPhoneAndSeats() && this.uid != null) {
        let mySeats = [];
        for (var i = 0; i < this.userSelectSeats.length; i++) {
          this.cart.seats += this.userSelectSeats[i];
          mySeats[i] = this.userSelectSeats[i];
        }
        this.$message({
          message: "已为您添加购物车成功，请尽快付款吧!",
          type: "success",
        });
        this.cart.seats = mySeats + "";
        this.cart.status = 0;
        AddCart(this.cart).then((res) => {
          //代码
          if (res.code == 200) {
            console.log(res.data);
          }
        });
        // this.$router.go(-1);
        console.log(this.cart.seats);
      } else {
        this.$message({
          message: "请先登录",
          type: "error",
        });
      }
    },
    handleSelect(index) {
      if (this.userSelectSeats.length > 3) {
        var d = this.userSelectSeats[0] - 1;
        this.seats[d].status = 1;
        this.userSelectSeats.splice(0, 1);
        this.userSelectSeats.push(index + 1);
        this.seats[index].status = 2;
        this.cart.price = this.arrangement.price * this.userSelectSeats.length;
      } else {
        this.userSelectSeats.push(index + 1);
        this.seats[index].status = 2;
        this.cart.price = this.arrangement.price * this.userSelectSeats.length;
      }
    },
    handleDisSelect(index) {
      this.seats[index].status = 1;
      this.userSelectSeats.splice(this.userSelectSeats.indexOf(index + 1), 1);
      this.cart.price = this.arrangement.price * this.userSelectSeats.length;
    },

    checkPhoneAndSeats() {
      if (this.userSelectSeats.length === 0) {
        this.$message({
          message: "请选择要订购的座位",
          type: "warning",
        });
        return false;
      }
      if (this.cart.phone.length !== 11) {
        this.$message({
          message: "请输入11位订座号码",
          type: "warning",
        });
        return false;
      }
      return true;
    },

    queryInfo() {
      // console.log(this.$route.query.fid);//没有定义
      GetArrangementSeats(localStorage.getItem("arrangementId")).then((res) => {
        if (res.code == 200) {
          this.film = res.data.film; //获得电影信息
          this.film.cover = config.API_URL + this.film.cover; //修改图片地址
          this.arrangement = res.data.arrangement;
          //修改时间显示
          // this.arrangement.startTime = this.formatDate(
          //   this.arrangement.startTime
          // );
          // this.arrangement.endTime = this.formatDate(this.arrangement.endTime);
        }
        //获得对应的座位信息
        // http://localhost:8888/api/arrangement/getSeats
        GetSelectedSeats(localStorage.getItem("arrangementId")).then((res) => {
          if (res.code == 200) {
            this.SelectSeats = res.data; //这是一个集合
            console.log(res.data);
            //显示座位
            this.LoadSeats();
          }
        });
      });
    },
    LoadSeats() {
      const n = this.arrangement.seatNumber;
      let arr = new Array(n);
      for (var i = 0; i < n; i++) {
        arr[i] = { seat: i + 1, status: 1 };
      }
      //已经买过票的，去除掉
      for (let i = 0; i < this.SelectSeats.length; i++) {
        arr[this.SelectSeats[i].seat] = { status: 0 };
      }
      this.seats = arr;
    },
  },
  mounted() {
    this.queryInfo();
  },
};
</script>

<style lang="scss" scoped>
.seat-main {
  width: 1350px;
  margin: 40px auto 0;
  padding: 80px;
  .seat-content {
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.12), 0 0 6px rgba(0, 0, 0, 0.04);
    height: 850px;
    .seat-aside {
      width: 30%;
      height: 100%;
      float: left;
      background-color: #f5f6f7;
      img {
        width: 140px;
        height: 200px;
        padding: 30px;
      }
      .seat-aside-name {
        padding-top: 40px;
        padding-bottom: 10px;
        font-size: 25px;
        letter-spacing: 2px;
      }
      .seat-aside-text {
        padding-top: 8px;
        font-size: 13px;
        letter-spacing: 1px;
        color: #666666;
      }
      .seat_aside_content {
        padding: 5px 30px;
        .d1 {
          font-size: 16px;
          letter-spacing: 2px;
          padding-bottom: 15px;
          span {
            color: #91949c;
          }
        }
        .btn {
          .add-cart-btn {
            width: 100%;
            border-radius: 50px;
            height: 60px;
          }
        }
      }
    }
  }
  .hall {
    width: 700px;
    display: inline-block;
    vertical-align: top;
    position: relative;
    .seat-example {
      margin: 30px 0 30px 118px;
      .selectable-example {
        background: url("../assets/img/seat3.png") no-repeat;
      }
      .sold-example {
        background: url("../assets/img/seat1.png") no-repeat;
      }
      .selected-example {
        background: url("../assets/img/seat2.png") no-repeat;
      }
      .example {
        display: inline-block;
        font-size: 16px;
        color: #666;
        height: 26px;
        line-height: 26px;
        padding-left: 38px;
        background-repeat: no-repeat;
        margin-right: 50px;
      }
    }
    .seats-block {
      font-size: 0;
      overflow: hidden;
      margin-left: 20px;
      .seats-container {
        margin-left: 50px;
        overflow: auto;
        position: relative;
        padding-top: 112px;
        .screen-container {
          display: inline-block;
          position: absolute;
          top: 0;
          bottom: 0;
          z-index: -1;
          .screen {
            width: 550px;
            padding-top: 50px;
            text-align: center;
            font-size: 16px;
            color: #666;
            background: url("../assets/img/seat4.png") no-repeat;
            background-position-x: center;
            position: relative;
            margin-bottom: 40px;
          }
          .c-screen-line {
            width: 0;
            border-left: 1px dashed #e5e5e5;
            position: absolute;
            top: 90px;
            bottom: 0;
            left: 50%;
          }
        }
        .seats-wrapper {
          display: inline-block;
          min-width: 550px;
          .seat {
            display: inline-block;
            font-size: 0;
            width: 30px;
            height: 26px;
            margin: 0 5px;
            background: url("../assets/img/seat3.png") no-repeat;
            background-position: 0 1px;
          }
          .sold {
            background: url("../assets/img/seat1.png") no-repeat;
          }
          .selected {
            background: url("../assets/img/seat2.png") no-repeat;
          }
          .item {
            padding-bottom: 20px;
          }
        }
      }
    }
  }
  .seat-select {
    padding-top: 50px;
    padding-left: 8%;
  }
}
</style>