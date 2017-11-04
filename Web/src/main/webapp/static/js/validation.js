function validateDates(dateFrom, dateTo){
	
	var dateStart = Date.parse(dateFrom);
	var dateEnd = Date.parse(dateTo);
	
	if(isNaN(dateStart) || isNaN(dateEnd)){
		alert("invalid date input");
		return false;
	}
	
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
	
	if(dateOfFirstRecord > until){
		alert("not enough data");
		return false;
	}
	
	return true;
}