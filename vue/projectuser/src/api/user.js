import service from "@/utils/request";

//登录 2022-03-30 by pjy
export function Login(data){
    return service({
        url:'/api/user/login',
        method:'post',
        data:data
    })
}

//注册 2022-03-30 by pjy
export function Register(data){
    return service({
        url:'/api/user/register',
        method:'post',
        data:data
    })
}
//验证用户是否存在
export function IsExistUserName(data){
    return service({
        url:'/api/user/isExist',
        method:'post',
        data:data
    })
}
//获取邮箱验证码
export function SendEmailCode(data){
    return service({
        url:'/api/common/email',
        method:'post',
        data:data
    })
}
// 真正的传参接口
// export function UserInto(id){
//     return service({
//         url:'/api/user/' + id,
//         method:'get',
//     })
// }

//获取用户信息 2022-03-30 by pjy
export function UserInfo(){
    return service({
        url:'/api/user/id?id='+localStorage.getItem("uid"),
        method:'get'
    })
}

//真正的模糊查询
// export function SearchFilm(name){
//     return service({
//         url:'/api/film/' + name,
//         method:'get'
//     })
// }
//模糊查询 2022-03-31 by pjy
export function SearchFilm(name){
    return service({
        url:'/api/film/name?name='+name,
        method:'get'
    })
}

//退出系统
export function LogoutUser(uid) {
    return service({
        url: '/api/user/logout?uid='+uid,
        method: 'get',
    })
}

//个人设置
export function ModifySelf(data) { //id自动
    return service({
        url: '/api/user/modify',
        method: 'post',
		data:data
    })
}

export function AllUser(){
    return service({
        url: '/api/user',
        method: 'get',
    })
}

export function userDelete(data) { 
    return service({
        url: '/api/user/delete',
        method: 'post',
		data:data
    })
}