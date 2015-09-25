define(['jquery', 'jqueryUI', 'widget'], function($, $UI, widget){
	function Layer(){
		this.cfg = {
			title: '提示',			//标题
			content: '',			//内容
			hasMask: true,			//是否是模态
			skinClassName: null,	//皮肤类名
			hasClostBtn: true,		//标题右侧是否有关闭按钮
			handler4CloseBtn: null,	//标题右侧关闭按钮的回掉函数
			isDraggable: false,		//是否可拖动
			dragHandle: null,		//拖动触发区域
			container: null,		//父容器
			hasTitle: true,			//是否有标题区域
			hasContent: true,		//是否有内容区域
			hasFooter: true,		//是否有 footer区域
			//alert弹框
			text4AlertBtn: '确定',
			handler4AlertBtn: null,
			//confirm 弹框
			text4ConfirmBtn: '确定',
			handler4ConfirmBtn: null,
			text4CancelBtn: '取消',
			handler4CancelBtn: null,
			//prompt 弹框
			maxLength4PromptInput: 10,
			defaultValue4PromptInput: '',
			isPromptInputPassword: false,
			text4PromptBtn: '确定',
			handler4PromptBtn: null,
		};
	}
	
	Layer.prototype = $.extend({}, new widget.Widget(), {
		renderUI: function(){
			var footerContent = "";
			switch(this.cfg.winType){
			case 'alert': 
				footerContent = '<button type="button" class="layer-alertBtn">' + this.cfg.text4AlertBtn + '</button>';
				break;
			case 'confirm':
				footerContent = '<button type="button" class="zx-btn zx-btn-green zx-btn-confirm layer-confirmBtn">' + this.cfg.text4AlertBtn + '</button>'+
								'<button type="button" class="zx-btn zx-btn-red zx-btn-cancel layer-cancelBtn">' + this.cfg.text4CancelBtn + '</button>';
				break;
			case 'prompt':
				this.cfg.content += 
					'<p><input type="'+ (this.cfg.isPromptInputPassword ? 'password' : 'text') +'" value="' +
					this.cfg.defaultValue4PromptInput+ '" maxLength="'+ this.cfg.maxLength4PromptInput +'"></p>';
				footerContent = '<button type="button" class="layer-promptBtn">' + this.cfg.text4PromptBtn + '</button>'+
								'<button type="button" class="layer-cancelBtn">' + this.cfg.text4CancelBtn + '</button>';
				break;
			}
			
			this.boundingBox = $('<div class="layer-boundingBox"></div>');
			
			if(this.cfg.hasTitle)
				this.boundingBox.append('<div class="layer-header">'+ this.cfg.title +'</div>');
			if(this.cfg.hasContent)
				this.boundingBox.append('<div class="layer-body">' + this.cfg.content + '</div>');
			if(this.cfg.hasFooter)
				this.boundingBox.append('<div class="layer-footer">' + footerContent + '</div>');
			
			//遮罩层
			if(this.cfg.hasMask){
				this._mask = $('<div class="layer-mask"></div>');
				this._mask.appendTo('body');
			}
			
			//标题右侧关闭按钮
			if(this.cfg.hasClostBtn){
				$('<span class="layer-closeBtn"><i class="fa fa-times-circle"></i></span>').appendTo(this.boundingBox);
			}
		},
		
		bindUI: function(){
			var self = this;
			//事件绑定，触发
			this.boundingBox.delegate('.layer-closeBtn', 'click', function(){
				self.fire('close');
				self.destroy();
			}).delegate('.layer-alertBtn', 'click', function(){
				self.fire('alert');
				self.destroy();
			}).delegate('.layer-confirmBtn', 'click', function(){
				self.fire('confirm');
				self.destroy();
			}).delegate('.layer-promptBtn', 'click', function(){
				self.fire('prompt');
				self.destroy();
			}).delegate('.layer-cancelBtn', 'click', function(){
				self.fire('cancel');
				self.destroy();
			});
			//事件监听
			if(this.cfg.hasClostBtn && this.cfg.handler4CloseBtn){
				this.on('close', this.cfg.handler4CloseBtn);
			}
			//alert 弹框确定按钮
			if(this.cfg.hand4AlertBtn)
				this.on('alert', this.cfg.handler4AlertBtn);
			//confirm 弹框 按钮
			if(this.cfg.handler4ConfirmBtn)
				this.on('confirm', this.cfg.handler4ConfirmBtn);
			//prompt 弹框 按钮
			if(this.cfg.handler4PromptBtn)
				this.on('prompt', this.cfg.handler4PromptBtn);
			//confirm 和 prompt 弹框 取消 按钮
			if(this.cfg.handler4CancelBtn)
				this.on('cancel', this.cfg.handler4CancelBtn);
		},
		
		initDomAttribute: function(){
			//设置样式
			var style = {};
			if(this.cfg.width){
				style.width =  this.cfg.width + 'px';
				if(this.cfg.left == undefined)
					style.left = (window.innerWidth - this.cfg.width)/2  + 'px';
			}
			if(this.cfg.height){
				style.height = this.cfg.height + 'px';
				if(this.cfg.top == undefined)
					style.top = (window.innerHeight - this.cfg.height)/2 + 'px';
			}
			if(this.cfg.left != undefined){
				style.left = this.cfg.left + 'px';
			}
			if(this.cfg.top != undefined){
				style.top = this.cfg.top + 'px';
			}
			this.boundingBox.css(style);
			
			//设置皮肤
			if(this.cfg.skinClassName)
				this.boundingBox.addClass(this.cfg.skinClassName);
			//设置拖动
			if(this.cfg.isDraggable){
				if(this.cfg.dragHandle){
					this.boundingBox.find(this.cfg.dragHandle).css({
						cursor: 'move',
					});
					this.boundingBox.draggable({handle: this.cfg.dragHandle});
				} else {
					this.boundingBox.css({
						cursor: 'move',
					});
					this.boundingBox.draggable();
				}
			}
		},
		
		destructor: function(){
			this._mask && this._mask.remove();
		},
		alert : function(cfg){
			this.cfg = $.extend(this.cfg, cfg, {winType: 'alert'});
			this.render();
			return this;
		}, 
		confirm : function(cfg){
			this.cfg = $.extend(this.cfg, cfg, {winType: 'confirm'});
			this.render();
			return this;
		},
		prompt: function(cfg){
			this.cfg = $.extend(this.cfg, cfg, {winType: 'prompt'});
			this.render();
			return this;
		},
	});
	
	return {Layer: Layer};
});