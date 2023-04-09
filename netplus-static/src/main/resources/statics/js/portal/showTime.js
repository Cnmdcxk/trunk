// 倒计时
var day = 0
var hour = '00'
var min = '00'
var second = '00'

function countTime (timeSrt) {
    // 获取当前时间
    let date = new Date()
    let now = date.getTime()
    // 设置截止时间
    let endDate = new Date(timeSrt) // 需要倒计时的日期
    let end = endDate.getTime()
    // 时间差
    let leftTime = end - now
    // 定义变量 d,h,m,s保存倒计时的时间
    if (leftTime >= 0) {
        // 天
        let d = Math.floor(leftTime / 1000 / 60 / 60 / 24)
        day = d < 10 ? '0' + d : d
        // 时
        let h = Math.floor(leftTime / 1000 / 60 / 60 % 24)
        hour = h < 10 ? '0' + h : h
        // 分
        let m = Math.floor(leftTime / 1000 / 60 % 60)
        min = m < 10 ? '0' + m : m
        // 秒
        let s = Math.floor(leftTime / 1000 % 60)
        second = s < 10 ? '0' + s : s
    } else {
        day = 0
        hour = '00'
        min = '00'
        second = '00'
    }
    // 等于0的时候不调用
    if (Number(hour) === 0 && Number(day) === 0 && Number(min) === 0 && Number(second) === 0) {
        return
    } else {
        // 递归每秒调用countTime方法，显示动态时间效果,
        setTimeout(countTime, 1000)
    }
    return {  day,
        hour,
        min,
        second}
}

export default function countTimeFun(ck,t){
    setInterval((t)=>{
            let x = countTime(t);
            // console.log(x);
            ck(x)
        },
        1000,
        t
    )
}

