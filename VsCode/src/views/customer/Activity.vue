<template>
  <div class="box" style="padding: 50px">
    <el-button>添加影院活动</el-button>
    <br /><br /><br />
    <el-table :data="tableData" style="width: 100%">
      <el-table-column prop="startTime" label="开始时间" width="180">
      </el-table-column>
      <el-table-column prop="endTime" label="结束时间" width="180">
      </el-table-column>
      <el-table-column
        prop="content"
        label="活动内容"
        width="180"
        heitht="200"
        show-overflow-tooltip="true"
      >
      </el-table-column>
      <el-table-column prop="number" label="参加人数"> </el-table-column>
      <el-table-column label="操作">
        <el-button type="danger" plain>删除</el-button>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import { ListAllActivity } from "@/api/activity";
export default {
  data() {
    return {
      tableData: [
        // {
        //   start: "2016-05-02",
        //   end: "2018-05-02",
        //   content: "XXXX",
        //   num: "1 ",
        // },
        // {
        //   start: "2016-05-02",
        //   end: "2018-05-02",
        //   content: "XXXX",
        //   num: "1 ",
        // },
        // {
        //   start: "2016-05-02",
        //   end: "2018-05-02",
        //   content: "XXXX",
        //   num: "1 ",
        // },
        // {
        //   start: "2016-05-02",
        //   end: "2018-05-02",
        //   content: "XXXX",
        //   num: "1 ",
        // },
      ],
    };
  },
  mounted() {
    this.QListAllActivity();
  },
  methods: {
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
    QListAllActivity() {
      ListAllActivity().then((res) => {
				console.log(res.data);//没有输出？？？？
        if (res.code == 200) {
					for(var i=0;i<res.data.length;i++){
						res.data[i].startTime=this.formatDate(res.data[i].startTime);
						res.data[i].endTime=this.formatDate(res.data[i].endTime);
					}				
          this.tableData = res.data;
        }
      });
    },
  },
};
</script>

<style>
</style>