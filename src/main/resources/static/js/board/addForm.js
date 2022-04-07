'use strict';

// 게시글 등록 버튼

//목록 버튼
const $listBtn = document.getElementById('listBtn');
$listBtn?.addEventListener("click", e => {
 // console.log(bcategory);
  // 파라미터를 가져와야 하는데 자꾸 폼 객체만 읽어오네 타겟팅이 잘못돼있어
  const bcategory = document.getElementById('bcategory').dataset.bcategory;
  location.href = `/board/1?bcategory=${bcategory}`;
});

