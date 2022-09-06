<template>
  <div class="box" style="padding: 50px">
    <el-button @click="dialogVisible = true">添加影院活动</el-button>
    <br /><br /><br />
    <el-table :data="tableData" style="width: 100% height:100%">
      <el-table-column prop="startTime" label="开始时间" width="180">
      </el-table-column>
      <el-table-column prop="endTime" label="结束时间" width="180">
      </el-table-column>
      <el-table-column prop="content" label="活动内容" width="300" heitht="200">
      </el-table-column>
      <el-table-column prop="number" label="参加人数"> </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button type="danger" plain @click="del(scope.row)" prop="id"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>
    <el-dialog title="添加活动" :visible.sync="dialogVisible" width="45%">
      <el-form ref="form" :model="form" label-width="80px">
        <el-form-item label="活动时间">
          <el-col :span="11">
            <el-date-picker
              type="date"
              placeholder="选择日期"
              v-model="form.date1"
              style="width: 100%"
            ></el-date-picker>
          </el-col>
          <el-col class="line" :span="2">-</el-col>
          <el-col :span="11">
            <el-date-picker
              type="date"
              placeholder="选择日期"
              v-model="form.date2"
              style="width: 100%"
            ></el-date-picker>
          </el-col>
        </el-form-item>
      </el-form>
      <el-input
        type="textarea"
        width="55%"
        :autosize="{ minRows: 4, maxRows: 5 }"
        placeholder="请输入内容"
        v-model="text1"
      >
      </el-input>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="commit()">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { ListAllActivity, AddActivity, DelActivity } from "@/api/activity";
export default {
  data() {
    return {
      form: {
        date1: "",
        date2: "",
      },
      text1: "",
      dialogVisible: false,
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
    QListAllActivity() {
      ListAllActivity().then((res) => {
        console.log(res.data); //没有输出？？？？
        if (res.code == 200) {
          for (var i = 0; i < res.data.length; i++) {
            res.data[i].startTime = this.formatDate(res.data[i].startTime);
            res.data[i].endTime = this.formatDate(res.data[i].endTime);
          }
          this.tableData = res.data;
        }
      });
    },
    commit() {
      let data = {
        startTime: this.form.date1,
        endTime: this.form.date2,
        content: this.text1,
        number: 0,
      };
      console.log();
      AddActivity(data).then((res) => {
        if (res.code == 200) {
          this.$message({
            message: "添加成功",
            type: "success",
          });
          this.form = {};
          this.text1 = "";
          setTimeout(() => {
            location.reload();
          }, 500);
          this.dialogVisible = false;
        }
      });
    },
    del(row) {
      console.log(row);
      let data = row;
      DelActivity(data).then((res) => {
        if (res.code == 200) {
          this.$message({
            message: "删除成功",
            type: "success",
          });
          setTimeout(() => {
            location.reload();
          }, 500);
        }
      });
    },
  },
};
</script>

<style>
</style>