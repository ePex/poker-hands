<template>
  <div class="hands-form">
    <h1>{{ msg }}</h1>

    <label>
      First hand:
      <input type="text" v-model="firstHand" />
    </label>
    <label>
      Second hand:
      <input type="text" v-model="secondHand" />
    </label>

    <button class=”Search__button” @click="callRestService()">Compare</button>
    <h3>{{ response.message }}</h3>
  </div>
</template>

<script>
import {AXIOS} from './http-common'

export default {
  name: `HandsForm`,
  data () {
    return {
      firstHand: `C5 D3 D4 S7 C6`,
      secondHand: `DA D3 D5 H8 S8`,
      msg: `Welcome`,
      response: [],
      errors: []
    }
  },
  methods: {
    callRestService () {
      AXIOS.post(`/poker-hands/compare-hands`, {
        'firstHand': this.firstHand,
        'secondHand': this.secondHand
      })
        .then(response => {
          // JSON responses are automatically parsed.
          this.response = response.data
        })
        .catch(e => {
          this.errors.push(e)
        })
    }
  }
}

</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1, h2 {
  font-weight: normal;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>
