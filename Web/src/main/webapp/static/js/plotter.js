var chart;
var min;
var max;

function plot(response){	
	
	console.log(chart == null);
	if(chart != null){
		updateChart(response);
		return;
	}

	min = response.min;
	max = response.max;
	span = max - min;
	if(span == 0) span = 1;
	min = min-0.1*span;
	max = max+0.1*span;
	
	generatedDataPoints = [];
	len = response.rateDate.length;
	
	for(var i = 0; i < len; i++){
		generatedDataPoints.push({
			y : response.rateDate[i].exchangeRate,
			label : response.rateDate[i].date
		})
		
	}
		 
	chart = new CanvasJS.Chart("chartContainer", {
		title:{
			text: response.targetCurrency+"/"+response.baseCurrency       
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

function updateChart(response){
	
	generatedDataPoints = [];
	len = response.rateDate.length;
	
	for(var i = 0; i < len; i++){
		generatedDataPoints.push({
			y : response.rateDate[i].exchangeRate,
			label : response.rateDate[i].date
		})
		
	}
	
    var newSeries = {
		type: "line",
		dataPoints: generatedDataPoints
    };
    
	chart.options.data.push(newSeries);
	updateMinMax(response);
	chart.render();
}

function updateMinMax(response){
	minTemp = response.min;
	maxTemp = response.max;
	span = maxTemp-minTemp;
	if(span == 0) span = 1;
	
	if(min > minTemp){
		min = minTemp;
		chart.options.axisY.minimum = min;
	}
	if(max < maxTemp){
		max = maxTemp;
		chart.options.axisY.maximum = max;
	}

}