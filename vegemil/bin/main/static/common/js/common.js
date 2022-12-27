/*****************************************************************************
*	웹 사이트 전체에서 사용되는 인스턴스 모음
*
*	SESON
*	작성일자 : 2013-07-16
//****************************************************************************/
//=============================================================================
// 전역 변수 선언 (환경설정)
//=============================================================================
// GNB
var def = 0;

// 퀵 메뉴
var stmnLEFT = 13; // 왼쪽 여백 
var stmnGAP1 = 0; // 위쪽 여백 
var stmnGAP2 = 70; // 스크롤시 브라우저 위쪽과 떨어지는 거리 
var stmnBASE = 70; // 스크롤 시작위치 
var stmnActivateSpeed = 15;
var stmnScrollSpeed = 10;
var stmnTimer;

// 진도에디터
var oEditors = [];

// V컨슈머피아 설문 진행/마감 설정
var CyberResearchOpenValue = "0";	// 1:진행,  0:마감

//=============================================================================
// 페이지 초기화 작업
//=============================================================================
$(document).ready(function(){
	// iframe, popup 페이지에서 초기화 하지 않도록 처리
	// 해당 페이지에 <div id="pagePopup"> 추가 필요
	if($("#pagePopup").length < 1){
	
		// GNB 초기화
		gnbInit();
		
		// QuickMenu 초기화
		//quickMenuInit();
	}
	
	PopupView();
});


