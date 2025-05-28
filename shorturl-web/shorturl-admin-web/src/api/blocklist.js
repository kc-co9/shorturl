import request from "@/utils/request";

export function getBlocklistList(data) {
    return request({
        url: '/blocklist/v1/blocklistList',
        method: 'get',
        params: data
    })
}

export function addBlocklist(data) {
    return request({
        url: '/blocklist/v1/addBlocklist',
        method: 'post',
        data: data
    })
}

export function updateBlocklist(data) {
    return request({
        url: '/blocklist/v1/updateBlocklist',
        method: 'post',
        data: data
    })
}

export function removeBlocklist(data) {
    return request({
        url: '/blocklist/v1/removeBlocklist',
        method: 'post',
        data: data
    })
}
