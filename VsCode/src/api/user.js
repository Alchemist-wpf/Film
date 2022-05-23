import service from '../utils/request'

export function Login(data) {
    return service({
        url: '/api/user/login',
        method: 'post',
        data:data
    })
}

export function Register(data) {
    return service({
        url: '/api/user/register',
        method: 'post',
        data:data
    })
}
//验证用户名是否存在
export function IsExistUserName(data) {
    return service({
        url: '/api/user/isExist',
        method: 'post',
        data:data
    })
}
//发送邮箱验证码
export function SendEmailCode(data) {
    return service({
        url: '/api/common/email',
        method: 'post',
        data:data
    })
}
//真正传参
// export function UserInfo(id) { //id自动
//     return service({
//         url: '/api/user/'+id,
//         method: 'get',
//     })
// }
// 查询用户信息
export function UserInfo() {
    return service({
        url: '/api/user/id?id='+localStorage.getItem("uid"),
        method: 'get',
    })
}

// export function SearchFilm(name) {
//     return service({
//         url: '/api/film'+name,
//         method: 'get',
//     })
// }
//电影头部模糊查询
export function SearchFilm(name) {
    return service({
        url: '/api/film/name?name='+name,
        method: 'get',
    })
}
//查询电影列表
export function SearchFilmDetail() {
    return service({
        url: '/api/film/id',
        method: 'get',
    })
}

export function CartInfo() {
    return service({
        url: '/api/cart',
        method: 'get',
    })
}