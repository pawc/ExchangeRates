<%@ page language="java" contentType="text/html; charset=ISO-8859-1"  
 pageEncoding="ISO-8859-1"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">  
<html>  
<head>  
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">  
	<title>Exchange Rates</title>  
	<%@ page isELIgnored="false" %>
	<link rel="stylesheet" type="text/css" href="/Web/static/css/main.css">
	<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
	<script type="text/javascript" src="/Web/static/js/validation.js"></script>
	<script type="text/javascript" src="/Web/static/js/date.format.js"></script>
   	<script src="https://canvasjs.com/assets/script/canvasjs.min.js"></script>
 	<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
 	<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script> 
 	<script type="text/javascript" src="/Web/static/js/select.js"></script>
 	<script type="text/javascript" src="/Web/static/js/plotter.js"></script>
</head>  
<body>  
	<center>
	<script type="text/javascript"> 
	
	window.onload = doAjaxPost;
	
	$( function() {
		$("#dateStart").datepicker({
			dateFormat: "yy-mm-dd",
			minDate: "2017-10-20"
		});  
		$("#dateEnd").datepicker({
			dateFormat: "yy-mm-dd",
			minDate: "2017-10-20"
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

	function doAjaxPost(choice) {  
     
	var targetCurrency = $('#targetCurrency').val(); 
	var baseCurrency = $('#baseCurrency').val();

	var dateStart = $('#dateStart').val();
	var dateEnd = $('#dateEnd').val();
	
	if(!validateDates(dateStart, dateEnd)) return;
	
	$.ajax({  
		type : "Get",   
		url : "ajax.html",   
		dataType: "json",
		data : "targetCurrency=" + targetCurrency + "&baseCurrency=" + baseCurrency +"&dateStart=" + dateStart +"&dateEnd=" + dateEnd,
		
		success : function(response) {  
			plot(response, choice);
		}, 
			
		error : function(e) {  
			console.log(e);   
		}  
		});  
	}  
	</script>
		
    <form method="get">  
    <select name="targetCurrency" id="targetCurrency">
		<script type="text/javascript">
			select(${model.currencies}, "EUR");
		</script>
	</select>
	
    <select name="baseCurrency" id="baseCurrency">
		<script type="text/javascript">
			select(${model.currencies}, "PLN");
		</script>
	</select>
	
	<input type="button" id="reverseButton" value="reverse" /> 
	
	<input type="text" name="dateStart" id="dateStart" value='2017-10-20'/>
	<b>-</b>
	
	<script type="text/javascript">
	var today = new Date();
	var format = dateFormat(today, "yyyy-mm-dd");
	document.write("<input type='text' name='dateEnd' id='dateEnd' value='"+format+"'/>");	
	</script>
		
	<input type="button" value="plot" onclick="doAjaxPost(true);" />  
	<input type="button" value="add" onclick="doAjaxPost(false);" />  
   	<input type="button" value="clear" onclick="clearChart();" />  
    </form>
    
	<div id="chartContainer" style="height: 400px; width: 100%;"></div>
  
	</center>  
</body>  
</html>  