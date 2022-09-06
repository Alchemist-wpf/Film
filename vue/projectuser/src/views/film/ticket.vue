<template>
  <div class="ticket">
    <el-page-header @back="goBack" content="电影购票"></el-page-header>
    <div class="ticket_box"></div>
    <div class="no_film" v-if="list.length === 0">影院还没有排片</div>
    <div class="ticket_item" v-for="(item, index) in list" :key="index">
      <div class="ticket_info">
        <div class="d1">{{ item.name }}</div>
        <div class="d2">{{ item.startTime }}</div>
        <el-tag effect="plain">{{ item.type }}</el-tag>
      </div>
      <div class="item-btn">
        <el-button type="danger" @click="handleSelectSeat(item.id)" round
          >选座购票</el-button
        >
      </div>
      <div class="item-price">￥{{ item.price }}</div>
    </div>
  </div>
</template>

<script>
import { FindArrangementById, GetArrangementSeats } from "@/api/film";
export default {
  data() {
    return {
      list: [
        // {
        //   id: "1",
        //   date: "2022",
        //   startTime: "2020-12-20",
        //   type: "家庭",
        //   price: 40,
        // },
        // {
        //   id: "1",
        //   date: "2022",
        //   startTime: "2020-12-20",
        //   type: "家庭",
        //   price: 40,
        // },
        // {
        //   id: "1",
        //   date: "2022",
        //   startTime: "2020-12-20",
        //   type: "家庭",
        //   price: 40,
        // },
      ],
      login: true,
      filmId: this.$route.query.fid,
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
      var str =
        date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate();
      return str;
    },
    goBack() {
      this.$router.go(-1);
    },
    handleSelectSeat(id) {
      if (this.login == false) {
        this.$confirm("系统还没有检测到您的登录信息，是否去登陆?", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }).then(() => {
          this.$route.push("/login");
        });
      } else {
        localStorage.setItem("arrangementId", id);
        this.$router.push("/seat?arrangementId=" + id);
      }
    },
    QueryFilmArrangement() {
      FindArrangementById(this.filmId).then((res) => {
        if (res.code == 200) {
          //this.list = res.data.arrangements;
          //console.log(res.data)
          for (var i = 0; i < res.data.length; i++) {
            res.data[i].startTime = this.formatDate(res.data[i].startTime);
          }
          this.list = res.data;
        }
      });
    },
  },
};
</script>

<style scoped lang="scss">
.ticket {
  .ticket_box {
    width: 100%;
    height: 20px;
  }
  .no_film {
    font-weight: bold;
    letter-spacing: 2px;
    font-size: 25px;
    color: red;
  }
  .ticket_item {
    padding-top: 20px;
    height: 100px;
    padding-left: 100px;
    overflow: hidden;
    .ticket_info {
      float: left;
      .d1 {
        font-size: 15px;
        letter-spacing: 2px;
        padding-bottom: 7px;
      }
      .d2 {
        font-size: 13px;
        letter-spacing: 1px;
        padding-bottom: 7px;
        color: #999;
      }
    }
  }
  .item-btn {
    float: right;
    padding-top: 30px;
    padding-bottom: 50px;
  }
  .item-price {
    float: right;
    color: #f34d41;
    line-height: 100px;
    margin-right: 50px;
  }
}
</style>