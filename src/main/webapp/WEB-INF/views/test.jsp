<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
</head>
<script type="text/javascript">
	window.onload = function geoFindMe() {
		var output = document.getElementById("out");

		if (!navigator.geolocation) {
			output.innerHTML = "<p>������� �������� ���������̼��� �������� �ʽ��ϴ�.</p>";
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
			output.innerHTML = "������� ��ġ�� ã�� �� �����ϴ�.";
		}
		;

		navigator.geolocation.getCurrentPosition(success, error);

	}
</script>
<body>
	<div id="out"></div>
	<form id="form" name="f" action="home.do">
		<input type="text" name="latitude" id="latitude"> 
		<input type="text" name="longitude" id="longitude"> 
		<input type="submit" value="Ȯ��">

	</form>
</body>
</html>