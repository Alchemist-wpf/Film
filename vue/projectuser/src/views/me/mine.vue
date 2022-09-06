<template>
  <div class="main">
    <div class="mine">
      <el-row class="tac">
        <el-col :span="12">
          <h1>个人中心</h1>
          <el-menu
            router
            :default-active="$route.path"
            class="el-menu-vertical-demo"
          >
            <el-menu-item index="/mine/cart" class="inner">
              <i class="el-icon-shopping-cart-2"></i>
              <span slot="title">购物车</span>
            </el-menu-item>
            <el-menu-item index="/mine/order" class="inner">
              <i class="el-icon-document"></i>
              <span slot="title">我的订单</span>
            </el-menu-item>
            <el-menu-item index="/mine/setting" class="inner">
              <i class="el-icon-setting"></i>
              <span slot="title">基本设置</span>
            </el-menu-item>
          </el-menu>
        </el-col>
      </el-row>
    </div>

    <div class="content">
      <router-view />
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {};
  },
};
</script>

<style scoped>
.main {
  margin: 100px auto 0;
  width: 1510px;
  overflow: hidden;
}
.mine {
  width: 250px;
  height: 1000px;
  overflow: hidden;
  float: left;
  margin-left: 200px;
}
.content {
  width: 900px;
  height: 998px;
  float: left;
  border: 1px solid rgb(230, 230, 230);
}
.tac {
  border: 1px solid rgb(230, 230, 230);
  width: 250px;
  padding-top: 30px;
  height: 1000px;
}
.tac h1 {
  text-align: center;
  width: 250px;
  font-weight: 400;
  line-height: 70px;
  height: 70px;
  font-size: 24px;
}
.el-menu-vertical-demo {
  width: 248px;
}
.inner {
  font-size: 16px;
  margin-left: 10px;
}
.mine a {
  text-decoration: none;
  color: black;
}
</style>