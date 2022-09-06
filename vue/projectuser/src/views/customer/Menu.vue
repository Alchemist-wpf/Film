<template>
  <div class="menu">
    <div class="aside">
      <el-col style="width: 251px">
        <h3>
          <img src="../../assets/img/logo.png" alt="" />
          哇塞影院-客服
        </h3>
        <el-menu
          default-active="0"
          class="el-menu-vertical-demo"
          background-color="#545c64"
          text-color="#fff"
          active-text-color="#ffd04b"
          router
        >
          <el-menu-item index="/customer/leavemessage" class="item">
            <i class="el-icon-s-promotion"></i>
            <span slot="title" class="text"> 影院留言</span>
          </el-menu-item>
          <el-menu-item index="/customer/activity" class="item">
            <i class="el-icon-s-flag"></i>
            <span slot="title" class="text"> 活动安排</span>
          </el-menu-item>
          <el-menu-item index="/customer/statistic" class="item">
            <i class="el-icon-s-data"></i>
            <span slot="title" class="text"> 信息统计</span>
          </el-menu-item>
          <el-menu-item index="/customer/evaluate" class="item">
            <i class="el-icon-s-order"></i>
            <span slot="title" class="text"> 我的评价</span>
          </el-menu-item>
          <!-- <el-menu-item index="/customer/setting" class="item">
            <i class="el-icon-setting"></i>
            <span slot="title" class="text"> 个人设置</span>
          </el-menu-item> -->
        </el-menu>
      </el-col>
    </div>
    <div class="menu-main">
      <div class="menu-top">
        <div class="top_login">
          <el-link
            v-if="isLogin == false"
            href="/login1"
            class="header-link login_links"
            :underline="false"
            >登录</el-link
          >

          <el-dropdown
            v-if="isLogin == true"
            style="float: right; padding-right: 10px"
          >
            <el-button type="text">
              <div class="header-name">
                {{ this.worker.nickname }}
                <i class="el-icon-caret-bottom"></i>
              </div>
              <img alt="" :src="worker.avatar" />
            </el-button>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item>
                <el-link
                  :underline="false"
                  href="/back/setting"
                  style="padding-right: 7px"
                >
                  <i
                    style="font-size: 15px; padding-right: 3px"
                    class="el-icon-user-solid"
                  ></i
                  >个人设置
                </el-link>
              </el-dropdown-item>

              <el-button
                type="text"
                @click="handleLogout"
                :underline="false"
                style="margin: auto; width: 100%"
              >
                <i
                  style="font-size: 15px; padding-right: 3px"
                  class="el-icon-switch-button"
                ></i
                >退出登录
              </el-button>
            </el-dropdown-menu>
          </el-dropdown>
        </div>
      </div>
      <div class="main">
        <router-view></router-view>
      </div>
    </div>
  </div>
</template>

<script>
import { WorkerInfo, LogoutWorker } from "../../api/customer";
export default {
  data() {
    return {
      wid: localStorage.getItem("wid"),
      isLogin: false,
      state: "",
      worker: {},
    };
  },
  methods: {
    handleLogout() {
      //查询用户信息
      LogoutWorker(localStorage.getItem("wid")).then((res) => {
        if (res.code == 200) {
          console.log(res.data.token);
          localStorage.setItem("token", res.data.token);
          localStorage.clear(); //清理本地存储
        }
        this.$router.push("/login1");
      });
    },
    queryWorker() {
      console.log(this.wid);
      if (localStorage.getItem("wid") !== null) {
        WorkerInfo(localStorage.getItem("wid")).then((res) => {
          console.log(res);
          this.isLogin = true;
          this.worker = res.data;
        });
      }
    },
  },
  mounted() {
    this.queryWorker();
  },
};
</script>

<style lang="scss">
.menu {
  margin-left: 80px;
  height: 800px;
  //   background: yellow;
  .aside {
    width: 250px;
    overflow: hidden;
    display: block;
    float: left;
    background: #545c64;
    //   border: 1px solid #171718;
    height: 800px;
    h3 {
      img {
        width: 35px;
        height: 35px;
        display: inline-block;
        vertical-align: middle;
      }
      background: #545c64;
      height: 90px;
      line-height: 90px;
      text-align: center;
      font-size: 20px;
      color: white;
      font-weight: normal;
    }
    .item {
      height: 70px;
      line-height: 70px;
    }
    .text {
      font-size: 16px;
    }
  }
  .menu-main {
    // border: 1px solid #000;
    width: 1100px;
    overflow-x: hidden;
    height: 800px;
    .menu-top {
      height: 70px;
      background: #e2e4e5;
      width: 1100px;
      overflow: hidden;
      .top_login {
        .login_links {
          font-size: 16px;
          float: right;
          padding-top: 23px;
          padding-right: 50px;
        }
        .header-name {
          position: relative;
          top: 20px;
        }
        img {
          width: 45px;
          height: 45px;
          border-radius: 50%;
          position: relative;
          top: -10px;
          left: -80px;
        }
      }
    }
    .main {
      // margin-right: 60px;
      // float: right;
      // position: relative;
      left: 233px;
      width: 1100px;
      // height: 100%;
      border-left: 10px solid rgb(190, 190, 190);
      border-top: 10px solid rgb(190, 190, 190);
      // padding-left: 250px;
    }
  }
}
</style>