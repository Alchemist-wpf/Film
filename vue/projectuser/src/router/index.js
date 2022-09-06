import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '../views/Login.vue'
import Login1 from '../views/Login-customer.vue'
import Register from '../views/Register.vue'
import index from '../views/index.vue'
import Layout from '@/components/layout/layout'

Vue.use(VueRouter)

const routes = [
  {
    path: '/login',
    name: 'login',
    component: Login
  },
  {
    path: '/login1',
    name: 'login1',
    component: Login1
  },
  {
    path: '/Register',
    name: 'Register',
    component: Register
  },
  {
    path: '/index',
    name: 'index',
    component: index
  },
  {
    path:"/",
    component:Layout,
    children:[{
      path:"",
      component:()=>import('@/views/index.vue')
    },
    {
      path:"/films",
      component:()=>import('@/views/films')
    },
    {
      path:"/activity",
      component:()=>import('@/views/activity')
    },
    {
      path:"/message",
      component:()=>import('@/views/message')
    },
    {
      path:"/seat",
      component:()=>import('@/views/seat')
    },
    {
      path:"/film",
      component:()=>import('@/views/film/movie.vue'),
      children:[
        {
          path:"detail",
          component:()=>import('@/views/film/detail.vue'),
        },
        {
          path:"ticket",
          component:()=>import('@/views/film/ticket.vue'),
        },
      ],
    },
    {
      path:"/mine",
      component:()=>import('@/views/me/mine.vue'),
      children:[
        {
          path:"cart",
          component:()=>import('@/views/me/cart.vue'),
        },
        {
          path:"order",
          component:()=>import('@/views/me/order.vue'),
        },
        {
          path:"setting",
          component:()=>import('@/views/me/setting.vue'),
        },
      ],
    },
    ],
  },
  {
    path: '/customer',
    component: () => import("@/views/customer/Menu.vue"),
    children: [
        {
          path: '',
          redirect: 'leavemessage'
        },
        {
        path: "leavemessage",
        component:()=>import("@/views/customer/LeavingMessage.vue")
        },
        {
          path: "activity",
          component:()=>import("@/views/customer/Activity.vue")
        },
        {
          path: "statistic",
          component:()=>import("@/views/customer/Statistic.vue")
        },
        {
          path: "evaluate",
          component:()=>import("@/views/customer/Evaluate.vue")
        },
        {
          path: "setting",
          component:()=>import("@/views/customer/Setting.vue")
        },
    ]

  },
  {
    path: '/back',
    component: () => import("@/views/back/menu.vue"),
    children: [
        {
          path: '',
          redirect: 'movielist',
        },
        {
          path: "movielist",
          component:()=>import("@/views/back/movielist.vue")
        },
        {
          path: "workerlist",
          component:()=>import("@/views/back/workerlist.vue")
        },
        {
          path: "userlist",
          component:()=>import("@/views/back/userlist.vue")
        },
        {
          path: "orderlist",
          component:()=>import("@/views/back/orderlist.vue")
        },
        {
          path: "errororder",
          component:()=>import("@/views/back/errororder.vue")
        },
        {
          path: "addworker",
          component:()=>import("@/views/back/addworker.vue")
        },
        {
          path: "addfilm",
          component:()=>import("@/views/back/addfilm.vue")
        },
        {
          path: "poster",
          component:()=>import("@/views/back/poster.vue")
        },
        {
          path: "filmon",
          component:()=>import("@/views/back/filmon.vue")
        },
        {
          path: "setting",
          component:()=>import("@/views/back/setting.vue")
        },
    ]

  }

]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
