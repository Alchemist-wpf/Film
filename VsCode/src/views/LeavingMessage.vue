<template>
  <div class="message">
    <div class="service_person">
      <p class="service_person_text">客服人员</p>
      <ul>
        <li v-for="(item, index) in service_list" :key="index">
          <div class="service_message">
            <div class="img"><img :src="item.avatar" alt="" /></div>
            <div class="message_person">
              <p>名字：{{ item.username }}</p>
              <p>性别：{{ item.gender }}</p>
              <p>ID：{{ item.id }}</p>
            </div>
            <el-button type="text" @click="openmy(item)" class="open"
              >我的评价</el-button
            >
            <el-button
              type="text"
              @click="openkf(item)"
              :disable="isLogin == true"
              class="open"
              >评价客服</el-button
            >
          </div>
          <el-dialog
            title="提示"
            :visible.sync="item.dialogVisible"
            width="45%"
          >
            <el-table :data="tableData" style="width: 100%">
              <el-table-column prop="createTime" label="日期" width="180">
              </el-table-column>
              <el-table-column prop="content" label="评语" width="180">
              </el-table-column>
              <el-table-column prop="type" width="180">
                <template slot-scope="scope">
                  <el-tag v-if="scope.row.type == '非常满意'" type="success"
                    >非常满意</el-tag
                  >
                  <el-tag v-if="scope.row.type == '满意'" type="info"
                    >满意</el-tag
                  >
                  <el-tag v-if="scope.row.type == '不满意'" type="danger"
                    >不满意</el-tag
                  >
                </template>
              </el-table-column>
            </el-table>
          </el-dialog>
          <el-dialog
            title="客服评价"
            :visible.sync="item.dialogVisible2"
            width="45%"
          >
            <span>评价内容&nbsp;&nbsp;</span>
            <br /><br />
            <el-select
              v-model="value"
              placeholder="请选择"
              style="margin-left: 40px"
            >
              <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
            <br /><br /><br />
            <span>评价内容&nbsp;&nbsp;</span>
            <br /><br />
            <el-input
              type="textarea"
              width="55%"
              :autosize="{ minRows: 4, maxRows: 5 }"
              placeholder="请输入内容"
              v-model="input"
            >
            </el-input>
          </el-dialog>
        </li>
      </ul>
    </div>
    <div class="leaving_meaasge">
      <p class="service_person_text">影院留言</p>
      <ul>
        <li v-for="(item, index) in person_list" :key="index">
          <div class="service_message">
            <div class="img">
              <img v-if="item.user != null" :src="item.user.avatar" alt="null" />
              <img v-else src="../assets/img/nim.png" alt="" />
            </div>
            <div class="message_person">
              <p v-if="item.user">名字：{{ item.user.username }}</p>
              <p v-else>匿名用户</p>
              <p>{{ item.leavingMessage.content }}</p>
              <p>time：{{ item.leavingMessage.createTime }}</p>
            </div>
          </div>
          <div v-if="item.leavingMessage.reply != 'null'" class="reply">
            <div class="d1">客服回复</div>
            <div class="d2">{{ item.leavingMessage.reply }}</div>
          </div>
        </li>
      </ul>
      <el-button type="success" plain class="btn" @click="dialogVisible3 = true"
        >添加留言</el-button
      >
      <el-dialog title="添加留言" :visible.sync="dialogVisible3" width="45%">
        <el-input
          type="textarea"
          width="55%"
          :autosize="{ minRows: 4, maxRows: 5 }"
          placeholder="请输入内容"
        >
        </el-input>
        <span slot="footer" class="dialog-footer">
          <el-button @click="dialogVisible3 = false">取 消</el-button>
          <el-button type="primary" @click="dialogVisible3 = false"
            >确 定</el-button
          >
        </span>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import { WorkList, MyCritic, LMessage } from "@/api/lmessage";
