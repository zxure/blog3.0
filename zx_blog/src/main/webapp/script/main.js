window.onload=function(){

	goTop();
	//chgBackgroundColor();
};

var goTop = function(){
	var backTopBtn = document.getElementById("backToTop");
	var timer = null;
	window.onscroll = function(){
		var topPos = document.documentElement.scrollTop || document.body.scrollTop;
		//if(topPos <= clientHeight)
		if(topPos <= 0)
			backTopBtn.style.display = "none";
		else
			backTopBtn.style.display = "block";
	};
	
	var isUpBtnClick = false;

	backTopBtn.onclick = function(){
		if(isUpBtnClick) return;
		timer = setInterval(function(){
			isUpBtnClick = true;
			var topPos = document.documentElement.scrollTop || document.body.scrollTop;
			var offset = Math.floor(-topPos/6);
			document.documentElement.scrollTop = document.body.scrollTop = topPos + offset;
			if(topPos <= 0){
				isUpBtnClick = false;
				clearInterval(timer);
			}
		},30);
	};
};

/**
 * 发送ajax请求
 * formData 要发送的数据
 * reqestType 请求方式
 * url 请求地址
 */
function ajaxRequest(formData, requestType, url){
	return new Promise(function(resolve, reject){
		$.ajax({
			type: requestType,
			url: url,
			data: formData,
			success: function(data){resolve(data);},
			error: function(data){reject(data);}
		});
	});
}

//<!-- 多说公共JS代码 start (一个网页只需插入一次) -->
var duoshuoQuery = {short_name:"xuanzh"};
(function() {
	var ds = document.createElement('script');
	ds.type = 'text/javascript';ds.async = true;
	ds.src = (document.location.protocol == 'https:' ? 'https:' : 'http:') + '//static.duoshuo.com/embed.js';
	ds.charset = 'UTF-8';
	(document.getElementsByTagName('head')[0] 
	 || document.getElementsByTagName('body')[0]).appendChild(ds);
})();
//<!-- 多说公共JS代码 end -->
