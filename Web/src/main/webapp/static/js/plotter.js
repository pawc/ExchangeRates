function plot(response){		
	
	var rec = response;
	generatedDataPoints = [];
	len = rec.length;
	min = rec[len-2].exchangeRate;
	max = rec[len-1].exchangeRate;
	span = max-min;
	min = min-0.1*span;
	max = max+0.1*span;
	
	for(var i = 0; i <= len-3; i++){
		generatedDataPoints.push({
			y : rec[i].exchangeRate,
			label : rec[i].date
		})
		
	}
		 
	var chart = new CanvasJS.Chart("chartContainer", {
		title:{
			text: rec[0].targetCurrency+"/"+rec[0].baseCurrency       
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