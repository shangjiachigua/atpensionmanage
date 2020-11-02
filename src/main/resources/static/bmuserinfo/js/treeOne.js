// 组织架构
$(function() {
	/* $('.tree-one ul').hide(); */
	$('.tree-one div.tree-children div p.tree-arrow').click(function() {
		$(this).parent().parents('div.tree-children').nextAll('ul').slideToggle(300);
		if ($(this).find('img').attr('src') == 'img/icon_up.png') {
			$(this).find('img').attr('src', 'img/icon_down.png');
			//$(this).parent().siblings().find('img').attr('src', 'img/icon_up.png')
		} else {
			$(this).find('img').attr('src', 'img/icon_up.png')
		}
	})
	
	$('.tree-hide').click(function(){
		$('.layui-tips-main').fadeIn()
	})
	$('.layui-icon-close').click(function() {
		$('.layui-tips-main').fadeOut();
	})
})	