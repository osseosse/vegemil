



/* Tab 신제품리스트 */
$(document).ready(function() {
	var imgWrapper = $('.newPT th img');
	imgWrapper.hide().filter(':first').show();
	$('table.newPT td a').click(function () {
		if (this.className.indexOf('current') == -1){
			imgWrapper.hide();
			imgWrapper.filter(this.hash).fadeIn(500);
			$('table.newPT td a').removeClass('current');

		}
		return false;
	});
});
/* // Tab 신제품리스트 */


/* Tab 연혁  */
jQuery(function($){
	var tab = $('.tab_line');
	tab.removeClass('js_off');
	function onSelectTab(){
		var t = $(this);
		var myclass = [];
		t.parentsUntil('.tab_line:first').filter('li').each(function(){
			myclass.push( $(this).attr('class') );
		});
		myclass = myclass.join(' ');
		if (!tab.hasClass(myclass)) tab.attr('class','tab_line').addClass(myclass);
	}
	tab.find('li>a').click(onSelectTab).focus(onSelectTab);
});
/* // Tab 연혁  */


/* Tab 관련사  */
jQuery(function($){
	var tab = $('.tab_line2');
	tab.removeClass('js_off');
	function onSelectTab(){
		var t = $(this);
		var myclass = [];
		t.parentsUntil('.tab_line2:first').filter('li').each(function(){
			myclass.push( $(this).attr('class') );
		});
		myclass = myclass.join(' ');
		if (!tab.hasClass(myclass)) tab.attr('class','tab_line2').addClass(myclass);
	}
	tab.find('li>a').click(onSelectTab).focus(onSelectTab);
});
/* // Tab 관련사  */


/* Tab 대리점  */
jQuery(function($){
	var tab = $('.tab_line3');
	tab.removeClass('js_off');
	function onSelectTab(){
		var t = $(this);
		var myclass = [];
		t.parentsUntil('.tab_line3:first').filter('li').each(function(){
			myclass.push( $(this).attr('class') );
		});
		myclass = myclass.join(' ');
		if (!tab.hasClass(myclass)) tab.attr('class','tab_line3').addClass(myclass);
	}
	tab.find('li>a').click(onSelectTab).focus(onSelectTab);
});
/* // Tab 대리점  */

/* Tab 이용약관  */
jQuery(function($){
	var tab = $('.tab_line7');
	tab.removeClass('js_off');
	function onSelectTab(){
		var t = $(this);
		var myclass = [];
		t.parentsUntil('.tab_line7:first').filter('li').each(function(){
			myclass.push( $(this).attr('class') );
		});
		myclass = myclass.join(' ');
		if (!tab.hasClass(myclass)) tab.attr('class','tab_line7').addClass(myclass);
	}
	tab.find('li>a').click(onSelectTab).focus(onSelectTab);
});


/* Tab 기업홍보영상  */
jQuery(function($){
	var tab = $('.tab_line4');
	tab.removeClass('js_off');
	function onSelectTab(){
		var t = $(this);
		var myclass = [];
		t.parentsUntil('.tab_line4:first').filter('li').each(function(){
			myclass.push( $(this).attr('class') );
		});
		myclass = myclass.join(' ');
		if (!tab.hasClass(myclass)) tab.attr('class','tab_line4').addClass(myclass);
	}
	tab.find('li>a').click(onSelectTab).focus(onSelectTab);
});


/* Tab 공정거래 > 자율준프로그램운영현황  */
jQuery(function($){
	var tab = $('.tab_line5');
	tab.removeClass('js_off');
	function onSelectTab(){
		var t = $(this);
		var myclass = [];
		t.parentsUntil('.tab_line5:first').filter('li').each(function(){
			myclass.push( $(this).attr('class') );
		});
		myclass = myclass.join(' ');
		if (!tab.hasClass(myclass)) tab.attr('class','tab_line5').addClass(myclass);
	}
	tab.find('li>a').click(onSelectTab).focus(onSelectTab);
});


