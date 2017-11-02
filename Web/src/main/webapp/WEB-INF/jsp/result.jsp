<!DOCTYPE html>
<%@ page contentType = "text/html; charset = UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
	<head>
	
	<%@ page isELIgnored="false" %>
	<link rel="stylesheet" type="text/css" href="/Web/static/css/main.css">
	<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
	<script type="text/javascript" src="/Web/static/js/validation.js"></script>
   	<script src="https://canvasjs.com/assets/script/canvasjs.min.js"></script>
 	<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
 	<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
	<title>Exchange Rates Web Application</title>
	
	<script>
	$( function() {
	  $( "#dateFrom" ).datepicker({dateFormat: "yy-mm-dd"});  
	} );
	
	$( function() {
		  $( "#dateTo" ).datepicker({dateFormat: "yy-mm-dd"});  
	} );
	</script>
	
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
	<table><tr>
	<form name="targetCurrencySwitch" method="GET" action="result" onsubmit="return validate(dateFrom.value, dateTo.value)">
			
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
		<b>/</b>
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
		
		<input type="text" name="dateFrom" id="dateFrom" value="${model.dateFrom}">
		<b>-</b>
		<input type="text" name="dateTo" id="dateTo" value="${model.dateTo}">
		
		<input type="submit" value="plot" style="width: 100;"></td>
			
	</form>
	
	<form name="invert" method="GET" action="invert">
		<input type="submit" value="invert">
	</form>
	</tr></table>

	<div id="chartContainer" style="height: 400px; width: 100%;"></div>
		
</body>
	
</html>