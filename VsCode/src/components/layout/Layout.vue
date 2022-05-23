<template >
<el-container>
    <el-header>
        <Header/>
    </el-header>
    <el-main>
        <router-view/>
    </el-main>
    <el-footer>
        <Footer/>
    </el-footer>
</el-container>
</template>

<script>
import Footer from './Footer.vue';
import Header from './Header.vue';


export default {
 name:"Layout",
    components:{Footer,Header}
}
</script>

<style scoped>
.el-main{
    overflow: auto;
    margin-top: 70px;
}
</style>