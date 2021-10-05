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
    <el-table :data="tableData" stripe border style="width:100%">-->
      <!--      <el-table-column prop="date" label="Date" sortable="true"/>-->
      <!--      <el-table-column prop="name" label="Name" />-->
      <!--      <el-table-column prop="address" label="Address" />-->
      <el-table-column
          prop="id" label="ID" sortable>
      </el-table-column>
      <el-table-column
          prop="name" label="名称">
      </el-table-column>
      <el-table-column
          prop="price" label="单价">
      </el-table-column>
      <el-table-column
          prop="author" label="作者">
      </el-table-column>
      <el-table-column
          prop="createTime" label="出版日期">
      </el-table-column>

      <el-table-column
          label="封面">
       <template #default="scope">
         <el-image
             style="width: 100px; height: 100px"
             :src="scope.row.cover"
             :preview-src-list="[scope.row.cover]"
         >
         </el-image>
       </template>
      </el-table-column>

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
          <el-form-item label="名称">
            <el-input v-model="form.name" style="width: 80%"></el-input>
          </el-form-item>
          <el-form-item label="价格">
            <el-input v-model="form.price" style="width: 80%"></el-input>
          </el-form-item>
          <el-form-item label="作者">
            <el-input v-model="form.author" style="width: 80%"></el-input>
          </el-form-item>
          <el-form-item label="出版时间">
           <el-date-picker format="YYYY-MM-DD" type="date" placeholder="选择日期" v-model="form.createTime" clearable></el-date-picker>
          </el-form-item>
          <el-form-item label="封面">
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
  </div>


</template>

<script>


import request from "../utils/request";

export default {
  name: 'Book',

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
    this.load();
  },
  methods: {
    handleEdit(row) {
      this.form = JSON.parse(JSON.stringify(row));
      this.dialogVisible = true;
      this.nextTick(()=>{
        this.$refs['upload'].clearFiles();
      })
    },
    load() {
      request.get("http://localhost:9090/book", {
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

    fileUploadSuccess(res){
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
      this.$refs['upload'].clearFiles();
    },
    save() {
      if (this.form.id) { //  更新
        request.put("http://localhost:9090/book", this.form).then(res => {
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
        request.post("http://localhost:9090/book", this.form).then(res => {
          if (res.code === '0') {
            console.log("hahah")
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
      console.log("abcd")
      this.load();
      this.dialogVisible = false;
    },
    handleDelete(id) {
      request.delete('http://localhost:9090/book/' + id).then(res => {
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
