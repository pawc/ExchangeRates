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
	<script type="text/javascript" src="/Web/static/js/validation.js"></script>
   	<script src="https://canvasjs.com/assets/script/canvasjs.min.js"></script>
 	<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
 	<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script> 
</head>  
<body>  
	<center>  
	<b>Exchange Rates Web App</b><br />  
	<script type="text/javascript">  
	function doAjaxPost() {  
     
	var targetCurrency = $('#targetCurrency').val();  
	
	$.ajax({  
		type : "Get",   
		url : "ajax.html",   
		dataType: "json",
		data : "targetCurrency=" + targetCurrency,
		  
			success : function(response) {  
			
			var rec = response;
			generatedDataPoints = [];
			len = rec.length;
			min = rec[len-2].exchangeRate;
			max = rec[len-1].exchangeRate;
			span = max-min;
			min = min-0.3*span;
			max = max+0.3*span;
			
			for(var i = 0; i < len-3; i++){
				generatedDataPoints.push({
					y : rec[i].exchangeRate,
					label : rec[i].date
				})
				
			}
				 //console.log(generatedDataPoints);
				 //$("#result").html(response);
				 
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
			alert('Error: ' + e);   
		}  
		});  
	}  
	</script>  
	<div id="form">  
    <form method="get">  
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
    
	<input type="button" value="plot" onclick="doAjaxPost();" />  
    </form>  
    </div>
    
	<div id="chartContainer" style="height: 400px; width: 100%;"></div>
  
	</center>  
</body>  
</html>  