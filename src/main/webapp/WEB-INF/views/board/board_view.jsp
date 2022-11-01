<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/header.jsp"%>
<c:if test="${empty article}">
	<script type="text/javascript">
		alert("글이 삭제되었거나 정상적인 URL 접근이 아닙니다.");
		location.href = "${root}/board?act=list&pgno=1&key=&word=";
	</script>
</c:if>
<!DOCTYPE html>
<html lang="ko">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Enjoy Trip</title>
<!-- Favicons -->
<link href="${root}/assets/img/logo.png" rel="icon" />
<link href="${root}/assets/img/apple-touch-icon.png"
	rel="apple-touch-icon" />

<!-- Google Fonts -->
<link
	href="https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,600,600i,700,700i|Raleway:300,300i,400,400i,500,500i,600,600i,700,700i|Poppins:300,300i,400,400i,500,500i,600,600i,700,700i"
	rel="stylesheet" />

<!-- Vendor CSS Files -->
<link href="${root}/assets/vendor/aos/aos.css" rel="stylesheet" />
<link href="${root}/assets/vendor/bootstrap/css/bootstrap.min.css"
	rel="stylesheet" />
<link href="${root}/assets/vendor/bootstrap-icons/bootstrap-icons.css"
	rel="stylesheet" />
<link href="${root}/assets/vendor/boxicons/css/boxicons.min.css"
	rel="stylesheet" />
<link href="${root}/assets/vendor/glightbox/css/glightbox.min.css"
	rel="stylesheet" />
<link href="${root}/assets/vendor/swiper/swiper-bundle.min.css"
	rel="stylesheet" />

<!-- Template Main CSS File -->
<link href="${root}/assets/css/style.css" rel="stylesheet" />
<link rel="stylesheet" href="${root}/assets/css/board.css">

<!-- =======================================================
   * Template Name: Flexor - v4.8.0
   * Template URL: https://bootstrapmade.com/flexor-free-multipurpose-bootstrap-template/
   * Author: BootstrapMade.com
   * License: https://bootstrapmade.com/license/
   ======================================================== -->
</head>

<body>
	<div class="board_wrap">
		<div class="board_title">
			<h2 class="text-center mt-5">여행정보공유</h2>
			<p class="text-center mb-5">나만의 여행 정보를 공유해보세요!</p>
		</div>
		<div class="board_view_wrap">
			<div class="board_view">
				<div class="title">${article.subject}</div>
				<div class="info">
					<dl>
						<dt>번호</dt>
						<dd >${article.articleNo}</dd>
					</dl>
					<dl>
						<dt>글쓴이</dt>
						<dd>${article.userId}</dd>
					</dl>
					<dl>
						<dt>작성일</dt>
						<dd>${article.createdDate}</dd>
					</dl>
					<dl>
						<dt>조회</dt>
						<dd>${article.hit}</dd>
					</dl>
				</div>
				<div class="cont">
					${article.content}
				</div>
			</div>
			<div class="bt_wrap">
				<a href="#" class="on" id = "btn-list">목록</a> 
				<c:if test="${userinfo ne null}">
				<c:if test="${userinfo.userId eq article.userId}">
				<a href="#" id = "btn-mv-modify" >수정</a>
				</c:if>
				</c:if>
			</div>
		</div>
	</div>
	    <form id="form-param" method="get" action="">
      <input type="hidden" id="act" name="act" value="">
      <input type="hidden" id="pgno" name="pgno" value="${pgno}">
      <input type="hidden" id="key" name="key" value="${key}">
      <input type="hidden" id="word" name="word" value="${word}">
    </form>
    <form id="form-no-param" method="get" action="${root}/board">
      <input type="hidden" id="nact" name="act" value="">
      <input type="hidden" id="npgno" name="pgno" value="${pgno}">
      <input type="hidden" id="nkey" name="key" value="${key}">
      <input type="hidden" id="nword" name="word" value="${word}">
      <input type="hidden" id="articleno" name="articleno" value="${article.articleNo}">
    </form>
	<!-- ======= Footer ======= -->
	<%@ include file="/common/footer.jsp"%>
	<!-- End Footer -->
	<a href="#"
		class="back-to-top d-flex align-items-center justify-content-center"><i
		class="bi bi-arrow-up-short"></i></a>

	<script>
    document.querySelector("#btn-list").addEventListener("click", function () {
  	  let form = document.querySelector("#form-param");
  	  document.querySelector("#act").value = "list";
  	  form.setAttribute("action", "${root}/board");
        form.submit();
    });

		
	    document.querySelector("#btn-mv-modify").addEventListener("click", function () {
	    	  let form = document.querySelector("#form-no-param");
	    	  document.querySelector("#nact").value ="mvmodify";
	    	  form.setAttribute("action", "${root}/board");
	          form.submit();
	      });
	</script>
</body>

</html>