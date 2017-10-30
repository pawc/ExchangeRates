function validate(dateFrom, dateTo){
	
	var since = new Date(dateFrom);
	var until = new Date(dateTo);
	var dateOfFirstRecord = new Date("2017-10-20");
	
	if(since > until){
		alert("invalid date input");
		return false;
	}
	
	if(since > new Date()){
		alert("invalid date input");
		return false;
	}
	
	if(dateOfFirstRecord >= until){
		alert("not enough data");
		return false;
	}

}