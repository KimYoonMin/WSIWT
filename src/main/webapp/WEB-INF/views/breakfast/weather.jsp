<%@ page language="java" contentType="text/html; charset=EUC-KR"
   pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<table border="1" id="dusttable">
   <tr align="center">
      <td rowspan="3" align="center" width="80"><c:choose>
            <c:when test="${weather.category.SKY == 1}">
               <img src="resources/img/sunny.png">
            </c:when>
            <c:when test="${weather.category.SKY == 2}">
               <img src="resources/img/cloud.png">
            </c:when>
            <c:when test="${weather.category.SKY == 3}">
               <img src="resources/img/rainy.png">
            </c:when>
            <c:otherwise>
               <img src="resources/img/drop.png">
            </c:otherwise>
         </c:choose></td>
      <td align="center" class="font">${lo.city }${lo.gu }${lo.dong }</td>
      <td rowspan="3" align="center" class="fonttwo">
         <%-- ${weather.category.TMN}
         / ${weather.category.TMX } --%>10/0
      </td>

   </tr>
   <tr>
      <td align="center"><c:choose>
            <c:when test="${weather.category.SKY == 1}">맑음</c:when>
            <c:when test="${weather.category.SKY == 2}">구름조금</c:when>
            <c:when test="${weather.category.SKY == 3}">구름많음</c:when>
            <c:otherwise>흐림</c:otherwise>
         </c:choose></td>
   </tr>
   <tr>
      <td align="center">강수확률 : ${weather.category.POP}%</td>
   </tr>

</table>