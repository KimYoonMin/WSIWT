<%@page import="java.util.Calendar"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	//온도별 옷 변화
	String clothImg = "";
	int tem=-5;
  	switch((int)tem/3){
	case 1:
		clothImg="resources/img/clothImg/tem_3.png";
		break;
	case 2:
		clothImg="resources/img/clothImg/tem_6.png";
		break;
	case 3:
		clothImg="resources/img/clothImg/tem_9.png";
		break;
	case 4:
		clothImg="resources/img/clothImg/tem_12.png";
		break;
	case 5:
		clothImg="resources/img/clothImg/tem_15.png";
		break;
	case 6:
		clothImg="resources/img/clothImg/tem_18.png";
		break;
	case 7:
		clothImg="resources/img/clothImg/tem_21.png";
		break;
	case 8:
		clothImg="resources/img/clothImg/tem_24.png";
		break;
	default :
		clothImg="resources/img/clothImg/tem_3.png";
		break;
	}  
%>

<%
	//시간별 background
	String background="";
/* 	int hour = 3; */
	Calendar cal = Calendar.getInstance();

	int hour = cal.get(Calendar.HOUR_OF_DAY);
	if(hour>=6 && hour<12){
		background="'resources/img/morning.png'";
	}else if(hour>=12 && hour<18){
		background="'resources/img/afternoon.png'";
	}else{
		background="'resources/img/night.png'";
	}
%>
<div id="clothes" style="background: url(<%=background %>); background-size: contain;">

<img src=<%=clothImg%> id="character" />
<c:choose>
   <c:when test="${weather.category.POP >= 0 && weather.category.POP <= 40}">
   </c:when>
   <c:when test="${weather.category.POP >= 50 && weather.category.POP <= 100}">
      <img src="resources/img/umbrella.png" id="umb">
   </c:when>
   <c:otherwise>no value
   </c:otherwise>
</c:choose>
</div>