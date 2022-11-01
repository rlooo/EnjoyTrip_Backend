let url = "https://apis.data.go.kr/B551011/KorService/areaBasedList";

window.onload = sendRequest2();

function sendRequest2() {
  let queryParams =
    encodeURIComponent("serviceKey") +
    "=" +
    "KYOIVq7wl4Potw9VACNS429a%2FnGO%2BxzFFa%2BXyKs5I6YNm85eUF4N9AjAhf7tp1wVx0bvB6axbFPyBKUJUo4mfw%3D%3D"; /*Service Key*/
  queryParams += "&" + encodeURIComponent("numOfRows") + "=" + encodeURIComponent("500");
  queryParams += "&" + encodeURIComponent("pageNo") + "=" + encodeURIComponent("1");
  queryParams += "&" + encodeURIComponent("MobileOS") + "=" + encodeURIComponent("ETC");
  queryParams += "&" + encodeURIComponent("MobileApp") + "=" + encodeURIComponent("MapTest");
  queryParams += "&" + encodeURIComponent("_type") + "=" + encodeURIComponent("json");
  queryParams += "&" + encodeURIComponent("listYN") + "=" + encodeURIComponent("Y");
  queryParams += "&" + encodeURIComponent("arrange") + "=" + encodeURIComponent("C");
  // queryParams += "&" + encodeURIComponent("areaCode") + "=" + encodeURIComponent("1");
  // queryParams += "&" + encodeURIComponent("sigunguCode") + "=" + encodeURIComponent("1");

  fetch(`${url}?${queryParams}`)
    .then((response) => response.json())
    .then((data) => makeTouristApplications(data));
}

function makeTouristApplications(data) {
  let dataArr = data.response.body.items.item;

  makeMap(dataArr);
}

var map;
var overlays = [];
function makeMap(touristApplications) {
  overlays = [];
  var mapContainer = document.getElementById("map"), // 지도를 표시할 div
    mapOption = {
      center: new kakao.maps.LatLng(0, 0), // 지도의 중심좌표
      level: 13, // 지도의 확대 레벨
    };

  if (!map) {
    map = null;
  }

  map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다

  // HTML5의 geolocation으로 사용할 수 있는지 확인합니다
  if (navigator.geolocation) {
    // GeoLocation을 이용해서 접속 위치를 얻어옵니다
    navigator.geolocation.getCurrentPosition(function (position) {
      var lat = position.coords.latitude, // 위도
        lon = position.coords.longitude; // 경도

      var myPosition = new kakao.maps.LatLng(lat, lon); // 마커가 표시될 위치를 geolocation으로 얻어온 좌표로 생성합니다

      map.setCenter(myPosition);
    });
  } else {
    // HTML5의 GeoLocation을 사용할 수 없을때 마커 표시 위치와 설정합니다

    var myPosition = new kakao.maps.LatLng(33.450701, 126.570667);
    // 지도 중심좌표를 접속위치로 변경합니다
    map.setCenter(myPosition);
  }

  // 마커 클러스터러를 생성합니다
  var clusterer = new kakao.maps.MarkerClusterer({
    map: map, // 마커들을 클러스터로 관리하고 표시할 지도 객체
    averageCenter: true, // 클러스터에 포함된 마커들의 평균 위치를 클러스터 마커 위치로 설정
    minLevel: 5, // 클러스터 할 최소 지도 레벨
  });

  // 마커 이미지의 이미지 주소입니다
  var imageSrc = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png";
  var markers = [];

  // var overlays = [];
  for (var i = 0; i < touristApplications.length; i++) {
    // 마커 이미지의 이미지 크기 입니다
    var imageSize = new kakao.maps.Size(24, 35);

    // 커스텀 오버레이에 표시할 컨텐츠 입니다
    // 커스텀 오버레이는 아래와 같이 사용자가 자유롭게 컨텐츠를 구성하고 이벤트를 제어할 수 있기 때문에
    // 별도의 이벤트 메소드를 제공하지 않습니다
    var content = `<div class="wrap">
                        <div class="info">
                            <div class="title"> ${touristApplications[i].title} 
                                <div class="close" onclick="closeOverlay( ${i})" title="닫기"></div>
                            </div>
                            <div class="body">
                            <div class="img">
                                <img src=${touristApplications[i].firstimage} width="73" height="70">
                            </div>
                            <div class="desc">
                              <div class="ellipsis">${touristApplications[i].addr1}</div>
                              <div class="ellipsis"> ${touristApplications[i].addr2}</div>
                              <div class="cat">${touristApplications[i].cat1}//분류코드</div>
                            </div> +
                        </div> +
                    </div> +
                </div>`;

    const position = new kakao.maps.LatLng(
      touristApplications[i].mapy,
      touristApplications[i].mapx
    );

    // 마커 위에 커스텀오버레이를 표시합니다
    // 마커를 중심으로 커스텀 오버레이를 표시하기위해 CSS를 이용해 위치를 설정했습니다
    var overlay = new kakao.maps.CustomOverlay({
      content: content,
      map: map,
      position: position,
      yAnchor: 1,
    });

    overlay.setMap(null); //처음에 지도에 표시하지 않기 위함

    overlays.push(overlay);

    // 마커 이미지를 생성합니다
    var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);

    // 마커를 생성합니다
    var marker = new kakao.maps.Marker({
      map: map, // 마커를 표시할 지도
      position: position, // 마커를 표시할 위치
      // title: touristApplications [i].title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
      image: markerImage, // 마커 이미지
    });

    // 마커가 지도 위에 표시되도록 설정합니다
    marker.setMap(map);

    markers.push(marker);

    // 마커에 click 이벤트를 등록합니다
    // for문에서 만들어 주지 않으면 마지막 마커에만 이벤트가 등록됩니다
    // 마커를 클릭했을 때 커스텀 오버레이를 표시합니다
    kakao.maps.event.addListener(
      markers[i],
      "click",
      (function (i) {
        return function () {
          overlays[i].setMap(map);
        };
      })(i)
    );
  }

  // 클러스터러에 마커들을 추가합니다
  clusterer.addMarkers(markers);

  // 마커 클러스터러에 클릭이벤트를 등록합니다
  // 마커 클러스터러를 생성할 때 disableClickZoom을 true로 설정하지 않은 경우
  // 이벤트 헨들러로 cluster 객체가 넘어오지 않을 수도 있습니다
  kakao.maps.event.addListener(clusterer, "clusterclick", function (cluster) {
    // 현재 지도 레벨에서 1레벨 확대한 레벨
    var level = map.getLevel() - 1;

    // 지도를 클릭된 클러스터의 마커의 위치를 기준으로 확대합니다
    map.setLevel(level, { anchor: cluster.getCenter() });
  });
}

// 커스텀 오버레이를 닫기 위해 호출되는 함수입니다
function closeOverlay(index) {
  overlays[index].setMap(null);
}


function panTo(lat, lon) {
  map.setLevel(3);

  var moveLatLon = new kakao.maps.LatLng(lon, lat);
  // 지도 중심을 이동 시킵니다
  map.setCenter(moveLatLon);
  console.log("a");
}
