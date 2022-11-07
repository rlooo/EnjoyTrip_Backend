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
          <h2 class="text-center mt-4">내 여행 계획 세우기</h2>
          <p class="text-center mb-4">나만의 여행 계획을 세워보세요!</p>
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
                <div class="sidebar row h-100 d-flex text-center justify-content-center py-1">
                  <div>
                    <div class="sidebar-title">여행 계획</div>
                    <input type="text" class="form-control col-auto" id="plan-title" placeholder="여행 계획 제목"/>
                  </div>
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
const polyline = new kakao.maps.Polyline({
  strokeWeight: 5, // 선의 두께 입니다
  strokeColor: '#FFAE00', // 선의 색깔입니다
  strokeOpacity: 0.7, // 선의 불투명도 입니다 1에서 0 사이의 값이며 0에 가까울수록 투명합니다
  strokeStyle: 'solid', // 선의 스타일입니다
  endArrow: true,
});
let map = null;
let clusterer = null;
let markerList = [];
let infoList = [];
let planPlace = [];
let planMarker = [];
let planInfoWindow = [];
let linePath = [];
let planListUUID = [];

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

function allInfoClose(){
  closeInfo(infoList);
  closeInfo(planInfoWindow);
}

placeSearchBtn.addEventListener("click", async function() {
  
  kakao.maps.event.addListener(map, "drag", allInfoClose);
  
  delMarker();
  if (inputPlace.value) {
      let places = await fetch("/content/searchPlace/"+inputPlace.value)
          .then((response) => response.json());
      allInfoClose();
      infoList = [];
      markerList = [];
      if(planMarker.length > 0){
        for(let i = 0; i < planMarker.length; i++){
          markerList.push(Object.assign(new kakao.maps.Marker(), planMarker[i]));
          infoList.push(Object.assign(new kakao.maps.InfoWindow(), planInfoWindow[i]));
        }
      }
      places = places.filter((item) => !planPlace.some((c) => c.contentId == item.contentId));
      var bounds = new kakao.maps.LatLngBounds();

      for (let i = 0; i < places.length; i++) {
        displayMarker(places[i]);
        bounds.extend(new kakao.maps.LatLng(Number(places[i].mapY), Number(places[i].mapX)));
      }
      clusterer = new kakao.maps.MarkerClusterer({
          map: map, // 마커들을 클러스터로 관리하고 표시할 지도 객체 
          averageCenter: true, // 클러스터에 포함된 마커들의 평균 위치를 클러스터 마커 위치로 설정 
          minLevel: 7 // 클러스터 할 최소 지도 레벨 
      });

      clusterer.addMarkers(markerList);

      setBounds(bounds);
  }
});

// 지도에 마커를 표시하는 함수입니다
function displayMarker(place) {
  // 마커를 생성하고 지도에 표시합니다
  const pos = new kakao.maps.LatLng(Number(place.mapY), Number(place.mapX));
  const marker = new kakao.maps.Marker({
    position: pos,
    title: place.title,
    image: new kakao.maps.MarkerImage("${root}/assets/img/marker/"+locImage[place.contentType]+".png",
      new kakao.maps.Size(37, 37)
    ),
  });

  const infoContent = makeInfo(marker, place);

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
  if(clusterer){
    clusterer.removeMarkers(markerList);
  }
}

// 마커 클릭 시 해당 콘텐츠 정보 표시
function makeClickListener(marker, infoWindow, place) {
  return function () {
    // 마커를 클릭하면 장소명이 인포윈도우에 표출됩니다
    allInfoClose();
    infoWindow.open(map, marker);
    setCenter(new kakao.maps.LatLng(Number(place.mapY), Number(place.mapX)));
  };
}

function addPlanPlace(marker, place, uuid){
  planPlace.push(Object.assign({},place));
  planMarker.push(Object.assign(new kakao.maps.Marker(),marker));
  planListUUID.push(uuid);
  addMarkerLinePath(marker);
  for(let i = 0; i < markerList.length; i++){
    if((markerList[i].getPosition().getLat()).toPrecision(14) == place.mapY 
        && (markerList[i].getPosition().getLng()).toPrecision(14) == place.mapX){
        // markerList.splice(i, 1);
        planInfoWindow.push(Object.assign(new kakao.maps.InfoWindow(), infoList[i]));
        // infoList.splice(i, 1);
    }
  }
}

function removePlanPlace(uuid){
  for(let i = 0; i < planListUUID.length; i++){
    if(planListUUID[i] == uuid){
      removeMarkerLinePath(i);
      planMarker[i].setMap(null);
      planListUUID.splice(i, 1);
      planPlace.splice(i, 1);
      planMarker.splice(i, 1);
      planInfoWindow.splice(i, 1);
    }
  }
}

function addMarkerLinePath(marker){
  linePath.push(Object.assign(new kakao.maps.LatLng(), marker.getPosition()));
  if(linePath.length > 1){
    polyline.setMap(null);
    polyline.setPath(linePath);
    polyline.setMap(map);
  }
}

