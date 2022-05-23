<template>
  <div class="seat">
    <div class="container">
      <div class="header">
        <el-steps :active="2" align-center>
          <el-step title="步骤1" description="选择电影场次"></el-step>
          <el-step title="步骤2" description="选择座位号"></el-step>
          <el-step title="步骤3" description="加入购物车"></el-step>
          <el-step title="步骤4" description="结算付款"></el-step>
        </el-steps>
      </div>
      <div class="box">
        <div class="aside" style="width: 350px">
          <img :src="film.cover" alt="" />
          <div class="message">
            <h3>{{ film.name }}</h3>
            <p>类型：{{ film.type }}</p>
            <p>地区：{{ film.region }}</p>
            <p>时长：{{ film.duration }}</p>
          </div>
          <div class="start">
            <p><span>放映：</span>{{ filmarrgement.type }}</p>
            <p><span>开场：</span>{{ filmarrgement.startTime }}</p>
            <p><span>散场：</span>{{ filmarrgement.endTime }}</p>
            <p><span>票价：</span>￥{{ filmarrgement.price }}/张</p>
          </div>
          <el-divider class="divide"></el-divider>
          <div class="dl">
            已选座位：
            <el-tag
              v-for="(item, i) in userSelectSeats"
              :key="i"
              type="danger"
              style="margin-right: 5px"
              effect="plain"
              >{{ item }}</el-tag
            >
          </div>
          <p>
            总计：
            <span style="color: red">￥</span>
            <span style="color: red; font-size: 22px; font-weight: bold">{{
              cart.price
            }}</span>
          </p>
          <el-divider class="divide"></el-divider>
          <el-input
            class="input"
            placeholder="请输入手机号"
            v-model="cart.phone"
            clearable
          >
          </el-input>
          <el-button class="btn" type="danger" @click="submitSeat"
            >加入购物车</el-button
          >
        </div>
        <div class="main">
          <div class="top">
            <img src="../assets/img/seat3.png" alt="" />
            <span>可选座位</span>
            <img src="../assets/img/seat1.png" alt="" />
            <span>已售座位</span>
            <img src="../assets/img/seat2.png" alt="" />
            <span>已选座位</span>
          </div>
          <div style="margin: 0 auto; width: 580px">
            <img src="../assets/img/seat4.png" alt="" />
          </div>
          <p>银幕中央</p>
          <div class="c-screen-line"></div>
          <div class="seates">
            <div
              style="
                margin: 0 auto;
                padding: 0 40px;
                width: 500px;
                height: 300px;
              "
            >
              <span v-for="(item, index) in seats" :key="index">
                <!-- 不可选 -->
                <span
                  v-if="item.seatstatus === 0"
                  class="seat sold item"
                ></span>
                <!-- 可选 -->
                <span
                  v-if="item.seatstatus === 1"
                  @click="handleSelect(index)"
                  class="seat selectable item"
                ></span>
                <!-- 已选 -->
                <span
                  v-if="item.seatstatus === 2"
                  @click="handleDisSelect(index)"
                  class="seat selected item"
                ></span>
              </span>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { FilmArrangementDetail, FilmSeat,AddCart } from "@/api/film";
