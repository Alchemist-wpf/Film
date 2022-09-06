<template>
  <div class="header">
    <el-card class="box-card">
      <div class="header_main">
        <div class="header_logo">
          <img width="50px" src="../../assets/img/logo.png" alt="" />
          <div class="logo_text">哇塞影院</div>
        </div>

        <div class="header_links">
          <el-link href="/" class="header-link" :underline="false"
            >首页</el-link
          >
          <el-link href="/films" class="header-link" :underline="false"
            >电影</el-link
          >
          <el-link href="/activity" class="header-link" :underline="false"
            >活动</el-link
          >
          <el-link href="/message" class="header-link" :underline="false"
            >留言</el-link
          >
        </div>

        <div class="header-search">
          <el-autocomplete
            style="width: 250px"
            v-model="state"
            :fetch-suggestions="querySearchAsync"
            placeholder="请输入电影名"
            @select="handleSelect"
          />
        </div>

        <div class="top_login">
          <el-link
            v-if="isLogin == false"
            href="/login"
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
                {{ this.user.nickname }}
                <i class="el-icon-caret-bottom"></i>
              </div>
              <img alt="" :src="user.avatar" />
            </el-button>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item>
                <el-link
                  :underline="false"
                  href="/mine/cart"
                  style="padding-right: 7px"
                >
                  <i
                    style="font-size: 15px; padding-right: 3px"
                    class="el-icon-shopping-cart-2"
                  ></i
                  >购物车
                </el-link>
              </el-dropdown-item>
              <el-dropdown-item>
                <el-link
                  :underline="false"
                  href="/mine/order"
                  style="padding-right: 7px"
                >
                  <i
                    style="font-size: 15px; padding-right: 3px"
                    class="el-icon-s-order"
                  ></i
                  >我的订单
                </el-link>
              </el-dropdown-item>
              <el-dropdown-item>
                <el-link
                  :underline="false"
                  href="/mine/setting"
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
    </el-card>
  </div>
</template>

<script>
import { UserInfo, LogoutUser, SearchFilm } from "../../api/user";
import { globalBus } from "@/api/globalBus";
export default {
  data() {
    return {
      isLogin: false,
      state: "",
      user: {},
      searchList: [],
    };
  },
  methods: {
    querySearchAsync(queryString, cb) {
      if (queryString) {
        SearchFilm(this.state).then((res) => {
          setTimeout(() => {
            this.searchList = res.data;
            cb(this.loadSearchList());
          }, 600);
        });
      }
    },
    loadSearchList() {
      let arr = new Array(0);
      for (let i = 0; i < this.searchList.length; i++) {
        arr.push({
          id: this.searchList[i].id,
          value: this.searchList[i].name,
        });
      }
      return arr;
    },
    handleSelect(item) {
      console.log(item);
      this.$router.push("/film/detail?fid=" + item.id);
      setTimeout(() => {
        location.reload();
      }, 100);
    },
    handleLogout() {
      //进不来
      //提交到服务器中，返回一个生命周期很短的token
      console.log("asfdasdfasdfasdfasd");
      //查询用户信息
      LogoutUser(localStorage.getItem("uid")).then((res) => {
        if (res.code == 200) {
          console.log(res.data.token);
          localStorage.setItem("token", res.data.token);
          localStorage.clear(); //清理本地存储
        }
        this.$router.push("/login");
      });
    },
    queryUser() {
      if (localStorage.getItem("uid") !== null) {
        UserInfo().then((res) => {
          console.log(res);
          this.isLogin = true;
          this.user = res.data;
        });
      }
    },
  },
  mounted() {
    console.log(localStorage.getItem("uid"));
    this.queryUser();
  },
};
</script>

<style scoped lang="scss">
.header {
  width: 1510px;
  margin: 0 auto;
  height: 70px;
  background-color: #fff;
  .header_main {
    width: 100%;
    height: 70px;
    .header_logo {
      float: left;
      width: 350px;
      margin: 0 auto;
      img {
        float: left;
        margin-left: 150px;
        margin-right: 20px;
        margin-top: 10px;
      }
      .logo_text {
        height: 70px;
        line-height: 70px;
        font-size: 30px;
        font-weight: bolder;
      }
    }
    .header_links {
      float: left;
      padding-top: 23px;
      letter-spacing: 2px;
      .header-link {
        padding-left: 40px;
        font-size: 20px;
      }
    }
    .header-search {
      float: left;
      margin: 18px 30px;
    }
    .top_login {
      .login_links {
        font-size: 16px;
        float: right;
        padding-top: 23px;
        padding-right: 50px;
      }
      .header-name {
        position: relative;
        top: 30px;
      }
      img {
        width: 45px;
        height: 45px;
        border-radius: 50%;
        position: relative;
        top: 0px;
        left: -80px;
      }
    }
  }
}
</style>