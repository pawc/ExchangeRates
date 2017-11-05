function select(currencies, defaultCurrency){
	
	for(var i = 0, len = currencies.length; i < len; i++){
		currency = currencies[i];
		selected = "";
		if(currency == defaultCurrency) selected = "selected";
		document.write("<option value="+currency+" "+selected+">"+currency+"</option>");			
	}
	
}