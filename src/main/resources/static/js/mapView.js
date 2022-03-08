

    // 지도생성시 초기 옵션 주기
    var mapOptions = {
        center: new naver.maps.LatLng(35.5352071674071, 129.31091449029512),
        zoom: 18,
        mapTypeId: naver.maps.MapTypeId.NORMAL
    };

    // 지도 그리기
    var map = new naver.maps.Map('map', mapOptions);

    // 마커생성하기
    var marker = new naver.maps.Marker({
      position: new naver.maps.LatLng(35.5352071674071, 129.31091449029512), //좌표값
      map: map, // 마커 표기할 지도

      // 네이버에서 제공하는 기본마커가 아닌 사용자 정의 마커 정의할때
      icon: {
          content: `<div class='mymarker'></div>`,
          //size: new naver.maps.Size(16, 16),   //마커 사이즈
          anchor: new naver.maps.Point(8, 8), //마커 중심좌표
      },
      //animation: naver.maps.Animation.BOUNCE
    });


    // 인포 창 생성
    var infowindow = new naver.maps.InfoWindow({
        content: `<div class='info'>hello</div>`,
        anchorSize : new naver.maps.Size(10,10)  //인포 창 뾰족이 크기
    });

    // 마커 클릭시
    naver.maps.Event.addListener(marker, "click", function(e) {
        // 인포창이 지도에 나타나 있으면 인포창 닫기
        if (infowindow.getMap()) {
            infowindow.close();
        // 인포창이 지도에 없으면 인포창 띄우기
        } else {

            infowindow.open(map, marker);
        }
    });


    // 내위치
    const $myposition = document.getElementById('myposition');
    $myposition.addEventListener('click', myposition_f );

    function myposition_f(e){
      //alert('hi'+e);
      if(window.navigator.geolocation){
        window.navigator.geolocation.getCurrentPosition(onSuccess, onError);
      }else{
        window.alert('현재 브라우저는 위치정보를 제고하지 않습니다!');
      }
    }

    function onSuccess(position) {
      const location = new naver.maps.LatLng(position.coords.latitude,
                                          position.coords.longitude);

      map.setCenter(location); // 얻은 좌표를 지도의 중심으로 설정합니다.
      map.setZoom(10); // 지도의 줌 레벨을 변경합니다.

      infowindow.setContent('<div style="padding:20px;">' + 'geolocation.getCurrentPosition() 위치' + '</div>');
      infowindow.open(map, location);

      console.log('Coordinates: ' + location.toString());
    }

    function onError(err) {
      const center = map.getCenter();
      console.warn(`ERROR(${err.code}): ${err.message}`);
      infowindow.setContent(
        `<div style="padding:20px;">
          <h5 style="margin-bottom:5px;color:#f00;">Geolocation failed!</h5>
            latitude: ${center.lat()} <br/>
            longitude: ${center.lng()}
        </div>`
      );

      infowindow.open(map, center);
    }


    const $keyword = document.getElementById('keyword');
    const $searchBtn = document.getElementById('searchBtn');

    $keyword.addEventListener('keydown',searchKeyword);
    $searchBtn.addEventListener('click',searchKeyword);

    function searchKeyword(e){
      const tagName = e.target.tagName;
      switch(tagName){
        case 'INPUT':
          if(e.key == 'Enter'){
            const ps = new kakao.maps.services.Places();
            // 키워드로 장소를 검색합니다
            ps.keywordSearch($keyword.value, placesSearchCB);
          }
          break;
        case 'BUTTON':
            const ps = new kakao.maps.services.Places();
            // 키워드로 장소를 검색합니다
            ps.keywordSearch($keyword.value, placesSearchCB);
          break;
        default:
          console.log('etc..');
      }
    }

        // 마커생성하기
    // parameter : 위도, 경도
    function makeMarker(lat,lng){

      const marker = new naver.maps.Marker({
        position: new naver.maps.LatLng(lat,lng), //좌표값
        map: map, // 마커 표기할 지도

        // 네이버에서 제공하는 기본마커가 아닌 사용자 정의 마커 정의할때
        icon: {
            content: `<div class='mymarker'></div>`,
            //size: new naver.maps.Size(16, 16),   //마커 사이즈
            anchor: new naver.maps.Point(8, 8), //마커 중심좌표
        },
        //animation: naver.maps.Animation.BOUNCE
      });

      return marker;
    }


    // 인포창 생성하기
    // parameter : {상호명,연락처,부가정보,도로명주소}
    function makeInforWindow( info ){

      const infowindow = new naver.maps.InfoWindow({
        content:
          `<div class='info'>
            <div>상호명</div><div>${info.place_name}</div>
            <div>연락처</div><div>${info.phone}</div>
            <div>부가정보</div><div><a href='${info.place_url}' target='_blank'>${info.place_url}</a></div>
            <div>도로명주소</div><div>${info.road_address_name}</div>
          </div>`,
        // anchorSize : new naver.maps.Size(10,10)  //인포 창 크기
      });

      return infowindow;
    }

    const markers=[];
    const infos=[];

    // 키워드 검색 완료 시 호출되는 콜백함수 입니다
    function placesSearchCB (data, status, pagination) {

      // console.log(data);
      // console.log(status);
      // console.log(pagination);

      //검색전 마크존재시 지도에서 제거
      markers.forEach(marker=>marker.setMap(null));

      //검색전 인포창 지도에서 제거
      infos.forEach(info=>info.close());

      // 마커,인포 배열에서 제거
      markers.splice(0,markers.length);
      infos.splice(0,infos.length);

      switch (status) {

        case kakao.maps.services.Status.OK : //검색 결과 있음
            console.log(data);
            const result = data.splice(0,5);  // 검색어에 근접한 5개만 추출
            console.log(result);

            //지도에 마커 표시, 인포창 생성
            result.forEach(ele => {

              let marker = makeMarker(ele.y, ele.x)
              markers.push(marker);

              let infowindow = makeInforWindow(
                {
                  place_name : ele.place_name,  //상호명
                  phone : ele.phone,            //연락처
                  place_url : ele.place_url,    //부가정보
                  road_address_name : ele.road_address_name //도로명주소
                }
              )
              infos.push(infowindow);
            });

            const lats =[]; //경도
            const lngs =[]; //위도
            //마커 클릭시 인포창 띄우기
            markers.forEach( (marker, idx) => {
              naver.maps.Event.addListener(marker, 'click', e=>{
                // 인포창이 지도에 나타나 있으면 인포창 닫기
                if (infos[idx].getMap()) {
                  infos[idx].close();
                // 인포창이 지도에 없으면 인포창 띄우기
                } else {
                  infos[idx].open(map, marker);
                }
              });
              lats.push(marker.position.y);
              lngs.push(marker.position.x);
            });

            //위도,경도 최대 최소 구하기
            const minLat = Math.min.apply(null,lats);
            const minLng = Math.min.apply(null,lngs);
            const maxLat = Math.max.apply(null,lats);
            const maxLng = Math.max.apply(null,lngs);

            //좌표경계구하기
            const target = new naver.maps.LatLngBounds(
              new naver.maps.LatLng(minLat, minLng),
              new naver.maps.LatLng(maxLat, maxLng));

            //지도 중심 좌표 이동
            map.panToBounds(target);

            break;
        case kakao.maps.services.Status.ZERO_RESULT  : //정상적으로 응답 받았으나 검색 결과는 없음
          alert('정상적으로 응답 받았으나 검색 결과는 없음');
          break;
        case kakao.maps.services.Status.ERROR   : //서버 응답에 문제가 있는 경우
          alert('서버 응답에 문제가 있는 경우');
          break;
        default:
        alert('기타');
          break;
      }
    }