<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core"%> <%@ include
file="/WEB-INF/views/common/header.jsp"%>
<!DOCTYPE html>
<html lang="ko">
  <head>
    <meta charset="utf-8" />
    <meta content="width=device-width, initial-scale=1.0" name="viewport" />

    <meta content="" name="description" />
    <meta content="" name="keywords" />

    <title>Enjoy Trip</title>

    <link href="${root}/assets/css/search.css" rel="stylesheet" />

    <!-- =======================================================
  * Template Name: Flexor - v4.8.0
  * Template URL: https://bootstrapmade.com/flexor-free-multipurpose-bootstrap-template/
  * Author: BootstrapMade.com
  * License: https://bootstrapmade.com/license/
  ======================================================== -->
    <script
      type="text/javascript"
      src="//dapi.kakao.com/v2/maps/sdk.js?appkey=27e1e8bef9f79b21bbe6735035a00943&libraries=services,clusterer"
    ></script>
    <script
      type="text/javascript"
      src="//dapi.kakao.com/v2/maps/sdk.js?appkey=27e1e8bef9f79b21bbe6735035a00943"
    ></script>
  </head>

  <body>
    <!-- ======= Header ======= -->

    <!-- End Header -->

    <!-- body -->
    <section id="blog" class="blog mt-3" style="padding-top: 0">
      <div class="title">
        <h2 class="text-center mt-4">지역으로 찾기</h2>
        <p class="text-center mb-4">가고 싶은 여행 지역을 찾아보세요!</p>
      </div>
      <div class="container">
        <div id="map" style="width: 100%; min-height: 700px">
          <div
            class="row p-2 justify-content-end"
            style="
              z-index: 99;
              position: relative;
              background: rgba(255, 255, 255, 0.8);
            "
          >
            <div class="col-md-2">
              <select class="form-select" id="select-state">
                <option value="" disabled selected style="display: none">
                  시/도 선택
                </option>
              </select>
            </div>
            <div class="col-md-2">
              <select class="form-select col-md-3" id="select-region">
                <option value="" disabled selected style="display: none">
                  구/군 선택
                </option>
              </select>
            </div>
            <div class="col-md-2">
              <select class="form-select" id="select-content">
                <option value="">전체</option>
              </select>
            </div>
          </div>
        </div>
      </div>
      <a
        href="#"
        class="back-to-top d-flex align-items-center justify-content-center"
        ><i class="bi bi-arrow-up-short"></i
      ></a>
    </section>

    <script src="${root}/assets/js/smoothScroll.js"></script>
    <script type="text/javascript" src="${root}/assets/js/kakaoMap.js"></script>
    <script>
      const selectState = document.querySelector("#select-state");
      const selectRegion = document.querySelector("#select-region");
      const selectContent = document.querySelector("#select-content");

      const mapContainer = document.getElementById("map");
      const contentList = new Set();
      let map = null;
      let markerList = null;
      let itemList = null;
      let infoList = null;

      let locImage = {
        12: "tour",
        14: "culture",
        15: "event",
        25: "travel",
        28: "sports",
        32: "bed",
        38: "shop",
        39: "restaurant",
      };

      let contentName = {
        12: "관광지",
        14: "문화시설",
        15: "행사/공연/축제",
        25: "여행코스",
        28: "레포츠",
        32: "숙박",
        38: "쇼핑",
        39: "음식점",
      };

      // 시,도 데이터로 options 설정
      async function getStateCode() {
        const items = await fetch("/content/getArea").then((response) =>
          response.json(),
        );

        items.forEach((item) => {
          const option = document.createElement("option");
          option.setAttribute("value", item.areaCode);
          option.textContent = item.areaName;

          selectState.appendChild(option);
        });
      }

      // 시,도 선택시 해당 관할 구,군으로 options 설정
      selectState.addEventListener("change", async function () {
        const items = await fetch(
          "/content/getSigungu/" + this.options[this.selectedIndex].value,
        ).then((response) => response.json());

        selectRegion.length = 0;
        initRegion();
        initContent();
        items.forEach((item) => {
          const option = document.createElement("option");
          option.setAttribute("value", item.sigunguCode);
          option.textContent = item.sigunguName;

          selectRegion.appendChild(option);
        });
      });

      // 구,군 선택시 지역내 콘텐츠 지도에 표시
      selectRegion.addEventListener("change", async function () {
        const items = await fetch(
          "/content/getPlace/" +
            selectState.options[selectState.selectedIndex].value +
            "/" +
            this.options[this.selectedIndex].value,
        ).then((response) => response.json());

        itemList = [];
        for (let idx = 0; idx < items.length; idx++) {
          itemList.push({
            id: idx,
            contenttypeid: items[idx].contentType,
          });
          contentList.add(items[idx].contentType);
        }

        initContent();
        contentList.forEach((contentType) => {
          const initOption = document.createElement("option");
          initOption.setAttribute("value", contentType);
          initOption.textContent = contentName[contentType];
          selectContent.appendChild(initOption);
        });

        delMarker();
        closeInfo();
        infoList = [];
        showMarker(items);
      });

      // 콘텐츠 선택시 해당 콘텐츠를 지도에 표시
      selectContent.addEventListener("change", function () {
        viewMarker();
      });

      // 구,군 tag 초기화
      function initRegion() {
        const initOption = document.createElement("option");
        initOption.setAttribute("value", "");
        initOption.setAttribute("disabled", "disabled");
        initOption.setAttribute("selected", "selected");
        initOption.style.display = "none";
        initOption.textContent = "구/군 선택";
        selectRegion.appendChild(initOption);
      }

      // 콘텐츠 tag 초기화
      function initContent() {
        selectContent.length = 0;
        const initOption = document.createElement("option");
        initOption.setAttribute("value", "");
        initOption.setAttribute("selected", "selected");
        initOption.textContent = "전체";
        selectContent.appendChild(initOption);
      }

      // 마커 위치 및 이미지 설정
      function showMarker(items) {
        markerList = [];
        const bounds = new kakao.maps.LatLngBounds();
        items.forEach((item) => {
          const infoContent = makeInfo(
            item.title,
            encodeURI(item.placeImg),
            item.address,
            item.zipCode,
            item.tel,
          );

          const infoWindow = new kakao.maps.InfoWindow({
            content: infoContent,
            removable: true,
          });
          infoList.push(infoWindow);

          const pos = new kakao.maps.LatLng(
            Number(item.mapY),
            Number(item.mapX),
          );
          const marker = new kakao.maps.Marker({
            position: pos,
            title: item.title,
            clickable: true,
            image: new kakao.maps.MarkerImage(
              "${root}/assets/img/marker/" +
                locImage[item.contentType] +
                ".png",
              new kakao.maps.Size(37, 37),
            ),
          });
          kakao.maps.event.addListener(
            marker,
            "click",
            makeClickListener(marker, infoWindow),
          );
          markerList.push(marker);
          bounds.extend(pos);
        });
        viewMarker();
        setBounds(bounds);
      }

      // 포시되는 마커 설정
      function viewMarker() {
        closeInfo();
        const contentId =
          selectContent.options[selectContent.selectedIndex].value;
        if (itemList) {
          for (let idx = 0; idx < itemList.length; idx++) {
            if (itemList[idx].contenttypeid == contentId || contentId === "") {
              markerList[idx].setMap(map);
            } else {
              markerList[idx].setMap(null);
            }
          }
        }
      }

      // 지역 수정 시 마커 삭제
      function delMarker() {
        if (markerList) {
          markerList.forEach((marker) => {
            marker.setMap(null);
          });
        }
      }

      // 마커 클릭 시 해당 콘텐츠 정보 표시
      function makeClickListener(marker, infoWindow) {
        return function () {
          closeInfo(infoList);
          infoWindow.open(map, marker);
        };
      }
      // 콘텐츠 정보 표시 tag 설정
      function makeInfo(title, img, addr, zipCode, tel) {
        if (img == "null") {
          console.log(img);
          img = "<c:out value='${root}/assets/img/no_img.jpg'/>";
        }
        var contents = `<div style="max-width:400px">
	    <div class="px-2 py-1 fw-bold fs-5" style="background:#eee">\${title}</div>
	    <div class="row">
	    <div class="col-md-5">
	      <img src=\${img} class="img-thumbnail"/>
	    </div>
	    <div class="col-md-7 align-self-center flex-wrap">
	      <div class="fw-bold text-truncate">\${addr}</div>`;

        if (zipCode != null) {
          contents += `<div>(우) \${zipCode}</div>`;
        } else {
          contents += `<div>(우) 없어요 ㅠㅠ</div>`;
        }

        if (tel != null) {
          contents += `<div>(전화번호) \${tel}</div>`;
        } else {
          contents += `<div>(전화번호) 없어요 ㅠㅠ</div>`;
        }
        return (
          contents +
          `</div>
    </div>
  </div>`
        );
      }

      getLocation();
      getStateCode();
      initRegion();
    </script>
  </body>
</html>
