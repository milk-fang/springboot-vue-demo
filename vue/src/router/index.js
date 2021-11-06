import { createRouter, createWebHistory } from 'vue-router'

import Layout from '../layout/layout'
import notFound from '../components/notFound'
const routes = [
  {
    path: '/',
    name: 'Layout',
    component: Layout,
    redirect:'/article',
    children:[
      {
        path: 'user',
        name: 'User',
        component: ()=>import("@/views/User"),
      },
      {
        path: 'report',
        name: 'Report',
        component: ()=>import("@/views/Report")
      },

      {
        path: 'course',
        name: 'Course',
        component: ()=>import("@/views/Course"),
      },

      {
        path:'/article',
        name:'/Article',
        component: ()=>import("@/views/Article")
      },
      {
        path:'/individual',
        name:'/Individual',
        component: ()=>import("@/views/Individual")
      },
      {
        path:'/chat/:id',
        name:'/Chat',
        component: ()=>import("@/views/Chat")
      },
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
  },
  {
    path:'/test',
    name:'/Test',
    component: ()=>import("@/views/Test")
  },
  {

    path: "/404",
    name: "notFound",
    component:notFound

  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

router.beforeEach((to,from,next)=>{
  if(to.matched.length == 0){
    next({path:'/404'})
  }else{
    next();
  }
})

export default router
