<template>
  <div class="all">
    <div class="aside">
      <el-row class="tac">
        <el-col :span="12" style="width: 260px">
          <h2>个人中心</h2>
          <el-menu default-active="2" class="el-menu-vertical-demo" router>
            <el-menu-item index="/personalcenter/shop" style="height: 68px">
              <i class="el-icon-shopping-cart-2"></i>
              <span slot="title" class="t1">购物车</span>
            </el-menu-item>

            <el-menu-item index="/personalcenter/order" style="height: 68px">
              <i class="el-icon-user"></i>
              <span slot="title" class="t1">我的订单</span>
            </el-menu-item>

            <el-menu-item index="/personalcenter/set" style="height: 68px">
              <i class="el-icon-setting"></i>
              <span slot="title" class="t1">基本设置</span>
            </el-menu-item>
          </el-menu>
        </el-col>
      </el-row>
    </div>
    <div class="personalcotent">
      <router-view></router-view>
    </div>
  </div>
</template>
<script>
export default {
  data() {
    return {};
  },
  methods: {
    handleOpen(key, keyPath) {
      console.log(key, keyPath);
    },
    handleClose(key, keyPath) {
      console.log(key, keyPath);
    },
  },
};
</script>

<style lang="scss">
.all {
  width: 1100px;
  height: 800px;
  margin: 0 auto;
  border: 1px solid rgb(158, 156, 156);
  .aside {
    float: left;
    width: 260px;
    height: 800px;
    border-right: 1px solid rgb(158, 156, 156);
    h2 {
      text-align: center;
      margin-top: 70px;
      margin-bottom: 40px;
      font-weight: normal;
    }
    .t1 {
      display: inline-block;
      font-size: 17px;
    }
  }
}
</style>