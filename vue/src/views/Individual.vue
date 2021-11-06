<template>
  <div>
    <div class="demo-image__preview" style="text-align: center; margin: 20px">
      <el-image

          style="width: 100px; height: 100px; border-radius:50px;"
          :src="user.img"
          lazy
          @click="changeImg()"
      >
      </el-image>

      <el-dialog
          v-model="dialogVisible"
          title="提示"
          width="30%"

      >
        <el-form :model="user" label-width="120px">

          <el-form-item label="头像">
            <el-upload ref="upload" action="http://localhost:9090/files/upload" :on-success="fileUploadSuccess">
              <el-button type="primary">选择文件</el-button>
            </el-upload>
          </el-form-item>

        </el-form>

        <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary"
                   @click="save()">确认</el-button>
      </span>
        </template>
      </el-dialog>
    </div>
    <div style="text-align: center;">
      <el-form :model="form" label-width="100px" style="">

        <el-form-item label="用户名">
          <el-input v-model="user.username" style="width: 50%" :value="user.username"></el-input>
        </el-form-item>
        <el-form-item label="昵称">
          <el-input v-model="user.nickName" style="width: 50%" :value="user.nickName"></el-input>
        </el-form-item>
        <el-form-item label="年龄">
          <el-input v-model="user.age" style="width: 50%" :value="user.age"></el-input>
        </el-form-item>
        <el-form-item label="性别">
          <el-radio v-model="user.sex" label="男">男</el-radio>
          <el-radio v-model="user.sex" label="女">女</el-radio>
<!--          <el-radio v-model="form.sex" label="未知">未知</el-radio>-->
        </el-form-item>
        <el-form-item label="地址">
          <el-input type="textarea" v-model="user.address" :value="user.address" style="width: 50%"></el-input>
        </el-form-item>
      </el-form>
    </div>

    <div style="text-align: center">
      <el-button type="primary" @click="save()">保存</el-button>
    </div>
  </div>



</template>

<script>


import request from "../utils/request";
import {ref} from 'vue'

export default {

  name: 'Individual',

  components: {},
  data() {
    return {
      total: 10,
      currentPage: 1,
      pageSize: 10,
      search: '',
      form: {},
      dialogVisible: false,
      tableData: {},
      user: '',
      url: '',
      message: ''
    }
  },
  created() {

    this.load();
  },
  methods: {
    changeImg(){
      this.dialogVisible = true;
    },
    fileUploadSuccess(res){
      console.log(res);
      this.user.img = res.data;

    },
    save() {

      request.put("http://localhost:9090/user", this.user).then(res => {
        if (res.code === '0') {
          this.$message({
            type: "success",
            message: "保存成功",
          })
        } else {
          this.$message({
            type: "error",
            message: "保存失败",
          })
        }

      })


      // 关闭弹窗

      // 刷新表格的数据
      this.search = '';
      this.load();
      this.dialogVisible = false;
    },
    handleEdit(row) {
      this.form = JSON.parse(JSON.stringify(row));
      this.dialogVisible = true;
    },
    load() {

      let userJson = sessionStorage.getItem("user");
      if (!userJson) {
        return
      }
      let userId = JSON.parse(userJson).id
      request.get("http://localhost:9090/user/" + userId).then(res => {

        this.user = res.data

      })
      // console.log("load结束");
    },
    add() {
      this.dialogVisible = true;
      this.user = {};
      this.$refs['upload'].clearFiles();
    },
    // request.get("http://localhost:9090/user", {
    //   params: {
    //     pageNum: this.currentPage,
    //     pageSize: this.pageSize,
    //     search: this.search,
    //   }
    // }).then(res => {
    //   this.tableData = res.data.records;
    //   this.total = res.data.total;
    // }
  },

  // handleSizeChange(pageSize) {    // 改变当前每页的个数触发
  //   this.pageSize = pageSize;
  //   this.load();
  // },
  // handleCurrentChange(pageNum) { // 该表当前页码触发
  //   this.currentPage = pageNum;
  //   this.load();
  // },
  // add() {
  //   this.dialogVisible = true;
  //   this.form = {};
  // },

  // 保存

  // handleDelete(id) {
  //   request.delete('http://localhost:9090/user/' + id).then(res => {
  //     if (res.code === '0') {
  //       this.$message({
  //         type: "success",
  //         message: "删除成功",
  //       })
  //     } else {
  //       this.$message({
  //         type: "error",
  //         message: "删除失败",
  //       })
  //     }
  //     this.load(); // 删除后重新加载数据
  //   })

  // }


}
</script>
