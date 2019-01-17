<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
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

<img src=<%=clothImg %> id="character"/>
<img src="resources/img/umbrella.png" id="umb"/>