<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/WEB-INF/views/common/header.jsp"%>
<!DOCTYPE html>
<html lang="ko">
  <head>
    <meta charset="utf-8" />
    <meta content="width=device-width, initial-scale=1.0" name="viewport" />

    <meta content="" name="description" />
    <meta content="" name="keywords" />

    <title>Enjoy Trip</title>

    <link href="${root}/assets/css/route.css" rel="stylesheet" />

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
    <div class="container">
      <section id="blog" class="blog" style="padding-top: 0">
        <div class="title">
          <h2 class="text-center mt-5">내 여행 계회 세우기</h2>
          <p class="text-center mb-5">나만의 여행 계획을 세워보세요!</p>
        </div>
          <div class="container pe-4">
            <div class="row">
              <div class="col-lg-9">
                <div id="kakaomap" style="width: 100%; min-height: 700px">
                  <div
                    class="row p-2 justify-content-end"
                    style="
                      z-index: 99;
                      position: relative;
                      background: rgba(255, 255, 255, 0.8);
                    "
                  >
                    <div class="col-md-3">
                      <input
                        type="text"
                        class="form-control col-auto"
                        id="place-input"
                        placeholder="장소 검색"
                      />
                    </div>
                    <div class="d-grid col-sm-2">
                      <button class="btn" id="place-search-btn">검색</button>
                    </div>
                  </div>
                </div>
              </div>
              <div class="col-lg-3">
                <div class="sidebar row h-100 d-flex text-center justify-content-center">
                  <div class="sidebar-title">여행 계획</div>
                  <div class="row">
                    <div
                      class="w-100 px-1"
                      id="add-place-list"
                      style="height: 500px; overflow: auto; overflow-x:hidden"
                    ></div>
                  </div>
                  <div
                    class="d-grid gap-2 px-4 my-3"
                    style="position: absolute; bottom: 0"
                  >
                  </div>
                  <div class="row ms-1 pe-4">
                    <button
                      type="button"
                      class="btn btn-primary my-1"
                      id="search-path-btn"
                    >
                      경로탐색
                    </button>
                    <button
                      type="button"
                      class="btn btn-primary my-1"
                      id="save-plan-btn"
                    >
                      저장
                    </button>
                  </div>
                </div>
              </div>

            </div>
          </div>
        <a href="#" class="back-to-top d-flex align-items-center justify-content-center"
          ><i class="bi bi-arrow-up-short"></i
        ></a>
        </section>
      </div>
<script type="text/javascript" src="${root}/assets/js/kakaoMap.js"></script>
<script>
const mapContainer = document.getElementById("kakaomap");
const inputPlace = document.getElementById("place-input");
const placeSearchBtn = document.getElementById("place-search-btn");
const placeList = document.getElementById("add-place-list");
let map = null;
let markerList = null;
let infoList = null;
let planMarker = [];

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

mapContainer.addEventListener("mousedrag", function() {
  closeInfo();
})

placeSearchBtn.addEventListener("click", async function() {
    
    delMarker();
    if (inputPlace.value) {
        const places = await fetch("/content/searchPlace/"+inputPlace.value)
            .then((response) => response.json());
        closeInfo();
        infoList = [];
        markerList = [];
        var bounds = new kakao.maps.LatLngBounds();

        for (var i = 0; i < places.length; i++) {
        displayMarker(places[i]);
        bounds.extend(new kakao.maps.LatLng(Number(places[i].mapY), Number(places[i].mapX)));
        }

        setBounds(bounds);
    }
});

// mapContainer.addEventListener("click", () => {
//   closeInfo();
// })

// 지도에 마커를 표시하는 함수입니다
function displayMarker(place) {
  // 마커를 생성하고 지도에 표시합니다
  const pos = new kakao.maps.LatLng(Number(place.mapY), Number(place.mapX));
    const marker = new kakao.maps.Marker({
        map : map,
      position: pos,
      title: place.title,
      image: new kakao.maps.MarkerImage("${root}/assets/img/marker/"+locImage[place.contentType]+".png",
        new kakao.maps.Size(37, 37)
      ),
    });

  const infoContent = makeInfo(
      place.contentId,
      place.title,
      encodeURI(place.placeImg),
      place.address,
      place.zipCode,
      place.tel
      );

  const infoWindow = new kakao.maps.InfoWindow({
    content: infoContent,
    removable: true,
  });
  markerList.push(marker);
  infoList.push(infoWindow);

  // 마커에 클릭이벤트를 등록합니다
  kakao.maps.event.addListener(marker, "click", makeClickListener(marker, infoWindow, place));
}

function setCenter(pos) {
  map.setCenter(pos);
}

function delMarker() {
  if (markerList) {
    markerList.forEach((marker) => {
      marker.setMap(null);
    });
  }
}

