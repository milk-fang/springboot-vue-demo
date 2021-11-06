<template>
  <div class="home" style="padding: 10px">
    <!--    功能区域-->
    <div style="margin: 10px">
      <el-button type="primary" @click="add">发贴</el-button>
    </div>
    <!--    搜索区域-->
    <div style="margin: 10px 0">
      <el-input v-model="search" placeholder="请输入关键字" style="width: 20%" clearable/>
      <el-button type="primary" style="margin-left: 5px" @click="load">查询</el-button>
    </div>

<div>
  <el-descriptions  :column="3" border v-for="article in articles" @click="$router.push(`/chat/${article.id}`)" style="margin-top: 10px">
    <el-descriptions-item
        label="Username"
        label-align="right"
        align="center"
        label-class-name="my-label"
        class-name="my-content"
        width="150px"

    >{{article.username}}</el-descriptions-item
    >
    <el-descriptions-item label="title" label-align="right" align="center"
    >{{article.title}}</el-descriptions-item
    >
    <el-descriptions-item label="course" label-align="right" align="center"
    >{{article.course}}</el-descriptions-item
    >
    <el-descriptions-item label="tag" label-align="right" align="center">
      <el-tag size="small">{{article.tag}}</el-tag>
    </el-descriptions-item>
    <el-descriptions-item label="content" label-align="right" align="center"
    >
      <div v-html="article.content">

      </div>
    </el-descriptions-item
    >

  </el-descriptions>

</div>


    <div style="margin: 10px 0" >
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

    <div>
      <el-dialog
          v-model="dialogVisible"
          title="Tips"
          width="60%"
          :before-close="handleClose"
      >
        <el-form ref="form" :model="form" label-width="100px" style="width: 80%;margin-left: 30px" align="center">
          <el-form-item label="课程">

            <el-select v-model="form.course" filterable placeholder="请选择课程" style="width: 100%">
              <el-option
                  v-for="item in options"
                  :key="item.id"
                  :label="item.name"
                  :value="item.teacher">
                <span style="float: left">{{ item.name }}</span>
                <span
                    style="
          float: right;
          color: var(--el-text-color-secondary);
          font-size: 13px;
        "
                >{{item.teacher}}</span>

              </el-option>
            </el-select>

          </el-form-item>
          <el-form-item label="标签">
            <el-input v-model="form.tag"></el-input>
          </el-form-item>

        </el-form>
        <div id="article" ></div>
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

import E from 'wangeditor'
import request from "../utils/request";
let editor;
export default {
  name: 'Course',

  components: {

  },
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
      loading: true,
      articles:[{
        // title:'课程评价',
        // username:'爱吃猫的鱼',
        // tag:'C++',
        // createTime:'2021-10-7',
        // content:'这课给分怎么样',
        // course:'编译原理'
      }],
      options:[

      ]
    }
  },
  created() {
    this.load();
  },
  mounted() {


  },
  methods: {
    details(row) {
      this.detail = row;
      this.vis = true;
    },
    handleClose(){
      this.dialogVisible = false;
    },
    handleEdit(row) {
      this.form = JSON.parse(JSON.stringify(row));
      this.dialogVisible = true;
      this.$nextTick(() => {
        if (!editor) {
          editor = new E('#div1');
          editor.create();
        }
        //editor.txt.html(row.content);
      })

    },
    load() {
      this.loading = true
      request.get("http://localhost:9090/article", {
        params: {
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          search: this.search,
        }
      }).then(res => {
        this.loading = false;
        this.articles = res.data.records;
        this.total = res.data.total;
      })

      request.get("http://localhost:9090/course/all", {

      }).then(res => {

        this.options = res.data;
        this.$forceUpdate();
      })
    },

    // fileUploadSuccess(res) {
    //   console.log(res);
    //   this.form.cover = res.data;
    // },


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
       if(!editor){
         editor = new E('#article');

         //配置server接口地址
         // editor.config.uploadImgServer = 'http://localhost:9090/files/editor/upload';
         // // 配置上传文件参数名称
         // editor.config.uploadFileName = "file";
         editor.create();
       }
         editor.txt.html("")
       })
    },
    save() {

      this.form.content = editor.txt.html(); // 获取编辑器里面的值到form的内容中

        let userStr = sessionStorage.getItem("user") || "{}"
        let user = JSON.parse(userStr);
        //console.log(user)
        this.form.username = user.username;
        request.post("http://localhost:9090/article", this.form).then(res => {
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
      this.search = '';

      this.$router.go(0);
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