// 쿠키 체크 후 팝업 띄움
function PopupView(){
	// 팝업(멀티)
	
	if(CookieRead("PopupView") != "1"){
		
		$("#PopupDiv2").show();
		//$("#PopupFrame2").attr("src","/Main/popup/PopupDetail.aspx");	                        
	} 
	
	else {
	
		$("#PopupDiv2").hide();
		$("#PopupFrame2").attr("src","about:blank");	                        
	}
	
	
	if(CookieRead("PopupView2") != "1"){
	
	
		$("#PopupDiv").show();
		$("#PopupFrame").attr("src","/Main/popup/PopupDetail3.aspx");	                        
	} 
	
	else {
	
		$("#PopupDiv").hide();
		$("#PopupFrame").attr("src","about:blank");	                        
	}
	
	if(CookieRead("PopupView3") != "1"){
		$("#PopupDiv3").show();              
	} else {
		$("#PopupDiv3").hide();
		$("#PopupFrame3").attr("src","/Main/popup/PopupDetail3.aspx");	                        
	}
	
	if(CookieRead("PopupView4") != "1"){
		$("#PopupDiv4").show();              
	} else {
		$("#PopupDiv4").hide();
		$("#PopupFrame4").attr("src","about:blank");	                        
	}
	
	if(CookieRead("PopupView5") != "1"){
		$("#PopupDiv5").show();              
	} else {
		$("#PopupDiv5").hide();
		$("#PopupFrame5").attr("src","about:blank");	                        
	}
	
	if(CookieRead("PopupView6") != "1"){
		$("#PopupDiv6").show();              
	} else {
		$("#PopupDiv6").hide();
		$("#PopupFrame6").attr("src","about:blank");	                        
	}
	
	if(CookieRead("PopupView7") != "1"){
		$("#PopupDiv7").show();              
	} else {
		$("#PopupDiv7").hide();
		$("#PopupFrame7").attr("src","about:blank");	                        
	}
	
	if(CookieRead("PopupView8") != "1"){
		$("#PopupDiv8").show();              
	} else {
		$("#PopupDiv8").hide();
		$("#PopupFrame8").attr("src","about:blank");	                        
	}
	
	if(CookieRead("PopupView9") != "1"){
		$("#PopupDiv9").show();              
	} else {
		$("#PopupDiv9").hide();
		$("#PopupFrame9").attr("src","about:blank");	                        
	}
	
	if(CookieRead("PopupView10") != "1"){
		$("#PopupDiv10").show();              
	} else {
		$("#PopupDiv10").hide();
		$("#PopupFrame10").attr("src","about:blank");	                        
	}
}
//-----------------------------------------------------------------------------
// GNB 실행
//-----------------------------------------------------------------------------
function gnbInit(){
	var showin = function() {
		$(this).siblings("div").slideDown(320);
		$(this).parent("li").siblings("li").find("div").slideUp(1);
		var s = this;
		// 이미지 반전시켜 적용
		$(this).parent('li').siblings().each(function() {
			if($(this).children('a').children('img').prop('src').indexOf('_on') > 0) {
				$(this).children('a').children('img').prop('src', $(this).find('img').prop('src').substr(0, $(this).find('img').prop('src').lastIndexOf('_'))+'.png');
			}
		});
		if($(this).find('img').prop('src').indexOf('_on') < 0) {
			$(this).find('img').prop('src', $(this).find('img').prop('src').substr(0, $(this).find('img').prop('src').lastIndexOf('.'))+'_on.png');
		}
	}
	var showout = function() {
		//alert("showout");
		$(this).siblings().each(function() {
			//alert($("#gnb>li>div>ul>li>a>img").size());
			if($("#gnb>li>div>ul>li>a>img").prop('src').indexOf('_on') > 0) {
				//$($("#gnb>li>div>ul>li>a>img").prop('src', $($("#gnb>li>div>ul>li>a>img").prop('src').substr(0, $($("#gnb>li>div>ul>li>a>img").prop('src').lastIndexOf('_'))+'.png');
			} else {
			}
		});
	}
	var showoutSub = function() {
	}
	$("#gnb>li>a").mouseover(showin).focusin(showin).mouseleave(showout);
	$('#gnb').mouseleave(function(){
		$("#gnb>li>a").siblings('div').css({'display':'none'});
		$("#gnb>li>a").each(function(){
			// 메인 메뉴가 활성화 되어 있으면, 전체 초기화
			if($(this).index() != (def-1) && $(this).children('img').prop('src').indexOf('_on') > 0) {
				$(this).children('img').prop('src', $(this).children('img').prop('src').substr(0, $(this).children('img').prop('src').lastIndexOf('_'))+'.gif');
			}
		});
		// 메인 메뉴 중 선택된 항목 _on 으로 표시
		if($("#gnb>li>a>img").eq(def-1).prop('src').indexOf('_on') < 0 && def > 0) {
			$("#gnb>li>a>img").eq(def-1).prop('src', $("#gnb>li>a>img").eq(def-1).prop('src').substr(0, $("#gnb>li>a>img").eq(def-1).prop('src').lastIndexOf('.'))+'_on.gif');
		}

	});
	$('#gnb a:last').bind('focusout',function(){
		$('.gnb08').hide();
		$('#gnb li div').hide();
	});
	/* 2013-04-29 추가 */
	$("#gnb>li>div>ul>li>a").mouseover(showin).focusin(showin).mouseleave(showoutSub);
	/* 2013-07-30 추가 */
	$('#gnb>li>div>ul>li>a').mouseleave(function(){
		$("#gnb>li>div>ul>li>a").siblings('div').css({'display':'none'});
		$("#gnb>li>div>ul>li>a").each(function(){
			// 메인 메뉴가 활성화 되어 있으면, 전체 초기화
			if($(this).index() != (def-1) && $(this).children('img').prop('src').indexOf('_on') > 0) {
				$(this).children('img').prop('src', $(this).children('img').prop('src').substr(0, $(this).children('img').prop('src').lastIndexOf('_'))+'.png');
			}
		});
	});
	/* // 2013-07-30 추가 */
	/*
	$('#gnb').mouseleave(function(){
			if($("#gnb>li>a>img").eq(def-1).prop('src').indexOf('_on')<0&&def>0){
			$("#gnb>li>div>ul>li>a").eq(def-1).prop('src',$("#gnb>li>div>ul>li>a").eq(def-1).prop('src').substr(0,$("#gnb>li>div>ul>li>a").eq(def-1).prop('src').lastIndexOf('.'))+'_on.png');} 
	})
		$('#gnb a:last').bind('focusout',function(){
		$('.gnb08').hide();
	});
	*/
}
//-----------------------------------------------------------------------------
// 퀵 메뉴
//-----------------------------------------------------------------------------
function quickMenuRefresh() { 
	var stmnStartPoint, stmnEndPoint; 
	stmnStartPoint = parseInt(document.getElementById('quick').style.top, 10); 
	stmnEndPoint = Math.max(document.documentElement.scrollTop, document.body.scrollTop) + stmnGAP2; 
	if (stmnEndPoint < stmnGAP1) stmnEndPoint = stmnGAP1; 
	if (stmnStartPoint != stmnEndPoint) { 
		stmnScrollAmount = Math.ceil( Math.abs( stmnEndPoint - stmnStartPoint ) / 15 ); 
		document.getElementById('quick').style.top = parseInt(document.getElementById('quick').style.top, 10) + ( ( stmnEndPoint<stmnStartPoint ) ? -stmnScrollAmount : stmnScrollAmount ) + 'px'; 
		stmnRefreshTimer = stmnScrollSpeed; 
	}
	stmnTimer = setTimeout("quickMenuRefresh();", stmnActivateSpeed); 
} 
function quickMenuInit() {
	document.getElementById('quick').style.right = stmnLEFT + 'px'; 
	document.getElementById('quick').style.top = document.body.scrollTop + stmnBASE + 'px'; 
	quickMenuRefresh();
}
//--------------------------------------------------------------------------------
// 왼쪽 서브메뉴 선택 적용
//--------------------------------------------------------------------------------
function LeftMenuSelected(leftMenuM, leftMenuS){
	//var leftMenuSwf = window.document.getElementById("leftMenu");
	//leftMenuSwf.SetVariable("m", eval(leftMenuM));
	//leftMenuSwf.SetVariable("s", eval(leftMenuS));
}
//-----------------------------------------------------------------------------
// 우편번호 팝업
//-----------------------------------------------------------------------------
// 열기
function PopZipcode(){
	$("#ZipcodeFrame").attr("src","/main/include/zipcodeCheck.aspx");
	$("#ZipcodeDiv").show();
}
// 닫기
function PopZipcodeClose(){
	$("#ZipcodeFrame").attr("src","about:blank");
	$("#ZipcodeDiv").hide();
}
// 부모창으로 닫기
function PopParentZipcodeClose(){
	window.parent.$("#ZipcodeFrame").attr("src","about:blank");
	window.parent.$("#ZipcodeDiv").hide();
}
//-----------------------------------------------------------------------------
// 메인 페이지 전용
//-----------------------------------------------------------------------------
// 메인 - 대리점 엔터 체크
function agencyEnterClick(){
	if(event.keyCode == 13){
		self.focus();		// 이 문장이 없을 경우 제대로 동작하지 않는다.(즉, 포스트백이 일어난다.)
		agencyResultList();	// 팝업
		return false;
	}
}
// 메인 - 대리점 검색 팝업
function agencyResultList(){
	//var str = document.Form1.txtFind.value;
	//window.open("/main/Company/AgencyFindSmallResult.aspx?n="+str,"agencyResultList","left=0,top=0,width=620,height=200," +
	//	"toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=no");
	window.open("/main/company/agencyFindSmallResult.aspx","agencyResultList","left=0,top=0,width=620,height=400," +
		"toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=no");
}
// 메인 - 대리점 검색 팝업 검색 버튼
function agencyResultPopup(){
	self.focus();
	var str = document.getElementById("txtFind").value;
	if(str.length > 0) {
		document.location.href("/main/company/agencyFindSmallResult.aspx?n=" + str);
	} else {
		document.location.href("/main/company/agencyFindSmallResult.aspx");
	}
}
// 메인 - 팝업 숨김
function pageDefaultClearAllPopup(){
	$("#PopupDiv").hide("slow");
	$("#PopupFrame").attr("src","about:blank");
}
// 메인 - 부모창 팝업 숨김
function pageDefaultParentPopupClose(){
	window.parent.$("#PopupDiv").hide("slow");
	window.parent.$("#PopupFrame").attr("src","about:blank");
}
// 메인 - 쿠키 저장 후 숨김
function pageDefaultParentOneDayClose(){
	CookieSave("PopupMulti", "1" , 1);
	pageDefaultParentPopupClose();
}
//-----------------------------------------------------------------------------
// 연혁 탭 뷰 선택
//-----------------------------------------------------------------------------
function historyTabView(num){
	var tabMenu1;
	var tabMenu2;
	// 모두 닫기
	for(i=1;i<6;i++){	// 갯수 셋팅
		tabMenu1 = eval("document.all.m"+i+".style");
		if(tabMenu1.display == "")
			tabMenu1.display = "none";
	}
	// 1개만 펼침
	tabMenu2 = eval("document.all.m"+num+".style");
	tabMenu2.display = "";
}
//-----------------------------------------------------------------------------
// 연혁 탭 뷰 선택
//-----------------------------------------------------------------------------
function familyTabView(num){
	var tabMenu1;
	var tabMenu2;
	// 모두 닫기
	for(i=1;i<5;i++){	// 갯수 셋팅
		tabMenu1 = eval("document.all.m"+i+".style");
		if(tabMenu1.display == "")
			tabMenu1.display = "none";
	}
	// 1개만 펼침
	tabMenu2 = eval("document.all.m"+num+".style");
	tabMenu2.display = "";
}
//-----------------------------------------------------------------------------
// 연구.특허현황 탭
//-----------------------------------------------------------------------------
function devPaperTabView(num)
{
	var tabMenu1;
	var tabMenu2;
	// 모두 닫기
	for(i=1;i<4;i++)	// 갯수 셋팅
	{
		tabMenu1 = eval("document.all.m"+i+".style");
		if(tabMenu1.display == "")
				tabMenu1.display = "none";
	}
	// 1개만 펼침
	tabMenu2 = eval("document.all.m"+num+".style");
	tabMenu2.display = "";
}
//-----------------------------------------------------------------------------
// 제품소개
//-----------------------------------------------------------------------------
// 상세 내용 인쇄
function ProductDetailDPrint(rCode, imgXD){
	var objWin = window.open('','ProductDetailDPrint','top=0,left=0,width='+imgXD+',height=1000'+
		',toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizeble=no');
	objWin.focus();
	objWin.document.open();
	objWin.document.write('<'+'html'+'><'+'body bottommargin=0 leftmargin=0 rightmargin=0 topmargin=0'+'>');
	objWin.document.write('<'+'img'+' src=/upload/PRODUCTS/'+rCode+'_D.jpg'+' height=1050 />');	// 한페이지에 모두 인쇄
	objWin.document.write('<'+'/body'+'><'+'/html'+'>');
	objWin.document.close();
	objWin.print();
	objWin.close();
}
// 영양성분표 보기
function ProductDetailSView(){
	if($("#DetailSDiv").is(":hidden")){
		$("#DetailSDiv").css("top",eval(document.body.scrollTop) + 20);
		$("#DetailSDiv").css("left",250);
		$("#DetailSDiv").show();
	}else{
		$("#DetailSDiv").hide();
	}
}
//-----------------------------------------------------------------------------
// 홍보센터
//-----------------------------------------------------------------------------
// 베지밀 스토리 열기
function PopVegemilStory(){
	window.open("/main/publicCenter/vegemilStoryFrame.aspx","VegemilStory","fullscreen=yes,width=800,height=600,top=0,left=0"+
	",marginwidth=0,marginheight=0,toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=no");
}
function VSClose(){
	self.close();
}
// AD 플레이

