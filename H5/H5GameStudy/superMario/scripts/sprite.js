var sr1,sr2,sr3;
var jumpImage;
var smallMarioRunImages;
function initSprites(){
	sr1 = new Image();
	sr1.src = "res/mario/sr1.png";
	sr1 = new Sprite(sr1,0,0,22,32);
	sr2 = new Image();
	sr2.src = "res/mario/sr2.png";
	sr2 = new Sprite(sr2,0,0,26,30);
	sr3 = new Image();
	sr3.src = "res/mario/sr3.png";
	sr3 = new Sprite(sr3,0,0,30,32);
	smallMarioRunImages = Array(sr1,sr2,sr3);
	
	jumpImage = new Image();
	jumpImage.src = "res/mario/jump.png";
	jumpImage = new Sprite(jumpImage,0,0,32,32);
}

function Sprite(img, x, y, width, height) {
	this.img = img;
	this.x = x * 2;
	this.y = y * 2;
	this.width = width;
	this.height = height;
}

Sprite.prototype.draw = function(ctx, x, y) {
	ctx.drawImage(this.img, this.x, this.y, this.width, this.height,
		x, y, this.width, this.height);
};

Sprite.prototype.draw2 = function(ctx,x,y,faceToRight){
	var x2 = this.x;
	var width = this.width;
	
	if(!faceToRight){
		width = -width;
		x2 += this.width;
	}
	ctx.drawImage(this.img, x2, this.y, width, this.height,
		x, y, this.width, this.height);
}
