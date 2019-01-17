<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<table border="0" class="dusttable">
	<tr align="center">
		<td rowspan="3" align="center" width="80"><img
			src="resources/img/sun.png"></td>
		<td align="center" class="font">${lo.city } ${lo.gu } ${lo.dong }</td>
		<td rowspan="3" align="center" class="fonttwo">${weather.category.TMN} / ${weather.category.TMX }</td>

	</tr>
	<tr>
		<td align="center">
			<c:choose>
				<c:when test="${weather.category.SKY == 1}">맑음</c:when>
				<c:when test="${weather.category.SKY == 2}">구름조금</c:when>
				<c:when test="${weather.category.SKY == 3}">구름많음</c:when>
				<c:otherwise>흐림</c:otherwise>
			</c:choose>
		</td>
	</tr>
	<tr>
		<td align="center">강수확률 : ${weather.category.POP}%</td>
	</tr>

</table>
