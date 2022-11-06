<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="root" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8" />
  <meta content="width=device-width, initial-scale=1.0" name="viewport" />

  <meta content="" name="description" />
  <meta content="" name="keywords" />
  
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

  <!-- =======================================================
  * Template Name: Flexor - v4.8.0
  * Template URL: https://bootstrapmade.com/flexor-free-multipurpose-bootstrap-template/
  * Author: BootstrapMade.com
  * License: https://bootstrapmade.com/license/
  ======================================================== -->
</head>

<body>
  <!-- ======= Header ======= -->
  <header id="header" class="d-flex align-items-center">
    <div class="container d-flex align-items-center justify-content-between">
      <div class="logo">
        <h1><a href="/index">Enjoy Trip</a></h1>
        <!-- Uncomment below if you prefer to use an image logo -->
        <!-- <a href="index.html"><img src="assets/img/logo.png" alt="" class="img-fluid"></a>-->
      </div>



      <nav id="navbar" class="navbar">
       <ul>
					<li><a id="search-btn" href="/search">지역으로찾기</a></li>
					<li><a id="route-btn" href="/route">나의여행계획</a></li>
					<li><a id="board-btn" href="/hot_place">핫플자랑하기</a></li>
					<li><a id="board-btn" href="/mvboard">여행정보공유</a></li>
					<li class="disable islogin" id="logout"><a
						href="/user/logout">로그아웃</a></li>
					<c:if test="${userinfo eq null}">
						<li><a id="login-btn" href="/mvlogin">로그인/회원가입</a></li>
					</c:if>
					<c:if test="${userinfo ne null}">
						<li><a href="/mvlogin">마이페이지</a></li>
						<li><a href="/user/logout">로그아웃</a></li>
					</c:if>
				</ul>
        <i class="bi bi-list mobile-nav-toggle"></i>
      </nav>
      <!-- .navbar -->
    </div>
  </header>
  <!-- End Header -->
</body>
</html>