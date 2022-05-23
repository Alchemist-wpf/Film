<template>
  <div class="block">
    <el-timeline>
      <el-timeline-item
        v-for="(item, index) in activitylist"
        :key="index"
        :timestamp="item.createAt"
        placement="top"
      >
        <el-card class="activity-card">
          <h4>{{ item.content }}</h4>
          <br />
          <p>活动开始时间： {{ item.startTime }}</p>
          <br>
          <p>活动结束时间： {{ item.endTime }}</p>
          <br />
          <el-button
            type="text"
            @click="handleRegister(item)"
            href="#"
            style="font-size: 12px; color: rgb(57, 149, 255)"
            >报名参加</el-button
          >
        </el-card>
      </el-timeline-item>
    </el-timeline>
  </div>
</template>
<script >
import {ListAllActivity,AddRegistration} from '@/api/activity'
export default {
  data() {
    return {
      login: true,
      activitylist: [
      ],
    };
  },
  mounted(){
    this.QueryListAllActivity()
  },
  methods: {
    handleRegister(item) {
      if (this.login == false) {
        this.$confirm("系统还没有检测到您的信息 是否登录？", "提示", {
          confirmButtonText: "确定",
          confirmButtonText: "取消",
          type: "warning",
        }).then(() => {
          this.$router.push("/login");
        });
      } else {
				//开始报名
				//console.log(item);
				let data={
					uid:localStorage.getItem("uid"),
					aid:item.id,
				}
				AddRegistration(data).then((res)=>{
					if(res.code==200){
						this.$message({
						  message: "报名成功",
						  type: "success",
						});
					}
				});
        
      }
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
			
			return str;
		},
    QueryListAllActivity(){
      ListAllActivity().then((res)=>{
        if (res.code == 200) {
					for(var i=0;i<res.data.length;i++){
						res.data[i].startTime=this.formatDate(res.data[i].startTime);
						res.data[i].endTime=this.formatDate(res.data[i].endTime);
					}				
          this.activitylist=res.data
        }
      })
    }
  },
};
</script>
<style lang="scss">
.block {
  width: 1100px;
  margin: 0 auto;
  .activity-card {
    padding: 5px 20px;
  }
}
</style>