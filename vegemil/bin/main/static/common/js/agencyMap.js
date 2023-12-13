function selectData() {
	$(".result").click(function () {
		var infoData = $(this).find("td");

		var area = $(infoData[0]).text();
		var addr = $(infoData[2]).text();
		var name = $(infoData[1]).text();

		// 상단탭 제어
		var tabList = $(".mapTab").find("li");
		for (var i = 0; i < tabList.length; i++) {
			if ($(tabList[i]).hasClass("current")) {
				$(tabList[i]).removeClass("current");
			}
		}

		$("#" + area).addClass("current");

		// 결과탭 제어
		var tabContentList = $(".tab_content5").find(".row");
		for (var i = 0; i < tabContentList.length; i++) {
			if ($(tabContentList[i]).css("display") == "block"){
				$(tabContentList[i]).css("display","none");
			}
		}		
		
		$("#map" + area).css("display","block");

		// 지도 정보 
		var mapContainer = document.getElementById('map');
		var mapOption = {
			center: new kakao.maps.LatLng(33.450701, 127.100132), // 지도의 중심좌표
			level: 4 // 지도의 확대 레벨
		};

		// 지도를 생성합니다    
		var map = new kakao.maps.Map(mapContainer, mapOption);

		// 주소-좌표 변환 객체를 생성합니다
		var geocoder = new kakao.maps.services.Geocoder();

		searchAndMark(map, geocoder, addr , name + " 대리점" , true);

		// 지도로 스크롤 이동 
		var offset = $('.mapTab').offset(); //선택한 태그의 위치를 반환
		$('html').animate({ scrollTop: offset.top }, 400);

		$(this).blur();
		return;
	});
}

function searchAgency() {
	
	$(".btn-search").blur();
	var searchKeyword = $("#searchKeyword").val();
	$('#searchKeywordRes').text(" ' " + searchKeyword + " ' ");
	$.ajax({
		url: "/company/agencyDevSearch?searchKeyword=" + $("#searchKeyword").val() + "&searchType=" + $("#searchType").val(),
		type: "get",
		success: function (data) {

			// 결과 테이블 렌더링
			if (data.length > 0) {
				$("#resultInfo").attr("hidden", false);
				$("#resultTbl").attr("hidden", false);
				$("#resultSentence").attr("hidden", true);

				$("#totalCount").text(data.length);
				$("#agencyTable").html(
					'<colgroup><col width="15%"><col width="15%"><col width="50%"><col width="20%"></colgroup>'
					+ '<tr><th>지역</th><th>대리점명</th><th>주소</th><th>연락처</th></tr>'
				);

				// foreach loop
				data.forEach(function (item, index) {

					// 테이블
					var dataRow =
						'<tr class="result" onclick="selectData();"><td>' + item.area + '</td><td>' + item.name + '</td><td><a>' + item.addr + '</a></td><td>' + item.hp + '</td></tr>'
					$("#agencyTable").append(dataRow);

				});

			} else {
				$("#resultInfo").attr("hidden", true);
				$("#resultTbl").attr("hidden", true);
				$("#resultSentence").attr("hidden", false);
				$("#resultSentence").text('"' + searchKeyword + '"' + ' 검색 결과가 없습니다.');
			}

			var offset = $('#searchBlockStart').offset(); //선택한 태그의 위치를 반환
			$('html').animate({ scrollTop: offset.top - 50 }, 400);
			
			return;
		},
		error: function () {
			console.log("error>>>>>>>");
			return;
		}
	});
}
  
function getAddrList(area) {

	$.ajax({
		url: "/company/agencyDev?area=" + area,
		type: "get",
		data: "전송할 데이터",
		dataType: "json",
		success: function (data) {

			var mapContainer = document.getElementById('map'); // 지도를 표시할 div 
			var	mapOption = {
					center: new kakao.maps.LatLng(33.450701, 127.100132), // 지도의 중심좌표
					level: 9 // 지도의 확대 레벨
				};

			// 지도를 생성합니다    
			var map = new kakao.maps.Map(mapContainer, mapOption);

			// 주소-좌표 변환 객체를 생성합니다
			var geocoder = new kakao.maps.services.Geocoder();

			// foreach loop
			try {
				data.forEach(function (item, index) {
					if(index == data.length-1){
						throw new Error("영업소!!");
					}
					searchAndMark(map, geocoder, item.addr, item.name + " 대리점", false);
				});
			} catch (error) {
			}
			
			searchAndMark(map, geocoder, data[data.length - 1].addr,data[data.length - 1].name, true);
			return;
		},
		error: function () {
			console.log("error>>>>>>>");
			return;
		}
	});
}

function searchAndMark(map, geocoder, addr , name, isCenter){

	// 주소-좌표 변환 객체를 생성합니다
	var geocoder = new kakao.maps.services.Geocoder();

	var count = 0;
	geocoder.addressSearch(addr, function (result, status) {
					
		if (status === daum.maps.services.Status.OK) {

			var coords = new daum.maps.LatLng(result[0].y, result[0].x);

			var marker = new daum.maps.Marker({
				position: coords,
				clickable: true
			});

			// 마커를 지도에 표시합니다.
			marker.setMap(map);

			// 영업소면 인포윈도우를 생성합니다
			name = name + "";

			if(isCenter == true){
				var infowindow = new kakao.maps.InfoWindow({
					content: '<div style="width:150px;text-align:center;padding:6px 0;">' + name + '<div>',
					removable: true
				});
				infowindow.open(map, marker);
				map.setCenter(coords);
				$("#roadView").attr("href", "https://map.kakao.com/link/roadview/" + result[0].y + "," + result[0].x);
				$("#findRoad").attr("href", "https://map.kakao.com/link/to/" + name + "," + result[0].y + "," + result[0].x);
				$("#wideMap").attr("href", "https://map.kakao.com/link/map/" + result[0].y + "," + result[0].x);
			}
	
			// 마커에 클릭이벤트를 등록합니다(필요시)
			kakao.maps.event.addListener(marker, 'click', function () {

				var infowindow = new kakao.maps.InfoWindow({
					content: '<div style="width:150px;text-align:center;padding:6px 0;">' + name + '</div>',
					removable: true
				});
	
				infowindow.open(map, marker);

				var position = new kakao.maps.LatLng(result[0].y, result[0].x);
				map.setCenter(position);
				$("#roadView").attr("href", "https://map.kakao.com/link/roadview/" + result[0].y + "," + result[0].x);
				$("#findRoad").attr("href", "https://map.kakao.com/link/to/" + name + "," + result[0].y + "," + result[0].x);
				$("#wideMap").attr("href", "https://map.kakao.com/link/map/" + result[0].y + "," + result[0].x);
			});

		} else {
			console.log("error>>>>>>>");
		}
	});
}