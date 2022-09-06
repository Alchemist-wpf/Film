import service from '../utils/request'

//查询订单列表
export function AllOrder() {
    return service({
        url: '/api/order',
        method: 'get',
    })
}

export function Login(data){
    return service({
        url:'/api/worker/login',
        method:'post',
        data:data
    })
}

export function WorkerInfo(wid){
    return service({
        url:'/api/worker/id?id='+wid,
        method:'get'
    })
}

export function LogoutWorker(wid) {
    return service({
        url: '/api/worker/logout?wid='+wid,
        method: 'get',
    })
}

export function MyCritic(wid) {
    return service({
        url: '/api/me?wid='+wid,
        method: 'get',
    })
}

export function Modify(data) { //id自动
    return service({
        url: '/api/worker/modify',
        method: 'post',
		data:data
    })
}

export function Allworker() {
    return service({
        url: '/api/worker',
        method: 'get',
    })
}

export function workerDelete(data) { 
    return service({
        url: '/api/worker/delete',
        method: 'post',
		data:data
    })
}

export function orderUpdate(data){
    return service({
        url:'api/order/update',
        method: 'post',
        data:data,
    })
}

export function AllErrorOrder() {
    return service({
        url: '/api/order/error',
        method: 'get',
    })
}

export function AddWorker(data) { //id自动
    return service({
        url: '/api/worker/add',
        method: 'post',
		data:data
    })
}