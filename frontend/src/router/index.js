import Vue from 'vue'
import Router from 'vue-router'
import HandsForm from '@/components/HandsForm'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Main',
      component: HandsForm
    }
  ]
})
