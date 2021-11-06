<template>
  <div class="home" style="padding: 10px">
    <!--    功能区域-->
    <div style="margin: 10px">
      <el-button type="primary" @click="add">新增</el-button>
      <el-button type="primary" disabled>导入</el-button>
      <el-button type="primary" disabled>导出</el-button>
    </div>
    <!--    搜索区域-->
    <div style="margin: 10px 0">
      <el-input v-model="search" placeholder="请输入关键字" style="width: 20%" clearable/>
      <el-button type="primary" style="margin-left: 5px" @click="load">查询</el-button>
    </div>
    <el-table :data="tableData" stripe border style="width:100%">-->
      <!--      <el-table-column prop="date" label="Date" sortable="true"/>-->
      <!--      <el-table-column prop="name" label="Name" />-->
      <!--      <el-table-column prop="address" label="Address" />-->
      <el-table-column
          prop="id" label="ID" sortable>
      </el-table-column>
      <el-table-column
          prop="name" label="课程名称">
      </el-table-column>
      <el-table-column
          prop="teacher" label="授课教师">
      </el-table-column>
      <el-table-column
          prop="type" label="课程类型">
      </el-table-column>
      <el-table-column
          prop="mask" label="课程推荐指数">
      </el-table-column>


<!--      <el-table-column-->
<!--          prop="role" label="角色">-->
<!--        <template #default="scope">-->
<!--          <span v-if="scope.row.role === 1">管理员</span>-->
<!--          <span v-if="scope.row.role === 2">普通用户</span>-->
<!--        </template>-->
<!--      </el-table-column>-->
      <el-table-column fixed="right" label="操作" width="120">
        <template #default="scope">
          <el-button type="text" @click="handleEdit(scope.row)">编辑</el-button>

          <el-popconfirm title="确定删除吗?" @confirm="handleDelete(scope.row.id)">
            <template #reference>
              <el-button type="text" @click="">删除</el-button>
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
      <el-dialog
          v-model="dialogVisible"
          title="提示"
          width="30%"

      >
        <el-form :model="form" label-width="120px">
          <el-form-item label="课程名称">
            <el-input v-model="form.name" style="width: 80%"></el-input>
          </el-form-item>
          <el-form-item label="授课教师">
            <el-input v-model="form.teacher" style="width: 80%"></el-input>
          </el-form-item>

          <el-form-item label="课程类型">
            <el-radio v-model="form.type" label="专业课">专业课</el-radio>
            <el-radio v-model="form.type" label="通识课">通识课</el-radio>
            <el-radio v-model="form.type" label="公共基础课">公共基础课</el-radio>
            <!--            <el-radio v-model="form.sex" label="未知">未知</el-radio>-->
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
  </div>

</template>
<script>


import request from "../utils/request";

export default {
  name: 'Home',

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
    handleEdit(row) {
      this.form = JSON.parse(JSON.stringify(row));
      this.dialogVisible = true;
    },
    load() {
      request.get("http://localhost:9090/course", {
        params: {
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          search: this.search,
        }
      }).then(res => {
        this.tableData = res.data.records;
        this.total = res.data.total;
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
      this.form = {};
      this.dialogVisible = true;
    },
    save() {
      if (this.form.id) { //  更新
        console.log("更新")
        request.put("http://localhost:9090/course", this.form).then(res => {
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
        console.log("新增")
        request.post("http://localhost:9090/course", this.form).then(res => {
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

      this.dialogVisible = false;
      // this.load()
      this.$router.go(0);
    },
    handleDelete(id) {
      request.delete('http://localhost:9090/course/' + id).then(res => {
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
        this.$router.go(0);
      })

    }

  }
}
</script>