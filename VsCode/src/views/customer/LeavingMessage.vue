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
        <p>time：{{ item.leavingMessage.createAt }}</p>

        <div v-if="item.leavingMessage.reply != 'null'">
          <p>客服回复</p>
          <p class="p1">{{ item.leavingMessage.reply }}</p>
        </div>
        <el-button type="text" class="btn" @click="dialogVisible = true"
          >回复留言Reply</el-button
        >
      </div>

      <el-dialog title="回复留言" :visible.sync="dialogVisible" width="45%">
        <el-input
          type="textarea"
          width="55%"
          :autosize="{ minRows: 4, maxRows: 5 }"
          placeholder="请输入内容"
        >
        </el-input>
        <span slot="footer" class="dialog-footer">
          <el-button @click="dialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="dialogVisible = false"
            >确 定</el-button
          >
        </span>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import { LMessage } from "@/api/lmessage";
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
    };
  },
  mounted() {
    this.QLMessage();
  },
  methods: {
    QLMessage() {
      LMessage().then((res) => {
        if (res.code == 200) {
          console.log(res);
          this.list = res.data;
        }
      });
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