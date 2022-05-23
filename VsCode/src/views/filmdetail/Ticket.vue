<template>
  <div class="ticket">
    <i class="el-icon-back"></i
    ><el-link
      ><el-button type="text" @click="$router.back(-1)" style="color: black"
        >返回</el-button
      >
    </el-link>
    <span class="span1">电影购票</span>

    <div v-if="list.lenght != 0">
      <div class="div1" v-for="(item, index) in list" :key="index">
        <p>{{ item.name }}</p>
        <div class="time">{{ item.startTime }}</div>
        <el-button type="primary" plain style="background: white" disabled>{{
          item.type
        }}</el-button>
        <span class="price">￥{{ item.price }}</span>
        <el-button type="danger" round @click="handlebuy(item.id)"
          >选座购票</el-button
        >
      </div>
    </div>
    <div v-if="list[0] == null" class="none">影院还没有添加排片</div>
  </div>
</template>
<script>
import { FilmArrangement } from "@/api/film";
export default {
  data() {
    return {
      login: true,
      text: "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean euismod bibendum laoreet. Proin gravida dolor sit amet lacus accumsan et viverra justo commodo. Proin sodales  pulvinar sic tempor. Sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Nam fermentum, nulla luctus pharetra vulputate, felis tellus mollis orci, se d rhoncus pronin sapien nunc accuan eget.",
      list: [
        // {
        //   name: "XXX",
        //   time: "2021-03-31 16：12：45",
        //   btn: "2D放映",
        //   price: "30.5",
        //   id: "1",
        // },
        // {
        //   name: "XXX",
        //   time: "2021-03-31 16：12：45",
        //   btn: "3D放映",
        //   price: "35.5",
        //   id: "2",
        // },
      ],
    };
  },
  mounted() {
    this.QueryFilmArrangement();
  },
  methods: {
		formatDate(dateint) {
			//日期格式化，
			//console.log(dateint);
			var date = new Date(parseInt(dateint));
			var str=date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
			return str;
		} ,
    handlebuy(id) {
      if (this.login == false) {
        this.$confirm("系统还没有检测到您的信息 是否登录？", "提示", {
          confirmButtonText: "确定",
          confirmButtonText: "取消",
          type: "warning",
        }).then(() => {
          this.$router.push("/login");
        });
      } else {
				console.log(id);
				localStorage.setItem("arrangementId",id);
        this.$router.push("/seat");
      }
    },
    QueryFilmArrangement() {
      FilmArrangement(localStorage.getItem("fid")).then((res) => {
        if (res.code == 200) {
          //this.list = res.data.arrangements;
					//console.log(res.data)
					for(var i=0;i<res.data.length;i++){
						res.data[i].startTime=this.formatDate(res.data[i].startTime);
					}
					this.list = res.data;
        }
      });
    },
  },
};
</script>
<style lang="scss">
.ticket {
  // background: red;
  width: 1200px;
  margin-left: 200px;
  margin-top: 80px;
  // margin-bottom: 100px;
  .span1 {
    margin-left: 25px;
    font-size: 20px;
  }
  .div1 {
    // background: red;
    margin-left: 80px;
    margin-top: 50px;
    p {
      color: rgb(94, 92, 92);
      line-height: 40px;
    }
    .time {
      // float: right;
      color: rgb(150, 148, 148);
      line-height: 40px;
    }
    .price {
      font-size: 18px;
      display: inline-block;
      margin-left: 800px;
      color: red;
      margin-right: 20px;
    }
  }
  .none {
    font-size: 30px;
    color: rgb(214, 2, 2);
    height: 60px;
    line-height: 60px;
    font-weight: bold;
    // margin-bottom: 100px;
  }
}
</style>