function AdFlvPlay(flvFile, flvContent,subject, tabid){


  
  $('#labEtcList').html(subject);
  $('#ETCdownURL').attr("href" ,"http://www.vegemil.co.kr/Upload/PUBLIC/" + flvFile); 
  
  $('#ETCcfTabs ul li').removeClass('active');
  $('#ETCcfTabs ul li[id=' + tabid + ']').addClass('active');

}


/*
function AdFlvPlay(flvFile, flvContent){
	$("#FrameAdFlv").attr("src", "/main/publicCenter/advertPlayerFrame.aspx?f="+flvFile+"&s="+flvContent);
}
*/


// TV-CF 플레이
function CfFlvPlay(flvFile, flvSubject, flvDate){
	$("#FrameCfFlv").attr("src","/main/publicCenter/cfPlayerFrame.aspx?f="+flvFile+"&s="+flvSubject+"&d="+flvDate);
}
// AD 동영상 퍼가기 보이기
function AdFlvCopyView(flvFile, flvContent) {
	var cd = document.getElementById('CopyDiv');
	var ca = document.getElementById('CopyArea');
	var leftsize = GetXYLeft("btnCopy");
	cd.style.left = eval(leftsize)-400+"px";
	var topsize = GetXYTop("btnCopy");
	cd.style.top = eval(topsize)-335+"px";
	cd.style.display = "block";
	ca.value = "<iframe id='FrameFlv' name='FrameFlv' width='525' height='488' frameborder='0' scrolling='no' src='/main/publicCenter/advertPlayerFrame.aspx?f="+flvFile+"&s="+flvContent+"'></iframe>";
	ca.focus;
}
// CF 동영상 퍼가기 보이기
function CfFlvCopyView(flvFile, flvSubject, flvDate) {
	var cd = document.getElementById('CopyDiv');
	var ca = document.getElementById('CopyArea');
	var leftsize = GetXYLeft("btnCopy");
	cd.style.left = eval(leftsize)-400+"px";
	var topsize = GetXYTop("btnCopy");
	cd.style.top = eval(topsize)-335+"px";
	cd.style.display = "block";
	ca.value = "<iframe id='FrameFlv' name='FrameFlv' width='525' height='488' frameborder='0' scrolling='no' src='/main/publicCenter/cfPlayerFrame.aspx?f="+flvFile+"&s="+flvSubject+"&d="+flvDate+"'></iframe>";
	ca.focus;
}
// AD, CF 동영상 퍼가기 숨김
function FlvCopyHide() {
	$("#CopyArea").val("");
	$("#CopyDiv").hide();
}
// 인쇄광고 이미지 팝업
function MediaPrintView(idx){
	$("#OrgSizeDiv").css("top",0);
	//$("#OrgSizeDiv").css("left",350);
	$("#OrgSizeDiv").css("left",350);
	$("#OrgSizeDiv").show();
	$("#FrameImg").attr("src","/main/publicCenter/mediaPrintFrame.aspx?i="+idx);
}
// 인쇄광고 팝업 닫기
function MediaPrintClose(){
	$("#FrameImg").attr("src","about:blank");
	$("#OrgSizeDiv").hide();
}
//-----------------------------------------------------------------------------
// 웹진
//-----------------------------------------------------------------------------
// 웹진 팝업
function WebzinePopup(){
	window.open("/main/publicCenter/webzinePop.aspx","Webzine","width=1014,height=708,top=0,left=0"+
	",marginwidth=0,marginheight=0,toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=no");
}
// 목차
function SetFlashVariable_List(){
	var goPage = 2;
	var wz = document.getElementById("WebZineID");
	wz.SetVariable("gof", eval(goPage));			// 현재 페이지 값 넘겨줌
	wz.SetVariable("NP", "");
	$("#webzinePage").val(goPage);
}
// 처음
function SetFlashVariable_First(){
	var goPage = 1;
	var wz = document.getElementById("WebZineID");
	wz.SetVariable("gof", eval(goPage));			// 현재 페이지 값 넘겨줌
	wz.SetVariable("NP", "");
	$("#webzinePage").val(goPage);
}
// 이전
function SetFlashVariable_Prev(){
	var goPage = eval($("#webzinePage").val());	// 현재페이지
	var wz = document.getElementById("WebZineID");
	if(goPage <= 1)	{
		goPage = 1;
		wz.SetVariable("gof", eval(goPage));		// 현재 페이지 값 넘겨줌
		wz.SetVariable("NP", "");
		$("#webzinePage").val(goPage);
	}else{
		wz.SetVariable("gof", eval(goPage));		// 현재 페이지 값 넘겨줌
		wz.SetVariable("NP", "prev");
		$("#webzinePage").val(goPage - 1);
	}
}
// 다음
function SetFlashVariable_Next(){
	var goPage = eval($("#webzinePage").val());	// 현재페이지
	var wz = document.getElementById("WebZineID");
	if(goPage >= eval($("#nowPage").val()))	{
		goPage = eval($("#nowPage").val());
		wz.SetVariable("gof", eval(goPage));		// 현재 페이지 값 넘겨줌
		wz.SetVariable("NP", "");
		$("#webzinePage").val(goPage);
	}else{
		wz.SetVariable("gof", eval(goPage));		// 현재 페이지 값 넘겨줌
		wz.SetVariable("NP", "next");
		$("#webzinePage").val(goPage + 1);
	}
}
// 마지막
function SetFlashVariable_Last(){
	var goPage = eval($("#nowPage").val());
	var wz = document.getElementById("WebZineID");
	wz.SetVariable("gof", eval(goPage));			// 현재 페이지 값 넘겨줌
	wz.SetVariable("NP", "");
	$("#webzinePage").val(goPage);
}
// 바로가기
function SetFlashVariable_Go(){
	var goPage = eval($("#webzinePage").val());	// 현재페이지
	if(goPage < 1)	goPage = 1;
	if(goPage > eval($("#nowPage").val()))
		goPage = eval($("#nowPage").val());
	var wz = document.getElementById("WebZineID");
	wz.SetVariable("gof", eval(goPage));			// 현재 페이지 값 넘겨줌
	wz.SetVariable("NP", "");
	$("#webzinePage").val(goPage);
}
// 플래시에서 보낼때...
// getURL("javascript:getValue('gof값')");

