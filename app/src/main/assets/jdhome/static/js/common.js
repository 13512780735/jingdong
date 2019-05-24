let baseUrl = 'https://wx.jddengju.com/app/index.php?i=1&c=entry&r=dealer.'
let storage = window.localStorage;

// 左浮动 begin ------------------------
$('#floatRightBox').click(function (e) {
    e.stopPropagation();
})
// 查询
$('#floatRightBox #searchBtn').click(function (e) {
    console.log('searchBtn')
    toggle('#searchBox', 'on')
})
// 购物车
$('#floatRightBox #cartBtn').click(function (e) {
    console.log('cartBtn')
    appOpenCart()

})
// 分类
$('#floatRightBox #catalogBtn').click(function (e) {
    console.log('catalogBtn')
})
// 回到顶部
$('#floatRightBox #topBtn').click(function (e) {
    console.log('topBtn')
    $('html,body').animate({
        scrollTop: '0px'
    }, 500);
})

// 左浮动 end ------------------------



// 搜索框 begin ------------------------
$('#searchBox').click(function (e) {
    e.stopPropagation();
    toggle('#searchBox', 'on')
})
$('#searchBox .inner').click(function (e) {
    e.stopPropagation();
})
$('#searchBox .inner .clostBtn').click(function (e) {
    toggle('#searchBox', 'on')
})
// 搜索框 end ------------------------




// 商品分类 end ------------------------
$('#catalogBox .group .gitem .list span').click(function () {
    toggle($(this)[0], 'on')
})
$('#catalogBox').click(function (e) {
    e.stopPropagation();
    toggle('#catalogBox', 'in')
})
$('#catalogBox .inner').click(function (e) {
    e.stopPropagation();
})
// 重置
$('#catalogBox .inner .btns .btn.reset ').click(function () {
    $('#catalogBox .group .gitem .list span').removeClass('on')
})
// 确定
$('#catalogBox .inner .btns .btn.confirm ').click(function () {

})
$('#catalogBox .header i').click(function () {
    toggle('#catalogBox.in', 'in')
})
// 商品分类 end ------------------------











// 通用 begin ------------------------
$(function () {
    setCartNumber()
})

function toggle(e, c) {
    let p = $(e)
    p.hasClass(c) ? p.removeClass(c) : p.addClass(c)
}

let $_GET = (function () {
    var url = window.document.location.href.toString();
    var u = url.split("?");
    if (typeof (u[1]) == "string") {
        u = u[1].split("&");
        var get = {};
        for (var i in u) {
            var j = u[i].split("=");
            get[j[0]] = j[1];
        }
        return get;
    } else {
        return {};
    }
})();

// 获取购物车
function getCart() {
    return JSON.parse(localStorage.getItem('cart')) || []
}
// 添加购物车
function addCart(goods) {
    let is = false
    let c = getCart()
    $.each(c, function (i, e) {
        console.log(e)
        if (e.id == goods.id && e.suk == goods.suk) {
            c[i].suk = goods.suk
            c[i].number = Number(c[i].number) + Number(goods.number)
            c[i].select = true
            is = true
            return
        }
    })
    is ? {} : c.unshift(goods)
    storage.setItem('cart', JSON.stringify(c))
    setCartNumber()
}
// 删除选中
function delSelectCart(goods) {
    let c = getCart()
    console.log(c)
    console.log('********************')
    $.each(c, function (i, e) {
        if (e.id == goods.id && e.suk == goods.suk) {
            c.splice(i, 1)
            console.log(c)
            return false
        }
    })
    storage.setItem('cart', JSON.stringify(c))
}
// 清空购物车
function clearCart() {
    storage.setItem('cart', JSON.stringify([]))
}
// 设置购物车数字
function setCartNumber() {
    let c = getCart()
    let n = 0
    $.each(c, function (i, e) {
        // console.log(e)
        n = Number(e.number) + Number(n)
    })
    $('#floatRightBox li b#cartnumber').html(n)

}
// 通用 end ------------------------










// app方法 begin ------------------------
// 打开购物车
function appOpenCart() {
    app.openCart(JSON.stringify({

    }))
}
function appOpenDesc() {
    app.openCart(JSON.stringify({

    }))
}

// app方法 end ------------------------