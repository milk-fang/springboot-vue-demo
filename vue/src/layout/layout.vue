<template>
<div>
  <Header :user="user"></Header>
</div>
  <div style="display: flex">
    <!--    侧边栏-->
    <Aside></Aside>
    <!--    内容-->

    <router-view style="flex: 1"/>
</div>
</template>

<script>
import Header from "@/components/Header"
import Aside from "@/components/Aside"
import request from "../utils/request";

export default {
  name: "layout",
  components:{
    Header,
    Aside,
  },
  data(){
    return {
      user:{}
    }
  },
  created() {
    this.refreshUser()
  },
  methods: {
    refreshUser() {
      let userJson = sessionStorage.getItem("user");
      if (!userJson) {
        return
      }
      let userId = JSON.parse(userJson).id
      request.get("http://localhost:9090/user/" + userId).then(res => {
        this.user = res.data
      })
    }
  }
}
</script>

<style scoped>

</style>