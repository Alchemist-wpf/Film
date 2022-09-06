<template>
  <div class="index">
    <div class="block">
      <el-carousel indicator-position="outside" height="500px">
        <el-carousel-item v-for="(item, index) in posterList" :key="index">
          <img :src="item.url" alt="" width="100%" height="100%" />
        </el-carousel-item>
      </el-carousel>
    </div>

    <div class="as">
      <div class="top">
        <span>热门榜单Top10</span>
        <router-link :to="'/film/detail?fid=' + top1.id">
          <div class="top1">
            <img :src="top1.cover" width="120px" />
            <ul class="top1_text">
              <li>{{ top1.name }}</li>
              <li>上映时间: {{ top1.releaseTime }}</li>
              <li class="hot_text">热度值: {{ top1.hot }}</li>
            </ul>
          </div>
        </router-link>

        <router-link :to="'/film/detail?fid=' + top2.id">
          <div class="top2">
            <img :src="top2.cover" width="140px" />
            <p>{{ top2.name }}</p>
            <p class="top2_hot">热度值:{{ top2.hot }}</p>
          </div>
        </router-link>

        <router-link :to="'/film/detail?fid=' + top3.id">
          <div class="top3">
            <img :src="top3.cover" width="140px" />
            <p>{{ top3.name }}</p>
            <p class="top3_hot">热度值:{{ top3.hot }}</p>
          </div>
        </router-link>

        <div class="more_top">
          <ul>
            <li v-for="(item, index) in topList" :key="index">
              <span class="more_index">{{ index + 1 }}</span>
              <span class="more_name">{{ item.name }}</span>
              <span class="more_hot">热度值: {{ item.hot }}</span>
            </li>
          </ul>
        </div>
      </div>
    </div>

    <div class="main">
      <span class="main_hot">热门电影</span>
      <ul v-for="(item, index) in mainList" :key="index">
        <li>
          <router-link :to="'/film/detail?fid=' + item.id">
            <div class="main_ul">
              <div class="main_img">
                <img :src="item.cover" />
                <div class="black">
                  {{ item.name }}
                </div>
                <span>{{ item.hot }}人想看</span>
              </div>
              <span>{{ item.releaseTime }}上映</span>
            </div>
          </router-link>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
import { ListAllPoster, ListAllFilm, ListHots } from "../api/film";
export default {
  data() {
    return {
      posterList: [],
      status: 1,
      top1: {},
      top2: {},
      top3: {},
      mainList: [],
      topList: [],
    };
  },
  mounted() {
    this.posterQuery();
    this.nowplaying();
    this.topListQuery();
    this.formatDate();
  },

  methods: {
    posterQuery() {
      ListAllPoster(this.status).then((res) => {
        if (res.code == 200) {
          this.posterList = res.data;
        }
      });
    },
    nowplaying() {
      ListAllFilm().then((res) => {
        if (res.code == 200) {
          for (var i = 0; i < res.data.length; i++) {
            //res.data[i].releaseTime=ConvertJSONDateToJSDate("/Date("+res.data[i].releaseTime+")/");
            res.data[i].releaseTime = this.formatDate(res.data[i].releaseTime);
          }
          this.mainList = res.data;
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
      return str;
    },
    topListQuery() {
      ListHots().then((res) => {
        if (res.code == 200) {
          for (var i = 0; i < res.data.length; i++) {
            //res.data[i].releaseTime=ConvertJSONDateToJSDate("/Date("+res.data[i].releaseTime+")/");
            res.data[i].releaseTime = this.formatDate(res.data[i].releaseTime);
          }
          this.topList = res.data;
          this.top1 = this.topList[0];
          this.top2 = this.topList[1];
          this.top3 = this.topList[2];
          this.topList.splice(0, 3);
        }
      });
    },
  },
};
</script>

<style lang="scss" scoped>
.index {
  margin: 50px auto 0;
  width: 1510px;
  overflow: hidden;
  .block {
    width: 100%;
  }
  .as {
    width: 30%;
    float: right;
    .top {
      width: 320px;
      span {
        font-size: 26px;
        color: orange;
      }
      .top1 {
        margin-top: 20px;
        border: 1px solid #eee;
        height: 165px;
        img {
          float: left;
        }
        .top1_text {
          margin-top: 30px;
          float: right;
          height: 30px;
          line-height: 35px;
          font-size: 18px;
          list-style-type: none;
          color: #91949c;
          .hot_text {
            font-size: 14px;
            color: orange;
          }
        }
      }
      .top2 {
        clear: left;
        margin-top: 20px;
        border: 1px solid #eee;
        width: 150px;
        color: #91949c;
        font-size: 16px;
        float: left;
        img {
          width: 100%;
        }
        p {
          height: 20px;
          line-height: 15px;
          margin: 10px;
        }
        .top2_hot {
          font-size: 14px;
          color: orange;
        }
      }
      .top3 {
        margin-top: 20px;
        border: 1px solid #eee;
        width: 150px;
        color: #91949c;
        font-size: 16px;
        float: right;
        img {
          width: 100%;
        }
        p {
          height: 20px;
          line-height: 15px;
          margin: 10px;
        }
        .top3_hot {
          font-size: 14px;
          color: orange;
        }
      }
      .more_top {
        clear: both;
        margin-top: 20px;
        ul {
          list-style-type: none;
          li {
            span {
              font-size: 16px;
              color: #91949c;
              height: 50px;
              line-height: 50px;
              display: inline-block;
            }
            .more_name {
              padding-left: 20px;
            }
            .more_hot {
              float: right;
              font-size: 14px;
              color: orange;
            }
          }
        }
      }
    }
  }
  .main {
    width: 60%;
    float: left;
    .main_hot {
      font-size: 26px;
      color: orange;
      position: relative;
      top: 20px;
      left: 150px;
    }
    ul {
      list-style-type: none;
      margin-left: 100px;
      .main_ul {
        width: 150px;
        float: left;
        margin-left: 50px;
        margin-top: 60px;
        text-align: center;

        .main_img {
          border: 1px solid #eee;
          background-color: #eee;
          position: relative;
          overflow: hidden;
          img {
            width: 100%;
            height: 210px;
          }
          .black {
            position: absolute;
            width: 100%;
            height: 40px;
            background-color: rgba(0, 0, 0, 0.3);
            color: white;
            text-align: left;
            line-height: 45px;
            padding-left: 10px;
            top: 167px;
            left: 0;
            display: none;
            overflow: hidden;
          }
          img:hover + .black {
            display: block;
          }
          span {
            color: orange;
            margin-left: -40px;
            height: 30px;
            line-height: 30px;
            display: block;
          }
        }
        span {
          display: block;
          color: #91949c;
          font-size: 14px;
          height: 30px;
          line-height: 40px;
        }
      }
    }
  }
}
</style>