/* -----------------------------------------------------------
	Tab 제품 리스트 퍼블리싱용 스크립트
------------------------------------------------------------ 
$(document).ready(function(){
	$('#tabs div').hide(); 
	$('#tabs div:first').show(); 
	$('#tabs ul li:first').addClass('active');
	$('#tabs > ul > li > a').click(function(){ 
		$('#tabs ul li').removeClass('active');
		$(this).parent().addClass('active'); 
			var currentTab = $(this).attr('href'); 
			$('#tabs > div').hide(); 
			$(currentTab).show();
		return false;
		});
});
/* -----------------------------------------------------------
	// Tab 제품 리스트 퍼블리싱용 스크립트
------------------------------------------------------------ */


/* -----------------------------------------------------------
	Tab 제품 리스트 개발용 스크립트
------------------------------------------------------------ */
function Right(str, n){

  if (n <= 0)
     return "";
  else if (n > String(str).length)
     return str;
  else {
     var iLen = String(str).length;
     return String(str).substring(iLen, iLen - n);
   }
}

function curSubPageActivate(){
			 
	var frmUrl;
	var curLiIndex;
	var gubun;
		
	frmUrl = location.href;
	// 물음표 이후의 값들을 가져옴
	gubun = frmUrl.indexOf("?");
	frmUrl = frmUrl.substring(gubun+1);
	// &를 구분자로 쪼갬
	split_array = frmUrl.split("&");
	// 0번쨰는 Contents 1번째는 Tot 2번째는 Cur
	curLiIndex = "Cur_" + Right(split_array[1],1);
	$('.cookTab li[id='+curLiIndex+']').addClass('active');										
}
		


