import {getToken, setToken, removeToken} from '@/utils/auth'
import {resetRouter} from '@/router'
import {signIn, getAccountDetail, signOut} from "@/api/account";

const getDefaultState = () => {
    return {
        token: getToken(),
        name: '',
        avatar: ''
    }
}

const state = getDefaultState()

const mutations = {
    RESET_STATE: (state) => {
        Object.assign(state, getDefaultState())
    },
    SET_TOKEN: (state, token) => {
        state.token = token
    },
    SET_NAME: (state, name) => {
        state.name = name
    },
    SET_AVATAR: (state, avatar) => {
        state.avatar = avatar
    }
}

const actions = {
    // user login
    login({commit}, account) {
        const {email, password} = account
        return new Promise((resolve, reject) => {
            signIn({email: email.trim(), password: password}).then(response => {
                const {data} = response
                commit('SET_TOKEN', data.authToken)
                setToken(data.authToken)
                resolve()
            }).catch(error => {
                console.error(error)
                reject(error)
            })
        })
    },

    // get user info
    getInfo({commit, state}) {
        return new Promise((resolve, reject) => {
            getAccountDetail(state.token).then(response => {
                const {data} = response

                if (!data) {
                    return reject('认证失败，请重新登陆。')
                }

                const {administratorId, administratorName} = data

                commit('SET_NAME', administratorName)
                commit('SET_AVATAR', 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif')
                resolve(data)
            }).catch(error => {
                reject(error)
            })
        })
    },

    // user logout
    logout({commit, state}) {
        return new Promise((resolve, reject) => {
            signOut(state.token).then(() => {
                removeToken() // must remove  token  first
                resetRouter()
                commit('RESET_STATE')
                resolve()
            }).catch(error => {
                reject(error)
            })
        })
    },

    // remove token
    resetToken({commit}) {
        return new Promise(resolve => {
            removeToken() // must remove  token  first
            commit('RESET_STATE')
            resolve()
        })
    }
}

export default {
    namespaced: true,
    state,
    mutations,
    actions
}

