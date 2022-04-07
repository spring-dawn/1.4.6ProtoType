'use strict';

// 현재 접속해있는 게시판의 카테고리 읽어오기... 쿼리셀렉터는 반드시 "" 써야하는
const $main = document.querySelector("main");
const bcategory = ($main?.dataset.bcategory) ? $main.dataset.bcategory : '';


//작성 버튼
const $recipeWriteBtn = document.getElementById('recipeWriteBtn');
$recipeWriteBtn?.addEventListener('click', e => {
    location.href = `/board/add?bcategory=${bcategory}`;
});

// 참고 예시
// const $bbs = document.querySelector('.bbs-wrap');
// const category = ($bbs?.dataset.code) ? $bbs.dataset.code : '';

// const $recipeWriteBtn = document.getElementById('recipeWriteBtn');
// $recipeWriteBtn?.addEventListener("click", e => {
//     const url = `/bbs/add?category=${category}`
//     location.href = url;   // get /bbs/add
// });