// 플래시에서 값 받기
function getValue(flashPage){
	$("#webzinePage").val(flashPage);
}
// 로딩 컨트롤
function Loading_hidden(){
	$("#LOAD_DIV").hide();
}
//-----------------------------------------------------------------------------
// 회원 관련
//-----------------------------------------------------------------------------
// IPIN 확인
function JoinIpinCheck(){
	
	var IpinOpenOk = window.open("/Main/include/ipinRequest.aspx","IPINWindow","top=100,left=300,width=450,height=500");
	if(IpinOpenOk == null){
		alert(" ※ 윈도우 XP SP2 또는 인터넷 익스플로러 7 사용자일 경우에는 \n    화면 상단에 있는 팝업 차단 알림줄을 클릭하여 팝업을 허용해 주시기 바랍니다. \n\n※ MSN,야후,구글 팝업 차단 툴바가 설치된 경우 팝업허용을 해주시기 바랍니다.");
	}
}

// 휴대폰 인증 확인
function JoinHPCheck(){
	
	
	var HPOpenOk = window.open("/Main/include/hpRequest.aspx", 'PCCWindow', 'width=430, height=660, resizable=1, scrollbars=no, status=0, titlebar=0, toolbar=0, left=300, top=200' );
	if(HPOpenOk == null){
		alert(" ※ 윈도우 XP SP2 또는 인터넷 익스플로러 7 사용자일 경우에는 \n    화면 상단에 있는 팝업 차단 알림줄을 클릭하여 팝업을 허용해 주시기 바랍니다. \n\n※ MSN,야후,구글 팝업 차단 툴바가 설치된 경우 팝업허용을 해주시기 바랍니다.");
	}
	
}

