import request from "@/utils/request";

export function getShorturlList(data) {
    return request({
        url: '/shorturl/v1/shorturlList',
        method: 'get',
        params: data
    })
}

export function createShorturl(data) {
    return request({
        url: '/shorturl/v1/createShorturl',
        method: 'post',
        data: data
    })
}

export function updateShorturl(data) {
    return request({
        url: '/shorturl/v1/updateShorturl',
        method: 'post',
        data: data
    })
}
