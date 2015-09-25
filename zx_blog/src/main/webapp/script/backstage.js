;(function($){
	/**
	* 弹出 信息框插件
	* type 弹框类型
	* message 消息内容
	* parentElement 父元素
	*/
	$.fn.layerMessageBox = function(type, message){
		var a = this;
 		var self = $(this);

	  
	  Promise.resolve(self)
		.then(function(element){
			var newElement;
				newElement = $.createElement("section", "zx-notification-" + type);
			element.append(newElement);
			return newElement;
		})
		.then(function(element){
			
			Promise.resolve($.createElement("span", "zx-notification-" + type + "-icon"))
			.then(function(spanInfoIcon){
				spanInfoIcon.append($.createElement("i", "fa fa-exclamation-triangle"));
				return spanInfoIcon;
			}).then(function(spanInfoIcon){
				element.append(spanInfoIcon);
				return $.createElement("span", "zx-notification-" + type + "-message");
			}).then(function(spanInfoMessage){
				spanInfoMessage.text(message);
				return spanInfoMessage;
			}).then(function(spanInfoMessage){
				element.append(spanInfoMessage);
				return $.createElement("button", "zx-btn-close");
			}).then(function(spanBtn){
				spanBtn.append( $.createElement("i", "fa fa-times-circle"));
				return spanBtn;
			}).then(function(spanBtn){
				element.append(spanBtn);
				spanBtn.bind("click", function(){
					element.remove();
				});
			});
		});
		
/*		var newElement = $("<section class=\"zx-notification-error\">" +
						"<span class=\"zx-notification-error-icon\"><i class=\"fa fa-exclamation-triangle\"></i></span>" +
						"<span class=\"zx-notification-error-message\">" + message + "</span>" +
						"<button class=\"zx-btn-close\"><i class=\"fa fa-times-circle\"></i></button>" +
					"</section>");
		$(this).append(newElement);*/
	};

	//根据标签创建一个元素
	var C = function(elementName){
		return document.createElement(elementName);
	};

	/**
		根据标签 和 指定的 css  和 id 创建一个元素
	*/
	$.createElement = function(elementName, className, idName){
		var ele = $(C(elementName));
		ele.addClass(className);
		idName ? ele.attr("id", idName) : null;
		return ele;
	};
})(jQuery);

//登录页面表单验证
;(function(){
	$("#loginForm").validate({
		rules : {
			userName : {
				required : true,
				minlength : 5,
				maxlength : 20
			},
			password : {
				required : true,
				minlength : 5,
				maxlength : 20
			}
		},
		messages : {
			userName : {
				required : "用户名不能为空",
				minlength : "用户名长度至少为5为",
				maxlength : "用户名长度最多为20为"
			},
			password : {
				required : "密码不能为空",
				minlength : "密码长度至少为5为",
				maxlength : "密码长度最多为20为"
			}
		},
		showErrors: function(errorMap, errorList){
			$("#info_container").empty();
			for(var name in errorMap)
				$("#info_container").layerMessageBox("error", errorMap[name]);
		},
		onfocusout: false,
		onkeyup : false,
	});
})();

/***页面头部  弹出菜单的现实与关闭事件 ***/
;(function(){
	var isShow = false;
	$("#zx-user-toggle-menu").bind('click', function(){
		var container = $("#zx-user-toggle-menu-container");
		if(isShow){
			container.css("display", "none");
			isShow = false;
		} else {
			container.css("display", "block");
			isShow = true;
		}
	});
})();

