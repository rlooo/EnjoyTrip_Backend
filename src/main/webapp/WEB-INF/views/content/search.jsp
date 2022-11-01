<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> <%@ taglib
prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="root" value="${pageContext.request.contextPath}"></c:set>
<%@ include file="/common/header.jsp"%>
<!DOCTYPE html>
<html lang="ko">
  <head>
    <meta charset="utf-8" />
    <meta content="width=device-width, initial-scale=1.0" name="viewport" />

    <meta content="" name="description" />
    <meta content="" name="keywords" />

    <title>Enjoy Trip</title>

    <link href="${root}/assets/css/search.css" rel="stylesheet" />
    <link href="${root}/assets/css/map.css" rel="stylesheet" />

    <!-- =======================================================
  * Template Name: Flexor - v4.8.0
  * Template URL: https://bootstrapmade.com/flexor-free-multipurpose-bootstrap-template/
  * Author: BootstrapMade.com
  * License: https://bootstrapmade.com/license/
  ======================================================== -->
    <script
      type="text/javascript"
      src="//dapi.kakao.com/v2/maps/sdk.js?appkey=04fd4a45831b6d0e82f7e634d98c339d&libraries=services,clusterer"
    ></script>
    <script
      type="text/javascript"
      src="//dapi.kakao.com/v2/maps/sdk.js?appkey=04fd4a45831b6d0e82f7e634d98c339d"
    ></script>
  </head>

  <body>
    <!-- ======= Header ======= -->

    <!-- End Header -->

    <!-- body -->
    <section class="row">
      <div class="title">
        <h2 class="text-center mt-5">지역으로 찾기</h2>
        <p class="text-center mb-5">가고 싶은 여행 지역을 찾아보세요!</p>
      </div>

      <div class="select-box">
        <div class="row col-md-12 justify-content-center mb-2">
          <div class="form-group col-md-2">
            <select class="form-select bg-secondary text-light" id="sido"></select>
          </div>
          <div class="form-group col-md-2">
            <select class="form-select bg-secondary text-light" id="gugun">
              <option value="">구군선택</option>
            </select>
          </div>
          <div class="form-group col-md-2">
            <select class="form-select bg-secondary text-light" id="kind">
              <option value="">전체</option>
              <option value="12">관광지</option>
              <option value="14">문화시설</option>
              <option value="15">행사/공연/축제</option>
              <option value="25">여행코스</option>
              <option value="28">레포츠</option>
              <option value="32">숙박</option>
              <option value="38">쇼핑</option>
              <option value="39">음식점</option>
            </select>
          </div>
          <div class="form-group col-md-2">
            <button type="button" id="list-btn" class="btn btn-outline-dark">
              여행지정보 가져오기
            </button>
          </div>
        </div>
      </div>
    </section>
    <section class="row" id="result-area">
      <div class="col-md-7 border-2" id="map" style="height: 600px; border: 1px solid white"></div>
      <div class="col-md-5">
        <button id="next" type="button" class="btn btn-outline-dark">다음</button>
        <button id="previous" type="button" class="btn btn-outline-dark">이전</button>
        <!-- <button id="previous">이전</button>
      <button id="next">다음</button> -->
        <div class="container">
          <table class="table table-striped table-hover">
            <thead>
              <tr>
                <th>이미지</th>
                <th>가게이름</th>
                <th>분류</th>
                <th>전화번호</th>
                <th>주소</th>
              </tr>
            </thead>
            <tbody id="location-list"></tbody>
          </table>
        </div>
      </div>
      <!-- ======= Footer ======= -->
      <footer id="footer">
        <div class="container d-lg-flex py-4">
          <div class="me-lg-auto text-center text-lg-start">
            <div class="copyright">
              &copy; Copyright <strong><span>Flexor</span></strong
              >. All Rights Reserved
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
            <a href="#" class="twitter"><i class="bx bxl-twitter"></i></a>
            <a href="#" class="facebook"><i class="bx bxl-facebook"></i></a>
            <a href="#" class="instagram"><i class="bx bxl-instagram"></i></a>
            <a href="#" class="google-plus"><i class="bx bxl-skype"></i></a>
            <a href="#" class="linkedin"><i class="bx bxl-linkedin"></i></a>
          </div>
        </div>
      </footer>
      <!-- End Footer -->

      <a href="#" class="back-to-top d-flex align-items-center justify-content-center"
        ><i class="bi bi-arrow-up-short"></i
      ></a>
    </section>

    <script>
      const sido = document.getElementById("sido");
      const gugun = document.getElementById("gugun");
      const locations = document.getElementById("location-list");
      const kind = document.getElementById("kind");
      const nxtBtn = document.getElementById("next");
      const prvBtn = document.getElementById("previous");
      let sidoCode = null;
      let gugunCode = null;
      let kindCode = null;
      let pageNo = 1;

      let korlist = null;

      window.onload = (function () {
        fetch("${root}/map?action=loadDo")
          .then((datalist) => datalist.json())
          .then((dtolist) => {
            let list = `<option value="" disabled selected>시도선택</option>`;
            dtolist.forEach((dto) => {
              list += `<option value="\${dto.doId}">\${dto.doName}</option>`;
            });
            sido.innerHTML = list;
          });
      })();

      sido.addEventListener("change", () => {
        const curCode = sido.options[sido.selectedIndex].value;
        fetch(`${root}/map?action=loadGugun&areaCode=\${curCode}`)
          .then((datalist) => datalist.json())
          .then((dtolist) => {
            let list = `<option value="" disabled selected>구군선택</option>`;
            dtolist.forEach((dto) => {
              list += `<option value="\${dto.gugunCode}">\${dto.name}</option>`;
            });
            gugun.innerHTML = list;
          });
      });

      function getList(sidoCode, gugunCode, kindCode, pageN) {
        fetch(
          `${root}/map?action=search&sidoCode=\${sidoCode}&gugunCode=\${gugunCode}&kindCode=\${kindCode}&page=\${pageNo}`
        )
          .then((datalist) => datalist.json())
          .then((dtolist) => {

            if(!dtolist && pageN == 1){
              alert('검색결과가 없습니다.');
              return;
            }

            if(!dtolist) {
              alert('마지막 페이지입니다.')
              pageNo = pageN - 1;
              return;
            }
            
            let viewList = new Array();
            let content = ``;
            
            for (let i = 0; i < dtolist.length; i++) {
              viewList.push(dtolist[i]);
            }
            console.log('line 195')
            makeList(viewList);
          });
      }

      function makeList(views) {
        while (locations.firstChild) {
          console.log('remove 202')
          locations.removeChild(locations.firstChild);
        }
        views.forEach((view) => {
          let tr = document.createElement("tr");
          tr.setAttribute("data-mapx", view.mapx);
          tr.setAttribute("data-mapy", view.mapy);
          let itd = document.createElement("td");
          let img = document.createElement("img");
          img.setAttribute("src", view.firstImage == "" ? view.fistImage2 : view.firstImage);
          img.style.height = "50px";
          img.style.width = "50px";
          itd.appendChild(img);
          let td1 = document.createElement("td");
          td1.appendChild(document.createTextNode(view.title));
          let td2 = document.createElement("td");
          let contype;
          
          switch (view.contentType) {
                case 12:
                  contype = "관광지";
                  break;
                case 14:
                  contype = "문화시설";
                  break;
                case 15:
                  contype = "행사/공연/축제";
                  break;
                case 25:
                  contype = "여행코스";
                  break;
                case 28:
                  contype = "레포츠";
                  break;
                case 32:
                  contype = "숙박";
                  break;
                case 38:
                  contype = "쇼핑";
                  break;
                case 39:
                  contype = "음식점";
                  break;
          }
          td2.appendChild(document.createTextNode(contype));

          let td3 = document.createElement("td");
          td3.appendChild(document.createTextNode(view.tel));
          let td4 = document.createElement("td");
          td4.appendChild(document.createTextNode(view.addr1 + " " + view.addr2));

          tr.appendChild(itd);
          tr.appendChild(td1);
          tr.appendChild(td2);
          tr.appendChild(td3);
          tr.appendChild(td4);

          tr.addEventListener("click", function () {
            console.log(view.mapx + "             " + view.mapy);
            panTo(view.mapx, view.mapy);
          });

          locations.appendChild(tr);
        });
      }

      document.getElementById("list-btn").addEventListener("click", () => {
        sidoCode = sido.options[sido.selectedIndex].value;
        if (sidoCode == "") {
          alert("시/도를 선택해주세요");
          return;
        }
        gugunCode = gugun.options[gugun.selectedIndex].value;
        if (gugunCode == "") {
          alert("구/군을 선택해주세요");
          return;
        }
        kindCode =
          kind.options[kind.selectedIndex].value == ""
            ? "0"
            : kind.options[kind.selectedIndex].value;
        // kindCode = kindCode == "" ? "0" : kindCode;
        pageNo = 1;

        getList(sidoCode, gugunCode, kindCode, pageNo);
      });

      nxtBtn.addEventListener("click", () => {
        pageNo++;
        getList(sidoCode, gugunCode, kindCode, pageNo);
      });

      prvBtn.addEventListener("click", () => {
        if(pageNo == 1) {
          alert('첫번쨰 페이지입니다.');
          return;
        }
        pageNo--;
        getList(sidoCode, gugunCode, kindCode, pageNo);
      });
    </script>
    <script src="${root}/assets/js/map.js"></script>
  </body>
</html>
