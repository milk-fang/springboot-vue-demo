import { createRouter, createWebHistory } from 'vue-router'

import Layout from '../layout/layout'
const routes = [
  {
    path: '/',
    name: 'Layout',
    component: Layout,
    redirect:'/course',
    children:[
      {
        path: 'user',
        name: 'User',
        component: ()=>import("@/views/User"),
      },
      {
        path:'/book',
        name:'/Book',
        component: ()=>import("@/views/Book")
      },
      {
        path:'/course',
        name:'/Course',
        component: ()=>import("@/views/Course")
      },
      {
        path:'/individual',
        name:'/Individual',
        component: ()=>import("@/views/Individual")
      }
    ]
  },
  {
    path:'/login',
    name:'/Login',
    component: ()=>import("@/views/Login")
  },


  {
    path:'/register',
    name:'/Register',
    component: ()=>import("@/views/Register")
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
