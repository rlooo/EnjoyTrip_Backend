<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
  <%@ include file="/WEB-INF/views/common/header.jsp" %>
    <!DOCTYPE html>
    <html lang="ko">

    <head>
      <meta charset="UTF-8" />
      <meta name="viewport" content="width=device-width, initial-scale=1.0" />
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
      <link rel="stylesheet" href="${root}/assets/css/board.css" />

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
        <div class="board_list_wrap">
          <div class="board_list">
            <div class="top">
              <div class="num">번호</div>
              <div class="title">제목</div>
              <div class="writer">작성자</div>
              <div class="date">작성일</div>
              <div class="count">조회수</div>
            </div>


            <div id="boardList">
              <!-- <div class="num">${post.articleNo}</div>
              <div class="title">
                <a href="#" class="post-title link-dark" data-no="${post.articleNo}" style="text-decoration: none">
                  ${post.subject}</a>
              </div>
              <div class="writer">${post.userId}</div>
              <div class="date">${post.createdDate}</div>
              <div class="count">${post.hit}</div> -->

            </div>

          </div>
          <div class="board_page">
            <a href="#" class="bt first">
              << </a> <a href="#" class="bt prev">
                  < </a>
                    <a href="#" class="num on">1</a> <a href="#" class="num">2</a>
                    <a href="#" class="num">3</a> <a href="#" class="num">4</a> <a href="#" class="num">5</a>
                    <a href="#" class="bt next">></a> <a href="#" class="bt last">>></a>
          </div>
          <a href="/board/mvwrite/write" class="button" id="btn-mv-register">등록</a>
          <!--<c:if test="${userinfo ne null}">-->
          <div class="bt_wrap">

          </div>
          <!--</c:if>-->
        </div>
      </div>

      <form id="form-no-param" method="get" action="/board">
        <input type="hidden" id="act" name="act" value="view" />
        <input type="hidden" id="pgno" name="pgno" value="${pgno}" />
        <input type="hidden" id="key" name="key" value="${key}" />
        <input type="hidden" id="word" name="word" value="${word}" />
        <input type="hidden" id="articleno" name="articleno" value="" />
      </form>
      <!-- ======= Footer ======= -->
      <%@ include file="/WEB-INF/views/common/footer.jsp" %>
        <!-- End Footer -->
        <a href="#" class="back-to-top d-flex align-items-center justify-content-center"><i
            class="bi bi-arrow-up-short"></i></a>

        <script>
          let titles = document.querySelectorAll(".post-title");
          titles.forEach(function (title) {
            title.addEventListener("click", function () {
              document.querySelector("#articleno").value = this.getAttribute("data-no");
              document.querySelector("#form-no-param").submit();
            });
          });

          
          fetch("${root}/board/list")
            .then((response) => response.json())
            .then(datas => {
              console.log(datas);
              let list = ``;
              datas.forEach(data => {
                console.log(data);
                list += `
                
                <div class="num">\${data.articleNo}</div>
                <div class="title">
                  <a href="#" class="post-title link-dark" data-no="\${data.articleNo}" style="text-decoration: none">
                    \${data.title}</a>
                </div>
                <div class="writer">\${data.userId}</div>
                <div class="date">\${data.registDate}</div>
                <div class="count">\${data.hit}</div>
              
                `;
              });
              document.querySelector("#boardList").innerHTML = list;
            });
        </script>
    </body>

    </html>