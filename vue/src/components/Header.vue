<template>
<div style="height: 50px;line-height: 50px;border-bottom:1px solid #ccc;display:flex">
  <div style="width: 200px; padding-left:30px;font-weight:bold;color: dodgerblue">课程评价</div>
  <div style="flex: 1"></div>
  <div style="width: 100px">
    <el-dropdown>
    <span class="el-dropdown-link">
      <el-avatar :size="30"  style="position: relative; top: 10px" :src="user.img"></el-avatar>
      {{user.nickName}}
      <i class="el-icon-arrow-down el-icon--right"></i>
    </span>
      <template #dropdown>
        <el-dropdown-menu>
          <el-dropdown-item @click="$router.push('/Individual')">个人信息</el-dropdown-item>
          <el-dropdown-item @click="$router.push('/login')">退出系统</el-dropdown-item>

        </el-dropdown-menu>
      </template>
    </el-dropdown>
  </div>
</div>
</template>

<script>
export default {
  name: "Header",
  props: ['user'],
  data(){
    return {

    }
  }
}
</script>

<style scoped>

</style>