import  config from '@/config.js';
export default {
  data() {
    return {
      input: "",
      dialogVisible3: false,
      options: [
        {
          value: "选项1",
          label: "非常满意",
        },
        {
          value: "选项2",
          label: "满意",
        },
        {
          value: "选项3",
          label: "不满意",
        },
      ],
      value: "",
      isLogin: true,
      tableData: [
        // {
        //   date: "2016-05-02",
        //   message: "XXX",
        //   type: "非常满意",
        // },
        // {
        //   date: "2016-05-02",
        //   message: "hhh",
        //   type: "不满意",
        // },
      ],
      service_list: [
        // {
        //   url_show: require("../assets/img/avatar.png"),
        //   name: "客服1",
        //   sex: "男",
        //   id: "2657532",
        //   dialogVisible: false, //我的评价对话框
        //   dialogVisible2: false,
        // },
        // {
        //   url_show: require("../assets/img/avatar.png"),
        //   name: "客服2",
        //   sex: "女",
        //   id: "2434432",
        //   dialogVisible: false, //我的评价对话框
        //   dialogVisible2: false,
        // },
        // {
        //   url_show: require("../assets/img/avatar.png"),
        //   name: "客服3",
        //   sex: "男",
        //   id: "2675762",
        //   dialogVisible: false, //我的评价对话框
        //   dialogVisible2: false,
        // },
      ],
      person_list: [
        // {
        //   cover: require("../assets/img/avatar.png"),
        //   nickname: "客人1",
        //   message: "环境很好",
        //   createAt: "2022-01-19 14:45:12",
        //   reply: "xxxx",
        // },
        // {
        //   url_show: require("../assets/img/avatar.png"),
        //   name: "客人1",
        //   message: "环境很好",
        //   time: "2022-01-19 14:45:12",
        // },
        // {
        //   // url_show: require("../assets/img/nim.png"),
        //   // name: "客人1",
        //   message: "环境很好",
        //   time: "2022-01-19 14:45:12",
        // },
      ],
    };
  },
  mounted() {
    this.QWorkList();//客服人员列表
    this.QMyCritic();//我的评论
    this.QLMessage();//留言消息
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
    openmy(item) {
      item.dialogVisible = true;
    },
    openkf(item) {
      item.dialogVisible2 = true;
    },

    QWorkList() {
      WorkList().then((res) => {
        if (res.code == 200) {
          this.service_list = res.data;		
        }
      });
    },
    QMyCritic() {
      MyCritic(localStorage.getItem("uid")).then((res) => {
        if (res.code == 200) {
          this.tableData = res.data;
					//console.log(res.data);
        }
      });
    },
    QLMessage() {
      LMessage().then((res) => {
        if (res.code == 200) {
					//console.log(res.data)
					for(var i=0;i<res.data.length;i++){
						res.data[i].user.avatar=config.API_URL+res.data[i].user.avatar;
						res.data[i].leavingMessage.createTime=this.formatDate(res.data[i].leavingMessage.createTime);
					}
          this.person_list = res.data;
					
        }
      });
    },
  },
};
</script>

<style lang="scss">
.message {
  width: 1200px;
  height: 500px;
  overflow: auto;
  margin: 0 auto;
  background: rgb(122, 122, 121);
  textarea {
    resize: none;
    width: 500px;
    margin-left: 40px;
  }
  .service_person {
    padding: 40px 0px 40px 40px;
    display: inline-block;
    float: left;
    width: 350px;
    height: 900px;
    background: rgb(73, 73, 71);
    .service_person_text {
      width: 300px;
      font-size: 20px;
      font-weight: bold;
      height: 50px;
      line-height: 50px;
      //   padding: 18px;
      background: rgb(73, 73, 71);
      color: white;
    }
    .service_message {
      border-bottom: 1px dashed rgb(143, 142, 142);
      width: 320px;
      height: 160px;
      background: rgb(73, 73, 71);
      padding: 35px 0px;
      .img {
        width: 80px;
        height: 80px;
        border-radius: 40px;
        background: rgb(255, 255, 255);
        display: inline-block;
        img {
          width: 80px;
          border-radius: 40px;
          // border-radius: 25px;
        }
      }
      .message_person {
        //   background: rgb(255, 255, 255);
        color: white;
        margin-left: 25px;
        display: inline-block;
        width: 200px;
        margin-top: 25px;
        p {
          font-size: 16px;
          height: 30px;
          line-height: 30px;
        }
      }
      .open {
        height: 50px;
        line-height: 50px;
        font-size: 15px;
        margin-right: 35px;
        color: green;
      }
    }
  }
  .leaving_meaasge {
    width: 750px;
    height: 900px;
    display: inline-block;
    // background: yellow;
    float: right;
    padding: 40px 0px;
    .service_person_text {
      width: 300px;
      font-size: 20px;
      font-weight: bold;
      height: 50px;
      line-height: 50px;
      color: white;
    }
    ul {
      li {
        .service_message {
          height: 180px;
          .img {
            width: 68px;
            height: 68px;
            border-radius: 34px;
            background: rgb(255, 255, 255);
            display: inline-block;
            img {
              width: 68px;
              border-radius: 34px;
            }
          }
          .message_person {
            color: white;
            margin-left: 25px;
            display: inline-block;
            width: 300px;
            margin-top: 45px;
            p {
              font-size: 16px;
              height: 30px;
              line-height: 30px;
            }
          }
        }
        .reply {
          margin-left: 95px;
          .d1 {
            color: rgb(218, 214, 214);
            display: inline-block;
          }
          .d2 {
            color: white;
            margin-left: 5px;
            display: inline-block;
          }
        }
      }
    }
    .btn {
      position: relative;
      left: 600px;
      top: 250px;
    }
  }
}
</style>