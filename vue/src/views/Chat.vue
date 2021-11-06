<template>
  <el-container>
    <el-aside width="200px">Aside</el-aside>
    <el-container>
      <el-header style="border:1px solid blue">Header</el-header>

        <div id="main" style="margin-top: 30px;min-height: 100px"><p v-html="article.content"></p></div>


      <div id="footer">

        <div class="comment-wrap">
          <div class="photo">
                    <div class="avatar">
                      <el-avatar class="photo" :size="50" :src="user.img"></el-avatar>
                    </div>
          </div>
          <div class="comment-block">
            <form action="">
              <div id="chat">

              </div>
            </form>
          </div>
        </div>
          <el-button type="primary" style="margin-top: 10px;float: right" @click="comment()">评论</el-button><br><br><br>


<div style="height: 300px">
  <div class="infinite-list" v-infinite-scroll="load" style="overflow:auto">
    <div v-for="(comment,index) in comments" class="infinite-list-item"><div>

      <div class="item" :key="index">

        <div class="comment-wrap">
          <div class="photo">
            <el-avatar class="photo" :size="50" :src="comment.img"></el-avatar>
          </div>
          <div class="comment-block">
            <p class="comment-text" v-html="comment.content"></p>
            <div class="bottom-comment">
              <div class="comment-date">{{comment.createTime}}</div>
              <ul class="comment-actions">
<!--                点赞-->
                <li class="like"><i :key="index" :class="{iconfont:true, 'icon-dianzan':!liked[comments[index].id],'icon-dianzan1':liked[comments[index].id]}" @click="liking(index)">{{comment.likes}}</i></li>
<!--                举报-->
                <li class="complain"><i @click="report(comments[index].id)" class="iconfont icon-jubao"></i></li>

              </ul>
            </div>
          </div>
        </div>
      </div>
  <div class="comments">

        </div>
      </div>
    </div>
  </div>

    </div></div>

    </el-container>
    <el-aside width="200px">Aside</el-aside>

    <el-dialog
        v-model="dialogVisible"
        title="举报"
        width="30%"

    >
      <el-form :model="comment_form" label-width="120px">

        <el-form-item label="举报理由">
          <el-input type="textarea" v-model="comment_form.reason" style="width: 80%"></el-input>
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

  </el-container>


</template>

<script>
import E from 'wangeditor'
import request from "../utils/request";


let editor;

export default {

  name: "chat",
  data() {
    return {
      article: {},
      id: '',
      comments: [],
      count: 0,
      form: {},
      // loading:'',
      // noMore:'',
      // total:'',
      users: [],
      user: '',
      liked: [],
      newComment: {},
      like: {},
      dialogVisible:false,
      comment_index:0,
      comment_form:{}

    }
  },

  computed: {
    // computed:{
    //   noMore(){
    //     //   当循环添加的数据条数大于或等于总条数时停止加载
    //     return this.comments.length >= this.total
    //   },
    //   disabled () {
    //     return this.loading || this.noMore
    //   }
  },
  created() {
    this.id = this.$route.params.id;
    this.loading(this.id);
  },

  mounted() {
    this.$nextTick(() => {
      editor = new E('#chat');
      editor.config.height = 100;
      editor.config.placeholder = '请发表评论...';
      editor.config.menus = ['emoticon',
        'code']
      editor.create();
    })
  },


  methods: {
    // 确定举报
    save(){
      this.comment_form.userId = JSON.parse(sessionStorage.getItem('user')).id;
      this.comment_form.commentId = this.comment_index;

      request.post("http://localhost:9090/report", this.comment_form).then(res => {
        if (res.code === '0') {
          this.$message({
            type: "success",
            message: "举报成功",
          })
        } else {
          this.$message({
            type: "error",
            message: "举报失败",
          })
        }
      })
      this.dialogVisible = false;
    },

    // 举报，唤起确定框
    report(index){
      this.dialogVisible = true;
      this.comment_index = index;
    },
    liking(index) {
      this.like.userId = JSON.parse(sessionStorage.getItem('user')).id;
      this.like.commentId = this.comments[index].id;
      this.liked[this.comments[index].id] = !this.liked[this.comments[index].id];
      if (this.liked[this.comments[index].id]) {
        // 说明原来没点赞，添加数据
        this.comments[index].likes++;
        request.post("http://localhost:9090/like", this.like).then(res => {

        })
      } else { // 说明原来点赞，删除数据
        this.comments[index].likes--;
        request.delete('http://localhost:9090/like?userId=' + JSON.parse(sessionStorage.getItem('user')).id+"&commentId="+(this.comments[index].id)).then(res => {


        })
      }
      this.newComment.id = this.comments[index].id;
      this.newComment.content = this.comments[index].content;
      this.newComment.userId = this.comments[index].userId;
      this.newComment.articleId = this.comments[index].articleId;
      this.newComment.createTime = this.comments[index].createTime;
      this.newComment.likes = this.comments[index].likes;

      request.put("http://localhost:9090/comment", this.newComment).then(res => {

      })
      this.$forceUpdate();


    }
    ,

    comment() {
      this.form.content = editor.txt.html();
      console.log(this.form.content);

      this.form.articleId = this.id;
      this.form.userId = JSON.parse(sessionStorage.getItem('user')).id;
      request.post("http://localhost:9090/comment", this.form).then(res => {
        if (res.code === '0') {
          this.$message({
            type: "success",
            message: "评论成功",
          })
        } else {
          this.$message({
            type: "error",
            message: "评论失败",
          })
        }
      })

      this.$router.go(0);
      // this.loading(this.$route.params.id)
    },


    load() {

    },
    loading(id) {
      request.get("http://localhost:9090/comment?articleId=" + id, {}).then(res => {
        for (let i = 0; i < res.data.length; i++) {
          this.comments[i] = res.data[i];
        }
      })


      let userJson = sessionStorage.getItem("user");
      if (!userJson) {
        return
      }
      this.user = JSON.parse(userJson)


      request.get("http://localhost:9090/like/great/user?userId=" + this.user.id, {}).then(res => {
        for (let i = 0; i < res.data.length; i++) {
          this.liked[res.data[i].commentId] = res.data[i].commentId != 0 ? 1 : 0
        }
        //强制刷新页面
        this.$forceUpdate();
      })

    }

  }
}
</script>


