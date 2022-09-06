<template>
  <div class="message">
    <div class="kefu">
      <span class="kefu_title">客服人员</span>
      <div class="kefu_member" v-for="(item, index) in memberList" :key="index">
        <img :src="item.avatar" alt="" />
        <p>名字: {{ item.nickname }}</p>
        <p>性别: {{ item.gender }}</p>
        <p>ID: {{ item.id }}</p>
        <el-button
          type="text"
          class="wode_pingjia"
          @click="handleMyevaluate(item)"
          >我的评价</el-button
        >
        <el-button type="text" @click="handleEvaluate(item)"
          >评价客服</el-button
        >
      </div>
    </div>

    <div class="yingyuan">
      <span class="yingyuan_title">影院留言</span>
      <div
        class="visiter_member"
        v-for="(item, index) in visiterList"
        :key="index"
      >
        <img :src="item.user.avatar" alt="" />
        <p>{{ item.user.nickname }}</p>
        <p>{{ item.leavingMessage.content }}</p>
        <p>Time: {{ item.leavingMessage.createTime }}</p>

        <div v-if="item.leavingMessage.reply != null" class="reply">
          <p>客服回复: {{ item.leavingMessage.reply }}</p>
        </div>
      </div>

      <el-button type="success" class="add" @click="openMessage"
        >添加留言</el-button
      >
    </div>
    <!-- 我的评价 -->
    <el-dialog title="我的评价" :visible.sync="dialogViewpoint" width="600px">
      <el-table :data="viewpointTable" style="width: 100%">
        <el-table-column prop="updateTime" label="日期" width="180">
        </el-table-column>
        <el-table-column prop="content" label="评语" width="180">
        </el-table-column>
        <el-table-column property="type" width="150px">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.type === '非常满意'" type="success"
              >非常满意</el-tag
            >
            <el-tag v-if="scope.row.type === '满意'" type="info">满意</el-tag>
            <el-tag v-if="scope.row.type === '不满意'" type="danger"
              >不满意</el-tag
            >
          </template>
        </el-table-column>
      </el-table>
      <span slot="footer" class="dialog-footer"> </span>
    </el-dialog>
    <!-- 评价客服 -->
    <el-dialog title="客服评价" :visible.sync="dialogVisible" width="600px">
      <span style="margin-left: 60px">评价内容</span>
      <el-select v-model="value" placeholder="请选择" style="margin-left: 20px">
        <el-option
          v-for="item in options"
          :key="item.value"
          :label="item.label"
          :value="item.value"
        >
        </el-option>
      </el-select>
      <div>
        <span
          style="
            margin-left: 60px;
            margin-top: 20px;
            display: block;
            float: left;
          "
          >评价内容</span
        >
        <el-input type="textarea" v-model="desc" class="content"></el-input>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="Submit()">提交评价</el-button>
      </span>
    </el-dialog>

    <el-dialog title="留言" :visible.sync="dialogVisible2" width="600px">
      <div>
        <span
          style="
            margin-left: 60px;
            margin-top: 20px;
            display: block;
            float: left;
          "
          >留言内容</span
        >
        <el-input type="textarea" v-model="msg" class="content"></el-input>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible2 = false">取 消</el-button>
        <el-button type="primary" @click="addMessage">提交评价</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import {
  WorkList,
  MyCritic,
  LMessage,
  MyCritic2,
  AddMyEvaluate,
  AddEvaluate,
} from "@/api/lmessage";
import { globalBus } from "@/api/globalBus";
import config from "@/config.js";
export default {
  data() {
    return {
      memberList: [],
      visiterList: [],
      dialogViewpoint: false,
      viewpointTable: [],
      dialogVisible: false,
      dialogVisible2: false,
      options: [
        {
          value: "满意",
          label: "满意",
        },
        {
          value: "不满意",
          label: "不满意",
        },
        {
          value: "非常满意",
          label: "非常满意",
        },
      ],
      value: "",
      desc: "",
      data: {},
      message: {},
      msg: "",
      uid: localStorage.getItem("uid"),
      wid: "",
    };
  },
  mounted() {
    this.QWorkList(); //客服人员列表
    // this.QMyCritic(); //我的评论
    this.QLMessage(); //留言消息
    globalBus.$on("countNumber", () => {
      this.QLMessage();
    });
  },
  methods: {
    openMessage() {
      this.dialogVisible2 = true;
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
      //console.log(str);
      return str;
    },
    QWorkList() {
      WorkList().then((res) => {
        if (res.code == 200) {
          for (let i = 0; i < res.data.length; i++) {
            res.data[i].avatar = config.API_URL + res.data[i].avatar;
          }
          this.memberList = res.data;
        }
        console.log(this.memberList);
      });
    },
    // QMyCritic() {
    //   console.log(this.wid);
    //   MyCritic(localStorage.getItem("uid"), this.wid).then((res) => {
    //     if (res.code == 200) {
    //       for (var i = 0; i < res.data.length; i++) {
    //         res.data[i].updateTime = this.formatDate(res.data[i].updateTime);
    //       }
    //       this.viewpointTable = res.data;
    //       //console.log(res.data);
    //     }
    //   });
    // },
    QLMessage() {
      LMessage().then((res) => {
        if (res.code == 200) {
          console.log(res.data);
          for (var i = 0; i < res.data.length; i++) {
            res.data[i].user.avatar = config.API_URL + res.data[i].user.avatar;
            res.data[i].leavingMessage.createTime = this.formatDate(
              res.data[i].leavingMessage.createTime
            );
          }
          this.visiterList = res.data;
          this.visiterList = this.visiterList.reverse();
          console.log(this.visiterList);
        }
      });
    },
    //点击我的评价按钮时间
    handleMyevaluate(item) {
      this.dialogViewpoint = true;
      this.wid = item.id;
      console.log(this.wid);
      MyCritic(localStorage.getItem("uid"), this.wid).then((res) => {
        if (res.code == 200) {
          for (var i = 0; i < res.data.length; i++) {
            res.data[i].updateTime = this.formatDate(res.data[i].updateTime);
          }
          this.viewpointTable = res.data;
          //console.log(res.data);
        }
      });
    },
    handleEvaluate(item) {
      this.dialogVisible = true;
      this.data.wid = item.id;
      this.data.uid = localStorage.getItem("uid");
      console.log(this.data);
    },
    Submit() {
      this.data.content = this.desc;
      this.data.type = this.value;
      if (this.uid != null) {
        AddEvaluate(this.data).then((res) => {
          if (res.code == 200) {
            //提交成功后，清理上一个输入的内容
            this.value = "";
            this.desc = "";
            this.$message({
              type: "success",
              message: "评价成功",
            });
          }
          setTimeout(() => {
            location.reload();
          }, 100);
          this.dialogVisible = false;
        });
      } else {
        this.$message({
          type: "error",
          message: "请先登录",
        });
      }
    },
    addMessage() {
      this.message.uid = localStorage.getItem("uid");
      this.message.content = this.msg;
      if (this.uid != null) {
        AddMyEvaluate(this.message).then((res) => {
          if (res.code == 200) {
            this.msg = "";
            this.timer = setTimeout(() => {
              console.log("countNumber");
              globalBus.$emit("countNumber");
            }, 10000);
          }
          this.$message({
            type: "success",
            message: "留言成功",
          });
          this.dialogVisible2 = false;
          setTimeout(() => {
            location.reload();
          }, 500);
        });
      } else {
        this.$message({
          type: "error",
          message: "请先登录",
        });
      }
    },
  },
};
</script>

