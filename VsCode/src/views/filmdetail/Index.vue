<template>
  <div class="index">
    <div class="top">
      <img :src="film.cover" alt="" />
      <div class="text">
        <h1>{{ film.name }}</h1>
        <p>电影类型：{{ film.type }}</p>
        <p>{{ film.region }}/{{ film.duration }}分钟</p>
        <p class="p3">上映时间：{{ film.releaseTime }} 上映</p>
        <el-button type="danger"
          ><router-link
            :to="'/filmdetail/ticket?fid=' + filmId"
            style="color: white; text-decoration: none"
          >
            特惠购票</router-link
          ></el-button
        >
        <el-button type="danger" style="width: 100px" @click="dialog()"
          >评分</el-button
        >
      </div>
      <el-rate
        v-model="film.hot"
        disabled
        show-score
        text-color="#ff9900"
        style="
          display: inline-block;
          position: relative;
          bottom: 280px;
          left: 70px;
        "
      >
      </el-rate>
      <el-dialog title="电影评分" :visible.sync="dialogVisible" width="55%">
        <el-rate v-model="dialogvalue" show-text style="margin-left: 20px">
        </el-rate
        ><br /><br />
        <el-input
          type="textarea"
          width="45%"
          :autosize="{ minRows: 8, maxRows: 10 }"
          placeholder="请输入内容"
          resize="none"
					v-model="inputContent"
				>
        </el-input>
        <span slot="footer" class="dialog-footer">
          <el-button @click="dialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="submitComment">确定提交</el-button>
        </span>
      </el-dialog>
    </div>

    <div class="film-content">
      <router-view></router-view>
    </div>
  </div>
</template>
<script>
import { FilmDetail, FilmScore } from "@/api/film";
import { globalBus } from '@/api/globalBus';
export default {
  data() {
    return {
      dialogvalue: null,
      dialogVisible: false,
      film: {},
      login: true,
      filmId: this.$route.query.fid,
      form: {
        fid: "",
        uid: "",
        star: 5,
        comment: "",
      },
			inputContent:'',
			timer:'',
    };
  },
  mounted() {
    this.QueryFilmDetail();
		globalBus.$on('countNumber', () => {
		  this.QueryFilmDetail();//调用函数。如何监听一次？？？     
		});
  },
	// beforeDestroy() {
	// 	clearTimeout(this.timer);//销毁
	// },
  methods: {
    dialog() {
      if (!localStorage.getItem("uid")) {
        this.$confirm("系统还没有检测到您的信息 是否登录？", "提示", {
          confirmButtonText: "确定",
          confirmButtonText: "取消",
          type: "warning",
        }).then(() => {
          this.$router.push("/login");
        });
      } else {
        this.dialogVisible = true;
      }
    },
    submitComment() {
      this.form.fid = this.film.id;
      this.form.uid = localStorage.getItem("uid");
			this.form.star= this.dialogvalue;
			this.form.comment = this.inputContent;
      const form = this.form;
      FilmScore(form).then((res) => {
        if (res.code == 200) {
					console.log(res.data);
          this.dialogVisible = false;
					//this.timer = setTimeout("this.myFlush", 10000);//延迟函数如何调用
					this.timer = setTimeout(()=>{
						//console.log('countNumber');
						globalBus.$emit('countNumber');
					},10000);
					//重新跳转，不起作用
					if(res.data.success=="ok"){
						//setTimeout("","2000");//延迟函数如何调用
					}
					//this.$router.push("/filmdetail/detail?fid=" + item.id);
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
    QueryFilmDetail() {
			let fid=localStorage.getItem("fid");
			
			console.log("msg:"+this.filmId);
      FilmDetail(fid).then((res) => {
        if (res.code == 200) {
					res.data.releaseTime=this.formatDate(res.data.releaseTime);
          this.film = res.data;
					//console.log(this.film);
          // this.film.cover_show=config.API_URL+"/upload?id="+this.film.cover
        }
      });
    },
  },
};
</script>
<style lang="scss">
.index {
  margin-bottom: 100px;
  .top {
    height: 330px;
    background: rgb(192, 192, 236);
    padding: 0px 150px;
    img {
      display: inline-block;
      width: 230px;
      height: 300px;
      margin-top: 70px;
    }
    .text {
      display: inline-block;
      position: relative;
      bottom: 120px;
      left: 60px;
      color: white;
      h1 {
        height: 50px;
        line-height: 50px;
      }
      p {
        height: 30px;
        line-height: 30px;
      }
      .p3 {
        margin-bottom: 20px;
      }
    }
  }
}
</style>