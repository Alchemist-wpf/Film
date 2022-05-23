// import { Header } from 'element-ui'
import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import Login from '../views/Login.vue'
import Register from '../views/Register.vue'

 import Layout from '@/components/layout/Layout.vue'


Vue.use(VueRouter)

const routes = [

  {
    path: '/login',
    name: 'login',
    component: Login
  },
  {
    path: '/register',
    name: 'register',
    component: Register
  },

  {
    path: '/',
    component: Layout,
    children: [
      {
        path: '',
        redirect: '/home'
      },
      {
      path: "/home",
      component:()=>import("@/views/Home.vue")
      },
      {
        path: "/film",
        component:()=>import("@/views/Film.vue")
      },
      {
        path: "/activity",
        component:()=>import("@/views/Activity.vue")
      },
      {
        path: "/leavingmessage",
        component:()=>import("@/views/LeavingMessage.vue")
      },
      {
        path: "/seat",
        component:()=>import("@/views/Seat.vue")
      },
      {
        path: "/filmdetail",
        component: () => import("@/views/filmdetail/Index.vue"),
        children: [
          {
            path: '',
            redirect: 'detail'
          },
          {
            path: "detail",
            component: () => import("@/views/filmdetail/Detail.vue"),
          },
          {
            path: "ticket",
            component: () => import("@/views/filmdetail/Ticket.vue"),
          },
        ]
      },
      {
        path: "/personalcenter",
        component: () => import("@/views/personalcenter/Basic.vue"),
        children: [
          {
            path: '',
            redirect: 'order'
          },
          {
            path: "order",
            component: () => import("@/views/personalcenter/Order.vue"),
          },
          {
            path: "set",
            component: () => import("@/views/personalcenter/Set.vue"),
          },
          {
            path: "shop",
            component: () => import("@/views/personalcenter/Shop.vue"),
          },

        ]
      },

     ]

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

  }

]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router

