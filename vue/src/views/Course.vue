<template>
  <div class="home" style="padding: 10px">
    <!--    功能区域-->
    <div style="margin: 10px">
      <el-button type="primary" @click="add">新增</el-button>
      <el-button type="primary">导入</el-button>
      <el-button type="primary">导出</el-button>
    </div>
    <!--    搜索区域-->
    <div style="margin: 10px 0">
      <el-input v-model="search" placeholder="请输入关键字" style="width: 20%" clearable/>
      <el-button type="primary" style="margin-left: 5px" @click="load">查询</el-button>
    </div>
    <el-table v-loading="loading" :data="tableData" stripe border style="width:100%">-->
      <!--      <el-table-column prop="date" label="Date" sortable="true"/>-->
      <!--      <el-table-column prop="name" label="Name" />-->
      <!--      <el-table-column prop="address" label="Address" />-->
      <el-table-column
          prop="id" label="ID" sortable>
      </el-table-column>
      <el-table-column
          prop="title" label="标题">
      </el-table-column>
      <el-table-column
          prop="author" label="作者">
      </el-table-column>
      <el-table-column
          prop="time" label="时间">
      </el-table-column>


      <el-table-column fixed="right" label="操作" width="120">
        <template #default="scope">
          <el-button type="text" @click="details(scope.row)">详情</el-button>
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
          width="50%">


        <el-form :model="form" label-width="120px">
          <el-form-item label="标题">
            <el-input v-model="form.title" style="width: 50%"></el-input>
          </el-form-item>

          <div id="div1">

          </div>
          <!--          <el-form-item label="内容">-->
          <!--            <el-input v-model="form.content" style="width: 80%"></el-input>-->
          <!--          </el-form-item>-->

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
    <el-dialog
        v-model="vis"
        title="详情"
        width="50%">
      <el-card>
        <div v-html="detail.content" style="min-height: 100px"></div>
      </el-card>
    </el-dialog>

  </div>
</template>

<script>

import E from 'wangeditor'
import request from "../utils/request";

let editor;

export default {
  name: 'Course',

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
      user: {},
      detail: {},
      vis: false,
      loading: true
    }
  },
  created() {
    this.load();
  },
  methods: {
    details(row) {
      this.detail = row;
      this.vis = true;
    },
    handleEdit(row) {
      this.form = JSON.parse(JSON.stringify(row));
      this.dialogVisible = true;
      this.$nextTick(() => {
        if (!editor) {
          editor = new E('#div1');
          editor.create();
        }
        editor.txt.html(row.content);
      })

    },
    load() {
      this.loading = true
      request.get("http://localhost:9090/news", {
        params: {
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          search: this.search,
        }
      }).then(res => {
        this.loading = false;
        this.tableData = res.data.records;
        this.total = res.data.total;
      })
    },

    fileUploadSuccess(res) {
      console.log(res);
      this.form.cover = res.data;
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
      this.$nextTick(() => {
        editor = new E('#div1');
        // 配置server接口地址
        editor.config.uploadImgServer = 'http://localhost:9090/files/editor/upload';
        // 配置上传文件参数名称
        editor.config.uploadFileName = "file";
        editor.create();
      })
    },
    save() {

      this.form.content = editor.txt.html(); // 获取编辑器里面的值到form的内容中
      if (this.form.id) { //  更新


        request.put("http://localhost:9090/news", this.form).then(res => {
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
        // this.dialogVisible = false;
        let userStr = sessionStorage.getItem("user") || "{}"
        let user = JSON.parse(userStr);
        console.log(user)
        this.form.author = user.nickName;
        request.post("http://localhost:9090/news", this.form).then(res => {
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
      this.search = '';

      this.load();
      this.dialogVisible = false;
    },
    handleDelete(id) {
      request.delete('http://localhost:9090/news/' + id).then(res => {
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