$(document).ready(function(){
	
	var pathname = document.URL;
	var c_code = Right(pathname,3) ; 
	
	var realvalue = c_code.substring(0,1);
	
	/*기업현황 대리점*/
	if(c_code == "ARE"){/* 대리점 모집 */	
		$('#LNB ul li[id="agencyRecruit"]').addClass('active');
	}
	else if(c_code == "OST"){
		$('#LNB ul li[id="officeStatus"]').addClass('active');
	}
	else if(c_code == "VEA"){/* 제품 리스트 */	
		$('#LNB ul li[id="VegemilAdult"]').addClass('active');
	}
	else if(c_code == "NUT"){
		$('#LNB ul li[id="Nuts"]').addClass('active');
	}
	else if(c_code == "SUN"){
		$('#LNB ul li[id="Sunmond"]').addClass('active');
	}
	else if(c_code == "VEB"){
		$('#LNB ul li[id="VegemilBaby"]').addClass('active');
	}
	else if(c_code == "VEP"){
		$('#LNB ul li[id="VegemilPregnant"]').addClass('active');
	}
	else if(c_code == "WOO"){
		$('#LNB ul li[id="Woorian"]').addClass('active');
	}
	else if(c_code == "GRE"){
		$('#LNB ul li[id="Greenbia"]').addClass('active');
	}
	else if(c_code == "REP"){
		$('#LNB ul li[id="Reproduction"]').addClass('active');
	}
	else if(c_code == "ETC"){
		$('#LNB ul li[id="Etc"]').addClass('active');
	}
	else if(c_code == "NGS"){
		$('#LNB ul li[id="NewGoods"]').addClass('active');
	}/* 커뮤니티 */
	else if(c_code == "CNC"){
		$('#LNB ul li[id="CookNCook"]').addClass('active');
	}
	else if(c_code == "AVG"){
		$('#LNB ul li[id="AboutVegemil"]').addClass('active');
	}
	else if(c_code == "SOY"){
		$('#LNB ul li[id="SoyStory"]').addClass('active');
	}/* 홍보센터- 베지밀 스토리*/
	else if(c_code == "VES"){
		$('#LNB ul li[id="vegemilStory"]').addClass('active');
	}
	else if(c_code == "ADV"){
		$('#LNB ul li[id="advertise"]').addClass('active');
	}
	else if(c_code == "CFL"){
		$('#LNB ul li[id="cfList"]').addClass('active');
	}
	else if(c_code == "CML"){
		$('#LNB ul li[id="cmList"]').addClass('active');
	}
	else if(c_code == "ADE"){
		$('#LNB ul li[id="adEtcList"]').addClass('active');
		$('.LNB ul li[id="adEtcList"]').addClass('active');
	}
	else if(c_code == "MNL"){
		$('#LNB ul li[id="mediaNewsList"]').addClass('active');
		$('.LNB ul li[id="mediaNewsList"]').addClass('active');
	}
	else if(c_code == "MPL"){
		
		//$('#LNB ul li[id="mediaPrintList"]').addClass('active');
		//바로 id에  접근하도록 수정
		$('#mediaPrintList').addClass('active');
	}
	else if(c_code == "VCT"){
		
		//바로 id에  접근하도록 수정
		$('#videoContest').addClass('active');
	}
	else if(c_code == "WEB"){
		$('#LNB ul li[id="webzine"]').addClass('active');
	} /*고객 지원*/
	else if(c_code == "CCM"){
		$('#LNB ul li[id="ccmsInfo"]').addClass('active');
	}
	else if(c_code == "SPL"){
		$('#supportList').addClass('active');
	}
	else if(c_code == "SPF"){
		$('#LNB ul li[id="supportFlow"]').addClass('active');
	}
	else if(c_code == "FAQ"){
		$('#faqList').addClass('active');
	}
	else if(c_code == "OLN"){
		$('#LNB ul li[id="offlineNews"]').addClass('active');
	}
	else if(c_code == "MYP"){
		$('#LNB ul li[id="myPage"]').addClass('active');
	} /*CP*/
	else if(c_code == "DCM"){
		$('#LNB ul li[id="cpDeclarMsg"]').addClass('active');
	}
	else if(c_code == "PGM"){
		$('#LNB ul li[id="cpProgram"]').addClass('active');
	}
	else if(c_code == "MNG"){
		$('#LNB ul li[id="cpManage"]').addClass('active');
	}
	else if(c_code == "MNA"){
		$('#LNB ul li[id="cpManual"]').addClass('active');
	}
	else if(c_code == "PDS"){
		$('#LNB ul li[id="cpPds"]').addClass('active');
	}
	else if(c_code == "CLA"){
		$('#LNB ul li[id="cpClaim"]').addClass('active');
	}
	else if(c_code == "PNE"){
		$('#LNB ul li[id="cPNews"]').addClass('active');
	}/*사회 공헌*/
	else if(c_code == "SLS"){
		$('#LNB ul li[id="scholarship"]').addClass('active');
	}
	else if(c_code == "GNS"){
		$('#LNB ul li[id="goodneighbors"]').addClass('active');
	}
	else if(c_code == "HRT"){
		$('#LNB ul li[id="heart"]').addClass('active');
	}
	else if(c_code == "BLC"){
		$('#LNB ul li[id="bloodcancer"]').addClass('active');
	}
	else if(c_code == "SRV"){
		$('#LNB ul li[id="service"]').addClass('active');
	}/*이벤트*/
	else if(c_code == "EVT"){
		$('#eventList').addClass('active');
	}
	else if(c_code == "LVT"){
		$('#lastEventList').addClass('active');
	}
	else if(c_code == "IDC"){
		$('#ideaContest').addClass('active');
	}

	
	
	//$('#categoryid li[id='+c_code+']').addClass('active');
	
	return false;
	
});

/* -----------------------------------------------------------
	// Tab 제품 리스트 개발용 스크립트
------------------------------------------------------------ */


/* 제품리스트 오버 */
$(document).ready(function(){
		$('a.pro_border').borderEffect({borderColor : '#bd3823', speed : 100, borderWidth: 9}); /* 제품리스트 오버 */
		$('ul.listProduct > table tr:first-child td:first-child').borderEffect({borderColor : '#bd3823', speed : 100, borderWidth: 0}); /* 제품리스트중 제목부분 오버 */
});



/* quick */
(function($){
	$(document).ready(function() {
		//quick
	//	if (!$('#contents').length){return false};
		var limit =  $(window).height() - ($('#quick').height()+200);
		//alert(limit);
		if (limit > 0){
			$('#quick').addFloating({  
				targetRight: 10,  
				targetTop: 10,
				snap: true  
			});  
		} 
	});
})(jQuery);









/* ------------------------------------------------------
		***** slider리뉴얼 *****
--------------------------------------------------------*/
$(window).load(function(){
	
	/* 브랜드관 > 빅배너 */
	$('#slider4 .slider').prrpleSlider({
		autoPlay:true,				//play slider automatically?  //false 사용시 멈춤
		autoPlayInterval:	4000,		
		csstransforms:	false,
		richSwiping:false
	});
	
});
