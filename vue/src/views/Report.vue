<template>
  <div class="home" style="padding: 10px">

    <el-table :data="tableData" stripe border style="width:100%">-->

      <el-table-column
          prop="id" label="ID" sortable>
      </el-table-column>
      <el-table-column
          prop="username" label="举报者">
      </el-table-column>
      <el-table-column
          prop="reportedUsername" label="被举报者">
      </el-table-column>
      <el-table-column label="评论内容">
        <template #default="scope">
          <span v-html="scope.row.content"></span>
        </template>
      </el-table-column>
      <el-table-column prop="reason" label="举报理由">
      </el-table-column>

      <el-table-column
          prop="createTime" label="举报时间" sortable>
      </el-table-column>

      <el-table-column fixed="right" label="操作" width="120">
        <template #default="scope">

          <el-popconfirm title="确定删除该评论吗?" @confirm="handle(scope.row.id,scope.row.commentId)">
            <template #reference>
              <el-button type="text" @click="">处理</el-button>
            </template>
          </el-popconfirm>

          <el-popconfirm title="确定不处理该评论吗?" @confirm="notHandle(scope.row.id)">
            <template #reference>
              <el-button type="text" @click="">不处理</el-button>
            </template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <div style="margin: 10px 0">
      <el-pagination
          v-model:currentPage="currentPage"
          :page-sizes="[5,10,20]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
      >
      </el-pagination>
    </div>
  </div>


</template>

<script>


import request from "../utils/request";

export default {
  name: 'Report',

  components: {},
  data() {
    return {
      total: 10,
      currentPage: 1,
      pageSize: 10,
      search: '',
      form: {},
      dialogVisible: false,
      tableData: [],

    }
  },
  created() {
    if(sessionStorage.getItem('user')&& JSON.parse(sessionStorage.getItem('user')).role == 1){
      this.load();
    }else{
      alert("error:权限不足！");
      this.$router.push("/individual")
    }
  },
  methods: {
    // 处理，删除评论以及举报信息
    handle(id,commentId){
      console.log(commentId)
      // 删除举报信息
      request.delete('http://localhost:9090/report/' + id).then(res => {
        if (res.code === '0') {

        }

      })

      // 删除评论
      request.delete('http://localhost:9090/comment/' + commentId).then(res => {
        if (res.code === '0') {
          this.$message({
            type: "success",
            message: "处理成功",
          })
        } else {
          this.$message({
            type: "error",
            message: "处理失败",
          })
        }
        this.load(); // 删除后重新加载数据
      })
    },

    // 不处理，只删除举报信息
    notHandle(index){
      request.delete('http://localhost:9090/report/' + index).then(res => {
        if (res.code === '0') {
          this.$message({
            type: "success",
            message: "处理成功",
          })
        } else {
          this.$message({
            type: "error",
            message: "处理失败",
          })
        }
        this.load(); // 删除后重新加载数据
      })
    },
    handleEdit(row) {
      this.form = JSON.parse(JSON.stringify(row));
      this.dialogVisible = true;
    },
    load() {
      request.get("http://localhost:9090/report", {
        params: {
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          search: this.search,
        }
      }).then(res => {
        this.tableData = res.data;
        this.total = this.tableData.length;
      })
    },

    handleSizeChange(pageSize) {    // 改变当前每页的个数触发
      this.pageSize = pageSize;
      this.load();
    },
    handleCurrentChange(pageNum) { // 该表当前页码触发
      this.currentPage = pageNum;
      this.load();
    },
    add() {
      this.dialogVisible = true;
      this.form = {};
    },
    save() {
      if (this.form.id) { //  更新
        request.put("http://localhost:9090/user", this.form).then(res => {
          if (res.code === '0') {
            this.$message({
              type: "success",
              message: "更新成功",
            })
          } else {
            this.$message({
              type: "error",
              message: "更新失败",
            })
          }
        })

      } else {   // 新增
        request.post("http://localhost:9090/user", this.form).then(res => {
          if (res.code === '0') {
            this.$message({
              type: "success",
              message: "新增成功",
            })
          } else {
            this.$message({
              type: "error",
              message: "新增失败",
            })
          }

        })
      }

      // 关闭弹窗

      // 刷新表格的数据
      this.search = '';
      this.load();
      this.dialogVisible = false;
    },
    handleDelete(id) {
      request.delete('http://localhost:9090/user/' + id).then(res => {
        if (res.code === '0') {
          this.$message({
            type: "success",
            message: "删除成功",
          })
        } else {
          this.$message({
            type: "error",
            message: "删除失败",
          })
        }
        this.load(); // 删除后重新加载数据
      })

    }

  }
}
</script>
