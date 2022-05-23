import service from '../utils/request'

//查询订单列表
export function AllOrder() {
    return service({
        url: '/api/order',
        method: 'get',
    })
}
