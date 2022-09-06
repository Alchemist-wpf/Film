<template>
  <div class="info">
    <el-page-header @back="goBack" content="详情页面"></el-page-header>
    <div class="introduct_title">剧情简介</div>
    <div class="introdution_con">
      {{ film.introduction }}
    </div>
    <div class="comment_on">
      电影评论

      <div
        v-show="evaluateList == null || evaluateList.length == 0"
        class="comment_null"
      >
        暂无评论
      </div>
      <div class="comment_on_box">
        <div
          class="comment_item"
          v-for="(item, index) in evaluateList"
          :key="index"
          v-show="evaluateList.length != 0"
        >
          <img
            src="../../assets/img/nim.png"
            alt=""
            v-if="item !== null"
            class="comment_img"
          />

          <div class="header-name">
            <div class="anonymous_user" v-if="item === null">匿名用户</div>
            <div class="anonymous_user" v-if="item !== null">
              {{ item.user.nickname }}
            </div>
            <el-rate
              style="padding-top: 7px"
              v-model="item.filmEvaluate.star"
              disabled
              show-score
              text-color="#ff9900"
              score-template="{value}"
            ></el-rate>
          </div>

          <div class="comment_info">
            {{ item.filmEvaluate.comment }}
          </div>

          <i>Time : {{ item.filmEvaluate.createAt }}</i>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { FindFilmEvaluate, FindFilmById, updateHot } from "../../api/film";
import { ConvertJSONDateToJSDate } from "@/api/util.js";
import { globalBus } from "@/api/globalBus";
export default {
  name: "Info",
  data() {
    return {
      film: {
        // introduction: "李焕英讲述了.....",
      },
      newfilm: {},
      filmId: this.$route.query.fid,
      evaluateList: [
        //
        // nickname: "ADDDDDDDD",
        // avatar_show: require("../../assets/img/avatar.png"),
        // star: 3,
        // comment: "21234",
        // createAt: "2022-03-24",
        // },
      ],
      avgstar: 0,
    };
  },
  methods: {
    goBack() {
      this.$router.go(-1);
    },
    queryFilmDetail() {
      FindFilmById(this.filmId).then((res) => {
        if (res.code == 200) {
          this.film = res.data;
          console.log(this.film);
          // this.film.cover_show=config.API_URL+"/upload?id="+this.film.cover
        }
      });
    },
    commentQuery() {
      FindFilmEvaluate(this.filmId).then((res) => {
        if (res.code == 200) {
          for (var i = 0; i < res.data.length; i++) {
            res.data[i].filmEvaluate.createAt = this.formatDate(
              res.data[i].filmEvaluate.createAt
            );
            this.avgstar =
              res.data[i].filmEvaluate.star + parseFloat(this.avgstar);
          }
          this.evaluateList = res.data;
          this.evaluateList = this.evaluateList.reverse();
          this.avgstar = Math.round((this.avgstar / res.data.length) * 10) / 10;
          this.newfilm.hot = this.avgstar;
          this.newfilm.id = this.film.id;

          updateHot(this.newfilm).then((res) => {
            if (res.data == 200) {
              this.avgstar = 0;
            }
          });
        }
      });
    },
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
      return str;
    },
  },
  mounted() {
    this.commentQuery();
    this.queryFilmDetail();
    globalBus.$on("countNumber", () => {
      this.commentQuery();
    });
  },
};
</script>

<style scoped lang="scss">
.info {
  .introduct_title {
    font-weight: bolder;
    font-size: 25px;
    padding-top: 40px;
    padding-bottom: 40px;
  }
  .introduction_con {
    line-height: 30px;
    text-indent: 2.5em;
    padding-top: 20px;
    padding-bottom: 20px;
  }
  .comment_on {
    font-weight: bolder;
    font-size: 25px;
    padding-top: 40px;
    padding-bottom: 40px;
    .comment_null {
      font-size: 15px;
      font-weight: lighter;
      margin-left: 40px;
      margin-top: 40px;
    }
    .comment_on_box {
      margin-top: 20px;
      .comment_item {
        padding-bottom: 40px;
        .comment_img {
          float: left;
          width: 55px;
          height: 55px;
          border-radius: 50%;
        }
        .header-name {
          color: #000000;
          padding-top: 5px;
          float: left;
          padding-left: 20px;
          font-weight: bolder;
          font-size: 14px;
          letter-spacing: 2px;
          .anonymous_user {
            font-weight: 400;
            color: darkgrey;
            padding-left: 5px;
          }
        }
        .comment_info {
          font-size: 20px;
          text-indent: 2.5em;
          line-height: 28px;
          padding-left: 70px;
          padding-top: 80px;
          padding-bottom: 20px;
          color: #91949c;
        }
        i {
          font-size: 20px;
          float: right;
          padding-right: 18px;
          color: #91949c;
        }
      }
    }
  }
}
</style>