// 마커 클릭 시 해당 콘텐츠 정보 표시
function makeClickListener(marker, infoWindow, place) {
  return function () {
    // 마커를 클릭하면 장소명이 인포윈도우에 표출됩니다
    closeInfo();
    infoWindow.open(map, marker);
    document
      .getElementById("add-place-btn")
      .addEventListener("click", function () {
        // const placeList = document.getElementById("add-place-list");
        planMarker.push(marker);
        markerList = markerList.filter((el) => el !== marker);
        let img = encodeURI(place.placeImg);
        if(img == "null"){
          img = "<c:out value='${root}/assets/img/no_img.jpg'/>";
        }
        let tel = place.tel;
        if(tel == null){
          tel = "없어요ㅠㅠ";
        }
        let planInfoPlace = `
          <div class="text-start border py-1 rounded-2 my-1 placeList-item draggable"
                draggable="true"
                ondragstart="handlerDragStart(this)"
                ondragend="handlerDragEnd(this)">
            <div class="row">
              <div class="col-md-5 pe-1">
                <img src=\${img} class="img-fluid"/>
              </div>
              <div class="col-md-7 ps-0">
                <div class="fw-bold text-truncate" id="place-title" style="background:#eee; margin-top:-0.21rem; border-radius:0 0.2rem 0 0; font-size:0.8rem">
                  \${place.title}
                </div>
                <div class="mt-1">
                  <div style="font-size:0.3em">-\${place.address}</div>
                  <div style="font-size:0.2em">-\${tel}</div>
                </div>
              </div>
              </div class="mx-5">
                <textarea class="form-control my-1" placeholder="간단 메모" style="font-size: 0.3em"></textarea>
                <div class="row d-flex justify-content-between align-items-center px-1" style="font-size:0.7em">
                  <div class="col-auto" id="info-url" style="color:blue">
                    <a href="http://data.visitkorea.or.kr/resource/\${place.contentId}" target="_blank">상세보기</a>
                  </div>
                  <button class="col-auto btn btn-sm btn-danger me-2" onclick=delPlaceInfo(this) style="font-size:0.7rem">삭제</button>
                </div>
              </div>
            </div>
          </div>
        `;
        placeList.innerHTML += planInfoPlace;
      });
    setCenter(new kakao.maps.LatLng(Number(place.mapY), Number(place.mapX)));
  };
}

function delPlaceInfo(e){
  const placeList = document.getElementById("add-place-list");
  const parentEl = e.closest("#add-place-list > div");
  placeList.removeChild(parentEl);
}

// 콘텐츠 정보 표시 tag 설정
function makeInfo(contentId, title, img, addr, zipCode, tel) {
	if(img == "null"){
		img = "<c:out value='${root}/assets/img/no_img.jpg'/>";
	}
	var contents = `<div style="max-width:400px">
	    <div class="px-2 py-1 fw-bold fs-5" style="background:#eee">\${title}</div>
	    <div class="row">
	    <div class="col-md-5 pe-0">
	      <img src=\${img} class="img-thumbnail"/>
	    </div>
	    <div class="col-md-7 align-self-center flex-wrap mb-1 ps-1">
        <div class="h-100">
	      <div class="fw-bold text-truncate">\${addr}</div>`;
	
	if(zipCode != null){
		contents += `<div>(우) \${zipCode}</div>`;
	}else{
		contents += `<div>(우) 없어요 ㅠㅠ</div>`;
	}
	      
	if(tel != null){
		contents += `<div>(전화번호) \${tel}</div>`;
	}else{
		contents += `<div>(전화번호) 없어요 ㅠㅠ</div>`;
	}
    contents += `
        </div>
        <div class="row d-flex justify-content-between align-items-center">
            <div class="col-auto" id="info-url" style="color:blue"><a href="http://data.visitkorea.or.kr/resource/\${contentId}" target="_blank">상세보기</a></div>
            <button class="col-auto btn btn-sm btn-primary me-3" id="add-place-btn">추가</button>
        </div>`
  return contents +
    `
    </div>
    </div>
  </div>`;
}

// Place List Item Event
function handlerDragStart(e){
  e.classList.add('dragging');
}

function handlerDragEnd(e){
  e.classList.remove('dragging');
}

function getDragAfterEl(box, y){
  const draggingItems = [...placeList.querySelectorAll('.draggable:not(.dragging)')];
  return draggingItems.reduce((closest, child) => {
    const box = child.getBoundingClientRect();
    const offset = y - box.top - box.height / 2;
    if(offset < 0 && offset > closest.offset){
      return {offset: offset, element:child};
    }else{
      return closest
    }
  }, {offset: Number.NEGATIVE_INFINITY}).element;
}

placeList.addEventListener('dragover', (e) => {
  e.preventDefault();
  const afterEl = getDragAfterEl(placeList, e.clientY);
  const draggable = document.querySelector('.dragging');
  placeList.insertBefore(draggable, afterEl);
})

getLocation();
</script>
  </body>
</html>
