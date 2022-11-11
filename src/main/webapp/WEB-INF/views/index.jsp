<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="root" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html lang="ko">

<head>
<meta charset="utf-8" />
<meta content="width=device-width, initial-scale=1.0" name="viewport" />

<meta content="" name="description" />
<meta content="" name="keywords" />

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

<!-- This project default -->
<script src="https://kit.fontawesome.com/989420847f.js"
	crossorigin="anonymous"></script>

<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=27e1e8bef9f79b21bbe6735035a00943"></script>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=27e1e8bef9f79b21bbe6735035a00943&libraries=services"></script>

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
		<div
			class="container d-flex align-items-center justify-content-between">
			<div class="logo">
				<h1>
					<a href="/index">Enjoy Trip</a>
				</h1>
				<!-- Uncomment below if you prefer to use an image logo -->
				<!-- <a href="index.html"><img src="assets/img/logo.png" alt="" class="img-fluid"></a>-->
			</div>

			<form action="#" id="search-box">
				<input type="text" /> <input type="submit" value="검색"
					class="btn btn-outline-dark" id="submit-btn"></input>
			</form>

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
						<li><a href="/mvaccount">마이페이지</a></li>
						<li><a href="/user/logout">로그아웃</a></li>
					</c:if>
				</ul>
				<i class="bi bi-list mobile-nav-toggle"></i>
			</nav>
			<!-- .navbar -->
		</div>
	</header>
	<!-- End Header -->

	<!-- main -->
	<main id="main"> <!-- ======= carousel Section ======= -->
	<div id="carouselExampleFade"
		class="carousel slide carousel-fade carousel-box" style="margin-top:150px" 
		data-bs-ride="carousel">
		<div class="carousel-inner">
			<div class="carousel-item active">
				<img src="${root}/assets/img/index.jpg"
					class="d-block w-100" alt="travel01" />
			</div>
			<div class="carousel-item">
				<img src="${root}/assets/img/main_photo02.jpg" class="d-block w-100"
					alt="travel02" />
			</div>
			<div class="carousel-item">
				<img src="${root}/assets/img/main_photo03.jpg" class="d-block w-100"
					alt="travel03" />
			</div>
		</div>
	</div>

	<p class="mockup-title mt-5">오늘의 추천 여행지</p>
	<div class="mockup">
		<div class="card " style="width: 25rem;">
			<img src="./assets/img/mockup/bridge-gf257c3659_640.jpg"
				class="card-img-top" alt="...">
			<div class="card-body">
				<h5 class="card-title">Card title</h5>
				<p class="card-text">Some quick example text to build on the
					card title and make up the bulk of the card's content.</p>
				<a href="#" class="btn btn-primary">Go somewhere</a>
			</div>
		</div>
		<div class="card" style="width: 25rem;">
			<img src="./assets/img/mockup/cancun-gcc9de655f_640.jpg"
				class="card-img-top" alt="...">
			<div class="card-body">
				<h5 class="card-title">Card title</h5>
				<p class="card-text">Some quick example text to build on the
					card title and make up the bulk of the card's content.</p>
				<a href="#" class="btn btn-primary">Go somewhere</a>
			</div>
		</div>
		<div class="card" style="width: 25rem;">
			<img src="./assets/img/mockup/channel-gb5e21564c_640.jpg"
				class="card-img-top" alt="...">
			<div class="card-body">
				<h5 class="card-title">Card title</h5>
				<p class="card-text">Some quick example text to build on the
					card title and make up the bulk of the card's content.</p>
				<a href="#" class="btn btn-primary">Go somewhere</a>
			</div>
		</div>
		<div class="card" style="width: 25rem;">
			<img src="./assets/img/mockup/downtown-gc8b57ce99_640.jpg"
				class="card-img-top" alt="...">
			<div class="card-body">
				<h5 class="card-title">Card title</h5>
				<p class="card-text">Some quick example text to build on the
					card title and make up the bulk of the card's content.</p>
				<a href="#" class="btn btn-primary">Go somewhere</a>
			</div>
		</div>
	</div>
	<p class="mockup-title mt-5">인스타그램 감성의 여행지 추천</p>
	<div class="mockup">
		<div class="card " style="width: 25rem;">
			<img src="./assets/img/mockup/hallstatt-g4764ed4a3_640.jpg"
				class="card-img-top" alt="...">
			<div class="card-body">
				<h5 class="card-title">Card title</h5>
				<p class="card-text">Some quick example text to build on the
					card title and make up the bulk of the card's content.</p>
				<a href="#" class="btn btn-primary">Go somewhere</a>
			</div>
		</div>
		<div class="card" style="width: 25rem;">
			<img src="./assets/img/mockup/japan-ge015351c4_640.jpg"
				class="card-img-top" alt="...">
			<div class="card-body">
				<h5 class="card-title">Card title</h5>
				<p class="card-text">Some quick example text to build on the
					card title and make up the bulk of the card's content.</p>
				<a href="#" class="btn btn-primary">Go somewhere</a>
			</div>
		</div>
		<div class="card" style="width: 25rem;">
			<img src="./assets/img/mockup/jordan-gdaaaeb602_640.jpg"
				class="card-img-top" alt="...">
			<div class="card-body">
				<h5 class="card-title">Card title</h5>
				<p class="card-text">Some quick example text to build on the
					card title and make up the bulk of the card's content.</p>
				<a href="#" class="btn btn-primary">Go somewhere</a>
			</div>
		</div>
		<div class="card" style="width: 25rem;">
			<img src="./assets/img/mockup/lugu-lake-gbdb87f4f3_640.jpg"
				class="card-img-top" alt="...">
			<div class="card-body">
				<h5 class="card-title">Card title</h5>
				<p class="card-text">Some quick example text to build on the
					card title and make up the bulk of the card's content.</p>
				<a href="#" class="btn btn-primary">Go somewhere</a>
			</div>
		</div>
	</div>
	<p class="mockup-title mt-5">기차로 떠나는 여행지 추천</p>
	<div class="mockup">
		<div class="card " style="width: 25rem;">
			<img src="./assets/img/mockup/mountains-g02f7e3e10_640.jpg"
				class="card-img-top" alt="...">
			<div class="card-body">
				<h5 class="card-title">Card title</h5>
				<p class="card-text">Some quick example text to build on the
					card title and make up the bulk of the card's content.</p>
				<a href="#" class="btn btn-primary">Go somewhere</a>
			</div>
		</div>
		<div class="card" style="width: 25rem;">
			<img src="./assets/img/mockup/people-gcf04cb824_640.jpg"
				class="card-img-top" alt="...">
			<div class="card-body">
				<h5 class="card-title">Card title</h5>
				<p class="card-text">Some quick example text to build on the
					card title and make up the bulk of the card's content.</p>
				<a href="#" class="btn btn-primary">Go somewhere</a>
			</div>
		</div>
		<div class="card" style="width: 25rem;">
			<img src="./assets/img/mockup/santorini-ga8280d39d_640.jpg"
				class="card-img-top" alt="...">
			<div class="card-body">
				<h5 class="card-title">Card title</h5>
				<p class="card-text">Some quick example text to build on the
					card title and make up the bulk of the card's content.</p>
				<a href="#" class="btn btn-primary">Go somewhere</a>
			</div>
		</div>
		<div class="card" style="width: 25rem;">
			<img src="./assets/img/mockup/temple-g9fc736dc2_640.jpg"
				class="card-img-top" alt="...">
			<div class="card-body">
				<h5 class="card-title">Card title</h5>
				<p class="card-text">Some quick example text to build on the
					card title and make up the bulk of the card's content.</p>
				<a href="#" class="btn btn-primary">Go somewhere</a>
			</div>
		</div>
	</div>


	<!-- <div class="main-container">
      </div> -->

	<div id="search-result-container">
		<div id="search-result" class="row" data-aos="fade-up" style="overflow-x:hidden; overflow-y:hidden"></div>
	</div>

	</main>
	<!-- End #main -->

	<!-- ======= Footer ======= -->
	<footer id="footer">

		<div class="container d-lg-flex py-4">
			<div class="me-lg-auto text-center text-lg-start">
				<div class="copyright">
					&copy; Copyright <strong><span>Flexor</span></strong>. All Rights
					Reserved
				</div>
				<div class="credits">
					<!-- All the links in the footer should remain intact. -->
					<!-- You can delete the links only if you purchased the pro version. -->
					<!-- Licensing information: https://bootstrapmade.com/license/ -->
					<!-- Purchase the pro version with working PHP/AJAX contact form: https://bootstrapmade.com/flexor-free-multipurpose-bootstrap-template/ -->
					Designed by <a href="https://bootstrapmade.com/">BootstrapMade</a>
				</div>
			</div>
			<div class="social-links text-center text-lg-right pt-3 pt-lg-0">
				<a href="#" class="twitter"><i class="bx bxl-twitter"></i></a> <a
					href="#" class="facebook"><i class="bx bxl-facebook"></i></a> <a
					href="#" class="instagram"><i class="bx bxl-instagram"></i></a> <a
					href="#" class="google-plus"><i class="bx bxl-skype"></i></a> <a
					href="#" class="linkedin"><i class="bx bxl-linkedin"></i></a>
			</div>
		</div>
	</footer>
	<!-- End Footer -->

	<a href="#"
		class="back-to-top d-flex align-items-center justify-content-center"><i
		class="bi bi-arrow-up-short"></i></a>

	<!-- Modal -->

	<%-- <div class="modal fade" id="myModal">
		<div class="modal-dialog">
			<div class="modal-content">
				<div id="modal-description" class="row">
					<!-- Modal Header -->
					<div class="modal-header">
						<h4 class="modal-title"></h4>
						<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
					</div>

					<!-- Modal body -->
					<div class="modal-body"></div>
				</div>

				<!-- Modal footer -->
				<div class="modal-footer">
					<button type="button" class="btn btn-success">add favorite</button>
					<button type="button" class="btn btn-danger"
						data-bs-dismiss="modal">Close</button>
				</div>

			</div>
		</div>
	</div> --%>

	<!-- Vendor JS Files -->
	<script src="${root}/assets/vendor/aos/aos.js"></script>
	<script src="${root}/assets/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
	<script src="${root}/assets/vendor/glightbox/js/glightbox.min.js"></script>
	<script src="${root}/assets/vendor/isotope-layout/isotope.pkgd.min.js"></script>
	<script src="${root}/assets/vendor/swiper/swiper-bundle.min.js"></script>
	<script src="${root}/assets/vendor/php-email-form/validate.js"></script>

	<!-- Template Main JS File -->
	<script src="${root}/assets/js/main.js"></script>
	<script src="${root}/assets/js/smoothScroll.js"></script>
	<script>
		const searchBox = document.querySelector('#search-box');
		const searchBtn = document.querySelector('#search-box > button');
		const searchResult = document.getElementById('search-result');
		const scrollPos = document.querySelector('.main-container');
		const modalDes = document.getElementById('modal-description');
		const mockups = document.getElementsByClassName('mockup');
		const mockupTitles = document.getElementsByClassName('mockup-title');
		const logoutBtn = document.getElementById('logout');
		let curKeyword = '';
		let curData = '';

		searchBox.addEventListener('submit', function(e){
			e.preventDefault();
			// 키워드를 전역변수로 변경
			curKeyword = this.children[0].value.trim();
			if(curKeyword){
				for (const mockup of mockups) {
				mockup.style.display="none";
				}
				for (const title of mockupTitles) {
				title.style.display="none";
				}
				searchResult.innerHTML='';
				curData = '';
				window.scrollTo(0,0);
				sendRequest(curKeyword);
				// document.querySelector('#carouselExampleFade').style.opacity="0.9";
			}else{
				alert('검색어를 입력해주세요');
			}
		});


		function sendRequest(curKeyword) {
				// ///////////////////////////////////////////////////////
			fetch(`/content/searchPlace/\${curKeyword}`)
			.then((response) => response.json())
			.then((data) => makeCard(data));
		}

		function makeCard(data) {
			data.forEach((e) => {
				let img = encodeURI(e.placeImg);
				let tel = e.tel;
				if(img == "null"){
					img = "<c:out value='${root}/assets/img/no_img.jpg'/>";
				}
				if(tel == null){
					tel = "등록되지 않았습니다";
				}
				curData += `
				<div class="card mb-3 search-card" style="max-width: 540px;" data-aos="fade-up" data-bs-toggle="modal" data-bs-target="#myModal">
						<div class="row g-0">
							<div class="col-md-4 pt-1">
								<img src="\${img}" class="img-fluid rounded-start" alt="..." id="card-img">
							</div>
							<div class="col-md-8">
								<div class="card-body">
									<h5 class="card-title text-truncate">\${e.title}</h5>
									<p class="card-text">\${e.address}</p>
									<p class="card-text">\${tel}</p>
								</div>
							</div>
						</div>
				</div>
				`
			});
			searchResult.innerHTML += curData;
		}

		const APIKEY = "665e2b9849b2ffe8bf99a13aae61959a";
		
		function getLocation() {
		navigator.geolocation.getCurrentPosition(getCoord, getError, options);
		}
	
		function getCoord(response) {
		const { coords, timestamp } = response;
		console.log(coords.latitude);
		getWeather(coords.latitude, coords.longitude);
		}

		function getError(error) {
		console.log(error.code);
		console.log(error.message);
		}
		
		const options = {
				enableHighAccuracy: true,
				maximumAge: 0,
				timeout: Infinity,
		};

		const weather_icons = {
				"Thunderstorm" : "cloud-lightning-fill",
				"Drizzle" : "cloud-drizzle-fill",
				"Rain" : "cloud-rain-fill",
				"Snow" : "cloud-snow-fill",
				"Mist" : "cloud-haze-fill",
				"Smoke" : "cloud-haze-fill",
				"Haze" : "cloud-haze-fill",
				"Dust" : "cloud-haze-fill",
				"Fog" : "cloud-fog2-fill",
				"Sand" : "cloud-haze-fill",
				"Ash" : "cloud-haze-fill",
				"Squall" : "cloud-rain-heavy-fill",
				"Tornado" : "cloud-lightning-rain-fill",
				"Clear" : "brightness-high-fill",
				"Clouds" : "clouds-fill"
		};
		const weathers = {
				"Thunderstorm" : "천둥/번개",
				"Drizzle" : "이슬비",
				"Rain" : "비",
				"Snow" : "눈",
				"Mist" : "안개",
				"Smoke" : "안개",
				"Haze" : "안개",
				"Dust" : "안개",
				"Fog" : "안개",
				"Sand" : "모래/먼지",
				"Ash" : "안개",
				"Squall" : "강우",
				"Tornado" : "폭풍",
				"Clear" : "맑음",
				"Clouds" : "구름"
		};
		
		function getWeather(lat, lng){
			const url = `https://api.openweathermap.org/data/2.5/weather?lat=\${lat}&lon=\${lng}&appid=\${APIKEY}&units=metric`;
			
			let geocoder = new kakao.maps.services.Geocoder();

			let coord = new kakao.maps.LatLng(lat, lng);
			let callback = function(result, status) {
				if (status === kakao.maps.services.Status.OK) {
					document.querySelector("#main-img-text-content-city").innerText = result[0].address.region_3depth_name;
				}
			}
			geocoder.coord2Address(coord.getLng(), coord.getLat(), callback);
			fetch(url)
				.then((response) => response.json())
				.then((data) => {
					let weather = `<i class="bi bi-\${weather_icons[data.weather[0].main]}"></i> \${weathers[data.weather[0].main]} \${parseFloat(data.main.temp).toFixed(1)} °C`;
					document.querySelector("#main-img-text-content-weather").innerHTML = weather;
				});
		}
	
	getLocation();
	</script>
</body>

</html>