<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/views/common/header.jsp"%>
<!DOCTYPE html>
<html lang="ko">

<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Enjoy Trip</title>
<!-- Favicons -->

<link href="${root}/assets/css/account.css" rel="stylesheet">

</head>

<body>
	<div class="outer">

		<div id="joinInfoArea">
			<form id="joinForm" method="POST" action="">
				<input type="hidden" id="act" name="act" value=""> 
				<h2 class="form__title">비밀번호 찾기</h2>
				<div class="password_group">

					<h6 style="text-align : center">가입한 계정정보를 입력해주세요.</h6>
					<div class="password_input_area">
						<input type="text" name="userid" placeholder="아이디를 입력해주세요">
					</div>
					
					<div class="password_input_area">
						<input type="email" name="useremail" placeholder="이메일을 입력해주세요">
					</div>


				</div>
				<div class="password_btnArea">
					<a href="#" class="account_btn" style="visibility: hidden"></a> 
						<a href="#" id="btn-certfication" class="account_btn">인증번호 받기</a> 
						<a href="#" class="account_btn" style="visibility: hidden"></a> 
					

				</div>
			</form>
		</div>
	</div>

	<!-- ======= Footer ======= -->
	<%@ include file="/WEB-INF/views/common/footer.jsp"%>
	<!-- End Footer -->
	<script>
	document.querySelector("#btn-certfication").addEventListener("click",
			function() {
				let form = document.querySelector("#joinForm");
				form.setAttribute("action", "${root}/user");
				document.querySelector("#act").value = "forgot_password";
				form.submit();
			});
	</script>
</body>

</html>