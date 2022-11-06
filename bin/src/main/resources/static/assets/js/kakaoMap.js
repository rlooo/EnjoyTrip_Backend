// 지도의 범위를 마커 기준으로 설정
function setBounds(bounds) {
  map.setBounds(bounds);
}

// 현재 위치 가져오기
function getLocation() {
  navigator.geolocation.getCurrentPosition(getCoord, getError, options);
}

function getCoord(response) {
  const { coords, timestamp } = response;
  connectKakaoMap(coords.latitude, coords.longitude);
}

function getError(error) {
  console.log(error.code);
  console.log(error.message);
}

const options = {
  enableHighAccuracy: true,
  maximumAge: 0,
  timeout: Infinity,
};

// 카카오맵 생성
function connectKakaoMap(lat, lon) {
  let mapOption = {
    center: new kakao.maps.LatLng(lat, lon), // 지도의 중심좌표
    level: 3, // 지도의 확대 레벨
  };
  // 지도를 표시할 div와  지도 옵션으로  지도를 생성합니다
  map = new kakao.maps.Map(mapContainer, mapOption);
  const currentMarker = new kakao.maps.LatLng(lat, lon);

  // 마커를 생성합니다
  const marker = new kakao.maps.Marker({
    position: currentMarker,
  });

  // 마커가 지도 위에 표시되도록 설정합니다
  marker.setMap(map);
}

function closeInfo() {
  if (infoList) {
    infoList.forEach((item) => {
      item.close();
    });
  }
}

function setCenter(map, pos) {
  map.setCenter(pos);
}
