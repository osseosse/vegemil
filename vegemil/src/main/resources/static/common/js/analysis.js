/*<![CDATA[*/

	$.getJSON("https://api.ipify.org?format=jsonp&callback=?",
	
      function(json) {
        if(json.ip == '211.204.41.41' || json.ip == '115.88.198.133') {
        	$('#adminLink').css('display', 'block');
        } else {
        	$('#adminLink').css('display', 'none');
        }
      }
    );
    
	var isMobile = /Mobi/i.test(window.navigator.userAgent);// "Mobi" 가 User agent에 포함되어 있으면 모바일
	var varUA = navigator.userAgent.toLowerCase(); //userAgent 값 얻기
	var agent = "";
	var type = "";
	
	if(isMobile) {
		//모바일 카운트
		type = "mobile";
		if ( varUA.indexOf('android') > -1) {
		    //안드로이드
			agent = "android";
		} else if ( varUA.indexOf("iphone") > -1 ) {
		    //IOS
			agent = "ios";
		} else if ( varUA.indexOf("ipad") > -1 ||varUA.indexOf("ipod") > -1 ) {
		    //IOS
			agent = "tablet";
		} else {
		    //아이폰, 안드로이드 외 모바일
			agent = "etc";
		}
	} else {
		//pc 카운트
		type = "pc";
	}
	
	var url = window.location.pathname;
	var headers = {"Content-Type" : "application/json", "X-HTTP-Method-Override" : "POST"};
	var map = {"mAgent": agent, "mType": type, "mUrl": url};
	
	$.ajax({
		url: "/analysis/agentCount",
		type: 'POST',
		headers: headers,
		dataType: "json",
		//1. 기기 저장 2.주소 저장
		data: JSON.stringify(map),
		contentType: "application/json; charset=UTF-8",
		success: function (response) {
			return;
		}
	})
    

/*]]>*/