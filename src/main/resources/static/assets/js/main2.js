const searchBox = document.querySelector('#search-box');
const searchBtn = document.querySelector('#search-box > button');
const searchResult = document.getElementById('search-result');
const scrollPos = document.querySelector('.main-container');
const modalDes = document.getElementById('modal-description');
const mockups = document.getElementsByClassName('mockup');
const mockupTitles = document.getElementsByClassName('mockup-title');
const logoutBtn = document.getElementById('logout');
let isLastPage = false;
let curKeyword = '';
let page = 1;

searchBox.addEventListener('submit', function(e){
  e.preventDefault();
  // 키워드를 전역변수로 변경
  isLastPage = false;
  page = 1;
  curKeyword = this.children[0].value.trim();
  if(curKeyword){
    for (const mockup of mockups) {
      mockup.style.display="none";
    }
    for (const title of mockupTitles) {
      title.style.display="none";
    }
    searchResult.innerHTML='';
    sendRequest(curKeyword, page);
    // document.querySelector('#carouselExampleFade').style.opacity="0.9";
  }else{
    alert('검색어를 입력해주세요');
  }
});

// https://apis.data.go.kr/B551011/KorService/searchKeyword?serviceKey=tWxIf0gUBh9ILEMPV8%2FoB8ExQxxJuR02aHx5de43Pe8VPP7h5JpaqTytlaXtxMsElChQGr9hXE%2FKcE45c1CiRA%3D%3D&numOfRows=10&pageNo=1&MobileOS=ETC&MobileApp=AppTest&_type=json&listYN=Y&arrange=C&keyword=
function sendRequest(curKeyword, page) {
  let params = encodeURIComponent(curKeyword);
  const url = `/map?action=searchBytitle&title=${params}&page=${page}`;
  // console.log('line 19 send request curKeyword is .... ' + curKeyword);
  if(!isLastPage){
    fetch(`${url}`)
    .then((response) => response.json())
    // .then(data => console.log(data));
    .then((data) => makeCard(data));
  }
}


function makeCard(data) {
  const dataArr = data.response.body.items.item;
  // console.dir(dataArr)
  if(!dataArr) isLastPage = true;
  let curData = '';
  if(dataArr){
    dataArr.forEach(e => {
      // <div class="card mb-3" style="max-width: 540px;" data-aos="fade-up">
      curData += `
      <div class="card mb-3 search-card" style="max-width: 540px;" data-aos="fade-up" data-bs-toggle="modal" data-bs-target="#myModal">
              <div class="row g-0">
                <div class="col-md-4">
                  <img src="${e.firstimage}" class="img-fluid rounded-start" alt="..." id="card-img">
                </div>
                <div class="col-md-8">
                  <div class="card-body">
                    <h5 class="card-title">${e.title}</h5>
                    <p class="card-text">${e.addr1} ${e.addr2}</p>
                    <p class="card-text">${e.tel}</p>
                  </div>
                </div>
              </div>
            </div>
      `
    });
    searchResult.innerHTML += curData;
    
    let cardList = document.getElementsByClassName('card');
    // console.log(cardList)
    for (const card of cardList) {
      card.addEventListener('click', ()=>{
        modalSetting(card);
      });
    }
    console.log('observing line 77')
    selectObserveTarget();
  }
}

const io = new IntersectionObserver(e => {
  if(e[0].isIntersecting){
    sendRequest(curKeyword, ++page)
  }
});

function selectObserveTarget(){ 
  const target = document.querySelector('#search-result .card:last-child');
  io.observe(target);
}

// modal
function modalSetting(card){
  const title = card.getElementsByClassName('card-title')[0];
  const des = card.getElementsByClassName('card-text')[0];
  const img = card.querySelector('img');
  modalDes.innerHTML = `
  <div class="col-md-4">
    <img src='${img.src}' class="img-fluid rounded-start" alt="">
  </div>
  <div  class="col-md-8">
    <div class="modal-header">
      <h4 class="modal-title">${title.textContent}</h4>
        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
    </div>
    <div class="modal-body">${des.textContent}</div>
  </div>
  `
}

logoutBtn.addEventListener('click', ()=>{
  localStorage.removeItem('islogin');
  location.replace('./index.html');
})

window.onload = () => {
  if(localStorage.getItem('islogin') == 'true'){
    const islogin = document.getElementsByClassName('islogin');
    for (const li of islogin) {
      li.classList.toggle('disable');
    }
  }
}