<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/header.jsp"%>
<c:if test="${empty user}">
	<script type="text/javascript">
		alert("정상적인 URL 접근이 아닙니다.");
		location.href = "${root}/user";
	</script>
</c:if>
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
			<form id="joinForm">
				<h2 class="form__title">information</h2>
				<div class="group">
					<h6 width="150px">아이디</h6>
					<span class="input_area"><input type="text" maxlength="13"
						name="userId" value="${user.userId}" readonly></span>

					<h6>이름</h6>
					<span class="input_area"><input type="text" maxlength="5"
						name="userName" value="${user.userName}" readonly></span>

					<h6>이메일</h6>
					<span class="input_area"><input type="email" name="email"
						value="${user.userEmail}" readonly></span>

					<h6>휴대폰 번호</h6>
					<span class="input_area"><input type="text" maxlength="11"
						name="phone" value="${user.phone}" readonly></span>


				</div>
				<div class="btnArea">
					<a href="#" class="account_btn" style="visibility: hidden"></a> 
						<a href="#" id="btn-mv-modify" class="account_btn">정보수정</a> 
						<a href="#" class="account_btn" style="visibility: hidden"></a> 
					

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
		document.querySelector("#btn-mv-modify").addEventListener("click",
				function() {
					let form = document.querySelector("#form-account");
					form.setAttribute("action", "${root}/user");
					document.querySelector("#act").value = "mvmodify";
					form.submit();
				});

		document.querySelector("#btn-cancel").addEventListener("click",
				function() {
					location.href = "${root}/user";
				});
	</script>
</body>

</html>