<template>
  <div class="shop_aside">
    <p class="texttop">购物车</p>
    <div class="box" v-for="(item, index) in mainlist" :key="index">
      <input
        type="checkbox"
        :checked="isExists(item.cart.id)"
        @click="checkedOne(item.cart.id)"
      />
      <div class="img">
        <img :src="item.film.cover" alt="" />
      </div>
      <div class="text">
        <p class="name">《{{ item.film.name }}》</p>
        <p>座位：{{ item.cart.seats }}</p>
        <p>手机号码：{{ item.cart.phone }}</p>
        <p>开场时间：{{ item.arrangement.createAt}}</p>
      </div>
      <span class="price"> ￥{{ item.cart.price }}</span>
      <el-button type="text" @click="handledelete(index)" class="status"
        >删除</el-button
      >
    </div>
    <div class="total">
      <input
        type="checkbox"
        :checked="mainlistIds.length === mainlist.length"
        @click="checkedAll()"
      />
      <span> 全 选</span>
      <span class="spanall">合计：￥{{ totalall }}</span>
      <el-button type="danger" class="btnall" @click="handlesubmit"
        >全部结算</el-button
      >
    </div>
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
        <el-button type="primary" @click="submitpay">支付成功</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
import { MyCart,AddMyOrder } from "@/api/personal";
import  config from '@/config.js';
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
  // 全选和单选实现！！！
  methods: {
		isExists(id){
			let idIndex=-1;
			for(var i=0;i< this.mainlistIds.length;i++){
				if(id==this.mainlistIds[i]){
					idIndex = i;
					break;
				}
			}
			if(idIndex==-1){
				return false;
			}else{
				return true;
			}
		},
    checkedOne(id) {
      //拿到对应下标
      //let idIndex = this.mainlistIds.indexOf(id);
			//console.log(id);
			let idIndex=-1;
			for(var i=0;i< this.mainlistIds.length;i++){
				if(id==this.mainlistIds[i]){
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
    handlesubmit() {
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
					let data={
						ids:this.mainlistIds,
						uid:localStorage.getItem("uid"),
						phone:this.mainlist[0].cart.phone,
						aid:this.mainlist[0].arrangement.id,
						price:this.totalprie,
						status:0,
					}
					AddMyOrder(data).then((res)=>{
						//获得数据库的返回
						console.log(res);
					});
					console.log(this.mainlistIds);
					for (let i = 0; i < this.mainlistIds.length; i++) {
						for (let j = 0; j < this.mainlist.length; j++) {
							if (this.mainlist[j].cart.id == this.mainlistIds[i]) {
								//什么意思 
								//0，表示正常的购物车项，没有加入到订单中
								//2，表示已经加入到订单中，不用再显示了
								//3，表示异常的购物车项，
								this.mainlist[j].cart.status = 2;
								this.handledelete(j);//删除选中的内容
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
    submitpay() {
      this.centerDialogVisible = false;
    },
    QMyCart() {
      MyCart(localStorage.getItem("uid")).then((res) => {
        if (res.code == 200) {
          //console.log(res.data)
					for(var i=0;i<res.data.length;i++){
						res.data[i].film.cover=config.API_URL+res.data[i].film.cover;
						//数字转字符串
						//res.data[i].cart.id=res.data[i].cart.id+"";
					}
          this.mainlist = res.data;
					
        }
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

<style lang="scss">
.shop_aside {
  width: 780px;
  float: left;
  margin: 40px 0px 0px 40px;
  .texttop {
    font-size: 22px;
    //   margin-left: 30px;
    height: 80px;
    line-height: 80px;
  }
  .box {
    margin-left: 10px;
    margin-bottom: 20px;
    width: 740px;
    height: 180px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.12), 0 0 6px rgba(0, 0, 0, 0.04);
    input {
      position: relative;
      bottom: 80px;
      left: 10px;
      display: inline-block;
      width: 20px;
      height: 20px;
      margin-left: 10px;
    }
    .img {
      display: inline-block;
      margin: 20px 40px;
      img {
        width: 100px;
        height: 140px;
      }
    }
    .text {
      display: inline-block;
      // background: yellow;
      position: relative;
      bottom: 30px;
      .name {
        font-size: 20px;
        height: 50px;
        line-height: 50px;
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
      left: 90px;
      font-size: 20px;
    }
    .status {
      position: relative;
      bottom: 80px;
      left: 140px;
      font-size: 20px;
      color: orange;
    }
  }
  .total {
    width: 740px;
    height: 60px;
    background: rgb(153, 151, 151);
    margin-left: 10px;
    overflow: hidden;
    input {
      margin-left: 20px;
      display: inline-block;
      width: 15px;
      height: 15px;
    }
    .btnall {
      display: inline-block;
      background: rgb(236, 80, 80);
      height: 60px;
      float: right;
      font-size: 20px;
      color: black;
    }
    .spanall {
      height: 60px;
      display: inline-block;
      line-height: 60px;
      // float: right;
      position: relative;
      left: 420px;
    }
  }
}
</style>