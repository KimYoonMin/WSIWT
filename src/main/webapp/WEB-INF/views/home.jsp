<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<!--
	* 최초 작성일 : 2018.11.10
	* 작성자 : 조종민
	* 처리 작업
	 - web.xml 설정
	  : 인코딩(UTF-8)
	 - controller (Annotation 활용)
	  : BreakFastController 아침 
	  : LunchController 점심
	  : DinnerController 저녁
	 - view(jsp, js, css, images)'s Path
	  : webapp/resource/css 
	  : webapp/resource/js 
	  : webapp/resource/images
	  : webapp/WEB-INF/views/jspFile	  
	 - context
	  : servlet-context
	    >> resources mapping 설정 (css, js, images, 그 외)
	 - lib (maven)
	  : json-simple
	 
  -->
<!DOCTYPE html>
<html>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<head>
<title> What should I wear today��</title>
<link href='https://fonts.googleapis.com/css?family=Open+Sans'
	rel='stylesheet' type='text/css'>
<link rel="stylesheet" type="text/css" href="css/full-page-scroll.css">
<!-- one pagescroll css -->
<link rel="stylesheet" type="text/css" href="css/content-style.css">
<!--content css -->
</head>
<body>
	<div id="main" class="scroll-container">
		<section class="section1">
			<div>
				<%@ include file="breakfast/conmorning.jsp"%>
			</div>
		</section>
		<section class="section2">
			<div>
				<%@ include file="lunch/lunchLay.jsp"%>
			</div>
		</section>
		<section class="section3">
			<div>
				<%@ include file="dinner/dinnerLay.jsp"%>
			</div>
		</section>
	</div>

	<script src="js/full-page-scroll.js"></script>
	<!-- one pagescroll js -->
	<script src="js/scroll-anime.js"></script>
	<!-- scroll 이동시 우측 dot 표시 -->
</body>
</html>
