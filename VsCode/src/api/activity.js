import service from '../utils/request'
//查询活动列表
export function ListAllActivity() {
    return service({
        url: '/api/activity',
        method: 'get',
    })
}

//查询活动列表
export function AddRegistration(data) {
    return service({
        url: '/api/registration',
        method: 'post',
				data:data,
    })
}