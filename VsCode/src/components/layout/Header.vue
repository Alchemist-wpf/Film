<template>
  <div class="header">
    <el-card class="box-card">
      <div class="header_main">
        <div class="header_logo">
          <img width="50px" src="../../assets/img/logo.png" alt="" />
          <div class="logo_text">哇塞影院</div>
        </div>
        <div class="header_links">
          <el-link href="/home" class="header_link" :underline="false"
            >首页</el-link
          >
          <el-link href="/film" class="header_link" :underline="false"
            >电影</el-link
          >
          <el-link href="/activity" class="header_link" :underline="false"
            >活动</el-link
          >
          <el-link href="/leavingmessage" class="header_link" :underline="false"
            >留言</el-link
          >
        </div>
        <div class="header_search">
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
            class="login_link"
            :underline="false"
            >登录
          </el-link>

          <el-dropdown
            v-if="isLogin == true"
            style="float: right; padding-right: 10px"
          >
            <el-button type="text">
              <div class="header-name">
                {{ this.user.nickname }}
                <i class="el-icon-caret-bottom"></i>
              </div>

              <img
                :src="user.avatar"
                style="widrh: 45px; height: 45px; border-radius: 50%"
                alt=""
              />
            </el-button>

            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item>
                <el-link
                  :underline="false"
                  href="/personalcenter/shop"
                  style="padding-right: 7px"
                >
                  <i
                    style="font-size: 15px; padding-right: 3px"
                    class="el-icon-shopping-cart-2"
                  >
                  </i
                  >购物车
                </el-link>
              </el-dropdown-item>

              <el-dropdown-item>
                <el-link :underline="false" href="/personalcenter/order">
                  <i
                    style="font-size: 15px; padding-right: 3px"
                    class="el-icon-s-order"
                  >
                  </i
                  >我的订单
                </el-link>
              </el-dropdown-item>

              <el-dropdown-item>
                <el-link :underline="false" href="/personalcenter/set">
                  <i
                    style="font-size: 15px; padding-right: 3px"
                    class="el-icon-user-solid"
                  >
                  </i
                  >个人设置
                </el-link>
              </el-dropdown-item>

              <el-dropdown-item>
                <el-link :underline="false" href="/login" @click="handleLogout">
                  <i
                    style="font-size: 15px; padding-right: 3px"
                    class="el-icon-switch-button"
                  >
                  </i
                  >退出登录
                </el-link>
              </el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </div>
      </div>
    </el-card>
  </div>
</template>

<script>
import { UserInfo, SearchFilm } from "../../api/user";
import { globalBus } from '@/api/globalBus';
export default {
  data() {
    return {
      state: "",
      isLogin: false,
      user: {
        // nickname: "张三",
        // avatar: require("../../assets/img/logo.png"),
      },
      searchList: [],
    };
  },
  mounted() {
    this.queryUser();
  },
  methods: {
    //退出
    handleLogout() {
      localStorage.clear();
      this.$router.push("/login");
    },
    //查询用户信息
    queryUser() {
      if (localStorage.getItem("uid") !== null) {
        UserInfo().then((res) => {
          // console.log(res);
          this.isLogin = true;
          this.user = res.data;
        });
      }
    },
    //电影头部查询
    querySearchAsync(queryString, cb) {
			//console.log(queryString)
      if (queryString) {
        SearchFilm(queryString).then((res) => {
          setTimeout(() => {
            this.searchList = res.data;
            cb(this.loadSearchList());
          }, 1000);
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
      //接受id和value 跳转
      let id = item.id;
			localStorage.setItem("fid",id);
			//跳转后没哟刷新
			//https://www.jianshu.com/p/771d1645377d
			var date =new Date();
			//console.log(date.getTime());
      //this.$router.push("/filmdetail/detail?fid=" + id+"&num="+date.getTime());
			//location.reload();
			globalBus.$emit('countNumber');//使用全局事件，对事件进行引发。
    },
  },
};
</script>

<style lang="scss">
.header {
  height: 70px;
  background: #fff;
  .header_main {
    width: 1000px;
    height: 70px;
    margin: 0 auto;
    /* box-shadow: 2px 12px 0 rgb(0 0 0 0/10%); */
    .header_logo {
      width: 300px;
      img {
        float: left;
        margin-right: 15px;
        margin-top: 10px;
      }
      .logo_text {
        height: 70px;
        line-height: 70px;
        font-size: 24px;
        float: left;
      }
    }
    .header_links {
      float: left;
      padding-left: 70px;
      padding-top: 25px;
      .header_link {
        font-size: 18px;
        padding-left: 25px;
      }
    }
    .header_search {
      float: left;
      margin: 15px 50px;
    }
    .top_login {
      .login_link {
        float: right;
        font-size: 16px;
        padding-top: 23px;
        padding-right: 50px;
      }
      .header-name {
        position: relative;
        left: 10px;
        top: 10px;
        font-size: 15px;
      }
      img {
        width: 45px;
        height: 45px;
        border-radius: 50%;
        position: relative;
        left: -60px;
        top: -20px;
      }
    }
  }
}
</style>