// 휴대폰 인증 확인

function JoinHP_Check(){
	
	//alert('hhh');
	var HPOpenOk = window.open("/Main/event/hpRequest2.aspx", 'PCCWindow', 'width=430, height=660, resizable=1, scrollbars=no, status=0, titlebar=0, toolbar=0, left=300, top=200' );
	if(HPOpenOk == null){
		alert(" ※ 윈도우 XP SP2 또는 인터넷 익스플로러 7 사용자일 경우에는 \n    화면 상단에 있는 팝업 차단 알림줄을 클릭하여 팝업을 허용해 주시기 바랍니다. \n\n※ MSN,야후,구글 팝업 차단 툴바가 설치된 경우 팝업허용을 해주시기 바랍니다.");
	}
	
}


// 아이디 중복확인
function JoinIdCheck(oId){
	PopZipcodeClose();
	
	var topsize = GetXYTop(oId);
	var leftsize = GetXYLeft(oId);
	$("#IdCheckDiv").css("top",eval(topsize) + 22);
	$("#IdCheckDiv").css("left",eval(leftsize) - 50);

	$("#txtId").val('');
	$("#IdCheckFrame").attr("src","/main/member/joinIdCheck.aspx");
	$("#IdCheckDiv").show();
}
function JoinIdCheckForEday(oId){
	PopZipcodeClose();
	
	var var_edayYn = "0";
	var_edayYn = document.getElementById('edayYn').value;
	
	var topsize = GetXYTop(oId);
	var leftsize = GetXYLeft(oId);
	$("#IdCheckDiv").css("top",eval(topsize) + 22);
	$("#IdCheckDiv").css("left",eval(leftsize) - 50);
	
	$("#txtId").val('');
	$("#IdCheckFrame").attr("src","/main/member/joinIdCheck.aspx?edayYn=" + var_edayYn + "");
	$("#IdCheckDiv").show();
}
// 우편번호 검색
function JoinZipcode(oId){
	$("#IdCheckFrame").attr("src","about:blank");
	$("#IdCheckDiv").hide();
	
	var topsize = GetXYTop(oId);
	var leftsize = GetXYLeft(oId);
	$("#ZipcodeDiv").css("top",eval(topsize) + 22);
	$("#ZipcodeDiv").css("left",eval(leftsize) - 50);
	
	PopZipcode();
}
// ID 중복확인
function IdCheckClose(f){
	if(f == 1){
		window.parent.$("#txtId").val($("#txtId").val());
	}else{
		window.parent.$("#txtId").val("");
	}
	window.parent.$("#IdCheckDiv").hide();
	window.parent.$("#IdCheckFrame").attr("src","about:blank");
	window.parent.$("#txtPwd1").focus();
}
// 우편번호 검색
function EditZipcode(oName)
{
	var topsize = GetXYTop(oName.id);
	var leftsize = GetXYLeft(oName.id);
	$("#ZipcodeDiv").css("top",eval(topsize) + 22);
	$("#ZipcodeDiv").css("left",eval(leftsize) - 50);
	PopZipcode();
}

// 도로명주소 API 사용 검색
function EditZipcodeNew()
{
	var pop = window.open("/Main/include/jusoPopup.asp","pop","width=570,height=420, scrollbars=yes, resizable=yes"); 
}

