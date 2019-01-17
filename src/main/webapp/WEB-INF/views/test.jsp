<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
	
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Insert title here</title>
</head>
<script type="text/javascript">
	window.onload = function geoFindMe() {
		var output = document.getElementById("out");

		if (!navigator.geolocation) {
			output.innerHTML = "<p>사용자의 브라우저는 지오로케이션을 지원하지 않습니다.</p>";
			return;
		}

		function success(position) {
			var latitude = position.coords.latitude;
			var longitude = position.coords.longitude;

			document.getElementById("latitude").value = latitude;
			document.getElementById("longitude").value = longitude;
			/* document.f.submit(); */

		}
		;

		function error() {
			output.innerHTML = "사용자의 위치를 찾을 수 없습니다.";
		}
		;

		navigator.geolocation.getCurrentPosition(success, error);

	}
</script>

<c:if test="${'success' eq crawl}">
	<script type="text/javascript">
		alert("crawl success");
	</script>
</c:if>

<body>
	<div id="out"></div>
	<form id="home" name="f" action="home.do">
		<input type="text" name="latitude" id="latitude"> 
		<input type="text" name="longitude" id="longitude"> 
		<input type="submit" value="확인">
	</form>
	
	<form id="crawl" name="c" action="crawl.do">
		<input type="submit" value="crawl_start">
	</form>
</body>
</html>