function removeMarkerLinePath(idx){
  linePath.splice(idx, 1);
  polyline.setMap(null);
  polyline.setPath(linePath);
  polyline.setMap(map);
}

function addPlanBtn(marker, place){
  console.log("add check");
  let uuid = self.crypto.randomUUID();
  addPlanPlace(marker, place, uuid);
  let img = encodeURI(place.placeImg);
  if(img == "null"){
    img = "<c:out value='${root}/assets/img/no_img.jpg'/>";
  }
  let tel = place.tel;
  if(tel == null){
    tel = "없어요ㅠㅠ";
  }
  const planInfoDiv = document.createElement("div");
  planInfoDiv.setAttribute("class", "text-start border py-1 rounded-2 my-1 placeList-item draggable");
  planInfoDiv.draggable = true;
  planInfoDiv.addEventListener("dragstart", () => handlerDragStart(planInfoDiv));
  planInfoDiv.addEventListener("dragend", () => handlerDragEnd(planInfoDiv));

  const planInfoContentDiv = document.createElement("div");
  planInfoContentDiv.setAttribute("class", "row");

  const planInfoImgDiv = document.createElement("div");
  planInfoImgDiv.setAttribute("class", "col-md-5 pe-1");

  const planInfoImg = document.createElement("img");
  planInfoImg.setAttribute("src", img);
  planInfoImg.setAttribute("class", "img-fluid");
  planInfoImgDiv.appendChild(planInfoImg);
  planInfoContentDiv.appendChild(planInfoImgDiv);

  const planInfoRightDiv = document.createElement("div");
  planInfoRightDiv.setAttribute("class", "col-md-7 ps-0");

  const planInfoTitleDiv = document.createElement("div");
  planInfoTitleDiv.setAttribute("class", "fw-bold text-truncate");
  planInfoTitleDiv.setAttribute("id", "place-title");
  planInfoTitleDiv.style = "background:#eee; margin-top:-0.21rem; border-radius:0 0.2rem 0 0; font-size:0.8rem";
  planInfoTitleDiv.innerText = place.title;
  planInfoRightDiv.appendChild(planInfoTitleDiv);

  const planInfoEtc = document.createElement("div");
  planInfoEtc.setAttribute("class", "mt-1");

  const planInfoAddr = document.createElement("div");
  planInfoAddr.style = "font-size:0.3em";
  planInfoAddr.innerText = place.address;
  planInfoEtc.appendChild(planInfoAddr);

  const planInfoTel = document.createElement("div");
  planInfoTel.style = "font-size:0.2em";
  planInfoTel.innerText = tel;
  planInfoEtc.appendChild(planInfoTel);
  planInfoRightDiv.appendChild(planInfoEtc);
  planInfoContentDiv.appendChild(planInfoRightDiv);

  const planInfoEventDiv = document.createElement("div");
  planInfoEventDiv.setAttribute("class", "px-3");

  const planInfoTextArea = document.createElement("textarea");
  planInfoTextArea.setAttribute("class", "form-control my-1 plan-place-memo");
  planInfoTextArea.placeholder = "간단 메모";
  planInfoTextArea.style = "font-size: 0.3em";
  planInfoEventDiv.appendChild(planInfoTextArea);

  const planInfoBtnDiv = document.createElement("div");
  planInfoBtnDiv.setAttribute("class", "row d-flex justify-content-between align-items-center px-1");
  planInfoBtnDiv.style = "font-size:0.7em";

  const planInfoLinkDiv = document.createElement("div");
  planInfoLinkDiv.setAttribute("class", "col-auto");
  planInfoLinkDiv.setAttribute("id", "info-url");
  planInfoLinkDiv.style = "color:blue";

  const planInfoLink = document.createElement("a");
  planInfoLink.href = "http://data.visitkorea.or.kr/resource/"+place.contentId;
  planInfoLink.target = "_blank";
  planInfoLink.innerText = "상세보기";
  planInfoLinkDiv.appendChild(planInfoLink);
  planInfoBtnDiv.appendChild(planInfoLinkDiv);

  const planInfoDelBtn = document.createElement("button");
  planInfoDelBtn.setAttribute("class", "col-auto btn btn-sm btn-danger me-2");
  planInfoDelBtn.setAttribute("id", uuid);
  planInfoDelBtn.style = "font-size:0.7rem";
  planInfoDelBtn.innerText = "삭제";
  planInfoDelBtn.addEventListener("click", function(){
      removePlanPlace(uuid);
      console.log(linePath, planMarker);
      const parentEl = this.closest("#add-place-list > div");
      placeList.removeChild(parentEl);
  });
  planInfoBtnDiv.appendChild(planInfoDelBtn);
  planInfoEventDiv.appendChild(planInfoBtnDiv);
  planInfoContentDiv.appendChild(planInfoEventDiv);
  planInfoDiv.appendChild(planInfoContentDiv);

  placeList.appendChild(planInfoDiv);
}

