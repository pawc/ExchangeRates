<%@ page language="java" contentType="text/html; charset=ISO-8859-1"  
 pageEncoding="ISO-8859-1"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">  
<html>  
<head>  
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">  
	<title>Insert title here</title>  
	<%@ page isELIgnored="false" %>
	<link rel="stylesheet" type="text/css" href="/Web/static/css/main.css">
	<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
	
   	<script src="https://canvasjs.com/assets/script/canvasjs.min.js"></script>
 	<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
 	<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script> 
</head>  
<body>  
	<center>  
	<b>Exchange Rates</b><br /> 
 
	<script type="text/javascript"> 
	
	function validateDates(dateFrom, dateTo){
		
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
	}
	
	$( function() {
		$( "#dateFrom" ).datepicker({
		 dateFormat: "yy-mm-dd",
		});  
	});
		
	$(function() {
		$( "#dateTo" ).datepicker({
		 dateFormat: "yy-mm-dd",
		});  
	});
	
	$(document).ready(function(){
		$("#reverseButton").click(function(){
			
			var targetCurrency = $('#targetCurrency').val(); 
			var baseCurrency = $('#baseCurrency').val();
			
			$("#targetCurrency").val(baseCurrency);
			$("#baseCurrency").val(targetCurrency);
			
		});
	});

	function doAjaxPost() {  
     
	var targetCurrency = $('#targetCurrency').val(); 
	var baseCurrency = $('#baseCurrency').val();

	var dateFrom = $('#dateFrom').val();
	var dateTo = $('#dateTo').val();
	
	$.ajax({  
		type : "Get",   
		url : "ajax.html",   
		dataType: "json",
		data : "targetCurrency=" + targetCurrency + "&baseCurrency=" + baseCurrency +"&dateFrom=" + dateFrom +"&dateTo=" + dateTo,
		  
		success : function(response) {  
		
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
		}, 
			
		error : function(e) {  
			console.log(e);
			//alert('Error: ' + e);   
		}  
		});  
	}  
	</script>
	
    <form method="get" onsubmit="return validateDates(dateFrom.value, dateTo.value)">  
    <select name="targetCurrency" id="targetCurrency" selected="EUR">
		<script type="text/javascript">
			var currencies = ${model.currencies};
			for(var i = 0, len = currencies.length; i < len; i++){
				currency = currencies[i];
				selected = "";
				if(currency == "EUR") selected = "selected";
				document.write("<option value="+currency+" "+selected+">"+currency+"</option>");			
			}
		</script>
	</select>
	
    <select name="baseCurrency" id="baseCurrency" selected="PLN">
		<script type="text/javascript">
			var currencies = ${model.currencies};
			for(var i = 0, len = currencies.length; i < len; i++){
				currency = currencies[i];
				selected = "";
				if(currency == "PLN") selected = "selected";
				document.write("<option value="+currency+" "+selected+">"+currency+"</option>");			
			}
		</script>
	</select>
	
	<input type="button" id="reverseButton" value="reverse" /> 
	
	<input type="text" name="dateFrom" id="dateFrom" value='2017-10-20'>
	<b>-</b>
	<input type="text" name="dateTo" id="dateTo" value='2017-12-31'>
	
	<input type="button" value="plot" onclick="doAjaxPost();" />  
    </form>
    
	<div id="chartContainer" style="height: 400px; width: 100%;"></div>
  
	</center>  
</body>  
</html>  