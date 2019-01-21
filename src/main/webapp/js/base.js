var Global = {
	
	TIMEOUT : 3000,
	
	NETERROR : '网络异常，请稍候重试',

	/* 设置顶部导航高亮 */
	setNavActive : function(index) {
		$('#globalNav li.level_1').removeClass('active');
		$('#globalNav li.level_1:nth-child(' + index + ')').addClass('active');
	}
};