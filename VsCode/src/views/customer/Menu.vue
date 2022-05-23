<template>
  <div class="menu">
    <div class="aside">
      <el-col style="width: 251px">
        <h3>
          <img src="../../assets/img/logo.png" alt="" />
          哇塞影院-客服
        </h3>
        <el-menu
          default-active="2"
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
          <el-menu-item index="/customer/setting" class="item">
            <i class="el-icon-setting"></i>
            <span slot="title" class="text"> 个人设置</span>
          </el-menu-item>
        </el-menu>
      </el-col>
    </div>
    <div class="menu-main" >
      <div class="menu-top">
        <div class="top">
          <img :src="url" alt="" />
          <el-dropdown>
            <span class="el-dropdown-link">
              {{ name }}
            </span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item>
                <i class="el-icon-setting"></i>
                个人设置</el-dropdown-item
              >
              <el-dropdown-item>
                <i class="el-icon-user"></i>
                退出登录</el-dropdown-item
              >
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
export default {
  data() {
    return {
      url: require("@/assets/img/avatar.png"),
      name: "XXXXX",
    };
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
    display: inline-block;
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
    overflow: hidden;
    height: 800px;
    .menu-top {
      height: 70px;
      background: #e2e4e5;
      width: 1100px;
      .top {
        float: right;
        display: inline-block;
        line-height: 70px;
        margin-right: 60px;
        img {
          width: 36px;
          height: 36px;
          border-radius: 18px;
          vertical-align: middle;
          display: inline-block;
          margin-right: 30px;
        }
      }
    }
    .main {
      overflow: auto;
      margin-right: 60px;
      // float: right;
      // position: relative;
      left: 233px;
      width: 1100px;
      height: 800px;
      border-left: 10px solid rgb(190, 190, 190);
      border-top: 10px solid rgb(190, 190, 190);
      // padding-left: 250px;
    }
  }
}
</style>