<style scoped>
.item {
  width: 800px;
  display: flex;
  margin-bottom: 20px;
}

.infinite-list {

  padding: 0;
  margin: 0;
  list-style: none;
}
.infinite-list .infinite-list-item {

  display: flex;
  align-items: center;
  justify-content: left;
  /*height: 50px;*/
  /*background-color: #CCFFFF;*/
  margin: 20px;
  /*color: var(--el-color-primary);*/

}
.infinite-list .infinite-list-item + .list-item {
  margin-top: 10px;

}

html,
body {
  background-color: #f0f2fa;
  font-family: "PT Sans", "Helvetica Neue", "Helvetica", "Roboto", "Arial", sans-serif;
  color: #555f77;
  -webkit-font-smoothing: antialiased;
}
input,
textarea {
  outline: none;
  border: none;
  display: block;
  margin: 0;
  padding: 0;
  -webkit-font-smoothing: antialiased;
  font-family: "PT Sans", "Helvetica Neue", "Helvetica", "Roboto", "Arial", sans-serif;
  font-size: 1rem;
  color: #555f77;
}
input::-webkit-input-placeholder,
textarea::-webkit-input-placeholder {
  color: #ced2db;
}
input::-moz-placeholder,
textarea::-moz-placeholder {
  color: #ced2db;
}
input:-moz-placeholder,
textarea:-moz-placeholder {
  color: #ced2db;
}
input:-ms-input-placeholder,
textarea:-ms-input-placeholder {
  color: #ced2db;
}
p {
  line-height: 1.3125rem;
}
.comments {
  margin: 2.5rem auto 0;
  max-width: 60.75rem;
  padding: 0 1.25rem;
}
.comment-wrap {
  margin-bottom: 1.25rem;
  display: table;
  width: 100%;
  min-height: 5.3125rem;
}
.photo {
  padding-top: 0.625rem;
  display: table-cell;
  width: 3.5rem;
}
.photo .avatar {
  height: 2.25rem;
  width: 2.25rem;
  border-radius: 50%;
  background-size: contain;
}
.comment-block {
  padding: 1rem;
  background-color: #fff;
  display: table-cell;
  vertical-align: top;
  border-radius: 0.1875rem;
  box-shadow: 0 1px 3px 0 rgba(0, 0, 0, 0.08);
}
.comment-block textarea {
  width: 100%;
  max-width: 100%;
}
.comment-text {
  margin-bottom: 1.25rem;
}
.bottom-comment {
  color: #acb4c2;
  font-size: 0.875rem;
}
.comment-date {
  float: left;
}
.comment-actions {
  float: right;
}
.comment-actions li {
  display: inline;
}
/*.comment-actions {*/
/*  padding-right: 0.625rem;*/
/*  border-right: 1px solid #e1e5eb;*/
/*}*/

.comment-actions li.like{
  padding-right: 0.625rem;
  border-right: 1px solid #e1e5eb;
}
.comment-actions {
  padding-left: 0.625rem;
}

.comment-actions li.complain {
  padding-left: 0.625rem;
}
</style>

<style>
.w-e-menu {
  z-index: 2 !important;
}
.w-e-text-container {
  z-index: 1 !important;
}

.w-e-toolbar{
  z-index: 1 !important;
}

</style>