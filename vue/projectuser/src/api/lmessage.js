import service from '../utils/request'
//查询员工列表
export function WorkList() {
    return service({
        url: '/api/worker',
        method: 'get',
    })
}

//查看我的评价
export function MyCritic(uid,wid) {
    return service({
        url: '/api/we?uid='+uid+"&wid="+wid,
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

//查看我的评价
export function MyCritic2(uid,wid) {
    return service({
        url: '/api/we2?uid='+uid+"&wid="+wid,
        method: 'get',
    })
}

//添加评论
export function AddEvaluate(data) {
    return service({
        url: '/api/addEvaluate',
        method: 'post',
		data:data
    })
}

export function AddMyEvaluate(data){
    return service({
        url: '/api/addMyEvaluate',
        method: 'post',
		data:data
    })
}

export function reply(data){
    return service({
        url: '/api/reply',
        method: 'post',
		data:data
    })
}