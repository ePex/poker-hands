import axios from 'axios'

export const AXIOS = axios.create({
  baseURL: `http://localhost:8088/rest`,
  headers: {
    'Access-Control-Allow-Origin': 'http://localhost:3000'
  }
})
