<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="root" value="${pageContext.request.contextPath}" />
<c:if test="${cookie.user_id.value ne null}">
	<c:set var="idck" value="checked"></c:set>
	<c:set var="svid" value="${cookie.user_id.value}"></c:set>
</c:if>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>Enjoy Trip</title>
<!-- Favicons -->
<link href="${root}/assets/img/logo.png" rel="icon" />
<link href="${root}/assets/css/login.css" rel="stylesheet" />
</head>

<body>
	<div class="container right-panel-active">
		<!-- Sign Up -->
		<div class="container__form container--signup">
			<form action="#" class="form" id="form1">
				<input type="hidden" name="act" value="join">
				<h2 class="form__title">회원가입</h2>
				<input type="text" placeholder="Id" class="input" id="sign-up-id"
					name="sign-up-id" />
				<div id="idcheck-result"></div>
				<input type="text" placeholder="Name" class="input"
					id="sign-up-name" name="sign-up-name" /> <input type="email"
					placeholder="Email" class="input" id="sign-up-email"
					name="sign-up-email" /> <input type="password"
					placeholder="Password" class="input" id="sign-up-password"
					name="sign-up-password" />
				<button type="submit" class="btn">회원가입</button>
			</form>
		</div>

		<!-- Sign In -->
		<div class="container__form container--signin">
			<form action="" method="POST" class="form" id="form2">
				<input type="hidden" name="act" value="login">
				<h2 class="form__title">로그인</h2>
				<input type="text" placeholder="Id" class="input" id="sign-in-id"
					name="sign-in-id" value="${svid}" />
				<input type="password" placeholder="Password" class="input"
					id="sign-in-password" name="sign-in-password" /> <a
					href="${root}/user/forgot_password.jsp" class="link"
					id="btn-forgot-password">Forgot your password?</a>
        <div class="form-check mb-3 float-end">
					<input class="form-check-input" type="checkbox" value="ok"
						id="saveid" name="saveid" ${idck} /> <label
						class="form-check-label" for="saveid"> 아이디 저장 </label>
				</div>
				<button class="btn">로그인</button>
			</form>
		</div>

		<!-- Overlay -->
		<div class="container__overlay">
			<div class="overlay">
				<div class="overlay__panel overlay--left">
					<button class="btn" id="signIn">로그인</button>
				</div>
				<div class="overlay__panel overlay--right">
					<button class="btn" id="signUp">회원가입</button>
				</div>
			</div>
		</div>
	</div>

	<script>
      const signInBtn = document.querySelector("#signIn");
      const signUpBtn = document.querySelector("#signUp");
      const fistForm = document.querySelector("#form1");
      const secondForm = document.querySelector("#form2");
      const container = document.querySelector(".container");

      signInBtn.addEventListener("click", () => {
        container.classList.remove("right-panel-active");
      });

      signUpBtn.addEventListener("click", () => {
        container.classList.add("right-panel-active");
      });

      let isUseId = false;
      document.querySelector("#sign-up-id").addEventListener("keyup", function () {
        let userid = this.value;
        console.log(userid);
        let resultDiv = document.querySelector("#idcheck-result");
        if (userid.length < 5 || userid.length > 16) {
          resultDiv.setAttribute("class", "mb-3 text-dark");
          resultDiv.setAttribute("style", "font-size:12px;");
          resultDiv.textContent = "아이디는 6자 이상 16자 이하 입니다.";
          isUseId = false;
        } else {
          fetch("${root}/user?act=idcheck&userid=" + userid)
            .then((response) => response.text())
            .then((data) => {
              console.log(data);
              if (data == 0) {
                resultDiv.setAttribute("class", "mb-3 text-primary");
                resultDiv.textContent = userid + "는 사용할 수 있습니다.";
                isUseId = true;
              } else {
                resultDiv.setAttribute("class", "mb-3 text-danger");
                resultDiv.textContent = userid + "는 사용할 수 없습니다.";
                isUseId = false;
              }
            });
        }
      });

      fistForm.addEventListener("submit", () => {
        if (!document.querySelector("#sign-up-id").value) {
          alert("항목을 모두 입력해주세요.");
          return;
        } else if (!document.querySelector("#sign-up-name").value) {
          alert("항목을 모두 입력해주세요.");
          return;
        } else if (!document.querySelector("#sign-up-email").value) {
          alert("항목을 모두 입력해주세요.");
          return;
        } else if (!document.querySelector("#sign-up-password").value) {
          alert("항목을 모두 입력해주세요.");
          return;
        } else {
          let form = document.querySelector("#form1");
          form.setAttribute("action", "${root}/user");
        }
      });
      
      secondForm.addEventListener("submit", () => {
        if (!document.querySelector("#sign-in-id").value) {
          alert("아이디를 입력해주세요.");
          return;
        } else if (!document.querySelector("#sign-in-password").value) {
          alert("비밀번호를 입력해주세요.");
          return;
        } else {
          let form = document.querySelector("#form2");
          form.setAttribute("action", "${root}/user");
        }
      });
    </script>
</body>
</html>
