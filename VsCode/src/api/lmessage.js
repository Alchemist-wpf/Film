import service from '../utils/request'
//查询员工列表
export function WorkList() {
    return service({
        url: '/api/worker',
        method: 'get',
    })
}

//查看我的评价
export function MyCritic(uid) {
    return service({
        url: '/api/we?uid='+uid,
        method: 'get',
    })
}
//查询留言列表
export function LMessage() {
    return service({
        url: '/api/lm',
        method: 'get',
    })
}