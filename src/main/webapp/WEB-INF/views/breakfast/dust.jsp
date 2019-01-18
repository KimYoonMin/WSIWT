<%@ page language="java" contentType="text/html; charset=EUC-KR"
   pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link href="css/style.css" rel="stylesheet">
<table border="1" id="dusttable">
   <tr>
      <td align="center" colspan="3">${lo.city }${lo.gu }${lo.dong }</td>
   </tr>
   <tr align="center">
      <td rowspan="2" align="center" width="80"><c:choose>
            <c:when test="${dust.pm10Grade == -1}">없음</c:when>
            <c:when test="${dust.pm10Grade == 1}">
               <img src="resources/img/good.png">
            </c:when>
            <c:when test="${dust.pm10Grade == 2}">
               <img src="resources/img/soso.png">
            </c:when>
            <c:when test="${dust.pm10Grade == 3}">
               <img src="resources/img/bad.png">
            </c:when>
            <c:otherwise>
               <img src="resources/img/angry.png">
            </c:otherwise>
         </c:choose></td>

      <td align="center"><c:choose>
            <c:when test="${dust.pm10Grade == -1}">측정값 없음</c:when>
            <c:when test="${dust.pm10Grade == 1}">좋음</c:when>
            <c:when test="${dust.pm10Grade == 2}">보통</c:when>
            <c:when test="${dust.pm10Grade == 3}">나쁨</c:when>
            <c:otherwise>매우나쁨</c:otherwise>
         </c:choose></td>
      <td align="center">${dust.pm10Value}</td>
   </tr>
   <tr>
      <td align="center" colspan="2"><c:choose>
            <c:when test="${dust.pm10Grade == -1}">측정값 없음</c:when>
            <c:when test="${dust.pm10Grade == 1}">오늘은 공기가 상쾌!</c:when>
            <c:when test="${dust.pm10Grade == 2}">야외활동 지장없음!</c:when>
            <c:when test="${dust.pm10Grade == 3}">마스크 필수!</c:when>
            <c:otherwise>야외활동 자제!</c:otherwise>
         </c:choose></td>
</table>