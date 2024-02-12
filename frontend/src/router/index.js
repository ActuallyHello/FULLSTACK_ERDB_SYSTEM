import { createRouter, createWebHistory } from 'vue-router'
import ModelsView from '@/views/models/ModelsView.vue'
import CreateModelView from '@/views/models/CreateModelView.vue'
import DetailModelView from '@/views/models/DetailModelView.vue'
import MainView from '@/views/MainView.vue'
import MyTasksView from '@/views/tasks/MyTasksView.vue'
import TaskCreationView from '@/views/tasks/TaskCreationView.vue'
import SendTasksView from '@/views/tasks/SendTasksView.vue'
import TaskView from '@/views/tasks/TaskView.vue'

import LoginView from '@/views/LoginView.vue'

const routes = [
  {
    path: '/',
    name: 'main',
    component: MainView,
    meta: {
      requiredLogin: true,
      roles: ['студент', 'преподаватель']
    }
  },
  {
    path: '/models',
    name: 'models',
    component: ModelsView,
    meta: {
      requiredLogin: true,
      roles: ['студент', 'преподаватель']
    }
  },
  {
    path: '/create-model',
    name: 'create-model',
    component: CreateModelView,
    meta: {
      requiredLogin: true,
      roles: ['студент', 'преподаватель']
    }
  },
  {
    path: '/models/:id',
    name: 'model',
    component: DetailModelView,
    meta: {
      requiredLogin: true,
      roles: ['студент', 'преподаватель']
    }
  },
  {
    path: '/my-tasks',
    name: 'my-tasks',
    component: MyTasksView,
    meta: {
      requiredLogin: true,
      roles: ['студент', 'преподаватель']
    }
  },
  {
    path: '/my-tasks/:id',
    name: 'task',
    component: TaskView,
    meta: {
      requiredLogin: true,
      roles: ['студент', 'преподаватель']
    }
  },
  {
    path: '/task-creation',
    name: 'task-creation',
    component: TaskCreationView,
    meta: {
      requiredLogin: true,
      roles: ['студент', 'преподаватель']
    }
  },
  {
    path: '/send-tasks',
    name: 'send-tasks',
    component: SendTasksView,
    meta: {
      requiredLogin: true,
      roles: ['студент', 'преподаватель']
    }
  },
  {
    path: '/login',
    name: 'login',
    component: LoginView
  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
});

router.beforeEach((to, from, next) => {
  // let isAuth = Boolean(localStorage.getItem('isAuth'));
  // if (to.matched.some(record => record.meta.requiredLogin)) {
  //   if (isAuth) {
  //     if (to.meta.roles.includes('студент') && localStorage.getItem('student_id')) {
  //       next();
  //     } else if (to.meta.roles.includes('преподаватель') && localStorage.getItem('teacher_id')) {
  //       next();
  //     } else {
  //       console.log('ERROR NO SUCH ROLE!');
  //       next('/login');
  //     }
  //   } else {
  //     next('/login');
  //   }
  // } else {
  //   next();
  // }
  next();
});

export default router
