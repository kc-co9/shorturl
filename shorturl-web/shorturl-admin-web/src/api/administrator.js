import request from "@/utils/request";

export function getAdministratorList(data) {
    return request({
        url: '/administrator/v1/administratorList',
        method: 'get',
        params: data
    })
}

export function getAdministratorDetail(data) {
    return request({
        url: '/administrator/v1/administratorDetail',
        method: 'get',
        params: data
    })
}

export function addAdministrator(data) {
    return request({
        url: '/administrator/v1/addAdministrator',
        method: 'post',
        data: data
    })
}

export function updateAdministrator(data) {
    return request({
        url: '/administrator/v1/updateAdministrator',
        method: 'post',
        data: data
    })
}

export function removeAdministrator(data) {
    return request({
        url: '/administrator/v1/removeAdministrator',
        method: 'post',
        data: data
    })
}