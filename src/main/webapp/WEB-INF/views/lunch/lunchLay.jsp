<%@page import="java.util.Calendar"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<% 
	Calendar cal1 = Calendar.getInstance();
	//³¯Â¥
	String date1 = (cal1.get(Calendar.YEAR) + ".") + (((cal1.get(Calendar.MONTH) + 1)) + ". ")
			+(cal1.get(Calendar.DATE)+"  (") + (cal1.get(Calendar.HOUR_OF_DAY)+":")+(cal1.get(Calendar.MINUTE)+")"); 
%>

<div class="container">
      <div id="date">
         <h1><%=date1%></h1>
      </div>
<div id="clothes">
	<jsp:include page="/WEB-INF/views/breakfast/clothes.jsp" />
</div>
<div id="restcon" >
	<jsp:include page="/WEB-INF/views/lunch/restaurant.jsp" />
</div>
</div>