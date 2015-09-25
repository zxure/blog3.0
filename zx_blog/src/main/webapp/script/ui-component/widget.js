define(['jquery'], function(){
	function Widget(){
		this.boundingBox = null;
	}
	
	Widget.prototype = {
		//注册事件
		on: function(type, handler){
				if(typeof this.handlers[type] == "undefined"){
					this.handlers[type] = [];
				}
				this.handlers[type].push(handler);
				return this;
		},
		
		//触发事件
		fire: function(type, args){
			if(this.handlers[type] instanceof Array){
				var handlers = this.handlers[type];
				for(var i = 0, len = handlers.length;i < len; i++){
					handlers[i](args);
				}
			}
			return this;
		},
		//接口 添加 dom 节点
		renderUI: function(){},
		//接口 绑定事件
		bindUI: function(){},
		//接口 初始化dom节点属性
		initDomAttribute: function(){},
		//接口 销毁前的工作
		destructor: function(){},
		//初始化
		render: function(){
			this.renderUI();
			this.handlers = {};
			this.bindUI();
			this.initDomAttribute();
			$(this.cfg.container || document.body).append(this.boundingBox);
		},
		//销毁
		destroy: function(){
			this.destructor();
			this.boundingBox.off();
			this.boundingBox.remove();
		},
		
		
		
		
		
	}
	
	return {Widget: Widget};
});