function EditZipcodeNew2()
{
	var pop = window.open("/Main/include/jusoPopup2.asp","pop","width=570,height=420, scrollbars=yes, resizable=yes"); 
}
//-----------------------------------------------------------------------------
// CP
//-----------------------------------------------------------------------------
// 자율준수 메세지
function DeclarMsgOpen(no){
	switch(no){
		case "1": $("#DeclarImg").attr("src","/main/images/cp/detail0106.jpg"); break;
		case "2": $("#DeclarImg").attr("src","/main/images/cp/detail0107.jpg"); break;
		case "3": $("#DeclarImg").attr("src","/main/images/cp/detail0109.jpg"); break;
		case "4": $("#DeclarImg").attr("src","/main/images/cp/detail0110.jpg"); break;
		case "5": $("#DeclarImg").attr("src","/main/images/cp/detail0111.jpg"); break;
		case "6": $("#DeclarImg").attr("src","/main/images/cp/detail0112.jpg"); break;
		case "6": $("#DeclarImg").attr("src","/main/images/cp/detail0113.jpg"); break;
		default : $("#DeclarImg").attr("src","/main/images/cp/detail0113.jpg"); break;	// 초기 화면
	}
}
// 자율준수 운형현황
function ManagerView(no){
	if(no == "m1"){
		$("#m1").show();
		$("#m2").hide();
	}
	if(no == "m2"){
		$("#m2").show();
		$("#m1").hide();
	}
}
// EBook 스크립트
function CpOpenEbook(uri,page){
	window.open("/main/cp/cpEbookView.aspx?uri="+uri+"&page="+page,"NewWindow",
		"left=0,top=0,toolbar=no,location=no,directories=no,status=no,fullscreen=no,menubar=no,scrollbars=no,resizable=yes"+
		",width=1014,height=730");
}
// CP 자료실
function CpPdsOpen1(){
	$("#PdsDiv").show();
	$("#labTitle").text("정지기 CP교육 자료");
	$("#PdsFrame").attr("src", "/main/cp/cpPdsFrame.aspx?c=1");
	$("#PdsFrame").focus();
}
function CpPdsOpen2(){
	$("#PdsDiv").show();
	$("#labTitle").text("CP운용 보고서");
	$("#PdsFrame").attr("src","/main/cp/cpPdsFrame.aspx?c=2");
	$("#PdsFrame").focus();
}
function CpPdsClose(){	
	$("#PdsFrame").attr("src","about:blank");
	$("#PdsDiv").hide();
}
// 이메일 선택
function ClaimEmailChange(dName)
{
	if(document.getElementById(dName).options[document.getElementById(dName).selectedIndex].value == "0" ||
		document.getElementById(dName).options[document.getElementById(dName).selectedIndex].value == "9")
		$("#txtEmail2").show();
	else
		$("#txtEmail2").hide();
}
//-----------------------------------------------------------------------------
// 이벤트
//-----------------------------------------------------------------------------
// 현재 진행중인 이벤트 페이지 셋팅
function EventPageSet(){
	// 1.응모진행
	//$("#OrderDiv").hide();
	//$("#InputDiv").show();
	//$("#txtCode").focus();
	
	// 2.응모마감
	//alert("감사합니다.\n이벤트 응모가 마감되었습니다.\n\n* 당첨자 발표 : 2013-06-07");
	//top.location.href = "/main/event/list.aspx";

	// 3.당첨자발표
	$("#InputDiv").hide();
	$("#OrderDiv").show();
	$("#OrderFrame").attr("src","/main/event/e20130415_Mart_Order.htm");
	alert("당첨자 리스트를 확인하시기 바랍니다.");
	$("#OrderFrame").focus();
}
//-----------------------------------------------------------------------------
// V컨슈머피아
//-----------------------------------------------------------------------------
// 통합게시판 왼쪽 메뉴 선택
/*
function CyberBoardLeftMenu(){
	var params = GetQueryStrings();	// 스크립트로 값 받기
	var bcate = params["c"];		// flv 파일명
	if(typeof(bcate) != "undefined"){
		var menuM = "";
		var menuS = "";
		switch(bcate) {	// 메뉴번호
			case "00": menuM="1"; menuS="1"; break;	// 공지사항
			case "01": menuM="2"; menuS="1"; break;	// 쫑알쫑알 수다
			case "02": menuM="2"; menuS="2"; break;	// 맛있는 레시피
			case "04": menuM="1"; menuS="2"; break;	// 따끈한 소식
			case "07": menuM="4"; menuS="1"; break;	// 궁금해요
			case "08": menuM="2"; menuS="3"; break;	// 공감을 나눠요
			default  : menuM=""; menuS=""; break;
		}
		LeftMenuSelected(menuM, menuS);
	}
}
*/
// 진도에디터 스킨 적용 (마지막에 실행)
function CyberTextEditSkin(){
	nhn.husky.EZCreator.createInIFrame({
		oAppRef: oEditors,
		elPlaceHolder: "ir1",
		sSkinURI: "/main/SE/SmartEditor2Skin_CyberBoardWrite.html",
		fCreator: "createSEditor2"
	});
}
// 진도에디터 콘텐츠 저장
function CyberTextEditSave(){
	// 에디터의 내용을 에디터 생성시에 사용했던 textarea에 넣어 줍니다.
	oEditors.getById["ir1"].exec("UPDATE_CONTENTS_FIELD", []);
	// 에디터의 내용에 대한 값 검증은 이곳에서 document.getElementById("ir1").value를 이용해서 처리하면 됩니다.
	try{
		// 이 라인은 현재 사용 중인 폼에 따라 달라질수 있습니다.
		document.Form1.submit();
	}
	catch(e){}
}
//-----------------------------------------------------------------------------f
// Flash 로딩
//-----------------------------------------------------------------------------
// FlashWrite(아이디, 파일경로, 가로, 세로[, 변수][,배경색][,윈도우모드])