<style scoped>
.message {
  margin: 100px auto 0;
  width: 1510px;
  height: 1000px;
  overflow: hidden;
}
.kefu {
  width: 400px;
  height: 100%;
  overflow: hidden;
  margin-left: 50px;
  float: left;
  background-color: rgb(36, 41, 48);
}
.yingyuan {
  width: 1000px;
  height: 100%;
  float: left;
  overflow: auto;
  background-color: rgb(53, 59, 67);
}
.kefu_title {
  height: 100px;
  line-height: 100px;
  margin-left: 40px;
  font-size: 20px;
  font-weight: bold;
  color: white;
}
.yingyuan_title {
  height: 100px;
  line-height: 100px;
  margin-left: 80px;
  font-size: 20px;
  font-weight: bold;
  color: white;
}
.kefu_member {
  margin-top: 20px;
  margin-left: 40px;
  width: 320px;
  height: 150px;
  border-bottom: 2px dashed rgb(92, 94, 97);
  color: white;
}
.kefu_member p {
  height: 30px;
  line-height: 30px;
  font-size: 15px;
  font-weight: lighter;
}
.kefu_member img {
  width: 80px;
  height: 80px;
  border-radius: 50%;
  float: left;
  margin-right: 40px;
}
.kefu button {
  color: rgb(71, 178, 110);
}
.wode_pingjia {
  margin-top: 10px;
  margin-right: 60px;
}
.visiter_member {
  margin-top: 20px;
  margin-left: 80px;
  width: 320px;
  height: 150px;
  color: white;
}
.visiter_member p {
  height: 30px;
  line-height: 30px;
  font-size: 15px;
  font-weight: lighter;
}
.visiter_member img {
  width: 80px;
  height: 80px;
  border-radius: 50%;
  float: left;
  margin-right: 40px;
}
.add {
  float: right;
  margin-right: 80px;
  margin-bottom: 40px;
  width: 150px;
  background-color: rgb(87, 204, 138);
}
.reply {
  margin-left: 120px;
}
.content {
  margin: 20px;
  float: left;
  width: 400px;
}
</style>