import Vue from 'vue'
import HandsForm from '@/components/HandsForm'

describe('HandsForm.vue', () => {
  it('should render correct contents', () => {
    const Constructor = Vue.extend(HandsForm)
    const vm = new Constructor().$mount()
    expect(vm.$el.querySelector('.hands-form h1').textContent)
      .to.equal('Welcome')
  })
})
