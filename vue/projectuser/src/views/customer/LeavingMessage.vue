<template>
  <div class="lm">
    <div class="li" v-for="(item, index) in list" :key="index">
      <div class="img">
        <img v-if="item.user != null" :src="item.user.avatar" alt="null" />
        <img v-else src="../../assets/img/nim.png" alt="" />
      </div>
      <div class="text">
        <p v-if="item.user != null">{{ item.user.nickname }}</p>
        <p class="p1">{{ item.leavingMessage.content }}</p>
        <p>time：{{ item.leavingMessage.createTime }}</p>

        <div v-if="item.leavingMessage.reply != 'null'">
          <p>客服回复</p>
          <p class="p1">{{ item.leavingMessage.reply }}</p>
        </div>
        <el-button type="text" class="btn" @click="reply(item)"
          >回复留言Reply</el-button
        >
      </div>

      <el-dialog title="回复留言" :visible.sync="dialogVisible" width="45%">
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
  </div>
</template>

<script>
import { LMessage, reply } from "@/api/lmessage";
import { globalBus } from "@/api/globalBus";
import config from "@/config.js";
export default {
  data() {
    return {
      dialogVisible: false,
      list: [
        // {
        //   url: require("@/assets/img/avatar.png"),
        //   name: "Uxxx",
        //   critic: "环境很好",
        //   time: "2022-01-19  14:20:56",
        //   reply: "",
        // },
        // {
        //   url: require("@/assets/img/avatar.png"),
        //   name: "Uxxx",
        //   critic: "环境很好",
        //   time: "2022-01-19  14:20:56",
        //   reply: "感谢",
        // },
        // {
        //   url: require("@/assets/img/avatar.png"),
        //   name: "Uxxx",
        //   critic: "环境很好",
        //   time: "2022-01-19  14:20:56",
        //   reply: "",
        // },
        // {
        //   url: require("@/assets/img/avatar.png"),
        //   name: "Uxxx",
        //   critic: "环境很好",
        //   time: "2022-01-19  14:20:56",
        //   reply: "",
        // },
        // {
        //   url: require("@/assets/img/avatar.png"),
        //   name: "Uxxx",
        //   critic: "环境很好",
        //   time: "2022-01-19  14:20:56",
        //   reply: "",
        // },
      ],
      text1: "",
      leavingMessage: [],
    };
  },
  mounted() {
    this.QLMessage();
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
    QLMessage() {
      LMessage().then((res) => {
        if (res.code == 200) {
          console.log(res);
          for (var i = 0; i < res.data.length; i++) {
            res.data[i].user.avatar = config.API_URL + res.data[i].user.avatar;
            res.data[i].leavingMessage.createTime = this.formatDate(
              res.data[i].leavingMessage.createTime
            );
          }
          this.list = res.data;
        }
      });
    },
    reply(item) {
      this.leavingMessage.id = item.leavingMessage.id;
      this.dialogVisible = true;
      console.log(this.leavingMessage);
    },
    commit() {
      let data = {
        id: this.leavingMessage.id,
        reply: this.text1,
      };
      console.log(this.leavingMessage);
      reply(data).then((res) => {
        if (res.code == 200) {
          this.$message({
            message: "回复成功",
            type: "success",
          });
          this.text1 = "";
          setTimeout(() => {
            location.reload();
          }, 500);
        }
      });
      this.dialogVisible = false;
    },
  },
};
</script>

<style lang="scss">
.lm {
  margin-bottom: 90px;
  textarea {
    resize: none;
    width: 550px;
    margin-left: 50px;
  }
  .li {
    // height: 210px;
    padding: 30px;
    overflow: auto;
    position: relative;
    margin-bottom: 20px;
    .img {
      display: inline-block;
      //   height: 120px;
      position: absolute;
      left: 40px;
      top: 60px;
      img {
        width: 60px;
        height: 60px;
        border-radius: 60px;
        // vertical-align: middle;
      }
    }
    .text {
      display: inline-block;
      margin-left: 90px;
      p {
        height: 35px;
        line-height: 35px;
        color: rgb(172, 168, 168);
        font-size: 18px;
      }
      .btn {
        height: 35px;
        line-height: 35px;
        font-size: 18px;
      }
      .p1 {
        color: black;
      }
    }
  }
}
</style>