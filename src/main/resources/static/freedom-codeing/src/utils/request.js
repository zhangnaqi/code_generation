import axios from 'axios'

const request = axios.create({
    baseURL: 'http://127.0.0.1:8090',
})

request.interceptors.response.use(
    response => {
        if (response.data.code === 2001) {
            return Promise.resolve(response.data)
        } else {
            return Promise.reject(response.data)
        }
    }
)

export default request

