///////////////////////// select box 설정 (지역, 매매기간) /////////////////////////
document.write("<script type='text/javascript' src='route.js'><" + "/script>");
let date = new Date();
let page = 1;
let curQuery = "";

window.onload = function () {
  // 브라우저가 열리면 시도정보 얻기.
  sendRequest("sido", "");
};

// 시도가 바뀌면 구군정보 얻기.
document.querySelector("#sido").addEventListener("change", function () {
  if (this[this.selectedIndex].value) {
    let areaCode = this[this.selectedIndex].value;
    sendRequest("gugun", areaCode);
  } else {
    initOption("gugun");
  }
});

function sendRequest(selid, areaCode) {
  const url = "https://apis.data.go.kr/B551011/KorService/areaCode";
  let params = `serviceKey=tWxIf0gUBh9ILEMPV8%2FoB8ExQxxJuR02aHx5de43Pe8VPP7h5JpaqTytlaXtxMsElChQGr9hXE%2FKcE45c1CiRA%3D%3D&numOfRows=50&pageNo=1&MobileOS=ETC&MobileApp=AppTest&${
    areaCode ? `areaCode=${areaCode}&` : ""
  }_type=json`;
  fetch(`${url}?${params}`)
    .then((response) => response.json())
    .then((data) => addOption(selid, data));
}

function addOption(selid, data) {
  let dataArr = data.response.body.items.item;
  let opt = ``;
  initOption(selid);
  switch (selid) {
    case "sido":
      opt += `<option value="" disabled selected>시도선택</option>`;
      dataArr.forEach(function (info) {
        opt += `
        <option value="${info.code}">${info.name}</option>
        `;
      });
      break;
    case "gugun":
      opt += `<option value="" disabled selected>구군선택</option>`;
      dataArr.forEach(function (regcode) {
        opt += `
        <option value="${regcode.code}">${regcode.name}</option>
        `;
      });
      break;
  }
  document.querySelector(`#${selid}`).innerHTML = opt;
}

document.querySelector("#list-btn").addEventListener("click", function () {
  let sidoCode = document.querySelector("#sido").value;
  let gugunCode = document.querySelector("#gugun").value;
  let kind = document.querySelector("#kind").value;

  let url = `https://apis.data.go.kr/B551011/KorService/areaBasedList`;
  let queryParams = `serviceKey=tWxIf0gUBh9ILEMPV8%2FoB8ExQxxJuR02aHx5de43Pe8VPP7h5JpaqTytlaXtxMsElChQGr9hXE%2FKcE45c1CiRA%3D%3D&numOfRows=20&pageNo=1&MobileOS=ETC&MobileApp=AppTest&_type=json&listYN=Y&arrange=C&areaCode=${sidoCode}&sigunguCode=${gugunCode}${
    kind ? "&contentTypeId=" + kind : ""
  }`;
  curQuery = `${url}?${queryParams}`;
  // console.log(`line 62 ${kind}`);
  map = fetch(`${url}?${queryParams}`)
    .then((response) => response.json())
    .then((data) => {
      console.log(data);
      makeList(data);
      makeTouristApplications(data);
    });
});

function makeList(data) {
  document.querySelector("table").setAttribute("style", "display: ;");
  let tbody = document.querySelector("#location-list");
  console.log(tbody);
  initTable();
  console.log(data);
  let locations = data.response.body.items.item;
  console.log(locations);

  locations.forEach((location) => {
    let tr = document.createElement("tr");
    tr.setAttribute("data-mapx", location.mapx);
    tr.setAttribute("data-mapy", location.mapy);
    let imgTD = document.createElement("td");
    imgTD.innerHTML = `<img src="${location.firstimage}" alt="${location.title}" style="height:50px; height: 50px">`;
    tr.appendChild(imgTD);

    let titleTD = document.createElement("td");
    titleTD.appendChild(document.createTextNode(location.title));
    tr.appendChild(titleTD);

    let conTD = document.createElement("td");
    let con;
    switch (location.contenttypeid) {
      case "12":
        con = "관광지";
        break;
      case "14":
        con = "문화시설";
        break;
      case "15":
        con = "행사/공연/축제";
        break;
      case "25":
        con = "여행코스";
        break;
      case "28":
        con = "레포츠";
        break;
      case "32":
        con = "숙박";
        break;
      case "38":
        con = "쇼핑";
        break;
      case "39":
        con = "음식점";
        break;
    }
    conTD.appendChild(document.createTextNode(con));
    tr.appendChild(conTD);

    let telTD = document.createElement("td");
    telTD.appendChild(document.createTextNode(location.tel));
    tr.appendChild(telTD);

    let addrTD = document.createElement("td");
    addrTD.appendChild(document.createTextNode(location.addr1));
    addrTD.classList.add("text-end");
    tr.appendChild(addrTD);
    tbody.appendChild(tr);
  });

  console.log("line 109 >>>> loop fin");

  addEventInOptions();
}
function addEventInOptions() {
  let sidoCode = document.querySelector("#sido").value;
  let gugunCode = document.querySelector("#gugun").value;

  let url = `https://apis.data.go.kr/B551011/KorService/areaBasedList`;
  let queryParams = `serviceKey=tWxIf0gUBh9ILEMPV8%2FoB8ExQxxJuR02aHx5de43Pe8VPP7h5JpaqTytlaXtxMsElChQGr9hXE%2FKcE45c1CiRA%3D%3D&numOfRows=20&pageNo=1&MobileOS=ETC&MobileApp=AppTest&_type=json&listYN=Y&arrange=C&areaCode=${sidoCode}&sigunguCode=${gugunCode}`;

  const trList = document.querySelector("#location-list").children;
  for (const tr of trList) {
    tr.addEventListener("click", () => {
      console.log(tr.dataset.mapx);
      console.log(tr.dataset.mapy);

      panTo(tr.dataset.mapx, tr.dataset.mapy);
    });
  }
}

function initOption(selid) {
  let options = document.querySelector(`#${selid}`);
  options.length = 0;
}

function initTable() {
  let tbody = document.querySelector("#location-list");
  let len = tbody.rows.length;
  for (let i = len - 1; i >= 0; i--) {
    tbody.deleteRow(i);
  }
}

const previous = document.getElementById("previous");
const next = document.getElementById("next");

previous.addEventListener("click", () => {
  if (page != 1) {
    curQuery = curQuery.replace(/pageNo=[0-9]+/, `pageNo=${--page}`);
    fetch(curQuery)
      .then((response) => response.json())
      .then((data) => {
        makeList(data);
        makeTouristApplications(data);
      });
  }
});

next.addEventListener("click", () => {
  console.log("next");
  curQuery = curQuery.replace(/pageNo=[0-9]+/, `pageNo=${++page}`);
  fetch(curQuery)
    .then((response) => response.json())
    .then((data) => {
      makeList(data);
      makeTouristApplications(data);
    });
});

// const io = new IntersectionObserver(e => {
//   observeUrl.replace(/pageNo=[0-9]+/, `pageNo=${++page}`);
//   if(e[0].isIntersecting){
//     sendRequest(curKeyword, ++page)
//   }
// });

// function selectObserveTarget(){
//   const target = document.querySelector('tr:last-child');
//   io.observe(target);
// }