/***设置页面 逻辑 ***/
;(function(){
	/**类别设置页面， 修改按钮*/
	$("button[id^='category-modify-']").one('click', clickCategoryModifyBtnFirst);
	//删除按钮第一次点击
	function clickCategoryModifyBtnFirst(){
		var _$self_ = $(this);
		var idNum = this.idNum = getIdNum(_$self_.attr("id"));
		$("#category-input-name-" + idNum).removeAttr("disabled");
		this.oldContent = _$self_.html();
		_$self_.addClass("button-clicked-first");
		var newContent = "<i class='fa fa-save'></i> 保存"; 
		_$self_.html(newContent);
		_$self_.one('click', clickCategoryModifyBtnSecond);
	}
	//删除按钮第二次点击
	function clickCategoryModifyBtnSecond(){
		var _self_ = this;
		var _$self_ = $(this);
		//发送 ajax 请求
		ajaxRequest({'categoryId':_self_.idNum, "categoryName":$("#category-input-name-" + _self_.idNum).val()}, "post", "/zx_blog/admin/category/update")
		.then(function(result){
			if(result.msgCode == 0){
				$("#info_container").layerMessageBox("info", result.message);
				return true;
			}else{
				$("#info_container").layerMessageBox("error", result.message);
				return false;
			}
		},function(data){
			$("#info_container").layerMessageBox("error", "请求异常");
			return false;
		}).then(function(optState){
			if(optState){
				$("#category-input-name-" + _self_.idNum).attr("disabled", "disabled");
				_$self_.removeClass("button-clicked-first");
				_$self_.html(_self_.oldContent);
				_$self_.one('click', clickCategoryModifyBtnFirst);
			} else {
				_$self_.one('click', clickCategoryModifyBtnSecond);
			}
		});
	}
	
	/**类别设置页面， 删除按钮*/
	$("button[id^='category-delete-']").bind('click', clickCategoryDeleteBtn);
	function clickCategoryDeleteBtn(){
		var _$self_ = $(this);
		var idNum = getIdNum(_$self_.attr("id"));
		popConfirmLayer("zx-mask-layer").then(
			function(){
				ajaxRequest({'categoryId':idNum}, "post", "/zx_blog/admin/category/delete")
				.then(
					function(result){
						if(result.msgCode == 0){
							$("#info_container").layerMessageBox("info", result.message);
							return true;
						}else{
							$("#info_container").layerMessageBox("error", result.message);
							return false;
						}
					},
					function(result){
						$("#info_container").layerMessageBox("error", "请求异常");
						return false;
					}
				)
				.then(function(optState){
					if(optState){
						$("#category-delete-" + idNum).parent().parent().remove();
					}
				});
			},
			function(){
				$("#info_container").layerMessageBox("info", "已取消删除");
			}	
		);
	}
	
	/**类别设置页面， 新增类别按钮*/
	$("#category-add").bind('click', clickCategoryAddBtn);
	function clickCategoryAddBtn(){
		ajaxRequest({'categoryName':$("#category-add-input-name").val()}, "post", "/zx_blog/admin/category/add")
		.then(
			function(result){
				if(result.msgCode == 0){
					//插入到文档流
					var domStr = 
					"<div class=\"zx-setting-content-item\">" + 
						"<div class=\"zx-setting-content-input-holder\">" + 
							"<span class=\"zx-setting-content-input\">" + 
								"<input type=\"text\" name=\"categoryName\" value=\""+ $("#category-add-input-name").val() +"\" disabled=\"disabled\" id=\"category-input-name-"+ result.categoryId +"\">" +
							"</span>" + 
						"</div>" +
						"<span class=\"zx-setting-btn-holder\">" +
							"<button class=\"zx-btn zx-btn-green\" title=\"修改\" id=\"category-modify-"+ result.categoryId +"\"><i class=\"fa fa-pencil\"></i> 修改</button>" +
						"</span>" + 
						"<span class=\"zx-setting-btn-holder\">" +
								"<button class=\"zx-btn zx-btn-red\" title=\"删除\" id=\"category-delete-"+ result.categoryId +"\"><i class=\"fa fa-remove\"></i> 删除</button>" +
						"</span>" + 
					"</div>";
					$(".zx-setting-content-item").last().before($(domStr));
					//绑定事件
					$("#category-modify-" + result.categoryId).one('click', clickCategoryModifyBtnFirst);
					$("#category-delete-" + result.categoryId).bind('click', clickCategoryDeleteBtn);
					$("#category-add-input-name").val("");
					$("#info_container").layerMessageBox("info", result.message);
				}else{
					$("#info_container").layerMessageBox("error", result.message);
				}
			},
			function(result){
				$("#info_container").layerMessageBox("error", "请求异常");
				return false;
			}
		);
	}
	
	
	function getIdNum(idStr){
		var arr = idStr.split("-");
		return arr[arr.length - 1];
	}
	
	/**
	 * 发送ajax请求
	 * formData 要发送的数据
	 * reqestType 请求方式
	 * url 请求地址
	 */
	function ajaxRequest(formData, reqestType, url){
		return new Promise(function(resolve, reject){
			$.ajax({
				type: reqestType,
				url: url,
				data: formData,
				success: function(data){resolve(data);},
				error: function(data){reject(data);}
			});
		});
	}
	
	function popConfirmLayer(elementId){
		return new Promise(function(resolve, reject){
			var maskLayer = $("#" + elementId);
			maskLayer.css("display", "block");
			$("#confirm-btn").one("click", function(){
				maskLayer.css("display", "none");
				resolve();
			});
			$("#cancel-btn").one("click", function(){
				maskLayer.css("display", "none");
				reject();
			});
		});
	}
})();
