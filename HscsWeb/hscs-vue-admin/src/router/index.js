import Vue from 'vue'
import Router from 'vue-router'

// in development-env not use lazy-loading, because lazy-loading too many pages will cause webpack hot update too slow. so only in production use lazy-loading;
// detail: https://panjiachen.github.io/vue-element-admin-site/#/lazy-loading

Vue.use(Router)

/* Layout */
import Layout from '../views/layout/Layout'

/**
* hidden: true                   if `hidden:true` will not show in the sidebar(default is false)
* alwaysShow: true               if set true, will always show the root menu, whatever its child routes length
*                                if not set alwaysShow, only more than one route under the children
*                                it will becomes nested mode, otherwise not show the root menu
* redirect: noredirect           if `redirect:noredirect` will no redirect in the breadcrumb
* name:'router-name'             the name is used by <keep-alive> (must set!!!)
* meta : {
    title: 'title'               the name show in submenu and breadcrumb (recommend set)
    icon: 'svg-name'             the icon show in the sidebar,
  }
**/
export const constantRoutes = [
  { path: '/login', component: () => import('@/views/login/index'), hidden: true },
  { path: '/404', component: () => import('@/views/404'), hidden: true },
  // { path: '/login', component: () => import('@/views/login/Login'), hidden: true },

  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    name: 'Dashboard',
    hidden: true,
    children: [{
      path: 'dashboard',
      component: () => import('@/views/dashboard/index'),
      meta: { title: '家校沟通系统后台首页', icon: 'dashboard' }
    }]
  },

  {
    path: '/info',   
    component: Layout,
    redirect: '/info/table',  
    name: '信息管理',
    meta: { title: '信息管理', icon: 'user' },
    children: [
      {
        path: 'student/list',
        name: '学生列表',
        component: () => import('@/views/hscs/student/list'),
        meta: { title: '学生列表', icon: 'user' }
      },
      {
        path: 'student/save',
        name: '添加学生',
        component: () => import('@/views/hscs/student/save'),
        meta: { title: '添加学生', icon: 'tree' }
      },
      {
        path: 'editStudent/:id',
        name: 'EduStudentEdit',
        component: () => import('@/views/hscs/student/save'),
        meta: { title: '编辑学生信息', noCache: true },
        hidden: true // 隐藏路由
      },
      {
        path: 'table',
        name: '教师列表',
        component: () => import('@/views/hscs/teacher/list'),
        meta: { title: '教师列表', icon: 'user' }
      },
      {
        path: 'save',
        name: '添加教师',
        component: () => import('@/views/hscs/teacher/save'),
        meta: { title: '添加教师', icon: 'tree' }
      },
      {
        path: 'edit/:teacherId',
        name: 'EduTeacherEdit',
        component: () => import('@/views/hscs/teacher/save'),
        meta: { title: '编辑教师', noCache: true },
        hidden: true // 隐藏路由
      }
    ]
  },

  {
    path: '/course',
    component: Layout,
    redirect: '/course/list',
    name: '课程信息',
    meta: { title: '课程信息', icon: 'form' },
    children: [
      {
        path: 'subject',
        name: '课程分类',
        component: () => import('@/views/hscs/subject/list'),
        meta: { title: '课程分类', icon: 'tree' }
      },
      {
        path: 'list',
        name: '课程列表',
        component: () => import('@/views/hscs/course/list'),
        meta: { title: '课程列表', icon: 'table' }
      },
      {
        path: 'save',
        name: '添加课程',
        component: () => import('@/views/hscs/course/info'),
        meta: { title: '添加课程', icon: 'example' }
      },
      {
        path: 'info/:id',
        name: 'EduCourseInfoEdit',
        component: () => import('@/views/hscs/course/info'),
        meta: { title: '编辑课程基本信息', noCache: true },
        hidden: true
      },
      {
        path: 'chapter/:id',
        name: 'EduCourseChapterEdit',
        component: () => import('@/views/hscs/course/chapter'),
        meta: { title: '编辑课程大纲信息', noCache: true },
        hidden: true
      },
      {
        path: 'publish/:id',
        name: 'EduCoursePublishEdit',
        component: () => import('@/views/hscs/course/publish'),
        meta: { title: '发布课程', noCache: true },
        hidden: true
      }
    ]
  },
  
  {
    path: '/statistics',
    component: Layout,
    redirect: '/statistics/table',
    name: '统计分析',
    meta: {
      title: '统计分析',
      icon: 'example'
    },
    children: [{
        path: 'create',
        name: '生成数据',
        component: () => import('@/views/statistics/create'),
        meta: {
          title: '生成数据',
          icon: 'table'
        }
      },
      {
        path: 'showLog',
        name: '图表显示',
        component: () => import('@/views/statistics/showLog'),
        meta: {
          title: '图表显示',
          icon: 'tree'
        }
      },
    ]
  },

  //前台资源管理
  {
    path: '/cms',
    component: Layout,
    redirect: '/cms/list',
    name: '首页管理',
    meta: {
      title: '首页管理',
      icon: 'table'
    },
    children: [{
        path: 'ad/list',
        name: '广告推荐',
        component: () => import('@/views/cms/ad/list'),
        meta: {
          title: '广告推荐',
          icon: 'link'
        }
      },
      {
        path: 'ad/create',
        name: '添加广告',
        component: () => import('@/views/cms/ad/form'),
        meta: {
          title: '添加广告',
          icon: 'tree'
        }
      },
      {
        path: 'ad/edit/:id',
        name: '编辑广告推荐',
        component: () => import('@/views/cms/ad/form'),
        meta: {
          title: '编辑广告推荐'
        },
        hidden: true
      },

      {
        path: 'adType/list',
        name: '推荐位',
        component: () => import('@/views/cms/adType/list'),
        meta: {
          title: '推荐位',
          icon: 'eye'
        }
      },
      {
        path: 'adType/create',
        name: '添加推荐位',
        component: () => import('@/views/cms/adType/form'),
        meta: {
          title: '添加推荐位'
        },
        hidden: true
      },
      {
        path: 'adType/edit/:id',
        name: '编辑推荐位',
        component: () => import('@/views/cms/adType/form'),
        meta: {
          title: '编辑推荐位'
        },
        hidden: true
      }

    ]
  },


  //权限管理
  {
    path: '/acl',
    component: Layout,
    redirect: '/acl/user/list',
    name: '权限管理',
    meta: {
      title: '权限管理',
      icon: 'chart'
    },
    children: [{
        path: 'user/list',
        name: '用户管理',
        component: () => import('@/views/acl/user/list'),
        meta: {
          title: '用户管理'
        }
      },
      {
        path: 'role/list',
        name: '角色管理',
        component: () => import('@/views/acl/role/list'),
        meta: {
          title: '角色管理'
        }
      },
      {
        path: 'role/form',
        name: '角色添加',
        component: () => import('@/views/acl/role/form'),
        meta: {
          title: '角色添加'
        },
        hidden: true
      },
      {
        path: 'role/update/:id',
        name: '角色修改',
        component: () => import('@/views/acl/role/form'),
        meta: {
          title: '角色修改'
        },
        hidden: true
      },
      {
        path: 'role/distribution/:id',
        name: '角色权限',
        component: () => import('@/views/acl/role/roleForm'),
        meta: {
          title: '角色权限'
        },
        hidden: true
      },
      {
        path: 'menu/list',
        name: '菜单管理',
        component: () => import('@/views/acl/menu/list'),
        meta: {
          title: '菜单管理'
        }
      },
      {
        path: 'user/add',
        name: '用户添加',
        component: () => import('@/views/acl/user/form'),
        meta: {
          title: '用户添加'
        },
        hidden: true
      },
      {
        path: 'user/update/:id',
        name: '用户修改',
        component: () => import('@/views/acl/user/form'),
        meta: {
          title: '用户修改'
        },
        hidden: true
      },
      {
        path: 'user/role/:id',
        name: '用户角色',
        component: () => import('@/views/acl/user/roleForm'),
        meta: {
          title: '用户角色'
        },
        hidden: true
      }

    ]
  },

  //在线聊天
  {
    path: '/wechat',
    component: () => import('@/views/wechat/FriendChat'),
    name: '在线聊天',
    meta: {
      title: '在线聊天',
      icon: 'eye'
    }
  },

  { path: '*', redirect: '/404', hidden: true }
]

const createRouter = () => new Router({
  // mode: 'history', // require service support
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})

const router = createRouter()


export default router
