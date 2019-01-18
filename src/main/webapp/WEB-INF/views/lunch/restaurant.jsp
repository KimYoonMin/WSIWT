<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link href="css/style.css" rel="stylesheet">
<table border="0" id="resttable" >
<c:forEach var="dto" items="${food}">
	
		<tr align="center">
			<td rowspan="3" align="center"><img src="${dto.img}" width="80" height="80"></td>
			<td>${dto.name}</td>
		</tr>
		<tr>
			<td>${dto.tel}</td>
		</tr>
		<tr>
			<td>${dto.addr}</td>
		</tr>
	
</c:forEach>
</table>

<%-- 
<body>
	<c:choose>
		<c:when test="${empty food}">
			<tr>
				<td colspan="6"><h1>작성된 글이 없습니다.</h1></td>
			</tr>
		</c:when>
		
		<c:otherwise>
			<c:forEach var="dto" items="${food}">
					<table border="1" class="dusttable">
						<tr align="center">
							<td rowspan="3" align="center"><img src="${dto.img}"
								width="80" height="80"></td>
							<td>${dto.name}</td>
						</tr>
						<tr>
							<td>${dto.tel}</td>
						</tr>
						<tr>
							<td>${dto.addr}</td>
						</tr>
					</table>
				</div>
			</c:forEach>
		</c:otherwise>
	</c:choose>
</body> --%>