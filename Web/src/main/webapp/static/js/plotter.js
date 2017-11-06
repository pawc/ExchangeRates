function plot(response){		
	
	var rec = response;
	generatedDataPoints = [];
	len = rec.rateDate.length;
	min = rec.min;
	max = rec.max;
	span = max-min;
	if(span == 0) span = 1;
	min = min-0.1*span;
	max = max+0.1*span;
	
	for(var i = 0; i < len; i++){
		generatedDataPoints.push({
			y : rec.rateDate[i].exchangeRate,
			label : rec.rateDate[i].date
		})
		
	}
		 
	var chart = new CanvasJS.Chart("chartContainer", {
		title:{
			text: rec.targetCurrency+"/"+rec.baseCurrency       
		},
		data: [              
		{
			type: "line",
			dataPoints: generatedDataPoints
		}
		],
		axisY:{
	    	minimum: min,
	    	maximum: max,
		 },
		backgroundColor: "#EEEEEC"
	});
	chart.render();
	
}