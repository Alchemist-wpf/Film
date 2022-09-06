<template>
  <div class="st">
    <el-table :data="tableData" style="width: 100%">
      <el-table-column label="电影名" prop="name" width="120px">
      </el-table-column>
      <el-table-column label="放映时间" prop="startTime" width="160px">
      </el-table-column>
      <el-table-column label="开放座位" prop="seatNumber" width="100px">
      </el-table-column>
      <el-table-column label="上映类型" prop="type" width="100px">
        <template slot-scope="scope">
          <p v-if="scope.row.type == '2D放映'">
            <el-button type="success" size="mini">2D放映</el-button>
          </p>
          <p v-if="scope.row.type == '3D放映'">
            <el-button type="primary" size="mini">3D放映</el-button>
          </p>
        </template>
      </el-table-column>
      <el-table-column
        label="票房统计"
        prop="boxOffice"
        width="100px"
      ></el-table-column>
      <el-table-column label="结束时间" prop="endTime" width="160px">
      </el-table-column>
      <el-table-column label="本场票价" prop="price" width="100px">
        <template slot-scope="scope">
          <el-button type="primary" plain size="mini"
            >{{ scope.row.price }} 元</el-button
          >
        </template>
      </el-table-column>
      <el-table-column>
        <!-- <template slot="header" width="300px">
          <el-input
            v-model="search"
            size="mini"
            placeholder="输入关键字搜索"
            class="ipt1"
          />
          <el-button type="primary" size="mini" class="btn1" @click="Search"
            >搜索</el-button
          >
        </template> -->
        <template slot-scope="scope">
          <el-button size="mini" type="success" @click="handle1(scope.row)"
            >统计票房</el-button
          >
          <el-button size="mini" type="primary" @click="handleChange(scope.row)"
            >修改</el-button
          >
          <el-button size="mini" type="danger" @click="handleDelete(scope.row)"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>

    <el-dialog title="提示" :visible.sync="dialogVisible" width="30%">
      <span>确认删除？</span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="Submit1()">确 定</el-button>
      </span>
    </el-dialog>

    <el-dialog title="提示" :visible.sync="dialogFormVisible">
      <el-form>
        <el-form-item label="请输入该场次的票房">
          <el-input v-model="boxOffice" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="Submit2()">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="修改排片信息" :visible.sync="dialogFormVisible1">
      <el-form ref="form" :model="form" label-width="80px">
        <el-form-item label="电影名称">
          <el-input v-model="form.name"></el-input>
        </el-form-item>
        <el-form-item label="开放座位">
          <el-input-number
            v-model="form.seatNumber"
            :min="1"
            :max="200"
            label="开放座位"
          ></el-input-number>
        </el-form-item>
        <el-form-item label="放映类型">
          <el-select v-model="form.type" placeholder="请选择">
            <el-option label="2D放映" value="2D放映"></el-option>
            <el-option label="3D放映" value="3D放映"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="开始时间">
          <el-date-picker
            v-model="form.startTime"
            type="datetime"
            placeholder="选择日期时间"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="结束时间">
          <el-date-picker
            v-model="form.endTime"
            type="datetime"
            placeholder="选择日期时间"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="电影票价">
          <el-input-number
            v-model="form.price"
            :min="1"
            :max="200"
            label="电影票价"
          ></el-input-number>
        </el-form-item>
        <el-form-item label="排片人">
          <el-input v-model="form.founder"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible1 = false">取 消</el-button>
        <el-button type="primary" @click="Submit3()">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  AllArrangement,
  deleteArrangement,
  updateArrangement,
} from "@/api/film";
export default {
  data() {
    return {
      tableData: [],
      search: "",
      dialogVisible: false,
      dialogFormVisible: false,
      dialogFormVisible1: false,
      data1: [],
      data2: [],
      boxOffice: "",
      form: {
        name: "",
        seatNumber: "",
        startTime: "",
        endTime: "",
        type: "",
        price: "",
        founder: "",
      },
    };
  },
  mounted() {
    this.QAllArangement();
  },
  methods: {
    formatDate(dateint) {
      //日期格式化，
      //console.log(dateint);
      var date = new Date(parseInt(dateint));
      var str = date.getFullYear() + "-";
      if (date.getMonth() < 10) {
        str += "0";
      }
      str += date.getMonth() + 1 + "-";
      if (date.getDate() < 10) {
        str += "0";
      }
      str += date.getDate() + " ";
      if (date.getHours() < 10) {
        str += "0";
      }
      str += date.getHours() + ":";

      if (date.getMinutes() < 10) {
        str += "0";
      }
      str += date.getMinutes() + ":";
      if (date.getSeconds() < 10) {
        str += "0";
      }
      str += date.getSeconds();
      //console.log(str);
      return str;
    },
    QAllArangement() {
      AllArrangement().then((res) => {
        if (res.code == 200) {
          // for (let i = 0; i < res.data.length; i++) {
          //   res.data[i].startTime = this.formatDate(res.data[i].startTime);
          //   res.data[i].endTime = this.formatDate(res.data[i].endTime);
          // }
          this.tableData = res.data;
        }
      });
    },
    Search() {},

    //统计票房
    handle1(row) {
      this.dialogFormVisible = true;
      this.data2 = row;
    },
    Submit2() {
      this.data2.boxOffice = this.boxOffice;
      updateArrangement(this.data2).then((res) => {
        this.$message({
          message: "输入成功",
          type: "success",
        });
        this.dialogFormVisible = false;
      });
    },

    //修改信息
    handleChange(row) {
      this.dialogFormVisible1 = true;
      this.form = row;
    },
    Submit3() {
      updateArrangement(this.form).then((res) => {
        this.$message({
          message: "修改成功",
          type: "success",
        });
        this.boxOffice = "";
        this.dialogFormVisible1 = false;
      });
    },

    //删除
    handleDelete(row) {
      this.dialogVisible = true;
      this.data1 = row;
    },
    Submit1() {
      deleteArrangement(this.data1).then((res) => {
        this.$message({
          message: "删除成功",
          type: "success",
        });
        this.dialogVisible = false;
        setTimeout(() => {
          location.reload();
        });
      });
    },
  },
};
</script>

<style lang="scss">
// .st {
//   margin-left: 20px;
//   .items {
//     .item {
//       .collapse-title {
//         flex: 1 0 90%; //位于左侧
//         //   flex: 0 1 54%;// 位于中间
//         order: 1;
//       }
//     }
//   }
// }
.demo-table-expand {
  font-size: 0;
}
.demo-table-expand label {
  width: 90px;
  color: #99a9bf;
}
.demo-table-expand .el-form-item {
  margin-right: 0;
  margin-bottom: 0;
  width: 50%;
}
.btn1 {
  float: left;
}
.ipt1 {
  float: left;
  width: 160px;
}
</style>