<template>
  <div class="film">
    <div class="film_top">
      <div class="style">
        类型：
        <span
          v-for="(item, index) in typelist"
          :key="index"
          class="style_el"
          :class="selectype == item ? 'active' : ''"
          @click="handlestyle(item)"
        >
          <el-link :underline="false">{{ item }}</el-link>
        </span>
      </div>
      <div class="region">
        地区：
        <span
          v-for="(item, index) in regionlist"
          :key="index"
          class="region_el"
          :class="selecregion == item ? 'active' : ''"
          @click="handleregion(item)"
        >
          <el-link :underline="false">{{ item }}</el-link>
        </span>
      </div>
    </div>
    <div class="film_list">
      <ul>
        <li
          v-for="(item, index) in mainlist"
          :key="index"
          @click="handleclick(item)"
          style="cursor: pointer"
        >
          <div class="film_lists">
            <img :src="item.cover" alt=""/>
            <p class="name_text">{{ item.name }}</p>
          </div>
        </li>
      </ul>
    </div>
  </div>
</template>
<script>
import { ListAllFilm, FilmByRegionAndType } from "@/api/film";
export default {
  data() {
    return {
      mainlist: [],
      selectype: "全部",
      selecregion: "全部",
      typelist: [
        "全部",
        "家庭",
        "惊悚",
        "科幻",
        "爱情",
        "动作",
        "喜剧",
        "恐怖",
        "悬疑",
        "犯罪",
        "冒险",
        "战争",
        "历史",
        "武侠",
        "传记",
        "古装",
        "其他",
      ],
      regionlist: [
        "全部",
        "内地",
        "香港",
        "台湾",
        "美国",
        "韩国",
        "日本",
        "泰国",
        "印度",
        "法国",
        "英国",
        "德国",
        "其他",
      ],
    };
  },
  mounted() {
    this.FilmQuery();
  },
  methods: {
    handlestyle(item) {
      this.selectype = item;
			//console.log(this.selectype);
      this.LoadFilm();
    },
    handleregion(item) {
      this.selecregion = item;
			//console.log(this.selecregion);
      this.LoadFilm();
    },

    FilmQuery() {
      ListAllFilm().then((res) => {
        if (res.code == 200) {
          this.mainlist = res.data;
        }
      });
    },
    LoadFilm() {
      // FilmByRegionAndType(this.selecregion,this.selectype).then((res) => {
      //   if (res.code == 200) {
      //     this.mainlist = res.data;
      //     // for(var i=0;i<this.mainlist.length;i++){
      //     //   this.mainlist[i].cover=
      //     //   config.API_URL+"/upload?id="+this.mainlist[i].cover
      //     // }
      //   }
      // });
      FilmByRegionAndType(this.selecregion, this.selectype).then((res) => {
        if (res.code == 200) {
          this.mainlist = res.data;
          // for(var i=0;i<this.mainlist.length;i++){
          //   this.mainlist[i].cover=
          //   config.API_URL+"/upload?id="+this.mainlist[i].cover
          // }
        }
      });
    },
    handleclick(item) {
			localStorage.setItem("fid",item.id);
      this.$router.push("/filmdetail/detail?fid=" + item.id);
    },
  },
};
</script>
<style lang="scss">
.film {
  .film_list {
    width: 1100px;
    margin: 0 auto;
    li {
      display: inline-block;
      .film_lists {
        display: inline-block;
        margin: 16px;
        width: 150px;
        height: 260px;
        img {
          width: 150px;
          height: 210px;
        }
        p {
          width: 150px;
          height: 50px;
          line-height: 50px;
          text-align: center;
        }
      }
    }
  }
  .film_top {
    width: 1000px;
    height: 70px;
    border: 1px solid #eee;
    margin: 0 auto;
    padding: 10px 20px;
    .style {
      font-size: 15px;
      height: 25px;
      line-height: 25px;
      color: gray;
      margin-bottom: 10px;
      .active {
        background: red;
      }
      .style_el {
        display: inline-block;
        margin: 0 4px;
        width: 40px;
        border-radius: 10px;
        // background: red;
        text-align: center;
      }
    }
    .region {
      font-size: 15px;
      height: 25px;
      line-height: 25px;
      color: gray;
      margin-bottom: 10px;
      .active {
        background: red;
      }
      .region_el {
        display: inline-block;
        border-radius: 10px;
        margin: 0 4px;
        width: 40px;
        // background: red;
        text-align: center;
      }
    }
  }
}
</style>