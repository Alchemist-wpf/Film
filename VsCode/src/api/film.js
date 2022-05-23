import service from '../utils/request'

//首页轮播图
export function ListAllPoster() {
    return service({
        url: '/api/poster',
        method: 'get',
    })
}
// export function ListAllPoster(status) {
//     return service({
//         url: '/api/poster?status='+status,
//         method: 'get',
//     })
// }

//首页热播电影查询 //film页面查询所有电影
export function ListAllFilm() {
    return service({
        url: '/api/film',
        method: 'get',
    })
}
//首页热门榜单
export function ListTopFilm() {
    return service({
        url: '/api/film/hot/limit',
        method: 'get',
    })
}
//film页面查询不同类型电影 实际

export function FilmByRegionAndType(region,type) {
    return service({
        url: '/api/film?region='+region+'&type='+type,
        method: 'get',
    })
}
// export function FilmByRegionAndType() {
//     return service({
//         url: '/api/film/region/type',
//         method: 'get',
//     })
// }

//电影评论
export function FilmCritic(fid) {
    return service({
        url: '/api/fe?id='+fid,
        method: 'get',

    })
}
//评分
export function FilmScore(data) {
    return service({
        url: '/api/fe',
        method: 'post',
        data:data

    })
}

//电影详情
// export function FilmDetail(id) {
//     return service({
//         url: '/api/film/'+id,
//         method: 'get',
//     })
// }

export function FilmDetail(fid) {
    return service({
        url: '/api/film/id?fid='+fid,
        method: 'get',
    })
}
// export function FilmDetail() {
//     return service({
//         url: '/api/film/id',
//         method: 'get',
//     })
// }
//电影排片
// export function FilmArrangement(id) {
//     return service({
//         url: '/api/arrangement/film/'+id,
//         method: 'get',
//     })
// }
export function FilmArrangement(fid) {
    return service({
        url: '/api/arrangement/film/fid?fid='+fid,
        method: 'get',
    })
}

//电影排片详情
// export function FilmArrangementDetail(id) {
//     return service({
//         url: '/api/arrangement/'+id,
//         method: 'get',
//     })
// }
export function FilmArrangementDetail(arrangementId) {
    return service({
        url: '/api/arrangement/id?arrangementId='+arrangementId,
        method: 'get',
    })
}
//电影排片座位详情
export function FilmSeat(id) {
    return service({
        url: '/api/arrangement/getSeats?arrangementId='+id,
        method: 'get',
    })
}

//加入购物车
export function AddCart(data) {
    return service({
        url: '/api/cart/add',
        method: 'post',
        data:data

    })
}