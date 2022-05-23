<template>
  <div class="detail">
    <i class="el-icon-back"></i>
    <el-link
      ><el-button type="text" @click="$router.back(-1)" style="color: black"
        >返回</el-button
      >
    </el-link>
    <span class="span1">详情页面</span>
    <h1 class="title">剧情简介</h1>
    <p style="text-indent: 2em">{{ text }}</p>
    <h1 class="title">电影评论</h1>
    <div v-for="(item, index) in list" :key="index" v-show="list.length!=0">
      <img :src="item.user.avatar" />
      <div class="critic">
        <p>{{ item.user.nickname }}</p>
        <el-rate
          v-model="item.filmEvaluate.star"
          disabled
          show-score
          text-color="#ff9900"
          style="margin: 0px"
        >
        </el-rate>

        <p class="text">{{ item.filmEvaluate.comment }}</p>
        <div class="time">
          <i> time : {{ item.filmEvaluate.createAt }}</i>
        </div>
      </div>
    </div>
		<div v-show=" list == null || list.length==0"> 暂无评论 </div>
	</div>
</template>
<script>
import { FilmCritic, FilmDetail } from "@/api/film";
import { globalBus } from '@/api/globalBus.js';
import  config from '@/config.js';
//https://www.jianshu.com/p/01c7e752684e
export default {
  data() {
    return {
      text: "aa",
      filmId: this.$route.query.fid,
      list: [
        // {
        //   avatar: require("../../assets/img/avatar.png"),
        //   nickname: "ASSS",
        //   star: 5,
        //   comment: "good",
        //   createAt: "2021-03-31 16：12：45",
        // },
        // {
        //   avatar: require("../../assets/img/avatar.png"),
        //   nickname: "AShhS",
        //   star: 3,
        //   comment: "good",
        //   createAt: "2021-03-31 16：12：45",
        // },
      ],
    };
  },
  mounted() {
    this.QueryFilmCritic();
    this.QueryFilmDetail();
		//监听事件
		globalBus.$on('countNumber', () => {
		  this.QueryFilmCritic();//调用函数。如何监听一次？？？     
		});
  },
  methods: {
    QueryFilmCritic() {
			 
      FilmCritic(localStorage.getItem('fid')).then((res) => {
				//新加入的评论，如何刷新
        if (res.code == 200) {
					//console.log(res.data);
					for(var i=0;i<res.data.length;i++){
						res.data[i].user.avatar=config.API_URL+res.data[i].user.avatar
						res.data[i].filmEvaluate.createAt=this.formatDate(res.data[i].filmEvaluate.createAt);
					}
          this.list = res.data;
        }
      });
    },
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
			str+=date.getMinutes()+":";
			if(date.getSeconds()<10){
				str+="0";
			}
			str+=date.getSeconds();
			//console.log(str);
			return str;
		},
    QueryFilmDetail() {
			
      FilmDetail(localStorage.getItem('fid')).then((res) => {
        if (res.code == 200) {
          this.text = res.data.introduction;
        }
      });
    },
    
  },
};
</script>
<style lang="scss">
.detail {
  // background: red;
  width: 800px;
  margin: 0 auto;
  margin-top: 80px;
  .span1 {
    margin-left: 20px;
  }
  .title {
    height: 100px;
    line-height: 100px;
  }
  div {
    img {
      width: 50px;
      height: 50px;
      border-radius: 25px;
      display: inline-block;
      position: relative;
      bottom: 20px;
    }
    .critic {
      display: inline-block;
      margin-left: 20px;
      width: 680px;
      .text {
        height: 50px;
        line-height: 50px;
      }
      .time {
        float: right;
        color: rgb(209, 208, 208);
      }
    }
  }
}
</style>