let = mySwiper = null
let swiperIndex = 0
let imgs = []
// 商品id
let id = $_GET['id']
// 规格
let suk = ''
// 数量
let number = 1

$(function () {

    getData()
})

function getData() {



    $.ajax({
        type: "POST",
        url: `${baseUrl}api.goods.get_details`,
        dataType: "json",
        data: {
            id,
        },
        async: true,
        success: function (data) {
            console.log(data)

            // 伦播
            imgs = data.goods.thumbs

            // 标题
            $('.desc #info .title').text(data.goods.title)
            $('.desc #info .sp').text(data.goods.subtitle)
            $('.desc #info .price.p1 text').text(`￥${data.goods.productprice}`)

            // 价格
            if (data.goods.minprice == data.goods.maxprice) {
                $('.desc #info .price.p2 text').text(`￥${data.goods.minprice}`)
            } else {
                $('.desc #info .price.p2 text').text(`￥${data.goods.minprice} ~ ${data.goods.maxprice}`)
            }

            // 规格
            if (data.sales.skus) {
                let sku = `<div class="type">
				规格：`

                $.each(data.sales.skus, function (i1, e1) {
                    let name = ''
                    $.each(e1.attributes, function (i2, e2) {
                        name += `${e2.value} `
                    })

                    sku += `
                <div class="item ${e1.stockQuantity<=0?'disable':''}" data-id="${e1.id}" data-originPrice="${e1.originPrice}" data-stockQuantity="${e1.stockQuantity}">
                <div class="ds"></div> 
					<div class="icon"
						style="background: url(${e1.image}) no-repeat center;background-size: auto 100%; ">
					</div>
					<span>${name}</span>
				</div>
                    `
                })

                sku += '</div>'
                $('.desc #info .type').html(sku)
                setguige()
            }

            // 详情
            $('#morebox .inner .info .if.if1').html(data.goods.content)
            // 参数
            let canshu = ''
            $.each(data.goods.params, function (i, e) {
                console.log(e)
                canshu += `
                    <li>
                        <div class="d1">${e.title}</div>
                        <div class="d2">${e.value}</div>
                    </li>
                `
            })
            $('#morebox .inner .info .if.if2').html(canshu)


            // 库存
            if (data.goods.total > 0) {
                $('.desc .num').text(`有货 库存${data.goods.total}件  `)
            } else {
                $('.desc .num').text(`没货`)
            }


            initSwiper()
        }
    })

}

// 详情背景
$('#morebox').click(function (e) {
    e.stopPropagation();
    toggle('#morebox', 'in')
})
// 详情中心
$('#morebox .inner').click(function (e) {
    e.stopPropagation();
})
// 详情按钮点击
$('.desc .number .bt.bt1').click(function () {
    toggle('#morebox', 'in')
})
// 添加购物车点击
$('.desc .number .bt.bt2').click(function () {
    // let  c = getCart();
    // let is=false
    // let l = {
    //     id,
    //     suk,
    //     number
    // }
    addCart({
        id,
        suk,
        number
    })
})
// 详情关闭按钮
$('#morebox .inner .header i').click(function () {
    toggle('#morebox', 'in')
})

$('.desc .number .box .right .btn').click(function () {
    let num = $('.desc .number .box input#number')
    if ($(this).hasClass('plus')) {
        num.val(Number(num.val()) + 1)
    } else if ($(this).hasClass('sub')) {
        if (num.val() <= 1) {
            return
        }
        num.val(Number(num.val()) - 1)
    }
    number = num.val()
})


// 规格选择
function setguige() {
    $('.desc .type .item').click(function () {
        $('.desc .number .box input#number').val(1)
        if ($(this).hasClass('disable')) {
            return
        }
        $('.desc #info .price.p2 text').text(`￥${$(this).attr('data-originPrice')}`)

        if ($(this).attr('data-stockQuantity') > 0) {
            $('.desc .num').text(`有货 库存${$(this).attr('data-stockQuantity')}件  `)
        } else {
            $('.desc .num').text(`没货`)
        }

        suk = $(this).attr('data-id')

        $('.desc .type .item').removeClass('on')
        $(this).addClass('on')
    })
}


// 切换详情
$('#morebox .inner .header span').click(function () {
    let sp = $('#morebox .inner .header span')

    sp.removeClass('in')
    $(this).addClass('in')
    $('#morebox .inner .info .if').removeClass('on')
    $(`#morebox .inner .info .if.${$(this).attr('data-tag')}`).addClass('on')
})

// 初始化轮播
function initSwiper() {
    let dc = $('.desc')
    let sw = $('#swiper')
    let wp = sw.find('.swiper-wrapper')
    let lg = sw.find('.imgGroup')
    let wpStr = ''
    let lgStr = ''
    sw.width(sw.height())
    dc.css('padding-left', `${sw.height()}px`)
    $.each(imgs, function (i, e) {
        let idx = i
        wpStr += `
        <div class="swiper-slide" style="background: url(${e}) no-repeat center;background-size: auto 100%; "></div>
        `
        lgStr += `
        <div class="item ${i == swiperIndex?'on':''}" index="${idx}" style="background: url(${e}) no-repeat center;background-size: auto 100%;"></div>
        `
    })
    wp.html(wpStr)
    lg.html(lgStr)
    mySwiper = new Swiper('#swiper', {
        autoplay: {
            delay: 3000, //1秒切换一次
            stopOnLastSlide: false,
            disableOnInteraction: false,
        },
        // direction: 'vertical', // 垂直切换选项
        loop: true, // 循环模式选项

        // 如果需要分页器
        // pagination: {
        //     el: '.swiper-pagination',
        // },
        on: {
            slideChange: function () {
                if (mySwiper) {
                    swiperIndex = mySwiper.activeIndex
                    let idx = swiperIndex - 1
                    let its = $('#swiper .imgGroup .item')
                    idx >= its.length ? idx = 0 : {}
                    idx <= -1 ? idx = its.length - 1 : {}
                    its.removeClass('on')
                    $(its[idx]).addClass('on')
                    // console.log(idx);
                }
            },
        },
    })

    $('#swiper .imgGroup .item').click(function (e) {
        e.stopPropagation()
        let idx = Number($(this).attr('index'))
        $('#swiper .imgGroup .item').removeClass('on')
        $(this).addClass('on')
        // console.log(`idx ${idx}`)
        mySwiper.slideToLoop(idx, 1000);
    })

}