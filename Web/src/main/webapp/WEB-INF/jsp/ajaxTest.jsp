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
	<b>Plot results </b><br />  
	<script type="text/javascript">  
	function doAjaxPost() {  
     
	var name = $('#name').val();  
	var gender = $('#gender').val();  
	var email = $('#email').val();  
	var phone = $('#phone').val();  
	var city = $('#city').val();  
	
		$.ajax({  
		 type : "Get",   
		 url : "ajax.html",   
		 data : "name=" + name + "&gender=" + gender + "&email="  
		   + email + "&phone=" + phone + "&city=" + city,  
		 success : function(response) {  
			 console.log(response);
			 $("#result").html(response);
		 },  
		 error : function(e) {  
		 	alert('Error: ' + e);   
		 }  
		});  
	}  
	</script>  
	<div id="form">  
    <form method="get">  
	<input type="button" value="plot" onclick="doAjaxPost();" />  
    </form>  
   
	</div>
	
	<div id="result">
	
	</div>  
  
	</center>  
</body>  
</html>  