function FlashWrite(id,url,w,h,vars,bg,win){


	if(id == null){
		id = url.split("/")[url.split("/").length-1].split(".")[0];	// id가 없을때 파일명으로 설정
		if(document.forms.length > 0){id = document.forms[0].id;}
	}
	if(vars == null)	{vars='';}
	if(bg == null)		{bg='#FFFFFF';}
	if(win == null)		{win='transparent';}
	var FlashStr= "<OBJECT classid='clsid:d27cdb6e-ae6d-11cf-96b8-444553540000'";
		FlashStr+="	codebase='https://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=9,0,0,0'";
		FlashStr+="	width='"+w+"'";
		FlashStr+="	height='"+h+"'";
		FlashStr+="	id='"+id+"'";
		FlashStr+="	align='middle'>";
		FlashStr+="<PARAM name='allowScriptAccess' value='always' />";
		FlashStr+="<PARAM name='movie' value='"+url+"' />";
		FlashStr+="<PARAM name='FlashVars' value='"+vars+"' />";
		FlashStr+="<PARAM name='wmode' value='transparent' />";
		FlashStr+="<PARAM name='menu' value='-1' />";
		FlashStr+="<PARAM name='quality' value='high' />";
		FlashStr+="<PARAM name='bgcolor' value='"+bg+"' />";
		FlashStr+="<PARAM name='loop' value='-1' />";
		FlashStr+="<EMBED src='"+url+"'";
		FlashStr+="	flashVars='"+vars+"'";
		FlashStr+="	wmode='transparent'";
		FlashStr+="	autostart='-1'";
		FlashStr+="	menu='-1'";
		FlashStr+="	quality='high'";
		FlashStr+="	bgcolor='"+bg+"'";
		FlashStr+="	width='"+w+"'";
		FlashStr+="	height='"+h+"'";
		FlashStr+="	id='"+id+"Embed'";
		FlashStr+="	name='"+id+"Embed'";
		FlashStr+="	align='middle'";
		FlashStr+="	allowScriptAccess='always'";
		FlashStr+="	type='application/x-shockwave-flash'";
		FlashStr+="	pluginspage='http://www.microsoft.com/windows/mediaplay/download/default.asp' alt='flash'/>";
		FlashStr+="</OBJECT>";
	document.write(FlashStr);
	
	
}


function MP4Write(id,url,w,h,vars,bg,win){

	if(id == null){
		id = url.split("/")[url.split("/").length-1].split(".")[0];	// id가 없을때 파일명으로 설정
		if(document.forms.length > 0){id = document.forms[0].id;}
	}
	if(vars == null)	{vars='';}
	if(bg == null)		{bg='#FFFFFF';}
	if(win == null)		{win='transparent';}
	var FlashStr= "<video  controls>";
		FlashStr+="<source src='/Upload/PUBLIC/AD_20131017_40.mp4' type='video/mp4'>";
		FlashStr+="</video>";
		
		
	document.write(FlashStr);
	

}


//-----------------------------------------------------------------------------
// javascript 로 URL querystring 받기
//-----------------------------------------------------------------------------
/*
function GetQueryStrings(){ 
	var assoc = {};
	var decode = function(s){return decodeURIComponent(s.replace(/\+/g, " "));};
	var queryString = location.search.substring(1); 
	var keyValues = queryString.split('&'); 
	for(var i in keyValues){ 
		var key = keyValues[i].split('=');
		if (key.length > 1){
			assoc[decode(key[0])] = decode(key[1]);
		}
	}
	return assoc;
}
*/
//-----------------------------------------------------------------------------
// 쿠키 사용
//-----------------------------------------------------------------------------
function CookieRead(cookieName){
	var thisCookie = document.cookie.split("; ");
	for(i=0; i<thisCookie.length; i++){
		if(cookieName == thisCookie[i].split("=")[0]){
			return thisCookie[i].split("=")[1];
		}
	}
	return "x";
}
function CookieSave(name, value, expiredays){ 
	var todayDate = new Date(); 
	todayDate.setDate( todayDate.getDate() + expiredays ); 
	document.cookie = name + "=" + escape( value ) + "; path=/; expires=" + todayDate.toGMTString() + ";" 
}

//-----------------------------------------------------------------------------
// 입력폼 텍스트 바이트 계산
//-----------------------------------------------------------------------------
function realCharCount(tName, nName, mName, maxByte){
	var strCount = 0;
	var tempStr, tempStr2; 
	for(i = 0;i < document.getElementById(tName).innerHTML.length;i++){
		tempStr = document.getElementById(tName).innerHTML.charAt(i);
		if(escape(tempStr).length > 4) {strCount += 2;}
		else {strCount += 1;}
	}
	if (strCount > maxByte){
		alert("최대 " + maxByte + "Byte 이므로 초과된 글자는 자동으로 삭제됩니다.");  
		strCount = 0;  
		tempStr2 = "";
		for(i = 0; i < document.getElementById(tName).innerHTML.length; i++){
			tempStr = document.getElementById(tName).innerHTML.charAt(i); 
			if(escape(tempStr).length > 4) {strCount += 2;}
			else {strCount += 1;}
			if(strCount > maxByte){
				if(escape(tempStr).length > 4){strCount -= 2;}
				else {strCount -= 1;}
				break;
			}
			else{
				tempStr2 += tempStr;
			}
		}
		document.getElementById(tName).innerHTML = tempStr2;
	}
	document.getElementById(nName).innerHTML = strCount;
	document.getElementById(mName).innerHTML = maxByte;
}

