CloudEmotion=function()
{
}

CloudEmotion.SourceList=function()
{
}

CloudEmotion.CardList=function()
{
}

CloudEmotion.CardList.Card=function(div)
{
	_card=this;
	this.id=0;
	this.value="";
	this.text="";
	this.div=div;
	this.width=width;

	this.valueContain=document.createElement('div');
	this.div.appendChild(this.valueContain);

	this.textContain=document.createElement('div');
	this.div.appendChild(this.textContain);
}

CloudEmotion.CardList.Card.prototype.Init=function()
{
	
}

