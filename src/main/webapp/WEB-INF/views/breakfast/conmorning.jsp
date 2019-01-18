<%@page import="java.util.Calendar"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>

<% 
	Calendar cal = Calendar.getInstance();
	
	//³¯Â¥
	String date = (cal.get(Calendar.YEAR) + ".") + (((cal.get(Calendar.MONTH) + 1)) + ". ")
			+(cal.get(Calendar.DATE)+"  (") + (cal.get(Calendar.HOUR_OF_DAY)+":")+(cal.get(Calendar.MINUTE)+")"); 
%>


<div class="container">
      <div id="date">
         <h1><%=date%></h1>
      </div>
      <div id="clothes">
         <jsp:include page="/WEB-INF/views/breakfast/clothes.jsp" />

      </div>
      <div id="dustcon">
         <jsp:include page="/WEB-INF/views/breakfast/dust.jsp" />
      </div>
      <div id="weacon">
         <jsp:include page="/WEB-INF/views/breakfast/weather.jsp" />
      </div>
</div>