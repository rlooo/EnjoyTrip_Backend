<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@ include file="/common/header.jsp" %>
     <c:if test="${empty article}">
	<script type="text/javascript">
		alert("정상적인 URL 접근이 아닙니다.");
		location.href = "${root}/board";
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
    <link href="${root}/assets/img/apple-touch-icon.png" rel="apple-touch-icon" />

    <!-- Google Fonts -->
    <link
        href="https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,600,600i,700,700i|Raleway:300,300i,400,400i,500,500i,600,600i,700,700i|Poppins:300,300i,400,400i,500,500i,600,600i,700,700i"
        rel="stylesheet" />

    <!-- Vendor CSS Files -->
    <link href="${root}/assets/vendor/aos/aos.css" rel="stylesheet" />
    <link href="${root}/assets/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet" />
    <link href="${root}/assets/vendor/bootstrap-icons/bootstrap-icons.css" rel="stylesheet" />
    <link href="${root}/assets/vendor/boxicons/css/boxicons.min.css" rel="stylesheet" />
    <link href="${root}/assets/vendor/glightbox/css/glightbox.min.css" rel="stylesheet" />
    <link href="${root}/assets/vendor/swiper/swiper-bundle.min.css" rel="stylesheet" />

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
        <div class="board_write_wrap">
        <form id="form-modify" method="POST" action="">
        <input type="hidden" id = "act" name="act" value="">
        <input type="hidden" id="articleno" name="articleno" value="${article.articleNo}">
            <div class="board_write">
                <div class="title">
                    <dl>
                        <dt>제목</dt>
                        <dd><input type="text" placeholder="제목 입력" value="${article.subject}" id="subject" name="subject"></dd>
                    </dl>
                </div>
                <div class="info">
                    <dl>
                        <dt>글쓴이</dt>
                        <dd><input type="text" placeholder="글쓴이 입력" value="${article.userId}" readonly></dd>
                    </dl>

                </div>
                <div class="cont">
                    <textarea placeholder="내용 입력" id = "content" name = "content" style = "font-size:15px">
${article.content}</textarea>
                </div>
            </div>
            
            <div class="bt_wrap">
                <a href="#" class="on" id= "btn-modify">수정</a>
                <a href="#" class="on" id= "btn-delete">삭제</a>
                <a href="#" id ="btn-cancel">취소</a>
            </div>
            </form>
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
   <%@ include file="/common/footer.jsp" %>
    <!-- End Footer -->
    <a href="#" class="back-to-top d-flex align-items-center justify-content-center"><i
            class="bi bi-arrow-up-short"></i></a>
            
                <script>
                document.querySelector("#btn-modify").addEventListener("click", function () {
                    if (!document.querySelector("#subject").value) {
                      alert("제목 입력!!");
                      return;
                    } else if (!document.querySelector("#content").value) {
                      alert("내용 입력!!");
                      return;
                    } else {

                      let form = document.querySelector("#form-modify");
                      document.querySelector("#act").setAttribute("value", "modify");
                      form.setAttribute("action", "${root}/board?");
                      form.submit();
                    }
                  });
                
                document.querySelector("#btn-delete").addEventListener("click", function () {
                   
                	if(confirm("글을 삭제하시겠습니까?")) {
                		let form = document.querySelector("#form-modify");
                		document.querySelector("#act").setAttribute("value", "delete");
                        form.setAttribute("action", "${root}/board?");
                        form.submit();
               	    }
                    
                  });
                  
                  document.querySelector("#btn-cancel").addEventListener("click", function () {
                  	if(confirm("취소를 하시면 작성중인 글은 삭제됩니다.\n취소하시겠습니까?")) {
                		  let form = document.querySelector("#form-param");
                     	  
                     	  form.setAttribute("action", "${root}/board?act=list");
                        form.submit();
                 	    }
                    });
    </script>
</body>

</html>