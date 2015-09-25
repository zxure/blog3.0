require.config({
	paths :{
		'layer' : 'ui-component/layer',
		'jquery' : 'jquery',
		'jqueryUI' : 'jquery-ui-min',
		'jqueryValid' : 'jquery.validate.min',
		'widget' : 'ui-component/widget',
		'ZeroClipboard' : 'ueditor/third-party/zeroclipboard/ZeroClipboard.min',
	}
});
require(['jquery', 'jqueryValid', 'layer', 'ZeroClipboard'], function($, $valid, L, ZeroClipboard){
	var Layer = L.Layer;
	//表单验证
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
					new Layer().alert(getErrorLayerConfig(errorMap[name]));
			},
			submitHandler: function(form){
				ajaxRequest({userName: $("#userName").val().trim(), password:  $("#password").val().trim(),}, "post", "/zx_blog/admin/login")
				.then(function(data){
					if(data.msgCode == -1){
						new Layer().alert(getErrorLayerConfig(data.errMsg));
						$("#userName").focus();
					}else if(data.msgCode == 1){
						window.location.href='/zx_blog/admin';
					}
				}, function(data){
					new Layer().alert(getErrorLayerConfig('登录异常！'));
				});
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
	
	/**文章编辑页面逻辑*/
	;(function(){
		//初始化编辑器
		window['ZeroClipboard']=ZeroClipboard;
		var editor = null;
		if(typeof UE != 'undefined'){
			editor = UE.getEditor("editor-container");
			editor.webBaseUrl = 'http://localhost/zx_blog/';
		}
		//表单验证
		$('#form-submit').bind('click', function(form){
			var form = $("#article-editor-form");
			var canSunmit = true;
			if($("#title").val().trim() == ''){
				new Layer().alert(getErrorLayerConfig('文章标题不能为空！'));
				canSunmit = false;
			}
			if(editor.getContentTxt().trim() == ''){
				new Layer().alert(getErrorLayerConfig('文章内容不能为空！'));
				canSunmit = false;
			}
			if(canSunmit == false)
				return ;
			var formType4Post = form.attr("data-formType") == 'postArticle';
			var url = (formType4Post == true ) ? '/zx_blog/admin/article/post' : '/zx_blog/admin/article/update' ;
			var requestParam = (formType4Post == true ) ? {
				categoryId: $("#categoryId").val().trim(),
				title: $("#title").val().trim(), 
				content: editor.getContent(),
			} : {
				categoryId: $("#categoryId").val().trim(),
				title: $("#title").val().trim(), 
				articleId: $("#article-delete-btn").attr("data-id").trim(),
				content: editor.getContent(),
			}
			ajaxRequest(requestParam, "post", url).then(function(data){
				if(formType4Post == true){
					form.attr("data-formType", "updateArticle");
					//$("input[name='newsletter']").attr("checked", true);
					var inputEle = $("input[name='title']")
					inputEle.removeClass("zx-editor-article-title");
					var delBtn = $('<button type="button" class="zx-btn zx-btn-red" data-id="'+ data.articleId +'" id="article-delete-btn">删除文章</button>');
					inputEle.after(delBtn);
					delBtn.bind('click', submitHandle);
					$('#form-submit').text("更新");
				}
				if(data.msgCode == 1){
					$(".zx-page-main").animate({scrollTop: '0px'}, 500);
					new Layer().alert(getInfoLayerConfig(data.message));
				}
			}, function(data){
				new Layer().alert(getErrorLayerConfig('请求异常！'));
			});
		});
		
		//文章删除按钮
		$("#article-delete-btn").bind('click', submitHandle);
		function submitHandle(){
			var self = this;
			new Layer().confirm({
				skinClassName: 'layer-skin-confirm',
				width: 500,
				height: 170,
				title: '系统提示',
				isDraggable: true,
				dragHandle: '.layer-header',
				content:'确定删除该文章吗？',
				hasClaseBtn: true,
				handler4CloseBtn: function(){
					new Layer().alert(getInfoLayerConfig('已取消删除！'));
				},
				text4ConfirmBtn: '确定',
				handler4ConfirmBtn: function(){
					ajaxRequest({'articleId':$(self).attr("data-id")}, "post", "/zx_blog/admin/article/delete")
					.then(
						function(result){
							if(result.msgCode == 1){
								new Layer().alert(getInfoLayerConfig(result.message));
								setTimeout(function(){window.location.href = '/zx_blog/admin';}, 1000)
							}
						},
						function(result){
							new Layer().alert(getErrorLayerConfig('请求异常！'));
						}
					);
				},
				text4CancelBtn: '取消',
				handler4CancelBtn: function(){
					new Layer().alert(getInfoLayerConfig('已取消删除！'));
				},
			});
		}
		
		//发布文章 按钮
		
		//更新文章按钮
		
		
	})();
	
	/***设置页面 逻辑 ***/
	;(function(){
		/**类别设置页面， 修改按钮*/
		$("button[id^='category-modify-']").one('click', clickCategoryModifyBtnFirst);
		//修改按钮第一次点击
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
		//修改按钮第二次点击
		function clickCategoryModifyBtnSecond(){
			var _self_ = this;
			var _$self_ = $(this);
			//发送 ajax 请求
			ajaxRequest({'categoryId':_self_.idNum, "categoryName":$("#category-input-name-" + _self_.idNum).val()}, "post", "/zx_blog/admin/category/update")
			.then(function(result){
				if(result.msgCode == 0){
					new Layer().alert(getInfoLayerConfig(result.message));
					return true;
				}else{
					new Layer().alert(getErrorLayerConfig(result.message));
					return false;
				}
			},function(data){
				new Layer().alert(getErrorLayerConfig("请求异常！"));
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
			new Layer().confirm({
				skinClassName: 'layer-skin-confirm',
				width: 550,
				height: 200,
				title: '系统提示',
				isDraggable: true,
				dragHandle: '.layer-header',
				content:'确定删除该类别吗？ <span>删除该类别会连同删除此类别下的所有文章!</span>',
				hasClaseBtn: true,
				handler4CloseBtn: function(){
					new Layer().alert(getInfoLayerConfig('已取消删除！'));
				},
				text4ConfirmBtn: '确定',
				handler4ConfirmBtn: function(){
					ajaxRequest({'categoryId':idNum}, "post", "/zx_blog/admin/category/delete")
					.then(
						function(result){
							if(result.msgCode == 0){
								new Layer().alert(getInfoLayerConfig(result.message));
								return true;
							}else{
								new Layer().alert(getErrorLayerConfig(result.message));
								return false;
							}
						},
						function(result){
							new Layer().alert(getErrorLayerConfig('请求异常！'));
							return false;
						}
					)
					.then(function(optState){
						if(optState){
							$("#category-delete-" + idNum).parent().parent().remove();
						}
					});
				},
				text4CancelBtn: '取消',
				handler4CancelBtn: function(){
					new Layer().alert(getInfoLayerConfig('已取消删除！'));
				},
			});
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
						new Layer().alert(getInfoLayerConfig(result.message));
					}else{
						new Layer().alert(getErrorLayerConfig(result.message));
					}
				},
				function(result){
					new Layer().alert(getErrorLayerConfig('请求异常！'));
					return false;
				}
			);
		}
		
		function getIdNum(idStr){
			var arr = idStr.split("-");
			return arr[arr.length - 1];
		}
	})();
	
	//拿到错误提示的配置
	function getErrorLayerConfig(content){
		return {
				skinClassName: 'layer-skin-error-info',
				height: 43,
				top: 0,
				left: 0,
				container: '#info_container',
				title: '<i class="fa fa-exclamation-triangle"></i>', 
				content: content,
				text4AlertBtn: '<i class="fa fa-times-circle"></i>',
				hasFooter: false,
				hasMask: false,
		};
	}
	//拿到正确提示的配置
	function getInfoLayerConfig(content){
		return {
			skinClassName: 'layer-skin-correct-info',
			height: 43,
			top: 0,
			left: 0,
			container: '#info_container',
			title: '<i class="fa fa-exclamation-triangle"></i>', 
			content: content,
			text4AlertBtn: '<i class="fa fa-times-circle"></i>',
			hasFooter: false,
			hasMask: false,
		};
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
});