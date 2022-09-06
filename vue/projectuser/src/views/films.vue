<template>
  <div class="all">
    <div class="films">
      <ul class="tags-lines">
        <li class="tags-line">
          <div class="tags-title">类型 :</div>
          <ul class="tags">
            <li
              v-for="(item, index) in typeList"
              :key="index"
              :class="selectType === item ? 'active' : ''"
              @click="handleTypeSelect(item)"
            >
              <router-link
                :to="'/films?region=' + selectRegion + '&type=' + item"
                >{{ item }}</router-link
              >
            </li>
          </ul>
        </li>
        <li class="tags-line">
          <div class="tags-title">地区 :</div>
          <ul class="tags">
            <li
              v-for="(item, index) in regionList"
              :key="index"
              :class="selectRegion === item ? 'active' : ''"
              @click="handleRegionSelect(item)"
            >
              <router-link
                :to="'/films?region=' + item + '&type=' + selectType"
                >{{ item }}</router-link
              >
            </li>
          </ul>
        </li>
      </ul>
    </div>

    <el-row
      :gutter="20"
      style="padding-top: 60px; margin: 0 auto; width: 1000px"
    >
      <el-col
        style="padding-bottom: 40px; text-align: center"
        v-for="(item, index) in filmList"
        :key="index"
        :span="5"
      >
        <router-link :to="'/film/detail?fid=' + item.id">
          <el-card shadow="hover" style="padding: 0">
            <img
              :src="item.cover"
              style="width: 100%; height: 160px; padding-bottom: 10px"
              alt=""
            />
            <span class="s">{{ item.name }}</span>
          </el-card>
        </router-link>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { ListAllFilm, FindFilmByRegionAndType } from "../api/film";
export default {
  data() {
    return {
      selectType: "全部",
      selectRegion: "全部",
      typeList: [
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
      regionList: [
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
      filmList: [],
      // filmList: [
      //   {
      //     name: "电影1",
      //     cover_show: require("../assets/filmImg/lihuanying.jpg"),
      //   },
      //   {
      //     name: "电影2",
      //     cover_show: require("../assets/filmImg/lihuanying.jpg"),
      //   },
      //   {
      //     name: "电影2",
      //     cover_show: require("../assets/filmImg/lihuanying.jpg"),
      //   },
      //   {
      //     name: "电影2",
      //     cover_show: require("../assets/filmImg/lihuanying.jpg"),
      //   },
      //   {
      //     name: "电影2",
      //     cover_show: require("../assets/filmImg/lihuanying.jpg"),
      //   },
      // ],
    };
  },
  methods: {
    FilmQuery() {
      ListAllFilm().then((res) => {
        if (res.code == 200) {
          this.filmList = res.data;
        }
      });
    },
    handleTypeSelect(item) {
      this.selectType = item;
      this.reloadFilmList();
    },
    handleRegionSelect(item) {
      this.selectRegion = item;
      this.reloadFilmList();
    },
    reloadFilmList() {
      FindFilmByRegionAndType(this.selectRegion, this.selectType).then(
        (res) => {
          this.filmList = res.data;
        }
      );
    },
  },
  mounted() {
    this.FilmQuery();
  },
};
</script>

<style scoped>
.all {
  margin: 50px auto 0;
  width: 1510px;
  overflow: hidden;
}
.films {
  width: 1000px;
  height: 100px;
  margin: 0 auto;
  margin-top: 40px;
  border: 1px solid #91949c;
}
.tags-lines {
  list-style-type: none;
  height: 50px;
  line-height: 50px;
  margin-left: 30px;
}
.tags-title {
  display: block;
  float: left;
  color: #91949c;
}
.tags {
  list-style-type: none;
}
.tags-line-border {
  border-top: 1px dotted #e5e5e5;
}
.tags li {
  display: inline-block;
  margin: 0 2px;
  font-size: 14px;
}
.tags li.active a {
  color: white;
  background: #f34d41;
}
.tags li a {
  text-decoration: none;
  color: black;
  border-radius: 14px;
  padding: 4px 10px;
}

.el-card__body {
  padding: 0 0 10px;
}

.s {
  margin-bottom: 10px;
  padding: 0 8px;
  letter-spacing: 1px;
  color: coral;
  text-align: center;
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
}
a {
  text-decoration: none;
}
</style>