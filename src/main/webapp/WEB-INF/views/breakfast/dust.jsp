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
            <c:when test="${dust.pm10Grade == -1}">����</c:when>
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
            <c:when test="${dust.pm10Grade == -1}">������ ����</c:when>
            <c:when test="${dust.pm10Grade == 1}">����</c:when>
            <c:when test="${dust.pm10Grade == 2}">����</c:when>
            <c:when test="${dust.pm10Grade == 3}">����</c:when>
            <c:otherwise>�ſ쳪��</c:otherwise>
         </c:choose></td>
      <td align="center">${dust.pm10Value}</td>
   </tr>
   <tr>
      <td align="center" colspan="2"><c:choose>
            <c:when test="${dust.pm10Grade == -1}">������ ����</c:when>
            <c:when test="${dust.pm10Grade == 1}">������ ���Ⱑ ����!</c:when>
            <c:when test="${dust.pm10Grade == 2}">�߿�Ȱ�� �������!</c:when>
            <c:when test="${dust.pm10Grade == 3}">����ũ �ʼ�!</c:when>
            <c:otherwise>�߿�Ȱ�� ����!</c:otherwise>
         </c:choose></td>
</table>