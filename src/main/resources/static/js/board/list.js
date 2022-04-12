'use strict';

// 현재 접속해있는 게시판의 카테고리 읽어오기... 쿼리셀렉터는 반드시 "" 써야하는
const $main = document.querySelector("main");
const bcategory = ($main?.dataset.bcategory) ? $main.dataset.bcategory : '';


//작성 버튼
const $recipeWriteBtn = document.getElementById('recipeWriteBtn');
$recipeWriteBtn?.addEventListener('click', e => {
  location.href = `/board/add?bcategory=${bcategory}`;
});



// 관심리스트 생성. 설정/해제=================================================
// .bookmark-container(상위 태그=parent container)위치에 체크박스를 생성한다
// 0) 상위 컨테이너 위치 타겟팅. 여기 아래로 추가할 것이다.
const whereBookmark = document.querySelectorAll(".bookmark-container");

// 1) 요소 생성
const $label = document.createElement('label');
const $likeList = document.createElement('input');

const $checked = document.createElement('i');
const $unchecked = document.createElement('i');

// 2) label, input에 각각 속성 추가
$label.setAttribute('class', 'bookmark');

$likeList.setAttribute('type', 'checkbox');
$likeList.setAttribute('class', 'likeList');
$likeList.setAttribute('name', 'likeList');

$unchecked.setAttribute('class', 'fas fa-heart unchecked');
$checked.setAttribute('class', 'fas fa-heart checked');

// 3) 상위 컨테이너에 추가. 컨테이너 안에 자식 요소로 넣는다.
$label.appendChild($likeList);
$label.appendChild($unchecked);
$label.appendChild($checked);

[...whereBookmark].forEach(ele => ele.appendChild($label.cloneNode(true)));


// 체크 여부에 따른 게시물 정보(시퀀스) 읽기======================================================
// 1) 지도 만들 때처럼 체크박스가 생성되기 전엔 없다. 상위 컨테이너에 리스너를 위임 등록한다.
const $bookmark = document.querySelectorAll('.likeList');
$bookmark.forEach(ele => {
  // 생각해보니 이거 이벤트가 버튼 클릭이 아니잖냐? 체크박스가 체크/해제 되었을 때 이벤트 로직 처리.
  ele.addEventListener('change', isChecked_f);
});

// 이벤트 로직
function isChecked_f(e) {
  // 이벤트가 먹히는지 확인
   console.log('체크');

  // 체크 된 것에 한해 데이터 읽어오기.
  // 1) 체크 여부
  const query = 'input[name="likeList"]:checked';
  const theChecked = document.querySelectorAll(query);
  // console.log(theChecked);

  // 2) 체크 된 부분의 데이터를 읽는다
  if(theChecked){
    let ids = '';
    [...whereBookmark].forEach(ele => {
      ids += ele;
    });
    console.log(ids);
    // 첫 번째거 단수론 읽어지는데 다수가 안 된다. 

    

  }



}









