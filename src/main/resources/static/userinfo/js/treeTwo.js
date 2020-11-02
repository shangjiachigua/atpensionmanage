// 档案管理-搜索框
$(function() {
	/* $('.tree-one ul').hide(); */
	$('.tree-one div.tree-children div').click(function() {
		$('.tree-one div.tree-children div').removeClass("current");
		$(this).addClass('current');
		$(this).parent('div.tree-children').nextAll('ul').slideToggle(300);
		if ($(this).find('img').attr('src') == 'img/icon_select_up.png') {
			$(this).find('img').attr('src', 'img/icon_select_down.png');
			//$(this).parent().siblings().find('img').attr('src', 'img/icon_select_up.png')
			$('.tree-one div.tree-children div').removeClass("current");
		} else {
			$(this).find('img').attr('src', 'img/icon_select_up.png');
		}
	})
	$('.add-mess-child .tree-con-box').hide();
	$('.add-mess-form-tree').click(function(){
		$(this).next().slideToggle();
	})
})