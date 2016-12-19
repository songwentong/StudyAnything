var
//小鸟
s_bird,
//背景
s_bg,
//前景
s_fg,
//上面的桶
s_pipeNorth,
//下面的桶
s_pipeSouth,
//文字
s_text,
//分数
s_score,
//等待页
s_splash,
//按钮
s_buttons,
//小写阿拉伯数字
s_numberS,
//大写阿拉伯数字
s_numberB;


//给出图片,坐标大小来创建对象
function Sprite(img, x, y, width, height) {
	this.img = img;
	this.x = x*2;
	this.y = y*2;
	this.width = width*2;
	this.height = height*2;
};

/**
 * 	绘制小鸟
 * @param {Object} ctx
 * @param {Object} x
 * @param {Object} y
 */
Sprite.prototype.draw = function(ctx, x, y) {
	ctx.drawImage(this.img, this.x, this.y, this.width, this.height,
		x, y, this.width, this.height);
};

/**
 * 	初始化各种资源
 * @param {Object} img
 */
function initSprites(img) {

//取出对应区域的图片,是三个不同的状态
	s_bird = [
		new Sprite(img, 156, 115, 17, 12),
		new Sprite(img, 156, 128, 17, 12),
		new Sprite(img, 156, 141, 17, 12)
	];
	//取出背景图
	s_bg = new Sprite(img,   0, 0, 138, 114);
	//设置背景颜色
	s_bg.color = "#70C5CF"; // save background color
	//取出前景图
	s_fg = new Sprite(img, 138, 0, 112,  56);
	//上面的管道
	s_pipeNorth = new Sprite(img, 251, 0, 26, 200);
	//下面的管道
	s_pipeSouth = new Sprite(img, 277, 0, 26, 200);
	//flapybird,gameover,getready的文字
	s_text = {
		FlappyBird: new Sprite(img, 59, 114, 96, 22),
		GameOver:   new Sprite(img, 59, 136, 94, 19),
		GetReady:   new Sprite(img, 59, 155, 87, 22)
	}
	//6个按钮图
	s_buttons = {
		Rate:  new Sprite(img,  79, 177, 40, 14),
		Menu:  new Sprite(img, 119, 177, 40, 14),
		Share: new Sprite(img, 159, 177, 40, 14),
		Score: new Sprite(img,  79, 191, 40, 14),
		Ok:    new Sprite(img, 119, 191, 40, 14),
		Start: new Sprite(img, 159, 191, 40, 14)
	}
	//积分图
	s_score  = new Sprite(img, 138,  56, 113, 58);
	//启动图
	s_splash = new Sprite(img,   0, 114,  59, 49);
	//小写文字的图
	s_numberS = new Sprite(img, 0, 177, 6,  7);
	//大写文字的图
	s_numberB = new Sprite(img, 0, 188, 7, 10);

	/**
	 * Draw number to canvas
	 * 
	 * @param  {CanvasRenderingContext2D} ctx context used for drawing
	 * @param  {number} x      x-position
	 * @param  {number} y      y-position
	 * @param  {number} num    number to draw
	 * @param  {number} center center to offset from
	 * @param  {number} offset padd text to draw right to left
	 */
	s_numberS.draw = s_numberB.draw = function(ctx, x, y, num, center, offset) {
		num = num.toString();

		var step = this.width + 2;
		
		if (center) {
			x = center - (num.length*step-2)/2;
		}
		if (offset) {
			x += step*(offset - num.length);
		}

		for (var i = 0, len = num.length; i < len; i++) {
			var n = parseInt(num[i]);
			//画出对应的文字
			ctx.drawImage(img, step*n, this.y, this.width, this.height,
				x, y, this.width, this.height)
			x += step;
		}
	}
}
