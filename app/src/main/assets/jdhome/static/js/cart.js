// openid	是	string	用户openid
let openid = $_GET['openid']
let dealerid = $_GET['dealerid']
// cartids	是	string	购物车id (1,2,3)
let cartids = ''
// optionid	是	string	规格id （1,2,3）
let optionid = ''
// cartnum	是	string	商品数量 （1,2,3）
let c = []
let cartnum = ''


$(function () {
    c = getCart()
    console.log(c)
    $.each(c, function (i, e) {
        console.log(e)
        cartids += `${e.id},`
        optionid += `${e.suk},`
        cartnum += `${e.number},`
    })
    cartids = cartids.substr(0, cartids.length - 1);
    optionid = optionid.substr(0, optionid.length - 1);
    cartnum = cartnum.substr(0, cartnum.length - 1);
    getData()
})


function getData() {

    $.ajax({
        type: "POST",
        url: `${baseUrl}api.cart.get_cart`,
        dataType: "json",
        data: {
            openid,
            dealerid,
            cartids,
            optionid,
            cartnum,
        },
        async: true,
        success: function (data) {
            console.log(data);
            if (data.code == 200) {
                let str = ''
                $.each(data.data.list, function (i1, e1) {
                    $.each(e1.list, function (i2, e2) {
                        console.log(e2)
                        str += `
                        <div class="mui-row row">
                        <div class="mui-col-sm-1">
                            <div class="desc descCheck">
                                <div class="bg">
                                    <div class="checkbox" data-id="${e2.goodsid}" data-optionid="${e2.optionid}" ></div>
                                </div>
                            </div>
                        </div>
                        <div class="mui-col-sm-5">
                            <div class="desc descGoods">
                                <div class="icon"
                                    style="background:url(${e2.thumb}) no-repeat center;background-size: 110% auto;">
                                </div>
                                <div class="info">
                                    ${e2.title}
                                </div>
                                <div class="type">规格：${e2.optiontitle}</div>
                            </div>
                        </div>
                        <div class="mui-col-sm-2">
                            <div class="desc sprice">
                                <div class="bg">￥<text>${e2.marketprice}</text></div>
                            </div>
                        </div>
                        <div class="mui-col-sm-2">
                            <div class="desc number">
                                <div class="bg">
                                    <div class="num">
                                        <input type="button" data-tag="sub" value="-">
                                        <input type="number" data-id="${e2.goodsid}" data-totalmaxbuy="${e2.totalmaxbuy}" data-marketprice="${e2.marketprice}" value="${e2.total}" readonly />
                                        <input type="button" data-tag="plus" value="+">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="mui-col-sm-2">
                            <div class="desc price">
                                <div class="bg">￥${Number(e2.marketprice)*Number(e2.total)}</div>
                            </div>
                        </div>
                    </div>
                        `
                    })
                })

                $('.cart .list').html(str)
                setItem()
            }
        }
    })
}

// 商品设置
function setItem() {
    // 商品选择
    $('.descCheck .checkbox').click(function (e) {
        let t = $(this)
        let all = $('.bottom .left .checkbox.all')

        t.hasClass('check') ? t.removeClass('check') : t.addClass('check')

        $('.descCheck .checkbox').length == $('.descCheck .checkbox.check').length ? all.addClass('check') : all.removeClass('check')
        editTotalPrice()
    })
    // 修改数量
    $('.num input[type="button"]').click(function (e) {
        let sprice = $(this).parents('.row').find('.desc.price .bg')
        let tag = e.currentTarget.dataset.tag
        let num = $(this).parent().find('input[type="number"]')
        let n = Number(num.val())
        if (tag == 'plus') {
            if (Number(num.attr('data-totalmaxbuy')) > Number(num.val())) {
                num.val(n + 1)
            }
        } else if (tag == 'sub') {
            if (n <= 1)
                return
            num.val(n - 1)
        }
        let pri = `￥${Number(num.attr('data-marketprice'))*Number(num.val())}`
        sprice.html(pri)
        editTotalPrice()
    })
}

// 修改总价
function editTotalPrice() {
    let ck = $('.cart .list .desc.descCheck .checkbox.check')
    let num = ck.parents('.mui-row.row').find('.num input[type="number"]')
    let number = 0
    let total = 0
    $.each(num, function (i, e) {
        number += Number($(e).val())
        total += (Number($(e).attr('data-marketprice')) * Number($(e).val()))
    })
    $('.cart .bottom .right .inner').html(`已经选择 ${number} 件商品 合计 <b>￥${total}</b>`)
    console.log(`number: ${number}`)
    console.log(`total: ${total}`)
}

// 选中全部
$('.bottom .left .checkbox.all').click(function (e) {
    let t = $(this)
    let cbs = $('.descCheck .checkbox')
    if (t.hasClass('check')) {
        t.removeClass('check')
        cbs.removeClass('check')
    } else {
        t.addClass('check')
        cbs.addClass('check')
    }
    editTotalPrice()
})

// 删除选中
$('#removeSelect').click(function () {
    let cbs = $('.descCheck .checkbox.check')

    // (e.id == goods.id && e.suk == goods.suk) {
    $.each(cbs, function (i, e) {
        // console.log(`${$(e).attr('data-id')} -- ${$(e).attr('data-optionid')}`)
        delSelectCart({
            id: $(e).attr('data-id'),
            suk: $(e).attr('data-optionid')
        })
    })
   
    // console.log(`${}`)
    cbs.parents('.mui-row.row').remove()
    console.log('removeSelect')
})



// 删除所有
$('#removeAll').click(function () {
    console.log('removeAll')
    clearCart()
})

$('#submit').click(function () {
    console.log('submit')
})