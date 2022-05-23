<template>
  <div class="home">
    <div class="home_scroll">
      <el-carousel indicator-position="outside" height="500px">
        <el-carousel-item v-for="(item, index) in posterList" :key="index">
          <img :src="item.url" class="CarImg" alt="" @click="handleclick(item)" style="cursor:pointer;"/>
        </el-carousel-item>
      </el-carousel>
    </div>
    <div class="list">
      <div class="home_lists">
        <p
          style="
            color: orange;
            font-size: 30px;
            height: 50px;
            line-height: 30px;
          "
        >
          <b>热门电影</b>
        </p>
        <ul>
          <li v-for="(item, index) in mainlist" :key="index">
            <div class="img_block" @click="handleclick(item)" style="cursor:pointer;">
              <img :src="item.cover" alt="" />
              <div class="home_block">
                <p class="name_text">{{ item.name }}</p>
                <div class="box"></div>
              </div>
            </div>
            <p class="hot_text">{{ item.people }}人想看</p>
            <p class="home_time">{{ item.releaseTime }}</p>
          </li>
        </ul>
      </div>

      <div class="home_hot">
        <div class="top">
          <span>热门榜单</span>

          <div class="top1" @click="handleclick(top1)" style="cursor:pointer;">
            <img :src="top1.cover" alt="" width="130px" />
            <ul class="top1_text">
              <li>{{ top1.name }}</li>
              <li>上映时间：{{ top1.releaseTime }}</li>
              <li class="hot_text">热度值：{{ top1.hot }}</li>
            </ul>
          </div>

          <div class="top2" @click="handleclick(top2)" style="cursor:pointer;">
            <img :src="top2.cover" alt="" width="130px" />
            <ul class="top2_text">
              <li>{{ top2.name }}</li>
              <li class="hot_text">热度值：{{ top2.hot }}</li>
            </ul>
          </div>

          <div class="top3" @click="handleclick(top3)" style="cursor:pointer;">
            <img :src="top3.cover" alt="" width="130px" />
            <ul class="top3_text">
              <li>{{ top3.name }}</li>
              <li class="hot_text">热度值：{{ top3.hot }}</li>
            </ul>
          </div>
        </div>

        <div class="more_top">
          <ul>
            <li v-for="(item, index) in morelist" :key="index" @click="handleclick(item)" style="cursor:pointer;">
              <span class="more_index">{{ index + 4 }}</span>
              <span class="more_name">{{ item.name }}</span>
              <span class="more_hot">热度值：{{ item.hot }}</span>
            </li>
          </ul>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import { ListAllPoster, ListAllFilm, ListTopFilm } from "@/api/film";
import {ConvertJSONDateToJSDate} from "@/api/util.js";

export default {
  data() {
    return {
      status: 1,
      //热门电影
      mainlist: [],
      //轮播图数组
      posterList: [],
      top1: {
        // cover_show: require("../assets/filmImg/renchaoxiongyong.jpg"),
        // name: "人潮汹涌",
        // releasTime: "2021-11-12",
        // hot: "8.9",
      },
      top2: {
        // cover_show: require("../assets/filmImg/renchaoxiongyong.jpg"),
        // name: "人潮汹涌",
        // hot: "8.9",
      },
      top3: {
        // cover_show: require("../assets/filmImg/renchaoxiongyong.jpg"),
        // name: "人潮汹涌",
        // hot: "8.9",
      },
      morelist: [
        // { name: "成龙", hot: "8" },
        // { name: "成龙", hot: "8" },
        // { name: "XXXX", hot: "8" },
        // { name: "XXXX", hot: "8" },
        // { name: "XXXX", hot: "8" },
        // { name: "XXXX", hot: "8" },
      ],
    };
  },
  mounted() {
    this.posterQuery();
    this.nowplaying();
    this.TopFilmQuery();
  },
  methods: {
		
    posterQuery() {
      ListAllPoster().then((res) => {
        if (res.code == 200) {
          this.posterList = res.data;
					//console.log(res.data);
        }
      });
    },
    nowplaying() {
      ListAllFilm().then((res) => {
        if (res.code == 200) {
					for(var i=0;i<res.data.length;i++){
						res.data[i].releaseTime=this.formatDate(res.data[i].releaseTime);
					}
          this.mainlist = res.data;
					
        }
      });
    },
		formatDate(dateint) {
			//日期格式化，
			//console.log(dateint);
			var date = new Date(parseInt(dateint));
			var str=date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
			return str;
		} ,
    TopFilmQuery() {
      ListTopFilm().then((res) => {
        if (res.code == 200) {
					//console.log(res.data);
					for(var i=0;i<res.data.length;i++){
						//res.data[i].releaseTime=ConvertJSONDateToJSDate("/Date("+res.data[i].releaseTime+")/");
						res.data[i].releaseTime=this.formatDate(res.data[i].releaseTime);
					}
          this.top1 = res.data[0];
          this.top2 = res.data[1];
          this.top3 = res.data[2];
          this.morelist = res.data.splice(3);
        }
      });
    },
    handleclick(item) {
			localStorage.setItem("fid",item.id)
      this.$router.push("/filmdetail/detail?fid=" + item.id);
    },
  },
};
</script>