//-----------------------------------------------------------------------------
// 객체 좌표값 추출 함수
// obj : 측정할 객체
//-----------------------------------------------------------------------------
function getBounds(obj)
{
	var name = navigator.appName;
	var ver = navigator.appVersion;
	var ver_int = parseInt(navigator.appVersion);
	var ua = navigator.userAgent, infostr;
	
	var ret = new Object();
    if(name == "Microsoft Internet Explorer"){
		// IE 일때
		var rect = obj.getBoundingClientRect();
		ret.left = rect.left + (document.documentElement.scrollLeft || document.body.scrollLeft);
		ret.top = rect.top + (document.documentElement.scrollTop || document.body.scrollTop);
		ret.width = rect.right - rect.left;
		ret.height = rect.bottom - rect.top;
	}else{
		// IE가 아닐때
		//var box = document.getBoxObjectFor(obj);
		var box = obj.getBoundingClientRect();
		
		ret.left = box.left + (document.documentElement.scrollLeft || document.body.scrollLeft);
		ret.top = box.top + (document.documentElement.scrollTop || document.body.scrollTop);
		ret.width = box.right - box.left;
		ret.height = box.bottom - box.top;
		//ret.left = box.left;
		//ret.top = box.top;
		//ret.width = box.width;
		//ret.height = box.height;
		
	}
	return ret;
}
// 객체 LEFT 좌표
function GetXYLeft(n){
	var obj = getBounds(document.getElementById(n));
	return obj.left;
}
// 객체 TOP 좌표
function GetXYTop(n){
	var obj = getBounds(document.getElementById(n));
	return obj.top;
}
// 객체 WIDTH 좌표
function GetXYWidth(n){
	var obj = getBounds(document.getElementById(n));
	return obj.width;
}
// 객체 HEIGHT 좌표
function GetXYHeight(n){
	var obj = getBounds(document.getElementById(n));
	return obj.height;
}

function resize(img) {
			// 원본 이미지 사이즈 저장
			var width = img.width;
			var height = img.height;
    
			// 가로, 세로 최대 사이즈 설정
			var maxWidth = 550;
			var maxHeight = 540;
			var resizeWidth;
			var resizeHeight;
    
			// 이미지 비율 구하기
			var basisRatio = maxHeight / maxWidth;
			var imgRatio = height / width;

			if (imgRatio > basisRatio) {
			// height가 기준 비율보다 길다.
        
				if (height > maxHeight) {
					resizeHeight = maxHeight;
					resizeWidth = Math.round((width * resizeHeight) / height);
				} else {
					resizeWidth = width;
						resizeHeight = height;
				}
			} else if (imgRatio < basisRatio) {
				// width가 기준 비율보다 길다.
        
				if (width > maxWidth) {
					resizeWidth = maxWidth;
					resizeHeight = Math.round((height * resizeWidth) / width);
				} else {
					resizeWidth = width;
					resizeHeight = height;
				}
        
			} else {
				// 기준 비율과 동일한 경우
				resizeWidth = width;
				resizeHeight = height;
			}
    
			// 리사이즈한 크기로 이미지 크기 다시 지정
			img.width = resizeWidth;
			img.height = resizeHeight;
		}


function  fsOnLoad(frm){
	
	var now = new Date();
	var month = now.getMonth();
	var year = now.getFullYear();
	var yy = frm.searchControl1_year;
	yy.length = 10;
	
	for(var i=0; i<yy.length;i++){
		yy.options[i].value=year+3-i;
		yy.options[i].text=year+3-i;
	
		/*
		if( year == year +1){
			yy.options[i].selected =true;
		}
		*/
		
	}
	
	
	var mm = frm.searchControl1_month;
	mm.length =12;
	
	for(var x=0;x<mm.length;x++){
		mm.options[x].value=x+1;
		mm.options[x].text=x+1;
		
		/*
		if(month+1==x+1){
			mm.options[x].selected=true;
		}
		
		if(month==x+1){
			mm.options[x].selected=true;
		}
		*/
	}
	
	dayChange(frm);
}

function dayChange(frm){
	
	var dd = frm.searchControl1_day;
	var now = new Date();
	var date = now.getDate();
	var dArr = ['0','31','29','31','30','31','30','31','31','30','31','30','31'];
	dd.length = parseInt(dArr[frm.searchControl1_month.value]);
	
	var gYear = parseInt(frm.searchControl1_year.value);
	
	if(dd.length == 29){
		if((gYear%4 ==0 && gYear%100 !=0)||(gYear%400==0)){
			dd.length;
		}else{
			--dd.length;
		}
	}
	
	for(var y=0; y < dd.length; y++){
		dd.options[y].value=y+1;
		dd.options[y].text=y+1;
		
		/*
		if(date == y+1){
			dd.options[y].selected=true;
			//dd.options[y].value = y+1;
			
		}
		*/
	}
	
	
}
 
/*리퀘스트 값 받아오기*/
function Request(valuename) //javascript로 구현한 Request 
{ 
	var rtnval = ""; 
	var nowAddress = unescape(location.href); 
	var parameters = (nowAddress.slice(nowAddress.indexOf("?")+1,nowAddress.length)).split("&"); 

	for(var i = 0 ; i < parameters.length ; i++){ 
		var varName = parameters[i].split("=")[0]; 
		if(varName.toUpperCase() == valuename.toUpperCase()) { 
			rtnval = parameters[i].split("=")[1]; 
			break; 
		} 
	} 

	return rtnval; 
}