// 콘텐츠 정보 표시 tag 설정
function makeInfo(marker, place) {
  let contentId = place.contentId;
  let title = place.title;
  let img = encodeURI(place.placeImg)
  let addr = place.address;
  let zipCode = place.zipCode;
  let tel = place.tel;
	if(img == "null"){
		img = "<c:out value='${root}/assets/img/no_img.jpg'/>";
	}
  if(zipCode == null){
    zipCode = "등록된 우편번호가 없습니다";
  }
  if(tel == null){
    tel = "등록된  전화번호가 없습니다";
  }
  const infoDiv = document.createElement("div");
  infoDiv.style = "max-width:400px";

  const infoTitleDiv = document.createElement("div");
  infoTitleDiv.setAttribute("class", "px-2 py-1 fw-bold fs-5");
  infoTitleDiv.style = "background:#eee";
  infoTitleDiv.innerText = title;
  infoDiv.appendChild(infoTitleDiv);

  const infoContentDiv = document.createElement("div");
  infoContentDiv.setAttribute("class", "row");

  const infoContentImgDiv = document.createElement("div");
  infoContentImgDiv.setAttribute("class", "col-md-5 pe-0");

  const infoContentImg = document.createElement("img");
  infoContentImg.setAttribute("src", img);
  infoContentImg.setAttribute("class", "img-thumbnail");
  infoContentImgDiv.appendChild(infoContentImg);
  infoContentDiv.appendChild(infoContentImgDiv);

  const infoContentRightDiv = document.createElement("div");
  infoContentRightDiv.setAttribute("class", "col-md-7 align-self-center flex-wrap mb-1 ps-1");

  const infoContentRightInfoDiv = document.createElement("div");
  infoContentRightInfoDiv.setAttribute("class", "h-100");

  const infoContentRightAddr = document.createElement("div");
  infoContentRightAddr.setAttribute("class", "fw-bold text-truncate");
  infoContentRightAddr.innerText = addr;
  infoContentRightInfoDiv.appendChild(infoContentRightAddr);

  const infoContentRightZip = document.createElement("div");
  infoContentRightZip.innerText = zipCode;
  infoContentRightInfoDiv.appendChild(infoContentRightZip);

  const infoContentRightTel = document.createElement("div");
  infoContentRightTel.innerText = tel;
  infoContentRightInfoDiv.appendChild(infoContentRightTel);
  infoContentRightDiv.append(infoContentRightInfoDiv);

  const infoContentRightBtnDiv = document.createElement("div");
  infoContentRightBtnDiv.setAttribute("class", "row d-flex justify-content-between align-items-center");

  const infoContentRightLinkDiv = document.createElement("div");
  infoContentRightLinkDiv.setAttribute("class", "col-auto");
  infoContentRightLinkDiv.setAttribute("id", "info-url");
  infoContentRightLinkDiv.style = "color:blue";

  const infoContentRightLinkA = document.createElement("a");
  infoContentRightLinkA.setAttribute("href", "http://data.visitkorea.or.kr/resource/"+contentId);
  infoContentRightLinkA.target = "_blank";
  infoContentRightLinkA.innerText = "상세보기";
  infoContentRightLinkDiv.appendChild(infoContentRightLinkA);
  infoContentRightBtnDiv.appendChild(infoContentRightLinkDiv);

  const infoContentRightBtn = document.createElement("button");
  infoContentRightBtn.setAttribute("class", "col-auto btn btn-sm btn-primary me-3");
  infoContentRightBtn.innerText = "추가";
  infoContentRightBtn.addEventListener("click", () => addPlanBtn(marker, place));
  infoContentRightBtnDiv.appendChild(infoContentRightBtn);
  infoContentRightDiv.appendChild(infoContentRightBtnDiv);

  infoContentDiv.appendChild(infoContentRightDiv);
  infoDiv.appendChild(infoContentDiv);

	return infoDiv;
}

document.querySelector("#save-plan-btn").addEventListener("click", () => {
  let title = document.querySelector("#plan-title").value;
  let contents = planPlace.map((place) => place.contentId);
  if(title == ""){
    alert("제목을 입력해주세요");
  }
  if(contents.length == 0){
    console.log("check");
    alert("여행 지역을 1개 이상 선택해주세요");
  }else{
    let memoList = Array.from(document.querySelectorAll(".plan-place-memo")).map((el) => el.value);
    let contentList = [];
    for(let i = 0; i < contents.length; i++){
      contentList.push({
        contentId : contents[i],
        memo : memoList[i],
      })
    }
    console.log(contentList);
    let writePlan = {
      title : title,
      contentList: contentList,
    };
    let config = {
      method : "POST",
      headers : {"Content-Type":"application/json"},
      body : JSON.stringify(writePlan),
    };
    fetch(`/content/plan/write`, config).then((response) => console.log(response));
  }
});

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
