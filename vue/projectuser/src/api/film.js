import service from "@/utils/request";
import { FormItem } from "element-ui";

//查询轮播图  测试 2022-03-31 by pjy
export function ListAllPoster(){
    return service({
        url:"/api/poster",
        method:"get"
    })
}


// 热门榜单 实际接口 测试 2022-03-31 by pjy
export function ListHots(){
    return service({
        url:"/api/film/hot/limit",
        method:"get"
    })
}
// export function ListHots(){
//     return service({
//         url:"/api/film/hot/limit",
//         method:"get"
//     })
// }

//热播电影 测试 2022-03-31 by pjy
export function ListAllFilm(){
    return service({
        url:"/api/film",
        method:"get"
    })
}

//查询电影列表类型和地区实际接口 测试 2022-03-31 by pjy
export function FindFilmByRegionAndType(region,type){
    return service({
        url: '/api/film/select?region='+region+'&type='+type,
        method:"get"
    })
}

// export function FindFilmByRegionAndType(){
//     return service({
//         url:"/film/region/type",
//         method:"get"
//     })
// }

// 查询电影详情 测试 2022-04-01 by pjy
export function FindFilmById(id){
    return service({
        url:'/api/film/id?id=' + id,
        method:'get'
    })
}

// export function FindFilmById(){
//     return service({
//         url:'/api/film/id',
//         method:'get'
//     })
// }

//评分 测试 2022-04-01 by pjy
export function AddFilmEvaluate(data){
    return service({
        url:'/api/fe',
        method:'post',
        data:data
    })
}

//查询电影评论 测试 2022-04-01 by pjy
// export function FindFilmEvaluateByFId(id){
//     return service({
//         url:'/fe?fid=' + id,
//         method:'get'
//     })
// }
export function FindFilmEvaluate(fid){
    return service({
        url:'/api/fe?id='+fid,
        method:'get'
    })
}

//查询排片信息  测试 2022-04-01 by pjy
// export function FindArrangementById(id){
//     return service({
//         url:'/arrangement' + id,
//         method:'get'
//     })
// }
export function FindArrangementById(fid){
    return service({
        url:'/api/film/arrangement/fid?fid='+fid,
        method:'get'
    })
}

//查询排片座位情况  测试 2022-04-01 by pjy
// export function GetArrangementSeats(id){
//     return service({
//         url:'/arrangement/getSeats' + id,
//         method:'get'
//     })
// }
export function GetArrangementSeats(arrangementId){
    return service({
        url:'api/film/arrangement/id?arrangementId='+arrangementId,
        method:'get'
    })
}


export function GetSelectedSeats(arrangementId){
    return service({
        url:'api/film/arrangement/getSeats?arrangementId='+arrangementId,
        method:'get'
    })
}

export function AddCart(data) {
    return service({
        url: '/api/cart/add',
        method: 'post',
        data:data
    })
}

export function AddFilm(data) {
    return service({
        url: '/api/film/add',
        method: 'post',
        data:data
    })
}

export function AddPoster(data) {
    return service({
        url: '/api/poster/add',
        method: 'post',
        data:data
    })
}

export function AllPoster(){
    return service({
        url:"/api/poster/all",
        method:"get"
    })
}

export function DelPoster(data) {
    return service({
        url: '/api/poster/delete',
        method: 'post',
        data:data
    })
}

export function UpdatePoster(data) {
    return service({
        url: '/api/poster/delete',
        method: 'post',
        data:data
    })
}

export function deleteAllPoster() { 
    return service({
        url: '/api/film/all/delete',
        method: 'post',
    })
}

export function AllArrangement(){
    return service({
        url:"/api/film/arrangement",
        method:"get"
    })
}

export function deleteArrangement(data) { 
    return service({
        url: '/api/film/arrangement/delete',
        method: 'post',
        data:data
    })
}

export function updateArrangement(data) { 
    return service({
        url: '/api/film/arrangement/update',
        method: 'post',
        data:data
    })
}

export function deleteFilm(data) { 
    return service({
        url: '/api/film/delete',
        method: 'post',
        data:data
    })
}

export function AddArrangement(data) { 
    return service({
        url: '/api/film/arrangement/add',
        method: 'post',
        data:data
    })
}

export function updateFilm(data) { 
    return service({
        url: '/api/film/update',
        method: 'post',
        data:data
    })
}

export function updateHot(data) { 
    return service({
        url: '/api/film/hot/update',
        method: 'post',
        data:data
    })
}