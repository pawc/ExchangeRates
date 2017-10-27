<%@ page contentType = "text/html; charset = UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
	<head>
	<%@ page isELIgnored="false" %>
	<link rel="stylesheet" type="text/css" href="/Web/static/css/main.css">
	<title>Exchange Rates Web Application</title>
	
	<script src="https://canvasjs.com/assets/script/canvasjs.min.js"></script>
	<script type="text/javascript">
		window.onload = function () {
		var rec = ${model.recordsJackson};
		generatedDataPoints = [];
		
		for(var i = 0, len = rec.length; i < len; i++){
			generatedDataPoints.push({
				y : rec[i].exchangeRate,
				label : rec[i].date
			})
		}
		
		
		var chart = new CanvasJS.Chart("chartContainer", {
			title:{
				text: "${model.targetCurrency}/PLN"       
			},
			data: [              
			{
				type: "line",
				dataPoints: generatedDataPoints
			}
			],
			axisY:{
			   minimum: ${model.minVal},
			   maximum: ${model.maxVal},
			 },
			backgroundColor: "#EEEEEC"
		});
		chart.render();
		}
	</script>

</head>

<body>
	<form name="targetCurrencySwitch" method="GET" action="result">
		
			<select name="targetCurrency" id="targetCurrency">
			<option value="-1" selected disabled>-</option>
			<script type="text/javascript">
				var currencies = ${model.currencies};
				for(var i = 0, len = currencies.length; i < len; i++){
					currency = currencies[i];
					document.write("<option value="+currency+">"+currency+"</option>");
				}
			</script>
			</select>
			
			<input type="submit" value="Change" style="width: 100;"></td>
				
	</form>

	<div id="chartContainer" style="height: 400px; width: 100%;"></div>
		
</body>
	
</html>