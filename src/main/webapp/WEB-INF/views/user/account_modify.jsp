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
				<input type="hidden" id="act" name="act" value=""> <input
					type="hidden" id="userid" name="userid" value="${user.userId}" />
				<h2 class="form__title">information</h2>
				<div class="group">
				
					<h6 width="150px">아이디</h6>
					<span class="input_area"><input type="text" maxlength="13"
						name="userId" value="${user.userId}" readonly></span>


					<h6>이름</h6>
					<span class="input_area"><input type="text" maxlength="5"
						name="userName" value="${user.userName}"></span>

					<h6>이메일</h6>
					<span class="input_area"><input type="email" name="email"
						value="${user.userEmail}"></span>

					<h6>휴대폰 번호</h6>
					<span class="input_area"><input type="text" maxlength="11"
						name="phone" value="${user.phone}"></span>

				</div>
				<div class="btnArea">
					<a href="#" id="btn-modify" class="account_btn">수정완료</a> <a
						href="#" id="btn-cancel" class="account_btn">취소</a> <a href="#"
						id="btn-unregister" class="account_btn">회원탈퇴</a>
				</div>
			</form>
		</div>
	</div>
	<form id="form-account" method="get" action="">
		<input type="hidden" id="act" name="act" value=""> <input
			type="hidden" id="userid" name="userid" value="${user.userId}" />

	</form>
	<!-- ======= Footer ======= -->
	<%@ include file="/WEB-INF/views/common/footer.jsp"%>
	<!-- End Footer -->
	<script>
		document.querySelector("#btn-unregister").addEventListener("click",
				function() {
					let form = document.querySelector("#joinForm");
					form.setAttribute("action", "${root}/user");
					document.querySelector("#act").value = "delete";
					form.submit();
				});

		document.querySelector("#btn-cancel").addEventListener("click",
				function() {
					let form = document.querySelector("#form-account");
					form.setAttribute("action", "${root}/user");
					document.querySelector("#act").value = "mvmodify";
					form.submit();
				});

		document.querySelector("#btn-modify").addEventListener("click",
				function() {
					let form = document.querySelector("#joinForm");
					form.setAttribute("action", "${root}/user");
					document.querySelector("#act").value = "modify";
					form.submit();
				});
	</script>
</body>

</html>