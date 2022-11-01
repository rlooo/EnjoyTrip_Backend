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
var drawingFlag = false; // 선이 그려지고 있는 상태를 가지고 있을 변수입니다
var moveLine; // 선이 그려지고 있을때 마우스 움직임에 따라 그려질 선 객체 입니다
var clickLine; // 마우스로 클릭한 좌표로 그려질 선 객체입니다
var distanceOverlay; // 선의 거리정보를 표시할 커스텀오버레이 입니다
var dots = {}; // 선이 그려지고 있을때 클릭할 때마다 클릭 지점과 거리를 표시하는 커스텀 오버레이 배열입니다.
function makeMap(touristApplications) {
  overlays = [];
  var mapContainer = document.getElementById("map"), // 지도를 표시할 div
    mapOption = {
      center: new kakao.maps.LatLng(0, 0), // 지도의 중심좌표
      level: 7, // 지도의 확대 레벨
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

  /**
   * 지도에 선 추가하고 선의 거리 계산하기
   */

  // 지도에 클릭 이벤트를 등록합니다
  // 지도를 클릭하면 선 그리기가 시작됩니다 그려진 선이 있으면 지우고 다시 그립니다
  kakao.maps.event.addListener(map, "click", function (mouseEvent) {
    // 마우스로 클릭한 위치입니다
    var clickPosition = mouseEvent.latLng;

    // 지도 클릭이벤트가 발생했는데 선을 그리고있는 상태가 아니면
    if (!drawingFlag) {
      // 상태를 true로, 선이 그리고있는 상태로 변경합니다
      drawingFlag = true;

      // 지도 위에 선이 표시되고 있다면 지도에서 제거합니다
      deleteClickLine();

      // 지도 위에 커스텀오버레이가 표시되고 있다면 지도에서 제거합니다
      deleteDistnce();

      // 지도 위에 선을 그리기 위해 클릭한 지점과 해당 지점의 거리정보가 표시되고 있다면 지도에서 제거합니다
      deleteCircleDot();

      // 클릭한 위치를 기준으로 선을 생성하고 지도위에 표시합니다
      clickLine = new kakao.maps.Polyline({
        map: map, // 선을 표시할 지도입니다
        path: [clickPosition], // 선을 구성하는 좌표 배열입니다 클릭한 위치를 넣어줍니다
        strokeWeight: 3, // 선의 두께입니다
        strokeColor: "#db4040", // 선의 색깔입니다
        strokeOpacity: 1, // 선의 불투명도입니다 0에서 1 사이값이며 0에 가까울수록 투명합니다
        strokeStyle: "solid", // 선의 스타일입니다
      });

      // 선이 그려지고 있을 때 마우스 움직임에 따라 선이 그려질 위치를 표시할 선을 생성합니다
      moveLine = new kakao.maps.Polyline({
        strokeWeight: 3, // 선의 두께입니다
        strokeColor: "#db4040", // 선의 색깔입니다
        strokeOpacity: 0.5, // 선의 불투명도입니다 0에서 1 사이값이며 0에 가까울수록 투명합니다
        strokeStyle: "solid", // 선의 스타일입니다
      });

      // 클릭한 지점에 대한 정보를 지도에 표시합니다
      displayCircleDot(clickPosition, 0);
    } else {
      // 선이 그려지고 있는 상태이면

      // 그려지고 있는 선의 좌표 배열을 얻어옵니다
      var path = clickLine.getPath();

      // 좌표 배열에 클릭한 위치를 추가합니다
      path.push(clickPosition);

      // 다시 선에 좌표 배열을 설정하여 클릭 위치까지 선을 그리도록 설정합니다
      clickLine.setPath(path);

      var distance = Math.round(clickLine.getLength());
      displayCircleDot(clickPosition, distance);
    }
  });

  // 지도에 마우스무브 이벤트를 등록합니다
  // 선을 그리고있는 상태에서 마우스무브 이벤트가 발생하면 그려질 선의 위치를 동적으로 보여주도록 합니다
  kakao.maps.event.addListener(map, "mousemove", function (mouseEvent) {
    // 지도 마우스무브 이벤트가 발생했는데 선을 그리고있는 상태이면
    if (drawingFlag) {
      // 마우스 커서의 현재 위치를 얻어옵니다
      var mousePosition = mouseEvent.latLng;

      // 마우스 클릭으로 그려진 선의 좌표 배열을 얻어옵니다
      var path = clickLine.getPath();

      // 마우스 클릭으로 그려진 마지막 좌표와 마우스 커서 위치의 좌표로 선을 표시합니다
      var movepath = [path[path.length - 1], mousePosition];
      moveLine.setPath(movepath);
      moveLine.setMap(map);

      var distance = Math.round(clickLine.getLength() + moveLine.getLength()), // 선의 총 거리를 계산합니다
        content =
          '<div class="dotOverlay distanceInfo">총거리 <span class="number">' +
          distance +
          "</span>m</div>"; // 커스텀오버레이에 추가될 내용입니다

      // 거리정보를 지도에 표시합니다
      showDistance(content, mousePosition);
    }
  });

  // 지도에 마우스 오른쪽 클릭 이벤트를 등록합니다
  // 선을 그리고있는 상태에서 마우스 오른쪽 클릭 이벤트가 발생하면 선 그리기를 종료합니다
  kakao.maps.event.addListener(map, "rightclick", function (mouseEvent) {
    // 지도 오른쪽 클릭 이벤트가 발생했는데 선을 그리고있는 상태이면
    if (drawingFlag) {
      // 마우스무브로 그려진 선은 지도에서 제거합니다
      moveLine.setMap(null);
      moveLine = null;

      // 마우스 클릭으로 그린 선의 좌표 배열을 얻어옵니다
      var path = clickLine.getPath();

      // 선을 구성하는 좌표의 개수가 2개 이상이면
      if (path.length > 1) {
        // 마지막 클릭 지점에 대한 거리 정보 커스텀 오버레이를 지웁니다
        if (dots[dots.length - 1].distance) {
          dots[dots.length - 1].distance.setMap(null);
          dots[dots.length - 1].distance = null;
        }

        var distance = Math.round(clickLine.getLength()), // 선의 총 거리를 계산합니다
          content = getTimeHTML(distance); // 커스텀오버레이에 추가될 내용입니다

        // 그려진 선의 거리정보를 지도에 표시합니다
        showDistance(content, path[path.length - 1]);
      } else {
        // 선을 구성하는 좌표의 개수가 1개 이하이면
        // 지도에 표시되고 있는 선과 정보들을 지도에서 제거합니다.
        deleteClickLine();
        deleteCircleDot();
        deleteDistnce();
      }

      // 상태를 false로, 그리지 않고 있는 상태로 변경합니다
      drawingFlag = false;
    }
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
}

// 클릭으로 그려진 선을 지도에서 제거하는 함수입니다
function deleteClickLine() {
  if (clickLine) {
    clickLine.setMap(null);
    clickLine = null;
  }
}

// 마우스 드래그로 그려지고 있는 선의 총거리 정보를 표시하거
// 마우스 오른쪽 클릭으로 선 그리가 종료됐을 때 선의 정보를 표시하는 커스텀 오버레이를 생성하고 지도에 표시하는 함수입니다
function showDistance(content, position) {
  if (distanceOverlay) {
    // 커스텀오버레이가 생성된 상태이면

    // 커스텀 오버레이의 위치와 표시할 내용을 설정합니다
    distanceOverlay.setPosition(position);
    distanceOverlay.setContent(content);
  } else {
    // 커스텀 오버레이가 생성되지 않은 상태이면

    // 커스텀 오버레이를 생성하고 지도에 표시합니다
    distanceOverlay = new kakao.maps.CustomOverlay({
      map: map, // 커스텀오버레이를 표시할 지도입니다
      content: content, // 커스텀오버레이에 표시할 내용입니다
      position: position, // 커스텀오버레이를 표시할 위치입니다.
      xAnchor: 0,
      yAnchor: 0,
      zIndex: 3,
    });
  }
}

// 그려지고 있는 선의 총거리 정보와
// 선 그리가 종료됐을 때 선의 정보를 표시하는 커스텀 오버레이를 삭제하는 함수입니다
function deleteDistnce() {
  if (distanceOverlay) {
    distanceOverlay.setMap(null);
    distanceOverlay = null;
  }
}

// 선이 그려지고 있는 상태일 때 지도를 클릭하면 호출하여
// 클릭 지점에 대한 정보 (동그라미와 클릭 지점까지의 총거리)를 표출하는 함수입니다
function displayCircleDot(position, distance) {
  // 클릭 지점을 표시할 빨간 동그라미 커스텀오버레이를 생성합니다
  var circleOverlay = new kakao.maps.CustomOverlay({
    content: '<span class="dot"></span>',
    position: position,
    zIndex: 1,
  });

  // 지도에 표시합니다
  circleOverlay.setMap(map);

  if (distance > 0) {
    // 클릭한 지점까지의 그려진 선의 총 거리를 표시할 커스텀 오버레이를 생성합니다
    var distanceOverlay = new kakao.maps.CustomOverlay({
      content: '<div class="dotOverlay">거리 <span class="number">' + distance + "</span>m</div>",
      position: position,
      yAnchor: 1,
      zIndex: 2,
    });

    // 지도에 표시합니다
    distanceOverlay.setMap(map);
  }

  // 배열에 추가합니다
  dots.push({ circle: circleOverlay, distance: distanceOverlay });
}

// 클릭 지점에 대한 정보 (동그라미와 클릭 지점까지의 총거리)를 지도에서 모두 제거하는 함수입니다
function deleteCircleDot() {
  var i;

  for (i = 0; i < dots.length; i++) {
    if (dots[i].circle) {
      dots[i].circle.setMap(null);
    }

    if (dots[i].distance) {
      dots[i].distance.setMap(null);
    }
  }

  dots = [];
}

// 마우스 우클릭 하여 선 그리기가 종료됐을 때 호출하여
// 그려진 선의 총거리 정보와 거리에 대한 도보, 자전거 시간을 계산하여
// HTML Content를 만들어 리턴하는 함수입니다
function getTimeHTML(distance) {
  // 도보의 시속은 평균 4km/h 이고 도보의 분속은 67m/min입니다
  var walkkTime = (distance / 67) | 0;
  var walkHour = "",
    walkMin = "";

  // 계산한 도보 시간이 60분 보다 크면 시간으로 표시합니다
  if (walkkTime > 60) {
    walkHour = '<span class="number">' + Math.floor(walkkTime / 60) + "</span>시간 ";
  }
  walkMin = '<span class="number">' + (walkkTime % 60) + "</span>분";

  // 자전거의 평균 시속은 16km/h 이고 이것을 기준으로 자전거의 분속은 267m/min입니다
  var bycicleTime = (distance / 227) | 0;
  var bycicleHour = "",
    bycicleMin = "";

  // 계산한 자전거 시간이 60분 보다 크면 시간으로 표출합니다
  if (bycicleTime > 60) {
    bycicleHour = '<span class="number">' + Math.floor(bycicleTime / 60) + "</span>시간 ";
  }
  bycicleMin = '<span class="number">' + (bycicleTime % 60) + "</span>분";

  // 거리와 도보 시간, 자전거 시간을 가지고 HTML Content를 만들어 리턴합니다
  var content = '<ul class="dotOverlay distanceInfo">';
  content += "    <li>";
  content +=
    '        <span class="label">총거리</span><span class="number">' + distance + "</span>m";
  content += "    </li>";
  content += "    <li>";
  content += '        <span class="label">도보</span>' + walkHour + walkMin;
  content += "    </li>";
  content += "    <li>";
  content += '        <span class="label">자전거</span>' + bycicleHour + bycicleMin;
  content += "    </li>";
  content += "</ul>";

  return content;
}