<style lang="scss">
.home {
  margin: 0 auto;

  width: 1250px;
  //   background: pink;
  .home_scroll {
    margin: 0 auto;
    height: 500px;
    width: 100%;
    .CarImg {
      height: 100%;
      width: 100%;
    }
  }
  .list {
    margin: 50px 0;
    //   padding: 0 20px;
    width: 100%;
    height: 1400px;
    .home_lists {
      width: 60%;
      background: white;
      height: 1400px;
      float: left;
      padding: 10px;
      ul {
        li {
          width: 150px;
          margin: 0 15px;
          height: 300px;
          background: white;
          list-style: none;
          display: inline-block;
          .img_block {
            background: #eee;
            position: relative;
            overflow: hidden;
            img {
              width: 150px;
              height: 220px;
            }
            .home_block {
              .name_text {
                width: 150px;
                font-size: 17px;
                height: 40px;
                text-align: center;
                line-height: 40px;
                /*居中*/
                color: white;
                position: absolute;
                /* top:250px; */
                bottom: -40px;
                z-index: 1;
                transition: all 0.5s linear 0s;
              }
              .box {
                width: 150px;
                height: 40px;
                background-color: black;
                position: absolute;
                /* top:250px; */
                bottom: -40px;
                filter: alpha(opacity=50); /*处理IE兼容性*/
                opacity: 0.5;
                /*透明度*/
                /* background: rgba(255, 0, 0, 0.5); */
                /*或者透明度*/
                transition: all 0.5s linear 0s;
              }
            }
          }
          .img_block:hover .box,
          .img_block:hover .name_text {
            bottom: 0;
          }

          .hot_text {
            height: 30px;
            line-height: 30px;
            font-size: 15px;
            padding-left: 10px;
            color: orange;
            background: #eee;
          }
          .home_time {
            height: 50px;
            line-height: 50px;
            font-size: 16px;
            text-align: center;
            color: grey;
            background: white;
          }
        }
      }
    }
    .home_hot {
      width: 30%;
      //   background: yellowgreen;
      height: 960px;
      float: right;
      padding: 10px;
      .top {
        overflow: hidden;
        span {
          font-size: 26px;
          color: orange;
        }
        .top1 {
          margin: 20px 0;
          border: 1px solid #eee;
          height: 180px;
          img {
            float: left;
          }
          .top1_text {
            float: right;
            height: 35px;
            line-height: 35px;
            font-size: 18px;
            // margin-top: 20px;
            color: gray;
            margin-right: 40px;
            margin-top: 30px;
            li {
              list-style: none;
            }
            .hot_text {
              color: red;
            }
          }
        }
        .top2,
        .top3 {
          border: 1px solid #eee;
          width: 150px;
          color: gray;
          font-size: 16px;

          img {
            width: 150px;
          }
          li {
            height: 18px;
            line-height: 18px;
            margin: 10px;
            list-style: none;
          }
          .hot_text {
            font-size: 14px;
            color: orange;
          }
        }
        .top2 {
          float: left;
        }
        .top3 {
          float: right;
        }
      }

      .more_top {
        width: 100%;

        overflow: hidden;
        clear: both;
        margin-top: 20px;
        // background: red;
        ul {
          li {
            display: block;
            list-style: none;
            height: 60px;

            span {
              font-size: 19px;
              color: rgb(161, 157, 157);
              height: 60px;
              line-height: 60px;
              display: inline-block;
              //   float: left;
            }
            .more_name {
              padding-left: 22px;
              color: gray;
            }
            .more_hot {
              float: right;
              font-size: 14px;
              color: orange;
              // margin-left: 80px;
              z-index: 1;
            }
          }
        }
      }
    }
  }
}
</style>