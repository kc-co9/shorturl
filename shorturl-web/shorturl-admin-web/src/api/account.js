import request from "@/utils/request";

export function signIn(data) {
    return request({
        url: '/account/v1/signIn',
        method: 'post',
        data: data
    })
}

export function signOut(data) {
    return request({
        url: '/account/v1/signOut',
        method: 'post',
    })
}

export function getAccountDetail(data) {
    return request({
        url: '/account/v1/accountDetail',
        method: 'get',
    })
}