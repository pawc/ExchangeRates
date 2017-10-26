<%@ page contentType = "text/html; charset = UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
	<head>
	<%@ page isELIgnored="false" %>
	<title>Results222</title>
	</head>
   
    <table>   
   	<c:forEach items="${records}" var="record">
	<tr>
		<td>${record.baseCurrency}</td>
		<td>${record.targetCurrency}</td>
		<td>${record.exchangeRate}</td>
		<td>${record.date}</td>
	</tr>
	</c:forEach>
	</table>
</html>