<template>
  <div class="activity">
    <div class="block">
      <el-timeline>
        <el-timeline-item
          placement="top"
          size="large"
          v-for="(item, index) in actList"
          :key="index"
          :timestamp="item.createTime"
        >
          <!-- <div class="act_start">{{ item.startTime }}</div> -->
          <el-card class="box-card">
            <div class="act_content">{{ item.content }}</div>
            <div class="act_start">活动开始时间: {{ item.startTime }}</div>
            <div class="act_finish">活动结束时间: {{ item.endTime }}</div>
            <el-button
              style="float: left; padding: 3px 0; margin-bottom: 40px"
              type="text"
              @click="handleRegister(item)"
              >报名参加</el-button
            >
          </el-card>
        </el-timeline-item>
      </el-timeline>
    </div>
  </div>
</template>

<script>
import { ListAllActivity, AddRegistration } from "@/api/activity";
export default {
  data() {
    return {
      actList: [
        // {
        //   content:
        //     "活动简述:为了满足广大儿童与家长的娱乐、交流、学习需求,让我们的孩子们有-块专属于自己的屏幕与天空，晚报拟将晚报内部电影厅打造成专属的儿童亲子影院，定期播放专属儿童的各类电影与演出活动,如英语专场、爸爸去看电影专场、假期电影周、动漫专场等等,让亲子家庭在娱乐中提升、在快乐中沉思。同时影院将并与商业机构合作,开展各类知识讲座与交流,如儿童保险、家庭理财、儿童教育、母婴用品交流等等，在丰富儿童的业余生活，服务亲子家庭的基本需求的同时，促进家庭和谐，让父母与子女相互尊重、共同教育、-起成长",
        //   startTime: "2021-04-03",
        //   finishTime: "2021-04-03",
        // },
        // {
        //   content:
        //     "活动简述:为了满足广大儿童与家长的娱乐、交流、学习需求,让我们的孩子们有-块专属于自己的屏幕与天空，晚报拟将晚报内部电影厅打造成专属的儿童亲子影院，定期播放专属儿童的各类电影与演出活动,如英语专场、爸爸去看电影专场、假期电影周、动漫专场等等,让亲子家庭在娱乐中提升、在快乐中沉思。同时影院将并与商业机构合作,开展各类知识讲座与交流,如儿童保险、家庭理财、儿童教育、母婴用品交流等等，在丰富儿童的业余生活，服务亲子家庭的基本需求的同时，促进家庭和谐，让父母与子女相互尊重、共同教育、-起成长",
        //   startTime: "2021-04-03",
        //   finishTime: "2021-04-03",
        // },
        // {
        //   content:
        //     "活动简述:为了满足广大儿童与家长的娱乐、交流、学习需求,让我们的孩子们有-块专属于自己的屏幕与天空，晚报拟将晚报内部电影厅打造成专属的儿童亲子影院，定期播放专属儿童的各类电影与演出活动,如英语专场、爸爸去看电影专场、假期电影周、动漫专场等等,让亲子家庭在娱乐中提升、在快乐中沉思。同时影院将并与商业机构合作,开展各类知识讲座与交流,如儿童保险、家庭理财、儿童教育、母婴用品交流等等，在丰富儿童的业余生活，服务亲子家庭的基本需求的同时，促进家庭和谐，让父母与子女相互尊重、共同教育、-起成长",
        //   startTime: "2021-04-03",
        //   finishTime: "2021-04-03",
        // },
      ],
      form: {
        aid: "",
        uid: "",
      },
      uid: localStorage.getItem("uid"),
    };
  },
  mounted() {
    this.QueryListAllActivity();
  },
  methods: {
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
    handleRegister(item) {
      if (this.uid == null) {
        this.$confirm("系统还没有检测到您的登陆信息, 是否去登录?", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }).then(() => {
          this.$router.push("/login");
        });
      } else {
        //开始报名
        //console.log(item);
        let data = {
          uid: localStorage.getItem("uid"),
          aid: item.id,
        };
        AddRegistration(data).then((res) => {
          if (res.code == 200) {
            this.$message({
              message: "报名成功",
              type: "success",
            });
          }
        });
      }
    },
    QueryListAllActivity() {
      ListAllActivity().then((res) => {
        if (res.code == 200) {
          for (var i = 0; i < res.data.length; i++) {
            res.data[i].startTime = this.formatDate(res.data[i].startTime);
            res.data[i].endTime = this.formatDate(res.data[i].endTime);
            res.data[i].createTime = this.formatDate(res.data[i].createTime);
          }
          this.actList = res.data;
        }
      });
    },
  },
};
</script>

<style scoped>
.activity {
  margin: 150px auto 0;
  width: 1510px;
  overflow: hidden;
}
.block {
  margin-left: 150px;
}
.box-card {
  width: 1250px;
  border: 1px solid black;
}
.act_start {
  color: #91949c;
  position: relative;
  top: -10px;
  left: 0;
}
.act_content {
  font-size: 18px;
  font-weight: bold;
  margin: 30px 70px 30px 0;
  color: rgb(12, 11, 11);
}
.act_start {
  margin-bottom: 10px;
  color: #91949c;
}
.act_finish {
  margin-bottom: 30px;
  color: #91949c;
}
</style>