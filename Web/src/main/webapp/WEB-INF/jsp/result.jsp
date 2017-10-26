<%@ page contentType = "text/html; charset = UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
	<head>
	<%@ page isELIgnored="false" %>
	<title>Results</title>
	</head>
	
	<script language="javascript" type="text/javascript">
	var rec = ${recordsJackson};
	for(var i = 0, len = rec.length; i < len; i++){
		var text = "<p>"+rec[i].targetCurrency+"/"+rec[i].baseCurrency+" on "+rec[i].date+" : "+rec[i].exchangeRate+"</p>";
		document.write(text)
	}
	</script>
</html>