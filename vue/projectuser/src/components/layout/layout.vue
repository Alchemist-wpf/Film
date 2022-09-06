<template>
  <div>
    <el-container>
      <el-header>
        <Header />
      </el-header>
      <el-main>
        <router-view />
      </el-main>
      <el-footer>
        <Footer />
      </el-footer>
    </el-container>
  </div>
</template>

<script>
import Header from "@/components/layout/Header.vue";
import Footer from "@/components/layout/footer.vue";
export default {
  name: "Layout",
  components: { Header, Footer },
  data() {
    return {};
  },
};
</script>

<style scoped>
* {
  margin: 0;
  padding: 0;
}
.el-header {
  padding: 0;
}
.el-main {
  padding: 0;
}
.el-footer {
  margin-top: 100px;
  padding: 0;
}
</style>