import  config from '@/config.js';
export default {
  data() {
    return {
      userSelectSeats: [],
      film: {
        // title: "XXXXXX",
        // url: require("../assets/filmImg/波斯语课.jpg"),
        // type: "喜剧",
        // region: "中国大陆",
        // time: "128分钟",
      },
      filmarrgement: {
        // style: "2D放映",
        // start: "2022-01-24 06：00：00",
        // end: "08：00：00",
        // price: "45",
      },
      cart: {
         uid:localStorage.getItem("uid"),
         aid:localStorage.getItem("arrangementId"),
        seats: "",
        phone: "",
        price: 0,
      },
      seats: [],
      SelectSeats: [],
    };
  },
  mounted() {
    this.QueryFilmArrangementAndSeat();
  },
  methods: {
		formatDate(dateint) {
			//日期格式化，
			//console.log(dateint);
			var date = new Date(parseInt(dateint));
			var str=date.getFullYear()+"-";
			if(date.getMonth()<10){
				str+="0";
			}
			str+=(date.getMonth()+1)+"-";
			if(date.getDate()<10){
				str+="0";
			}
			str+=date.getDate()+" ";
			if(date.getHours()<10){
				str+="0";
			}
			str+=date.getHours()+":";
			
			if(date.getMinutes()<10){
				str+="0";
			}
			str+=date.getMinutes();
			//console.log(str);
			return str;
		},
    submitSeat() {
      if (this.checkPhoneAndSeats()) {
				let mySeats=[];
        for (let i = 0; i < this.userSelectSeats.length; i++) {
          this.cart.seats += this.userSelectSeats[i] + "号";
					mySeats[i]=this.userSelectSeats[i];
        }
				//console.log(mySeats);
        this.$message({
          message: "付款",
          type: "warning",
        });
				//加到购物车中，座位出现重复不好判断
				//购物车中一行，座位项中只有一个座位，如果同时购买多个座位，生成多条记录
				//批量新增的
				//https://blog.csdn.net/chang100111/article/details/115664432
				//(1)阿凡达2,狮子王，1号厅 18号
				this.cart.seats=mySeats+"";
				this.cart.status=0;
				AddCart(this.cart).then((res)=>{
					//代码
					if(res.code==200){
						console.log(res.data);
					}
				});
        //this.$router.go(-1);
      }
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
          message: "请输入11位号码",
          type: "warning",
        });
        return false;
      }
      return true;
    },
    handleSelect(index) {
      if (this.userSelectSeats.length >= 4) {
        let d = this.userSelectSeats[0] - 1;
        this.seats[d].seatstatus = 1;
        this.userSelectSeats.splice(0, 1);
      }
      this.userSelectSeats.push(index + 1);
      this.seats[index].seatstatus = 2;
      this.cart.price = this.filmarrgement.price * this.userSelectSeats.length;
    },
    handleDisSelect(index) {
      this.seats[index].seatstatus = 1;
      this.userSelectSeats.splice(this.userSelectSeats.indexOf(index + 1), 1);
      this.cart.price = this.filmarrgement.price * this.userSelectSeats.length;
    },
    QueryFilmArrangementAndSeat() {
			// console.log(this.$route.query.fid);//没有定义
      FilmArrangementDetail(localStorage.getItem("arrangementId")).then((res) => {
        if (res.code == 200) {
          this.film = res.data.film;//获得电影信息
					this.film.cover= config.API_URL +this.film.cover;//修改图片地址
          this.filmarrgement = res.data.arrangement;
					//修改时间显示
					this.filmarrgement.startTime=this.formatDate(this.filmarrgement.startTime);
					this.filmarrgement.endTime=this.formatDate(this.filmarrgement.endTime);
        }
				//获得对应的座位信息
				// http://localhost:8888/api/arrangement/getSeats
        FilmSeat(localStorage.getItem("arrangementId")).then((res) => {
          if (res.code == 200) {
            this.SelectSeats = res.data;//这是一个集合
						//console.log(res.data);
						//显示座位
            this.LoadSeats();
          }
        });
      });
    },
    LoadSeats() {
      const n = this.filmarrgement.seatNumber;//获得座位数
      let arr = new Array(n);
			//所有的座位，都是空的
      for (let i = 0; i < n; i++) {
				 arr[i] = { seatstatus: 1 };
        // if (this.SelectSeats.indexOf(i + 1) === -1) {
        //   arr[i] = { seatstatus: 1 };
        // } else {
        //   arr[i] = { seatstatus: 0 };
        // }
      }
			//已经买过票的，去除掉
			for(let i=0;i<this.SelectSeats.length;i++){
				arr[this.SelectSeats[i].seat]={seatstatus: 0};
			}
			//选座切换的问题，数据库中是不能修改的
			//0 不可选
			//1 未选
			//2 已选
      this.seats = arr;
    },
  },
};
</script >

<style lang="scss">
.seat {
  .container {
    // background: yellow;
    // height: 800px;

    .header {
      margin-bottom: 80px;
    }
    .box {
      // background: yellow;
      width: 1400px;
      height: 800px;
      margin-left: 40px;
      border: 1px solid rgb(214, 211, 211);
      .aside {
        background: rgb(245, 243, 243);
        padding: 20px 23px 63px 30px;
        // float: left;
        display: inline-block;
        // height: 800px;
        img {
          width: 140px;
          height: 200px;
          margin: 10px;
          display: inline-block;
          margin-right: 25px;
        }
        .message {
          position: relative;
          bottom: 80px;
          display: inline-block;
          h3 {
            height: 50px;
            line-height: 50px;
            font-size: 24px;
          }
          p {
            height: 30px;
            line-height: 30px;
            color: gray;
            font-size: 16px;
          }
        }
        .start {
          p {
            height: 40px;
            line-height: 40px;
            font-size: 18px;
            span {
              height: 40px;
              line-height: 40px;
              color: gray;
              font-size: 20px;
            }
          }
        }
        .divide {
          background: rgb(83, 82, 82);
          // height:1px
        }
        p {
          height: 40px;
          line-height: 40px;
          font-size: 18px;
        }
        .input {
          margin: 20px 0px 40px 0px;
        }
        .btn {
          width: 230px;
          height: 50px;
          border-radius: 25px;
          display: block;
          margin: 0 auto;
          font-size: 16px;
          // padding-bottom: 20px;
        }
      }
      .main {
        background: blanchedalmond;
        // float: left;
        width: 800px;
        display: inline-block;
        position: relative;
        bottom: 200px;
        left: 100px;
        .top {
          margin: 0 auto;
          width: 500px;
          img {
            display: inline-block;
            width: 50px;
            margin: 30px 10px 30px 30px;
          }
          span {
            display: inline-block;
            position: relative;
            bottom: 46px;
            font-size: 16px;
          }
        }
        .divide2 {
          height: 20px;
        }
        p {
          text-align: center;
          height: 60px;
          line-height: 60px;
        }
        .c-screen-line {
          width: 0;
          border-left: 1px dashed #e5e5e5;
          position: absolute;
          top: 190px;
          bottom: 0;
          left: 50%;
        }
        .seates {
          wideth: 100%;
          height: 400px;
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
          .item {
            padding-bottom: 20px;
          }
          .selectable {
          }
          .selected {
            background: url("../assets/img/seat2.png") no-repeat;
          }
        }
      }
    }
  }
}
</style>