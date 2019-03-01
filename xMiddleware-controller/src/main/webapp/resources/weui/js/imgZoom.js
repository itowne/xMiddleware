function imgZoom() {
	var imgs = [];
	var imgObj = $("img");// 这里改成相应的对象
	for (var i = 0; i < imgObj.length; i++) {
		imgs.push(imgObj.eq(i).attr('src'));
		imgObj.eq(i).click(function() {
			var nowImgurl = $(this).attr('src');
			WeixinJSBridge.invoke("imagePreview", {
				"urls" : imgs,
				"current" : nowImgurl
			});
		});
	}
}