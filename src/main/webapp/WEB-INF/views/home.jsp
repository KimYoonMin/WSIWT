<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<!--
	* ìµì´ ìì±ì¼ : 2018.11.10
	* ìì±ì : ì¡°ì¢ë¯¼
	* ì²ë¦¬ ìì
	 - web.xml ì¤ì 
	  : ì¸ì½ë©(UTF-8)
	 - controller (Annotation íì©)
	  : BreakFastController ìì¹¨ 
	  : LunchController ì ì¬
	  : DinnerController ì ë
	 - view(jsp, js, css, images)'s Path
	  : webapp/resource/css 
	  : webapp/resource/js 
	  : webapp/resource/images
	  : webapp/WEB-INF/views/jspFile	  
	 - context
	  : servlet-context
	    >> resources mapping ì¤ì  (css, js, images, ê·¸ ì¸)
	 - lib (maven)
	  : json-simple
	 
  -->
<!DOCTYPE html>
<html>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<head>
<title>ìì¹¨ ì ì¬ ì ë</title>
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
			미세먼지 수치 =${dust.pm10Value}	</br>
			초 미세먼지 수치 =${dust.pm25Value}</br>
			미세먼지 등급 =${dust.pm10Grade}</br>
			초미세먼지 등급 =${dust.pm25Grade}
			</div>
		</section>
		<section class="section2">
			<div>
				<%@ include file="recommend.jsp"%>
				<%@ include file="lunch/lunchLay.jsp"%>
			</div>
		</section>
		<section class="section3">
			<div>
				<%@ include file="recommend.jsp"%>
				<%@ include file="dinner/dinnerLay.jsp"%>
			</div>
		</section>
	</div>

	<script src="js/full-page-scroll.js"></script>
	<!-- one pagescroll js -->
	<script src="js/scroll-anime.js"></script>
	<!-- scroll ì´ëì ì°ì¸¡ dot íì -->
</body>
</html>
