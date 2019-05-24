// openid	是	string	用户openid
let openid = $_GET['openid']
let dealerid = $_GET['dealerid']

mui.init();

$(function () {

    getData()

    var gallery = mui('.mui-slider');
    gallery.slider({
        interval: 1000 //自动轮播周期，若为0则不自动播放，默认为0；
    });


})


function getData() {

    $.ajax({
        type: "POST",
        url: `${baseUrl}api.home`,
        dataType: "json",
        data: {
            openid,
            dealerid,
        },
        async: true,
        success: function (data) {
            console.log(data);
            if (data.code == 200) {

                let listStr = ''
                $.each(data.data.categorylist, function (i1, e1) {
                    // console.log(e1)
                    listStr += `<div class="list">
                    <div class="ltitle">${e1.title} <span class="more">MORE>></span></div>`
                    $.each(e1.list, function (i2, e2) {
                        listStr += `
                        <div class="item">
                        <div class="bg">
                            <div class="icon"
                                style="background:url(${e2.thumb}) no-repeat center;background-size:110% auto;">
                            </div>
                            <div class="title">
                                <div class="t">
                                    ${e2.title}</div>
                            </div>
                            <div class="price">
                                <div class="group">
                                    <div class="p1">￥${e2.productprice}</div>
                                    <div class="p2">立即抢购</div>
                                </div>
                            </div>
                        </div>
                    </div>
                        `
                    })
                    listStr += '</div>'
                })
                $('.catalogs').html(listStr)


                let jsStr = ''
                let js = [data.data.hot, data.data.new, data.data.recommand]
                console.log(js)
                $.each(js, function (i1, e1) {
                    jsStr += `
                    <div class="mui-col-sm-4">
					<div class="in">
						<div class="mui-content list">
							<div class="mui-row">
								<div class="mui-col-sm-6 ">
									<div class="ltitle">
										${e1.title}
									</div>
								</div>
							</div>
							<div class="mui-row">
                                `
                    $.each(e1.list.list, function (i2, e2) {
                        jsStr += `
                    <div class="mui-col-sm-6">
                    <div class="item">
                        <div class="bg">
                            <div class="icon"
                                style="background:url(${e2.thumb}) no-repeat center;background-size:110% auto;;">
                            </div>
                            <div class="title">
                                <div class="t">
                                    ${e2.title}</div>
                            </div>
                            <div class="price">
                                <div class="group">
                                    <div class="p1">￥${e2.productprice}</div>
                                    <div class="p2">立即抢购</div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                    `
                    })
                    jsStr += `
							</div>
						</div>
					</div>
				</div>
                    `
                })

                $('.goodsGroup .mui-row').html(jsStr)



                let msliderStr = ''
                $.each(data.data.thumbs, function (i, e) {
                    msliderStr += `
                    <div class="mui-slider-item mui-slider-item-duplicate">
                    <div class="item"
                        style="background:url(${e}) no-repeat center;background-size: auto 110%;">
                    </div>
                </div>
                    `
                })
                $('#mslider').html(msliderStr)


                let discountStr = ''
                $('.index .top .left .ltitle span').text(data.data.discount.title)
                $.each(data.data.discount.list.list, function (i,e) { 
                    discountStr += `
                    <div class="item">
                    <div class="icon"
                        style="background:url(${e.thumb}) no-repeat center;background-size: 110% auto;">
                    </div>
                    <div class="title">
                        <div class="t">
                            ${e.title}</div>
                    </div>
                    <div class="price">
                        <div class="group">
                            <div class="p1">￥${e.marketprice}</div>
                            <div class="p2">￥${e.productprice}</div>
                        </div>
                    </div>
                </div>
                    `
                })
                $('.index .top .left').append(discountStr)

            }
        }
    })
}


$('.index .top .right .btns ul li span.w').click(function () {
    toggle('#catalogBox', 'in')
})