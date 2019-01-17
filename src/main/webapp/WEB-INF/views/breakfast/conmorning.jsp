<%@page import="java.util.Calendar"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
pageEncoding="utf-8"%>

<% 
	Calendar cal = Calendar.getInstance();
	//시간
	String time = (cal.get(Calendar.HOUR_OF_DAY)+":")+(cal.get(Calendar.MINUTE)+"");
	//날짜
	String date = (cal.get(Calendar.YEAR) + ". ") + (((cal.get(Calendar.MONTH) + 1)) + ". ")
			+(cal.get(Calendar.DATE)+"."); 
%>
<%
	//시간별 background
	String background="";
/* 	int hour = 3; */
	int hour = cal.get(Calendar.HOUR_OF_DAY);
	if(hour>=6 && hour<12){
		background="'resources/img/morning.png'";
	}else if(hour>=12 && hour<18){
		background="'resources/img/afternoon.png'";
	}else{
		background="'resources/img/night.png'";
	}
%>
<br>
<div id="time">
	<h5><%=time %></h5>
</div>
<div id="date">
	<h1><%=date%></h1>
</div>
<div id="clothes" style="background: url(<%=background %>); background-size: contain;">
	<jsp:include page="/WEB-INF/views/breakfast/clothes.jsp" />

</div>
<br><br>
<div>
	<jsp:include page="/WEB-INF/views/breakfast/dust.jsp" />
</div>
<br><br>
<div>
	<jsp:include page="/WEB-INF/views/breakfast/weather.jsp" />
</div>