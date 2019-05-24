$('#ziti').click(function () {
    toggle('.phone')
})
$('#kuaidi').click(function () {
    toggle('.qrcode')
})

$('.phone').click(function () {
    toggle('.phone')
})
$('.qrcode').click(function () {
    toggle('.qrcode')
})

$('.phone .inner').click(function (e) {
    e.stopPropagation()
})

$('#confirm').click(function () {
    toggle('.phone')
    toggle('.qrcode')
})


function toggle(e) {
    let p = $(e)
    p.hasClass('in') ? p.removeClass('in') : p.addClass('in')
}