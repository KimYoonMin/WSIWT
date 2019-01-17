<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Insert title here</title>
</head>


<body>
	<c:choose>
	<c:when test="${empty food}">
	<tr><td colspan="6"><h1>작성된 글이 없습니다.</h1></td></tr>
	</c:when>
	<c:otherwise>
	<c:forEach var="dto" items="${food}">
	${dto.name}</br>
	<img src="${dto.img}" width="100" height="100"></br>
	${dto.addr}</br>
	${dto.tel}</br>
	${dto.food}</br>
	${dto.price}</br>
	${dto.parking}</br>
	${dto.time}</br>
	${dto.lat}</br>
	${dto.lng}
	</c:forEach>	
	</c:otherwise>
</c:choose>
</body>
</html>