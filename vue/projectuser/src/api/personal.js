import service from '../utils/request'
//查询我的购物车
export function MyCart(uid) {
    return service({
        url: '/api/cart?uid='+uid,
        method: 'get',
    })
}

//查看我的订单
export function AddMyOrder(data) {
    return service({
        url: '/api/order/add',
        method: 'post',
		data:data,
    })
}

//查看我的订单
export function MyOrder(uid) {
    return service({
        url: '/api/order/user/id?uid='+uid,
        method: 'get',
    })
}


