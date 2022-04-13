'use strict';

// 현재 접속해있는 게시판의 카테고리 읽어오기... 쿼리셀렉터는 반드시 "" 써야하는
const $main = document.querySelector("main");
const bcategory = ($main?.dataset.bcategory) ? $main.dataset.bcategory : '';


//작성 버튼
const $recipeWriteBtn = document.getElementById('recipeWriteBtn');
$recipeWriteBtn?.addEventListener('click', e => {
  location.href = `/board/add?bcategory=${bcategory}`;
});



// 타임리프가 html쪽에서만 동작해서 그쪽에서 다시 만들었다. 아깝네...ㅠ CloneNode(true) 경험은 됐군.

// 관심리스트 생성. 설정/해제=================================================
// .bookmark-container(상위 태그=parent container)위치에 체크박스를 생성한다
// 0) 상위 컨테이너 위치 타겟팅. 여기 아래로 추가할 것이다.
// const whereBookmark = document.querySelectorAll(".bookmark-container");

// // 1) 요소 생성
// const $label = document.createElement('label');
// const $likeList = document.createElement('input');

// const $checked = document.createElement('i');
// const $unchecked = document.createElement('i');

// // 2) label, input에 각각 속성 추가
// $label.setAttribute('class', 'bookmark');
// // $label.setAttribute('data-id', '${item.bbsId}');

// $likeList.setAttribute('type', 'checkbox');
// $likeList.setAttribute('class', 'likeList');
// $likeList.setAttribute('name', 'likeList');
// $likeList.setAttribute('value', `${item.bbsId}`);

// $unchecked.setAttribute('class', 'fas fa-heart unchecked');
// $checked.setAttribute('class', 'fas fa-heart checked');

// // 3) 상위 컨테이너에 추가. 컨테이너 안에 자식 요소로 넣는다.
// $label.appendChild($likeList);
// $label.appendChild($unchecked);
// $label.appendChild($checked);

// [...whereBookmark].forEach(ele => ele.appendChild($label.cloneNode(true)));


// 체크 여부에 따른 게시물 정보(시퀀스) 읽기======================================================
// 1) (html에 직접적으로 체크박스를 만들었지만,)상위 컨테이너에 리스너를 위임 등록한다.
const $bookmark = document.querySelectorAll('.bookmark');
$bookmark.forEach(ele => {
  // 생각해보니 이거 이벤트가 버튼 클릭이 아니잖냐? 체크박스가 체크/해제 되었을 때 이벤트 로직 처리.
  ele.addEventListener('change', isChecked_f);
});

// 이벤트 로직
function isChecked_f(e) {
  // 이벤트가 감지되는지 확인
  console.log('체크');

  // 1) 이벤트 발생 위치의 데이터를 읽는다. id가 undefined가 아니려면 정의가 필요하다
  const dataId = e.target.dataset.id;
  console.log(dataId);

  // 2) 체크/해제 여부를 따진 후(if) 서버로 적절한 데이터를 보내고, 체크면 관심글 등록/해제면 관심글 삭제가 돼야 한다.  
  // 쿼리셀렉터올로 '모든' 체크박스 타겟팅.
  const likeList = document.querySelectorAll('.likeList');       
  // 체크박스 체크/해제 여부 검토
  const is_checked = likeList.checked;


  // 체크여부 함수를 만들어 뺄까?
  // function is_checked() {
  
  //   // 1. checkbox element를 찾습니다.
  //   const checkbox = document.getElementById('my_checkbox');
  
  //   // 2. checked 속성을 체크합니다.
  //   const is_checked = checkbox.checked;
  
  //   // 3. 결과를 출력합니다.
  //   document.getElementById('result').innerText = is_checked;
    
  // }
  
  




}








