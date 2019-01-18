<%@page import="java.util.Calendar"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<% 
	Calendar cal2 = Calendar.getInstance();
	//³¯Â¥
	String date2 = (cal2.get(Calendar.YEAR) + ".") + (((cal2.get(Calendar.MONTH) + 1)) + ". ")
			+(cal2.get(Calendar.DATE)+"  (") + (cal2.get(Calendar.HOUR_OF_DAY)+":")+(cal2.get(Calendar.MINUTE)+")"); 
%>

<div class="container">
      <div id="date">
         <h1><%=date2%></h1>
      </div>
<div id="clothes">
	<jsp:include page="/WEB-INF/views/breakfast/clothes.jsp" />
</div>
<div>
	<img src="resources/img/sleep.gif" width="300px" height="200px"/>
</div>
</div>