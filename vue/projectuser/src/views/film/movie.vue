<template>
  <div class="movie">
    <div class="movie-header">
      <div class="movie-card">
        <img :src="film.cover" alt="" width="100%" height="100%" />
      </div>

      <div class="movie-des">
        <div class="p1">
          {{ film.name }}
          <el-rate v-model="film.hot" disabled show-score text-color="#ff9900">
          </el-rate>
        </div>

        <div class="p2">电影类型: {{ film.type }}</div>
        <div class="p2">{{ film.region }} / {{ film.duration }} 分钟</div>
        <div class="p2">上映日期: {{ film.releaseTime }} 上映</div>
        <div class="btn">
          <router-link
            style="margin-right: 20px"
            :to="'/film/ticket?fid=' + film.id"
          >
            <el-button type="danger">
              <i class="ek-icon-s-finance"></i>特惠购票
            </el-button>
          </router-link>
          <el-button @click="openComment" type="danger">
            <i class="el-icon-star-on"></i>评分
          </el-button>
        </div>
      </div>
    </div>

    <div class="movie-content">
      <router-view />
    </div>

    <el-dialog
      :show-close="false"
      title="电影评分"
      :visible.sync="dialogVisible"
      width="30%"
    >
      <div class="block">
        <el-rate v-model="form.star" :color="colors"></el-rate>
      </div>
      <el-input
        style="margin-top: 40px"
        type="textarea"
        :row="8"
        placeholder="请输入内容"
        v-model="form.comment"
      >
      </el-input>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitComment">确 认 提 交</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { AddFilmEvaluate, FindFilmById } from "../../api/film";
import { globalBus } from "@/api/globalBus";
export default {
  data() {
    return {
      film: {
        // cover_show: require("../../assets/filmImg/lihuanying.jpg"),
        // name: "你好！ 李焕英",
        // type: "家庭",
        // region: "中国大陆",
        // duration: "120",
        // releaseTime: "2020-12-20",
        // filmId: this.$route.query.fid,
      },
      form: {
        // fid: "",
        // uid: "",
        // star: 5,
        // comment: "",
      },
      login: true,
      colors: ["#99A9BF", "#F7BA2A", "#FF9900"],
      value: 0,
      filmId: this.$route.query.fid,
      dialogVisible: false,
      uid: localStorage.getItem("uid"),
    };
  },
  methods: {
    openComment() {
      if (this.uid == null) {
        this.$confirm("系统还没有检测到您的登录信息，是否去登陆?", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        })
          .then(() => {
            this.$route.push("/login");
          })
          .catch(() => {
            this.$message({
              type: "info",
              message: "已取消为电影评分",
            });
          });
      } else {
        this.dialogVisible = true;
      }
    },
    submitComment() {
      this.form.fid = this.filmId;
      this.form.uid = localStorage.getItem("uid");
      const form = this.form;

      AddFilmEvaluate(form).then((res) => {
        if (res.code == 200) {
          this.dialogVisible = false;
          this.timer = setTimeout(() => {
            console.log("countNumber");
            globalBus.$emit("countNumber");
          }, 500);
          setTimeout(() => {
            location.reload();
          }, 1000);
        }
      });
    },
    queryFilmDetail() {
      FindFilmById(this.filmId).then((res) => {
        if (res.code == 200) {
          res.data.releaseTime = this.formatDate(res.data.releaseTime);
          this.film = res.data;
        }
      });
    },
    formatDate(dateint) {
      //日期格式化，
      //console.log(dateint);
      var date = new Date(parseInt(dateint));
      var str =
        date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate();
      return str;
    },
  },
  mounted() {
    this.queryFilmDetail();
  },
};
</script>

<style scoped lang="scss">
.movie {
  margin: 50px auto 0;
  width: 1510px;
  .movie-header {
    position: relative;
    width: 100%;
    height: 350px;
    background-color: #5a84fd;
    .movie-card {
      width: 240px;
      height: 330px;
      background-color: #fff;
      margin-right: 180px;
      margin-left: 190px;
      position: absolute;
      overflow: hidden;
      top: 60px;
      z-index: 999;
    }
    .movie-des {
      margin-left: 50px;
      position: absolute;
      left: 450px;
      overflow: hidden;
      top: 50px;
      z-index: 999;
      .p1 {
        font-weight: bolder;
        letter-spacing: 3px;
        color: #fff;
        font-size: 30px;
        padding-top: 20px;
        padding-bottom: 10px;
        .el-rate {
          float: right;
          padding-left: 50px;
        }
      }
      .p2 {
        font-size: 14px;
        padding-top: 20px;
        letter-spacing: 2px;
        color: #fff;
      }
      .btn {
        padding-top: 30px;
        .el-button {
          width: 130px;
          letter-spacing: 2px;
          i {
            padding-right: 5px;
            font-size: 15px;
          }
        }
      }
    }
  }

  .movie-content {
    padding: 80px 200px 0;
    letter-spacing: 2px;
  }
}
</style>