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
				text: "${model.targetCurrency}/${model.baseCurrency}"       
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
			Plot
			<select name="targetCurrency" id="targetCurrency">
			<script type="text/javascript">
				var currencies = ${model.currencies};
				for(var i = 0, len = currencies.length; i < len; i++){
					currency = currencies[i];
					selection = "";
					if("${model.targetCurrency}".valueOf() == currency.valueOf()) selection = "selected";
					document.write("<option value="+currency+" "+selection+">"+currency+"</option>");			
				}
			</script>
			</select>
			against
			<select name="baseCurrency" id="baseCurrency">
			<script type="text/javascript">
				var currencies = ${model.currencies};
				for(var i = 0, len = currencies.length; i < len; i++){
					currency = currencies[i];
					selection = "";
					if("${model.baseCurrency}".valueOf() == currency.valueOf()) selection = "selected";
					document.write("<option value="+currency+" "+selection+">"+currency+"</option>");			
				}
			</script>
			</select>
			since
			<input type="date" name="dateFrom" size="8" value="${model.dateFrom}">
			until
			<input type="date" name="dateTo" size="8" value="${model.dateTo}">
			
			<input type="submit" value="go!" style="width: 100;"></td>
				
	</form>

	<div id="chartContainer" style="height: 400px; width: 100%;"></div>
		
</body>
	
</html>