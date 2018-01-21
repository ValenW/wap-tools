import axios from 'axios'

const base = ''

export const requestLogin = params => {
  return axios.post(`${base}/login`, params).then(res => res.data)
}

export const getUserList = params => {
  return axios.get(`${base}/user/list`, {
    params: params
  })
}

export const getUserListPage = params => {
  return axios.get(`${base}/user/listpage`, {
    params: params
  })
}

export const removeUser = params => {
  return axios.get(`${base}/user/remove`, {
    params: params
  })
}

export const batchRemoveUser = params => {
  return axios.get(`${base}/user/batchremove`, {
    params: params
  })
}

export const editUser = params => {
  return axios.get(`${base}/user/edit`, {
    params: params
  })
}

export const addUser = params => {
  return axios.get(`${base}/user/add`, {
    params: params
  })
}

export const getLinkList = params => {
  return axios.get(`${base}/api/links`, {
    params: params
  })
}

export const addLink = params => {
  return axios.post(`${base}/api/link`, params)
}

export const delLink = id => {
  return axios.delete(`${base}/api/link/${id}`)
}

export const getTags = () => {
  return axios.get(`${base}/api/tags`)
}

export const addTag = params => {
  return axios.post(`${base}/api/tag`, params)
}

export const delTag = params => {
  return axios.post(`${base}/api/tag/delete`, params)
}

export const getTextsapi = (params) => {
  return axios.get(`${base}/api/texts`, params)
}

export const saveTextapi = (params) => {
  return axios.post(`${base}/api/text/list`, params)
}

export const delTextapi = (params) => {
  return axios.delete(`${base}/api/text`, params)
}
export const bulkDelTxt = (params) => {
  return axios.post(`${base}/api/text/bulkDel`, params)
}
